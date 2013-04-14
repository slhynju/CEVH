package org.cevh.domain.ck2;

import java.io.Serializable;
import java.util.Date;

import org.jewel.util.BeanStringBuilder;
import org.jewel.util.EqualsUtil;
import org.jewel.util.HashCodeBuilder;
import org.jewel.util.StringUtil;

public class CK2Character implements Serializable {

	private static final long serialVersionUID = 4952034947732501713L;

	private String id;

	private String birthName;

	private Date birthDate;

	private Date deathDate;

	private Gender gender;

	// TODO attributes

	// TODO traits

	private String religion;

	private String culture;

	private String dynastyId;

	private String capitalTitleId;

	private String primaryTitleId;

	private double wealth;

	private String hostId;

	// TODO allies, enemies

	// TODO flags

	// TODO provinces??

	public CK2Character() {
		id = "";
		birthName = "";
		birthDate = null;
		deathDate = null;
		gender = Gender.MALE;
		religion = "";
		culture = "";
		dynastyId = "";
		capitalTitleId = "";
		primaryTitleId = "";
		wealth = 0.0;
		hostId = "";
	}

	public boolean isAlive() {
		return deathDate == null;
	}

	public boolean isMale() {
		return gender == Gender.MALE;
	}

	public boolean isFemale() {
		return gender == Gender.FEMALE;
	}

	public String getPrimaryTitleId() {
		if (StringUtil.isEmpty(primaryTitleId)) {
			return capitalTitleId;
		}
		return primaryTitleId;
	}

	public boolean isLord() {
		return StringUtil.notEmpty(getPrimaryTitleId());
	}

	public boolean isIndependentLord() {
		return isLord() && EqualsUtil.isEquals(id, hostId);
	}

	public boolean isBaron() {
		return isLord("b");
	}

	public boolean isCountOrAbove() {
		return isCount() || isDuke() || isKing() || isEmperor();
	}

	public boolean isCount() {
		return isLord("c");
	}

	public boolean isDuke() {
		return isLord("d");
	}

	public boolean isKing() {
		return isLord("k");
	}

	public boolean isEmperor() {
		return isLord("e");
	}

	private boolean isLord(String titlePrefix) {
		String title = getPrimaryTitleId();
		return StringUtil.notEmpty(title) && title.startsWith(titlePrefix);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBirthName() {
		return birthName;
	}

	public void setBirthName(String birthName) {
		this.birthName = birthName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getCulture() {
		return culture;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getDynastyId() {
		return dynastyId;
	}

	public void setDynastyId(String dynastyId) {
		this.dynastyId = dynastyId;
	}

	public String getCapitalTitleId() {
		return capitalTitleId;
	}

	public void setCapitalTitleId(String capitalTitleId) {
		this.capitalTitleId = capitalTitleId;
	}

	public void setPrimaryTitleId(String primaryTitleId) {
		this.primaryTitleId = primaryTitleId;
	}

	public double getWealth() {
		return wealth;
	}

	public void setWealth(double wealth) {
		this.wealth = wealth;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	@Override
	public String toString() {
		return new BeanStringBuilder(CK2Character.class).append("id", id)
				.append("birthName", birthName).append("birthDate", birthDate)
				.append("deathDate", deathDate).append("isAlive", isAlive())
				.append("gender", gender).append("religion", religion)
				.append("culture", culture).append("dynastyId", dynastyId)
				.append("capitalTitleId", capitalTitleId)
				.append("isLord", isLord())
				.append("primaryTitleId", primaryTitleId)
				.append("wealth", wealth, 2, 2, false).append("hostId", hostId)
				.append("isIndependentLord", isIndependentLord()).toS();

	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CK2Character) {
			CK2Character other = (CK2Character) o;
			return EqualsUtil.isEquals(id, other.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toValue();
	}
}
