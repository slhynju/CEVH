package org.cevh.ck2;

import org.cevh.domain.ck2.CK2Save;
import org.cevh.util.KeyValueListenerSupport;

public class PlayerCharacterListener extends KeyValueListenerSupport<CK2Save> {

	public PlayerCharacterListener(CK2Save save) {
		super(save);
	}

	@Override
	public void onKeyValue(String localKey, String fullKey, int fullKeySize,
			String valueStr) {
		if ("player.id".equals(fullKey)) {
			save.setPlayerCharacterId(valueStr);
		}
	}

}
