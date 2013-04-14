package org.cevh.ck2;

import java.nio.file.Path;
import java.util.List;

import org.cevh.domain.ck2.CK2Province;

public interface ProvinceLoader {

	public List<CK2Province> load(Path gameRoot);

}
