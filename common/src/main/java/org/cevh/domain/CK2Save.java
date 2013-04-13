package org.cevh.domain;

import java.io.Serializable;
import java.util.Date;

import org.jewel.util.BeanStringBuilder;
import org.jewel.util.EqualsUtil;
import org.jewel.util.HashCodeBuilder;

public class CK2Save implements Serializable {

	private static final long serialVersionUID = -3197474754514123592L;

	private String fileName;

	private String gameVersion;

	private Date gameDate;

	private String playerCharacterId;

	public CK2Save() {
		fileName = "";
		gameVersion = "";
		gameDate = null;
		playerCharacterId = "";
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGameVersion() {
		return gameVersion;
	}

	public void setGameVersion(String version) {
		this.gameVersion = version;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
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
				.append("fileName", fileName).append("version", gameVersion)
				.append("date", gameDate)
				.append("playerCharacterId", playerCharacterId).toS();

	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CK2Save) {
			CK2Save other = (CK2Save) o;
			return EqualsUtil.isEquals(fileName, other.fileName)
					&& EqualsUtil.isEquals(gameVersion, other.gameVersion)
					&& EqualsUtil.isEquals(gameDate, other.gameDate)
					&& EqualsUtil.isEquals(playerCharacterId,
							other.playerCharacterId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(fileName).append(gameVersion)
				.append(gameDate).append(playerCharacterId).toValue();
	}
}
