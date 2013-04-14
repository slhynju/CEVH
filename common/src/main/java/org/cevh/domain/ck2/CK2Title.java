package org.cevh.domain.ck2;

import java.io.Serializable;

import org.jewel.util.BeanStringBuilder;
import org.jewel.util.EqualsUtil;
import org.jewel.util.HashCodeBuilder;

public class CK2Title implements Serializable {

	private static final long serialVersionUID = -9202862661901155429L;

	private String id;

	private String holderId;

	private String liegeTitleId;

	private String deJureLiegeTitleId;

	public CK2Title() {
		id = "";
		holderId = "";
		liegeTitleId = "";
		deJureLiegeTitleId = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHolderId() {
		return holderId;
	}

	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}

	public String getLiegeTitleId() {
		return liegeTitleId;
	}

	public void setLiegeTitleId(String liegeTitleId) {
		this.liegeTitleId = liegeTitleId;
	}

	public String getDeJureLiegeTitleId() {
		return deJureLiegeTitleId;
	}

	public void setDeJureLiegeTitleId(String deJureLiegeTitleId) {
		this.deJureLiegeTitleId = deJureLiegeTitleId;
	}

	@Override
	public String toString() {
		return new BeanStringBuilder(CK2Title.class).append("id", id)
				.append("holderId", holderId)
				.append("liegeTitleId", liegeTitleId)
				.append("deJureLiegeTitleId", deJureLiegeTitleId).toS();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CK2Title) {
			CK2Title other = (CK2Title) o;
			return EqualsUtil.isEquals(id, other.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toValue();
	}

}
