package org.ceh.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Province implements Serializable {

	private static final long serialVersionUID = 4764206700437884891L;

	private int id;

	private String name;

	public Province() {
		id = 0;
		name = "";
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
		return new ToStringBuilder(this).append("id", id).append("name", name)
				.toString();
	}
}
