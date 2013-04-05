package org.cevh.ck2;

import java.nio.file.Path;
import java.util.List;

import org.cevh.domain.Province;

public interface ProvinceLoader {

	public List<Province> load(Path gameRoot);

}
