package ca.on.gov.gsc.s2i.e2e.model;

public class E2ELoopResult {
	private String id;
	private String desc = "";
	private String result = "";
	private String summary = "";

	//for ftl
	private String warnSign;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getWarnSign() {
		return warnSign;
	}
	public void setWarnSign(String warnSign) {
		this.warnSign = warnSign;
	}

}
