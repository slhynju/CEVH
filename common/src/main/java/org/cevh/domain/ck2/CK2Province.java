package org.cevh.domain.ck2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jewel.util.BeanStringBuilder;
import org.jewel.util.EqualsUtil;
import org.jewel.util.HashCodeBuilder;
import org.jewel.util.collection.CollectionUtil;

public class CK2Province implements Serializable {

	private static final long serialVersionUID = 4764206700437884891L;

	private String id;

	private String name;

	private String culture;

	private String religion;

	private String countTitleId;

	private List<String> baronTitleIds;

	public CK2Province() {
		this("", "");
	}

	public CK2Province(String id) {
		this(id, "");
	}

	public CK2Province(String id, String name) {
		this.id = id;
		this.name = name;
		this.culture = "";
		this.religion = "";
		this.countTitleId = "";
		this.baronTitleIds = new ArrayList<>(7);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCountTitleId() {
		return countTitleId;
	}

	public void setCountTitleId(String countTitleId) {
		this.countTitleId = countTitleId;
	}

	public List<String> getBaronTitleIds() {
		return baronTitleIds;
	}

	public void addBaronTitleId(String baronTitleId) {
		baronTitleIds.add(baronTitleId);
	}

	public String getCapitalBaronTitleId() {
		return CollectionUtil.getFirst(baronTitleIds);
	}

	@Override
	public String toString() {
		return new BeanStringBuilder(CK2Province.class).append("id", id)
				.append("name", name).append("culture", culture)
				.append("religion", religion)
				.append("countTitleId", countTitleId)
				.append("baronTitleIds", baronTitleIds).toS();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CK2Province) {
			CK2Province other = (CK2Province) o;
			return EqualsUtil.isEquals(id, other.id)
					&& EqualsUtil.isEquals(name, other.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(name).toValue();
	}
}
