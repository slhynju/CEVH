package org.cevh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.cevh.CevhException;
import org.jewel.util.FreeStringBuilder;
import org.jewel.util.StringUtil;
import org.jewel.util.collection.CollectionUtil;
import org.jewel.util.collection.KeyValue;
import org.jewel.util.date.DateUtil;
import org.jewel.util.io.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveLoaderImpl implements SaveLoader {

	private final Logger log;

	private final Deque<Object> stack;

	private final Deque<String> propertyStack;

	private final DateFormat dateFormat;

	private final Set<String> markedProperties;

	@SuppressWarnings("unused")
	private int lineCounter;

	public SaveLoaderImpl() {
		this.log = LoggerFactory.getLogger(SaveLoaderImpl.class);
		stack = new ArrayDeque<>();
		propertyStack = new ArrayDeque<>();
		dateFormat = new SimpleDateFormat("yyyy.M.d");
		markedProperties = new HashSet<>();
		lineCounter = 0;
	}

	@Override
	public void markProperty(String... properties) {
		markedProperties.addAll(CollectionUtil.toSet(properties));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KeyValue<String, Object>> load(Path path) {
		init();
		try (BufferedReader reader = PathUtil.readTextFile(path,
				StandardCharsets.ISO_8859_1)) {
			String line = reader.readLine();
			while (line != null) {
				processLine(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			StringBuilder sb = new StringBuilder(
					"IOException when reading file ");
			sb.append(path).append('.');
			throw new CevhException(sb, e);
		}
		return (List<KeyValue<String, Object>>) stack.pop();
	}

	private void init() {
		stack.clear();
		propertyStack.clear();
		startBlock();
		lineCounter = 0;
	}

	@SuppressWarnings({ "unchecked" })
	private void startBlock() {
		Object top = stack.peek();
		List<Object> list = new ArrayList<>();
		stack.push(list);
		if (top instanceof List<?>) {
			List<Object> originalList = (List<Object>) top;
			originalList.add(list);
		}
		if (top instanceof KeyValue<?, ?>) {
			KeyValue<String, Object> originalKV = (KeyValue<String, Object>) top;
			originalKV.setValue(list);
		}
	}

	private void endBlock() {
		if (stack.size() == 1) {
			// skip the right brace at end of file.
			return;
		}
		stack.pop();
		Object top = stack.peek();
		if (top instanceof KeyValue<?, ?>) {
			stack.pop();
			propertyStack.pop();
		}
	}

	private void processLine(String line) {
		lineCounter++;
		line = StringUtil.trim(line);
		if (StringUtil.isEmpty(line)) {
			return;
		}
		if (isKV(line)) {
			processKV(line);
			return;
		}
		if (isKey(line)) {
			processKey(line);
			return;
		}
		if (isBlockStart(line)) {
			startBlock();
			return;
		}
		if (isKeyAndBlockStart(line)) {
			processKey(line);
			startBlock();
			return;
		}
		if (isBlockEnd(line)) {
			endBlock();
			return;
		}
		if (isArrayAndBlockEnd(line)) {
			String arrayStr = line.substring(0, line.length() - 1);
			processArray(arrayStr);
			endBlock();
			return;
		}
		processArray(line);
	}

	private static boolean isKV(String line) {
		String[] pair = StringUtil.splitPair(line, "=");
		String valueStr = pair[1];
		if (StringUtil.isEmpty(valueStr) || "{".equals(valueStr)) {
			return false;
		}
		return true;
	}

	private static boolean isKey(String line) {
		int index = line.indexOf('=');
		if (index == -1) {
			return false;
		}
		return index == line.length() - 1;
	}

	private static boolean isKeyAndBlockStart(String line) {
		String[] pair = StringUtil.splitPair(line, "=");
		String valueStr = pair[1];
		return "{".equals(valueStr);
	}

	private static boolean isBlockStart(String line) {
		return "{".equals(line);
	}

	private static boolean isBlockEnd(String line) {
		return "}".equals(line);
	}

	private static boolean isArrayAndBlockEnd(String line) {
		return line.length() > 1 && line.endsWith("}");
	}

	private void processKV(String line) {
		String[] pair = StringUtil.splitPair(line, "=");
		String key = pair[0];
		if (!isMarkedProperty(key)) {
			return;
		}
		KeyValue<String, Object> kv = new KeyValue<>(key);
		addKV(kv);
		String valueStr = pair[1];
		Object value = parseValue(valueStr);
		kv.setValue(value);
		if (log.isInfoEnabled()) {
			log.info("Marked property: {} = {}", buildPropertyName(key),
					valueStr);
		}
	}

	private void processKey(String line) {
		String[] pair = StringUtil.splitPair(line, "=");
		String key = pair[0];
		KeyValue<String, Object> kv = new KeyValue<>(key);
		if (isMarkedProperty(key)) {
			addKV(kv);
		}
		stack.push(kv);
		propertyStack.push(key);
	}

	private boolean isMarkedProperty(String key) {
		String propertyName = buildPropertyName(key);
		if (markedProperties.contains(propertyName)) {
			return true;
		}
		for (String markedProperty : markedProperties) {
			if (markedProperty.startsWith(propertyName)) {
				return true;
			}
		}
		return false;
	}

	private String buildPropertyName(String key) {
		FreeStringBuilder sb = new FreeStringBuilder();
		sb.appendStack(propertyStack, "", ".", "");
		if (!propertyStack.isEmpty()) {
			sb.append('.');
		}
		sb.append(key);
		return sb.toS();
	}

	@SuppressWarnings("unchecked")
	private void addKV(KeyValue<String, Object> kv) {
		List<Object> top = (List<Object>) stack.peek();
		top.add(kv);
	}

	@SuppressWarnings("unchecked")
	private void processArray(String arrayStr) {
		StringTokenizer tokens = new StringTokenizer(arrayStr, "  \t");
		List<Object> list = (List<Object>) stack.peek();
		while (tokens.hasMoreTokens()) {
			String elementStr = tokens.nextToken();
			Object element = parseValue(elementStr);
			list.add(element);
		}
	}

	private Object parseValue(String valueStr) {
		if (isString(valueStr)) {
			return parseString(valueStr);
		}
		if (isDate(valueStr)) {
			return parseDate(valueStr);
		}
		if (isBoolean(valueStr)) {
			return parseBoolean(valueStr);
		}
		if (isDouble(valueStr)) {
			return parseDouble(valueStr);
		}
		if (isLong(valueStr)) {
			return parseLong(valueStr);
		}
		return parseInt(valueStr);
	}

	private static boolean isString(String valueStr) {
		return valueStr.length() >= 2 && valueStr.charAt(0) == '"'
				&& valueStr.charAt(valueStr.length() - 1) == '"';
	}

	private static String parseString(String valueStr) {
		return valueStr.substring(1, valueStr.length() - 1);
	}

	private static boolean isDate(String valueStr) {
		int firstIndex = valueStr.indexOf(".");
		if (firstIndex == -1) {
			return false;
		}
		int lastIndex = valueStr.lastIndexOf(".");
		return firstIndex < lastIndex;
	}

	private Date parseDate(String valueStr) {
		return DateUtil.parse(valueStr, dateFormat);
	}

	private static boolean isBoolean(String valueStr) {
		return "yes".equals(valueStr) || "no".equals(valueStr);
	}

	private static Boolean parseBoolean(String valueStr) {
		if ("yes".equals(valueStr)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private static boolean isDouble(String valueStr) {
		int index = valueStr.indexOf(".");
		return index != -1;
	}

	private static Double parseDouble(String valueStr) {
		return Double.valueOf(valueStr);
	}

	private static boolean isLong(String valueStr) {
		return valueStr.length() >= 10;
	}

	private static Long parseLong(String valueStr) {
		return Long.valueOf(valueStr);
	}

	private static Integer parseInt(String valueStr) {
		return Integer.valueOf(valueStr);
	}

}
