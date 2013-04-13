package org.cevh.ck2;

import org.cevh.domain.CK2Dynasty;
import org.cevh.domain.CK2Save;
import org.cevh.util.BlockListener;
import org.cevh.util.KeyValueListenerSupport;
import org.jewel.util.StringUtil;

public class DynastyListener extends KeyValueListenerSupport<CK2Save> implements
		BlockListener {

	private CK2Dynasty dynasty;

	public DynastyListener(CK2Save save) {
		super(save);
		dynasty = null;
	}

	@Override
	public void onKeyValue(String localKey, String fullKey, int fullKeySize,
			String valueStr) {
		if (!fullKey.startsWith("dynasties.")) {
			return;
		}
		switch (localKey) {
		case "name":
			dynasty.setFamilyName(StringUtil.unwrap(valueStr, 1));
			return;
		case "culture":
			dynasty.setCulture(StringUtil.unwrap(valueStr, 1));
			return;
		case "religion_group":
			dynasty.setReligion(StringUtil.unwrap(valueStr, 1));
			return;
		}
	}

	@Override
	public boolean onBlockStart(String localKey, String fullKey, int fullKeySize) {
		if (!fullKey.startsWith("dynasties.")) {
			return false;
		}
		if (fullKeySize != 2) {
			return false;
		}
		String[] pair = StringUtil.splitPair(fullKey, ".");
		dynasty = new CK2Dynasty();
		dynasty.setId(pair[1]);
		return true;
	}

	@Override
	public void onBlockEnd(String localKey, String fullKey, int fullKeySize) {
		save.addDynasty(dynasty);
	}

}
