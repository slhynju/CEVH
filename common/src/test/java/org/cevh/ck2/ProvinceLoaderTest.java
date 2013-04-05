package org.cevh.ck2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.cevh.ck2.ProvinceLoader;
import org.cevh.ck2.ProvinceLoaderImpl;
import org.cevh.domain.Province;
import org.junit.Assert;
import org.junit.Test;

public class ProvinceLoaderTest {

	@SuppressWarnings("static-method")
	@Test
	public void testLoad() {
		ProvinceLoader loader = new ProvinceLoaderImpl();
		Path gameRoot = Paths
				.get("G:/Steam/steamapps/common/crusader kings ii");
		List<Province> provinces = loader.load(gameRoot);
		Assert.assertEquals(924, provinces.size());
	}
}