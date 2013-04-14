package org.cevh.ck2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

import org.cevh.domain.ck2.CK2Character;
import org.cevh.domain.ck2.CK2Dynasty;
import org.cevh.domain.ck2.CK2Province;
import org.cevh.domain.ck2.CK2Save;
import org.jewel.util.EqualsUtil;
import org.jewel.util.date.DateUtil;
import org.jewel.util.io.PathUtil;
import org.junit.Test;

public class CK2SaveLoaderTest {

	@SuppressWarnings("static-method")
	@Test
	public void testLoad() {
		CK2SaveLoaderImpl loader = new CK2SaveLoaderImpl();
		Path path = PathUtil.findResource("s1_1067.ck2");
		assertNotNull(path);
		CK2Save save = loader.load(path);
		assertEquals("1.09", save.getGameVersion());
		Date d = DateUtil.parse("1067.7.29", "yyyy.M.d");
		assertEquals(d, save.getGameDate());
		assertEquals("3212", save.getPlayerCharacterId());
		assertEquals(2, save.getFlags().size());
		Map<String, CK2Dynasty> dynasties = save.getDynasties();
		assertEquals(9531, dynasties.size());
		CK2Dynasty diRomeDynasty = dynasties.get("2000018");
		assertNotNull(diRomeDynasty);
		assertEquals("di Rome", diRomeDynasty.getFamilyName());
		assertEquals("italian", diRomeDynasty.getCulture());
		assertEquals("christian", diRomeDynasty.getReligion());
		Map<String, CK2Character> lords = save.getLords();
		assertEquals(2459, lords.size());
		CK2Character ch = lords.get("20662");
		assertNotNull(ch);
		assertEquals("Durgulel", ch.getBirthName());
		Date birthDate = DateUtil.parse("992.1.1", "yyyy.M.d");
		assertEquals(birthDate, ch.getBirthDate());
		assertEquals("orthodox", ch.getReligion());
		assertEquals("alan", ch.getCulture());
		assertEquals("100603", ch.getDynastyId());
		assertEquals("b_vovnushki", ch.getCapitalTitleId());
		assertTrue(EqualsUtil.isEquals(69.50732, ch.getWealth(), 5));
		assertEquals("20662", ch.getHostId());
		assertTrue(ch.isAlive());
		assertTrue(ch.isMale());
		assertTrue(ch.isLord());
		assertTrue(ch.isIndependentLord());
		Map<String, CK2Province> provinces = save.getProvinces();
		assertEquals(929, provinces.size());
		CK2Province province = provinces.get("1");
		assertEquals("Vestisland", province.getName());
		assertEquals("norwegian", province.getCulture());
		assertEquals("catholic", province.getReligion());
		assertEquals("c_vestisland", province.getCountTitleId());
		assertEquals(2, province.getBaronTitleIds().size());
		assertEquals("b_reykjavik", province.getCapitalBaronTitleId());
	}

}
