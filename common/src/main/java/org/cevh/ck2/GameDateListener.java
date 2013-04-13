package org.cevh.ck2;

import java.util.Date;

import org.cevh.domain.CK2Save;
import org.cevh.util.KeyValueListenerSupport;
import org.cevh.util.SaveLoader;
import org.jewel.util.StringUtil;
import org.jewel.util.date.DateUtil;

public class GameDateListener extends KeyValueListenerSupport<CK2Save> {

	public GameDateListener(CK2Save save) {
		super(save);
	}

	@Override
	public void onKeyValue(String localKey, String fullKey, int fullKeySize,
			String valueStr) {
		if ("date".equals(fullKey)) {
			String unquoted = StringUtil.unwrap(valueStr, 1);
			Date gameDate = DateUtil.parse(unquoted, SaveLoader.DATE_FORMAT);
			save.setGameDate(gameDate);
		}
	}

}
