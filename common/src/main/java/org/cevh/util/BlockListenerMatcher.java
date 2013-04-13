package org.cevh.util;

import org.jewel.util.collection.Matcher;

public class BlockListenerMatcher implements Matcher<BlockListener> {

	private final String localKey;

	private final String fullKey;

	private final int fullKeySize;

	public BlockListenerMatcher(String localKey, String fullKey, int fullKeySize) {
		this.localKey = localKey;
		this.fullKey = fullKey;
		this.fullKeySize = fullKeySize;
	}

	@Override
	public boolean matches(BlockListener listener) {
		return listener.onBlockStart(localKey, fullKey, fullKeySize);
	}

}
