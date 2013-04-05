package org.cevh.ck2;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.cevh.CevhException;
import org.cevh.domain.Province;
import org.jewel.JewelException;
import org.jewel.util.NumberUtil;
import org.jewel.util.StringUtil;
import org.jewel.util.io.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProvinceLoaderImpl implements ProvinceLoader {

	private final Logger log;

	public ProvinceLoaderImpl() {
		log = LoggerFactory.getLogger(ProvinceLoaderImpl.class);
	}

	@Override
	public List<Province> load(Path gameRoot) {
		List<Path> paths = listProvinceFiles(gameRoot);
		List<Province> provinces = new ArrayList<>(paths.size());
		for (Path path : paths) {
			Province p = readProvinceFile(path);
			provinces.add(p);
		}
		return provinces;
	}

	private List<Path> listProvinceFiles(Path gameRoot) {
		try {
			Path provincesPath = gameRoot.resolve("history").resolve(
					"provinces");
			return PathUtil.listAll(provincesPath, "glob:*.txt", log);
		} catch (JewelException | InvalidPathException | NullPointerException e) {
			StringBuilder sb = new StringBuilder(
					"Failed to read province files under ").append(
					gameRoot.toString()).append(". Please double check.");
			throw new CevhException(sb, e);
		}
	}

	private static Province readProvinceFile(Path path) {
		String fileName = path.getFileName().toString();
		String[] tokens = StringUtil.splitTokens(fileName, "-", ".");
		int id = NumberUtil.toInt(tokens[0], 0);
		return new Province(id, tokens[1]);
	}
}
