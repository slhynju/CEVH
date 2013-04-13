package org.cevh.util;

import java.nio.file.Path;

public interface SaveLoader {

	public static final String ANONYMOUS_KEY = "#";
	
	public static final String DATE_FORMAT = "yyyy.M.d";

	public void load(Path path);

	public void addKeyValueListener(KeyValueListener listener);

	public void addBlockListener(BlockListener listener);
	
	public void addArrayListener(ArrayListener listener);
	
	public void clearListeners();
}
