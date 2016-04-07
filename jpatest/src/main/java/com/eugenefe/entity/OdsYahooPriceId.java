package com.eugenefe.entity;
// Generated Mar 25, 2016 3:20:32 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OdsYahooPriceId generated by hbm2java
 */
@Embeddable
public class OdsYahooPriceId implements java.io.Serializable {

	private String yahooId;
	private String baseDate;

	public OdsYahooPriceId() {
	}

	public OdsYahooPriceId(String yahooId, String baseDate) {
		this.yahooId = yahooId;
		this.baseDate = baseDate;
	}

	@Column(name = "YAHOO_ID", nullable = false, length = 20)
	public String getYahooId() {
		return this.yahooId;
	}

	public void setYahooId(String yahooId) {
		this.yahooId = yahooId;
	}

	@Column(name = "BASE_DATE", nullable = false, length = 20)
	public String getBaseDate() {
		return this.baseDate;
	}

	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OdsYahooPriceId))
			return false;
		OdsYahooPriceId castOther = (OdsYahooPriceId) other;

		return ((this.getYahooId() == castOther.getYahooId()) || (this.getYahooId() != null
				&& castOther.getYahooId() != null && this.getYahooId().equals(castOther.getYahooId())))
				&& ((this.getBaseDate() == castOther.getBaseDate()) || (this.getBaseDate() != null
						&& castOther.getBaseDate() != null && this.getBaseDate().equals(castOther.getBaseDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getYahooId() == null ? 0 : this.getYahooId().hashCode());
		result = 37 * result + (getBaseDate() == null ? 0 : this.getBaseDate().hashCode());
		return result;
	}

}