package org.cevh.util;

import java.nio.file.Path;
import java.util.List;

import org.jewel.util.collection.KeyValue;

public interface SaveLoader {

	public List<KeyValue<String, Object>> load(Path path);

	public void markProperty(String... properties);
}
