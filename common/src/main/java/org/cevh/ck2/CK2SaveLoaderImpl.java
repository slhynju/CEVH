package org.cevh.ck2;

import java.nio.file.Path;
import java.util.List;

import org.cevh.domain.CK2Save;
import org.cevh.util.SaveLoader;
import org.cevh.util.SaveLoaderImpl;
import org.jewel.util.collection.KeyValue;

public class CK2SaveLoaderImpl implements CK2SaveLoader {

	private final SaveLoader loader;

	private CK2Save save;

	public CK2SaveLoaderImpl() {
		loader = new SaveLoaderImpl();
		loader.markProperty("version", "date", "player.id");
		save = null;
	}

	@Override
	public CK2Save load(Path path) {
		List<KeyValue<String, Object>> list = loader.load(path);
		save = new CK2Save();
		save.setFileName(path.getFileName().toString());
		for (KeyValue<String, Object> kv : list) {
			processKV(kv);
		}
		return save;
	}

	private void processKV(KeyValue<String, Object> kv) {
		switch (kv.getKey()) {
		case "version":
			processVersion(kv);
			return;
		case "date":
			processDate(kv);
			return;
		case "player":
			processPlayer(kv);
			return;
		}
	}

	private void processVersion(KeyValue<String, Object> kv) {
		save.setVersion((String) kv.getValue());
	}

	private void processDate(KeyValue<String, Object> kv) {
		save.setDate((String) kv.getValue());
	}

	@SuppressWarnings("unchecked")
	private void processPlayer(KeyValue<String, Object> kv) {
		List<KeyValue<String,Object>> list = (List<KeyValue<String,Object>>) kv.getValue();
		for (KeyValue<String,Object> childKV : list) {
			processPlayerTree(childKV);
		}
	}

	private void processPlayerTree(KeyValue<String, Object> kv) {
		switch (kv.getKey()) {
		case "id":
			processPlayerID(kv);
			return;
		}
	}

	private void processPlayerID(KeyValue<String, Object> kv) {
		Integer playerCharacterId = (Integer) kv.getValue();
		save.setPlayerCharacterId(playerCharacterId.toString());
	}

}
