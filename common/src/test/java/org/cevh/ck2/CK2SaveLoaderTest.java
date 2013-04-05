package org.cevh.ck2;

import java.nio.file.Path;

import org.cevh.domain.CK2Save;
import org.jewel.util.io.PathUtil;
import org.junit.Assert;
import org.junit.Test;

public class CK2SaveLoaderTest {

	@SuppressWarnings("static-method")
	@Test
	public void testLoad() {
		CK2SaveLoaderImpl loader = new CK2SaveLoaderImpl();
		Path path = PathUtil.findResource("s1_1067.ck2");
		Assert.assertNotNull(path);
		CK2Save save = loader.load(path);
		Assert.assertEquals("1.09", save.getVersion());
		Assert.assertEquals("1067.7.29", save.getDate());
		Assert.assertEquals("3212", save.getPlayerCharacterId() );
	}

}
