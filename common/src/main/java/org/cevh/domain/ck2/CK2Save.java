package org.cevh.domain.ck2;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jewel.util.BeanStringBuilder;
import org.jewel.util.EqualsUtil;
import org.jewel.util.HashCodeBuilder;

public class CK2Save implements Serializable {

	private static final long serialVersionUID = -3197474754514123592L;

	private String fileName;

	private String gameVersion;

	private Date gameDate;

	private String playerCharacterId;

	private Map<String, String> flags;

	private Map<String, CK2Dynasty> dynasties;

	private Map<String, CK2Character> lords;

	private Map<String, CK2Province> provinces;

	private Map<String, CK2Province> provincesByCountTitle;

	private Map<String, CK2Province> provincesByCapitalBaronTitle;

	private Map<String, CK2Title> titles;

	public CK2Save() {
		fileName = "";
		gameVersion = "";
		gameDate = null;
		playerCharacterId = "";
		flags = new HashMap<>();
		dynasties = new HashMap<>(10000);
		lords = new HashMap<>(3000);
		provinces = new HashMap<>(1000);
		provincesByCountTitle = new HashMap<>(1000);
		provincesByCapitalBaronTitle = new HashMap<>(1000);
		titles = new HashMap<>(1000);
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

	public Map<String, String> getFlags() {
		return flags;
	}

	public void addFlag(String key, String value) {
		flags.put(key, value);
	}

	public Map<String, CK2Dynasty> getDynasties() {
		return dynasties;
	}

	public void addDynasty(CK2Dynasty dynasty) {
		dynasties.put(dynasty.getId(), dynasty);
	}

	public Map<String, CK2Character> getLords() {
		return lords;
	}

	public void addLord(CK2Character lord) {
		lords.put(lord.getId(), lord);
	}

	public Map<String, CK2Province> getProvinces() {
		return provinces;
	}

	public CK2Province getProvinceByCountTitle(String countTitleId) {
		return provincesByCountTitle.get(countTitleId);
	}

	public CK2Province getProvinceByCapitalBaronTitle(String baronTitleId) {
		return provincesByCapitalBaronTitle.get(baronTitleId);
	}

	public void addProvince(CK2Province province) {
		provinces.put(province.getId(), province);
		provincesByCountTitle.put(province.getCountTitleId(), province);
		provincesByCapitalBaronTitle.put(province.getCapitalBaronTitleId(),
				province);
	}

	public Map<String, CK2Title> getTitles() {
		return titles;
	}

	public void addTitle(CK2Title title) {
		titles.put(title.getId(), title);
	}

	@Override
	public String toString() {
		return new BeanStringBuilder(CK2Save.class)
				.append("fileName", fileName)
				.append("gameVersion", gameVersion)
				.append("gameDate", gameDate)
				.append("playerCharacterId", playerCharacterId)
				.append("flags", flags).append("dynastySize", dynasties.size())
				.append("lordSize", lords.size())
				.append("provinceSize", provinces.size())
				.append("titlesSize", titles.size()).toS();
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
