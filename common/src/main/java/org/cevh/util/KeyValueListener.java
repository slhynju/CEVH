package org.cevh.util;

public interface KeyValueListener {

	public void onKeyValue(String localKey, String fullKey, int fullKeySize,
			String valueStr);
}
