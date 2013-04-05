package org.cevh.domain;

import java.io.Serializable;

import org.jewel.util.BeanStringBuilder;
import org.jewel.util.EqualsUtil;
import org.jewel.util.HashCodeBuilder;

public class CK2Save implements Serializable {

	private static final long serialVersionUID = -3197474754514123592L;

	private String fileName;

	private String version;

	private String date;

	private String playerCharacterId;

	public CK2Save() {
		fileName = "";
		version = "";
		date = "";
		playerCharacterId = "";
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPlayerCharacterId() {
		return playerCharacterId;
	}

	public void setPlayerCharacterId(String playerCharacterId) {
		this.playerCharacterId = playerCharacterId;
	}

	@Override
	public String toString() {
		return new BeanStringBuilder(CK2Save.class)
				.append("fileName", fileName).append("version", version)
				.append("date", date)
				.append("playerCharacterId", playerCharacterId).toS();

	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CK2Save) {
			CK2Save other = (CK2Save) o;
			return EqualsUtil.isEquals(fileName, other.fileName)
					&& EqualsUtil.isEquals(version, other.version)
					&& EqualsUtil.isEquals(date, other.date)
					&& EqualsUtil.isEquals(playerCharacterId,
							other.playerCharacterId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(fileName).append(version)
				.append(date).append(playerCharacterId).toValue();
	}
}
