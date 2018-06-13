package org.shandong_univ.grapedb.derive_parse;

public class DeriveItem extends DeriveModel {
	private String outerKey;
	private String table;
	public String getOuterKey() {
		return outerKey;
	}
	public void setOuterKey(String outerKey) {
		this.outerKey = outerKey;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
}