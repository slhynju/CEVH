package org.ceh.ck2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.ceh.domain.Province;
import org.ceh.util.SuffixFileVisitor;

public class ProvinceLoaderImpl implements ProvinceLoader {

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

	private static List<Path> listProvinceFiles(Path gameRoot) {
		try {
			Path provincesPath = gameRoot.resolve("history").resolve(
					"provinces");
			SuffixFileVisitor txtFileVisitor = new SuffixFileVisitor(".txt");
			Files.walkFileTree(provincesPath, txtFileVisitor);
			return txtFileVisitor.getPaths();
		} catch (IOException | InvalidPathException | NullPointerException e) {
			StringBuilder sb = new StringBuilder(
					"Failed to read province files under ").append(
					gameRoot.toString()).append(". Please double check.");
			throw new RuntimeException(sb.toString(), e);
		}
	}

	private static Province readProvinceFile(Path path) {
		String fileName = path.getFileName().toString();
		StringTokenizer tokenizer = new StringTokenizer(fileName, "-.");
		String idStr = StringUtils.trimToEmpty(tokenizer.nextToken());
		int id = 0;
		if (!idStr.isEmpty()) {
			id = Integer.parseInt(idStr);
		}
		String name = StringUtils.trimToEmpty(tokenizer.nextToken());
		return new Province(id, name);
	}

}
