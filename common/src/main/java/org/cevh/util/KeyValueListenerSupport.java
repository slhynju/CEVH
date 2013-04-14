package org.cevh.util;

import java.util.List;
import java.util.StringTokenizer;

import org.jewel.util.StringUtil;
import org.jewel.util.collection.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class KeyValueListenerSupport<T> implements KeyValueListener {

	protected final T save;

	protected final Logger log;

	public KeyValueListenerSupport(T save) {
		this.save = save;
		log = LoggerFactory.getLogger(this.getClass());
	}

	public static final String getFirstKey(String fullKey) {
		String[] pair = StringUtil.splitPair(fullKey, ".");
		return pair[0];
	}
	
	public static final List<String> splitFullKey(String fullKey) {
		StringTokenizer tokenizer = new StringTokenizer(fullKey,".");
		return CollectionUtil.toList(tokenizer);
	}

}
