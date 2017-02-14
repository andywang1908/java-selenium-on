package ca.on.gov.gsc.s2i.e2e.acsc.model;

public class EligibilityData {
	private String id;
	private String desc;
	
	private EligibilityModel eligibility;

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

	public EligibilityModel getEligibility() {
		return eligibility;
	}

	public void setEligibility(EligibilityModel eligibility) {
		this.eligibility = eligibility;
	}

}
