package ca.on.gov.gsc.s2i.e2e.model;

import java.util.List;

public class CommonTestCase {
	private String id;
	private String desc;
	private List<?> caseArray;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<?> getCaseArray() {
		return caseArray;
	}
	public void setCaseArray(List<?> caseArray) {
		this.caseArray = caseArray;
	}
}
