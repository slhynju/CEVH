package org.cevh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

import org.cevh.CevhException;
import org.jewel.util.FreeStringBuilder;
import org.jewel.util.StringUtil;
import org.jewel.util.collection.CollectionUtil;
import org.jewel.util.io.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveLoaderImpl implements SaveLoader {

	private final Logger log;

	private final List<KeyValueListener> kvListeners;

	private final List<BlockListener> blockListeners;

	private final List<ArrayListener> arrayListeners;

	private final Deque<List<BlockListener>> blockListenerStack;

	private final Deque<String> keyStack;

	private State state;

	public SaveLoaderImpl() {
		this.log = LoggerFactory.getLogger(SaveLoaderImpl.class);
		kvListeners = new ArrayList<>();
		blockListeners = new ArrayList<>();
		arrayListeners = new ArrayList<>();
		blockListenerStack = new ArrayDeque<>();
		keyStack = new ArrayDeque<>();
		state = State.KV_EXPECTED;
	}

	@Override
	public void addKeyValueListener(KeyValueListener listener) {
		kvListeners.add(listener);
	}

	@Override
	public void addBlockListener(BlockListener listener) {
		blockListeners.add(listener);
	}

	@Override
	public void addArrayListener(ArrayListener listener) {
		arrayListeners.add(listener);
	}

	@Override
	public void clearListeners() {
		kvListeners.clear();
		blockListeners.clear();
		arrayListeners.clear();
	}

	@Override
	public void load(Path path) {
		long startTime = System.currentTimeMillis();
		if (log.isInfoEnabled()) {
			log.info("Loading save file {} ...", path.toString());
		}
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
		if (log.isInfoEnabled()) {
			long endTime = System.currentTimeMillis();
			log.info("File {} is loaded in {} ms.", path.toString(), endTime
					- startTime);
		}
	}

	private void init() {
		blockListenerStack.clear();
		keyStack.clear();
		state = State.KV_EXPECTED;
	}

	private void processLine(String line) {
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

	private void processKV(String line) {
		String[] pair = StringUtil.splitPair(line, "=");
		String localKey = pair[0];
		String valueStr = pair[1];
		String fullKey = buildFullKey(localKey);
		for (KeyValueListener listener : kvListeners) {
			listener.onKeyValue(localKey, fullKey, keyStack.size() + 1,
					valueStr);
		}
	}

	private static boolean isKey(String line) {
		int index = line.indexOf('=');
		if (index == -1) {
			return false;
		}
		return index == line.length() - 1;
	}

	private void processKey(String line) {
		String[] pair = StringUtil.splitPair(line, "=");
		String localKey = pair[0];
		keyStack.push(localKey);
		state = State.BLOCK_EXPECTED;
	}

	private static boolean isBlockStart(String line) {
		return "{".equals(line);
	}

	private void startBlock() {
		if (state == State.KV_EXPECTED) {
			keyStack.push(ANONYMOUS_KEY);
		} else {
			state = State.KV_EXPECTED;
		}
		String localKey = keyStack.peek();
		String fullKey = buildFullKey();
		int fullKeySize = keyStack.size();
		BlockListenerMatcher matcher = new BlockListenerMatcher(localKey,
				fullKey, fullKeySize);
		List<BlockListener> interestListeners = CollectionUtil.sub(
				blockListeners, matcher);
		blockListenerStack.push(interestListeners);
	}

	private static boolean isKeyAndBlockStart(String line) {
		String[] pair = StringUtil.splitPair(line, "=");
		String valueStr = pair[1];
		return "{".equals(valueStr);
	}

	private static boolean isBlockEnd(String line) {
		return "}".equals(line);
	}

	private void endBlock() {
		if (keyStack.isEmpty()) {
			// skip the end line of extra }
			return;
		}
		String fullKey = buildFullKey();
		int fullKeySize = keyStack.size();
		String localKey = keyStack.pop();
		List<BlockListener> interestListeners = blockListenerStack.pop();
		for (BlockListener listener : interestListeners) {
			listener.onBlockEnd(localKey, fullKey, fullKeySize);
		}
	}

	private static boolean isArrayAndBlockEnd(String line) {
		return line.length() > 1 && line.endsWith("}");
	}

	private void processArray(String arrayStr) {
		StringTokenizer tokenizer = new StringTokenizer(arrayStr, "  \t");
		List<String> list = CollectionUtil.toList(tokenizer);
		String localKey = keyStack.peek();
		String fullKey = buildFullKey();
		for (ArrayListener listener : arrayListeners) {
			listener.onArray(localKey, fullKey, list);
		}
	}

	private String buildFullKey() {
		FreeStringBuilder sb = new FreeStringBuilder();
		sb.appendStack(keyStack, "", ".", "");
		return sb.toS();
	}

	private String buildFullKey(String key) {
		if (keyStack.isEmpty()) {
			return key;
		}
		FreeStringBuilder sb = new FreeStringBuilder();
		sb.appendStack(keyStack, "", ".", "");
		sb.append('.');
		sb.append(key);
		return sb.toS();
	}

	private static enum State {
		KV_EXPECTED, BLOCK_EXPECTED
	}

}
