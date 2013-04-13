package org.cevh.ck2;

import org.cevh.domain.CK2Save;
import org.cevh.util.KeyValueListenerSupport;

public class FlagListener extends KeyValueListenerSupport<CK2Save> {

	public FlagListener(CK2Save save) {
		super(save);
	}

	@Override
	public void onKeyValue(String localKey, String fullKey, int fullKeySize,
			String valueStr) {
		if (fullKey.startsWith("flags.") && fullKeySize == 2) {
			save.addFlag(localKey, valueStr);
		}
	}

}
