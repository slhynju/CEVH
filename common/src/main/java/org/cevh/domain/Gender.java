package org.cevh.domain;

import org.jewel.util.StringUtil;

public enum Gender {

	MALE, FEMALE;

	public static final Gender toGender(String s) {
		if (StringUtil.isYes(s)) {
			return FEMALE;
		}
		return MALE;
	}
}
