package org.cevh.ck2;

import org.cevh.domain.ck2.CK2Save;
import org.cevh.domain.ck2.CK2Title;
import org.cevh.util.BlockListener;
import org.cevh.util.KeyValueListenerSupport;
import org.jewel.util.StringUtil;

public class TitleListener extends KeyValueListenerSupport<CK2Save> implements
		BlockListener {

	private CK2Title title;

	public TitleListener(CK2Save save) {
		super(save);
		title = null;
	}

	@Override
	public void onKeyValue(String localKey, String fullKey, int fullKeySize,
			String valueStr) {
		if (fullKeySize != 2) {
			return;
		}
		String firstKey = getFirstKey(fullKey);
		if (!isTitle(firstKey)) {
			return;
		}
		switch (localKey) {
		case "liege":
			title.setLiegeTitleId(StringUtil.unwrap(valueStr, 1));
			return;
		case "holder":
			title.setHolderId(valueStr);
			return;
		case "de_jure_liege":
			title.setDeJureLiegeTitleId(StringUtil.unwrap(valueStr, 1));
			return;
		}
	}

	@Override
	public boolean onBlockStart(String localKey, String fullKey, int fullKeySize) {
		if (fullKeySize != 1) {
			return false;
		}
		if (!isTitle(localKey)) {
			return false;
		}
		title = new CK2Title();
		title.setId(localKey);
		return true;
	}

	@Override
	public void onBlockEnd(String localKey, String fullKey, int fullKeySize) {
		save.addTitle(title);
	}

	private static boolean isTitle(String localKey) {
		return localKey.startsWith("b_") || localKey.startsWith("c_")
				|| localKey.startsWith("d_") || localKey.startsWith("k_")
				|| localKey.startsWith("e_");
	}

}
