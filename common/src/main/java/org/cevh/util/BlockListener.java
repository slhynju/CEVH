package org.cevh.util;

public interface BlockListener {

	public boolean onBlockStart(String localKey, String fullKey);
	
	public void onBlockEnd(String localKey, String fullKey);
}
