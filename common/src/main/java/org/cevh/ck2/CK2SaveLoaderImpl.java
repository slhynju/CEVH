package org.cevh.ck2;

import java.nio.file.Path;

import org.cevh.domain.CK2Save;
import org.cevh.util.SaveLoader;
import org.cevh.util.SaveLoaderImpl;

public class CK2SaveLoaderImpl implements CK2SaveLoader {

	private final SaveLoader loader;

	public CK2SaveLoaderImpl() {
		loader = new SaveLoaderImpl();
	}

	@Override
	public CK2Save load(Path path) {
		CK2Save save = new CK2Save();
		save.setFileName(path.getFileName().toString());
		initLoader(save);
		loader.load(path);
		return save;
	}

	private void initLoader(CK2Save save) {
		loader.clearListeners();
		loader.addKeyValueListener(new GameVersionListener(save));
		loader.addKeyValueListener(new GameDateListener(save));
		loader.addKeyValueListener(new PlayerCharacterListener(save));
		loader.addKeyValueListener(new FlagListener(save));
		DynastyListener dynastyListener = new DynastyListener(save);
		loader.addKeyValueListener(dynastyListener);
		loader.addBlockListener(dynastyListener);
		CharacterListener characterListener = new CharacterListener(save);
		loader.addKeyValueListener(characterListener);
		loader.addBlockListener(characterListener);
		// TODO add more listeners
	}

}
