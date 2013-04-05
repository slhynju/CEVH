package org.cevh.domain;

import java.io.Serializable;

import org.jewel.util.BeanStringBuilder;
import org.jewel.util.EqualsUtil;
import org.jewel.util.HashCodeBuilder;

public class Province implements Serializable {

	private static final long serialVersionUID = 4764206700437884891L;

	private int id;

	private String name;

	public Province() {
		this(0, "");
	}

	public Province(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return new BeanStringBuilder(Province.class).append("id", id)
				.append("name", name).toS();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Province) {
			Province other = (Province) o;
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
