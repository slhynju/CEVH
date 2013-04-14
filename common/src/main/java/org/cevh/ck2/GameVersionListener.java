package org.cevh.ck2;

import org.cevh.domain.ck2.CK2Save;
import org.cevh.util.KeyValueListenerSupport;
import org.jewel.util.StringUtil;

public class GameVersionListener extends KeyValueListenerSupport<CK2Save> {

	public GameVersionListener(CK2Save save) {
		super(save);
	}

	@Override
	public void onKeyValue(String localKey, String fullKey, int fullKeySize,
			String valueStr) {
		if ("version".equals(fullKey)) {
			String version = StringUtil.unwrap(valueStr, 1);
			save.setGameVersion(version);
		}
	}

}
