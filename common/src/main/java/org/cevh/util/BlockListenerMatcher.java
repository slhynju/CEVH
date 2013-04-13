package org.cevh.util;

import org.jewel.util.collection.Matcher;

public class BlockListenerMatcher implements Matcher<BlockListener> {

	private final String localKey;

	private final String fullKey;

	public BlockListenerMatcher(String localKey, String fullKey) {
		this.localKey = localKey;
		this.fullKey = fullKey;
	}

	@Override
	public boolean matches(BlockListener listener) {
		return listener.onBlockStart(localKey, fullKey);
	}

}
