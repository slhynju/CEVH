package org.cevh.ck2;

import org.cevh.domain.ck2.CK2Province;
import org.cevh.domain.ck2.CK2Save;
import org.cevh.util.BlockListener;
import org.cevh.util.KeyValueListenerSupport;
import org.jewel.util.StringUtil;

public class ProvinceListener extends KeyValueListenerSupport<CK2Save>
		implements BlockListener {

	private CK2Province province;

	public ProvinceListener(CK2Save save) {
		super(save);
		province = null;
	}

	@Override
	public void onKeyValue(String localKey, String fullKey, int fullKeySize,
			String valueStr) {
		if (fullKeySize <= 1) {
			return;
		}
		if (!isProvince(fullKey)) {
			return;
		}
		switch (localKey) {
		case "name":
			province.setName(StringUtil.unwrap(valueStr, 1));
			return;
		case "culture":
			province.setCulture(valueStr);
			return;
		case "religion":
			province.setReligion(valueStr);
			return;
		case "title":
			province.setCountTitleId(StringUtil.unwrap(valueStr, 1));
			return;
		}
	}

	@Override
	public boolean onBlockStart(String localKey, String fullKey, int fullKeySize) {
		if (!isProvince(fullKey)) {
			return false;
		}
		if (fullKeySize == 1) {
			province = new CK2Province(localKey);
			return true;
		}
		if (fullKeySize == 2 && localKey.startsWith("b_")) {
			province.addBaronTitleId(localKey);
		}
		return false;
	}

	@Override
	public void onBlockEnd(String localKey, String fullKey, int fullKeySize) {
		save.addProvince(province);
	}

	private static boolean isProvince(String fullKey) {
		String firstKey = getFirstKey(fullKey);
		return StringUtil.isDigits(firstKey);
	}

}
