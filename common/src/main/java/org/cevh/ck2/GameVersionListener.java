package org.cevh.ck2;

import org.cevh.domain.CK2Save;
import org.cevh.util.KeyValueListenerSupport;

public class GameVersionListener extends KeyValueListenerSupport<CK2Save> {

	public GameVersionListener(CK2Save save) {
		super(save);
	}

	@Override
	public void onKeyValue(String localKey, String fullKey, String valueStr) {
		if ("version".equals(fullKey)) {
			String version = valueStr.substring(1, valueStr.length() - 1);
			save.setGameVersion(version);
		}
	}

}
