package com.eugenefe.entity;
// Generated Mar 16, 2016 5:36:41 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * OdsKrxMetaDetail generated by hbm2java
 */
@Entity
@Table(name = "ods_krx_meta_detail", catalog = "eugenefe")
public class OdsKrxMetaDetail implements java.io.Serializable {

	private OdsKrxMetaDetailId id;
	private OdsKrxMeta odsKrxMeta;
	private String mapValue;

	public OdsKrxMetaDetail() {
	}

	public OdsKrxMetaDetail(OdsKrxMetaDetailId id, OdsKrxMeta odsKrxMeta) {
		this.id = id;
		this.odsKrxMeta = odsKrxMeta;
	}

	public OdsKrxMetaDetail(OdsKrxMetaDetailId id, OdsKrxMeta odsKrxMeta, String mapValue) {
		this.id = id;
		this.odsKrxMeta = odsKrxMeta;
		this.mapValue = mapValue;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "metaId", column = @Column(name = "META_ID", nullable = false, length = 20) ),
			@AttributeOverride(name = "mapKey", column = @Column(name = "MAP_KEY", nullable = false, length = 50) ) })
	public OdsKrxMetaDetailId getId() {
		return this.id;
	}

	public void setId(OdsKrxMetaDetailId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "META_ID", nullable = false, insertable = false, updatable = false)
	public OdsKrxMeta getOdsKrxMeta() {
		return this.odsKrxMeta;
	}

	public void setOdsKrxMeta(OdsKrxMeta odsKrxMeta) {
		this.odsKrxMeta = odsKrxMeta;
	}

	@Column(name = "MAP_VALUE", length = 200)
	public String getMapValue() {
		return this.mapValue;
	}

	public void setMapValue(String mapValue) {
		this.mapValue = mapValue;
	}

}
