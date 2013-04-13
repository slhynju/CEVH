package org.cevh.ck2;

import org.cevh.domain.CK2Character;
import org.cevh.domain.CK2Save;
import org.cevh.domain.Gender;
import org.cevh.util.BlockListener;
import org.cevh.util.KeyValueListenerSupport;
import org.cevh.util.SaveLoader;
import org.jewel.util.StringUtil;
import org.jewel.util.date.DateUtil;

public class CharacterListener extends KeyValueListenerSupport<CK2Save>
		implements BlockListener {

	private CK2Character character;

	public CharacterListener(CK2Save save) {
		super(save);
		character = null;
	}

	@Override
	public void onKeyValue(String localKey, String fullKey, int fullKeySize,
			String valueStr) {
		if (!fullKey.startsWith("character.")) {
			return;
		}
		switch (localKey) {
		case "birth_name":
			character.setBirthName(StringUtil.unwrap(valueStr, 1));
			return;
		case "female":
			character.setGender(Gender.toGender(valueStr));
			return;
		case "birth_date":
			character.setBirthDate(DateUtil.parse(
					StringUtil.unwrap(valueStr, 1), SaveLoader.DATE_FORMAT));
			return;
		case "death_date":
			character.setDeathDate(DateUtil.parse(
					StringUtil.unwrap(valueStr, 1), SaveLoader.DATE_FORMAT));
			return;
		case "religion":
			character.setReligion(StringUtil.unwrap(valueStr, 1));
			return;
		case "culture":
			character.setCulture(StringUtil.unwrap(valueStr, 1));
			return;
		case "dynasty":
			character.setDynastyId(valueStr);
			return;
		case "capital":
			character.setCapitalTitleId(StringUtil.unwrap(valueStr, 1));
			return;
		case "wealth":
			character.setWealth(Double.parseDouble(valueStr));
			return;
		case "host":
			character.setHostId(valueStr);
			return;
		case "primary":
			character.setPrimaryTitleId(StringUtil.unwrap(valueStr, 1));
			return;
		}
	}

	@Override
	public boolean onBlockStart(String localKey, String fullKey, int fullKeySize) {
		if (!fullKey.startsWith("character.")) {
			return false;
		}
		if (fullKeySize != 2) {
			return false;
		}
		String[] pair = StringUtil.splitPair(fullKey, ".");
		character = new CK2Character();
		character.setId(pair[1]);
		return true;
	}

	@Override
	public void onBlockEnd(String localKey, String fullKey, int fullKeySize) {
		if (character.isAlive() && character.isLord()) {
			save.addLord(character);
		}
	}
}
