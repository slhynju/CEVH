package org.cevh.domain.ck2;

import java.io.Serializable;

import org.jewel.util.BeanStringBuilder;
import org.jewel.util.EqualsUtil;
import org.jewel.util.HashCodeBuilder;

public class CK2Dynasty implements Serializable {

	private static final long serialVersionUID = -1452836712419597999L;

	private String id;

	private String familyName;

	private String culture;

	private String religion;

	public CK2Dynasty() {
		id = "";
		familyName = "";
		culture = "";
		religion = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getCulture() {
		return culture;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	@Override
	public String toString() {
		return new BeanStringBuilder(CK2Dynasty.class).append("id", id)
				.append("familyName", familyName).append("culture", culture)
				.append("religion", religion).toS();

	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CK2Dynasty) {
			CK2Dynasty other = (CK2Dynasty) o;
			return EqualsUtil.isEquals(id, other.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toValue();
	}

}
