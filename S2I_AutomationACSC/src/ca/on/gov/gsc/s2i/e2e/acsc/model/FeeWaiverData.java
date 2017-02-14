package ca.on.gov.gsc.s2i.e2e.acsc.model;

public class FeeWaiverData {
	private String id;
	private String desc;

	private EligibilityModel eligibility;
	private FeeModel fee;

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

	public FeeModel getFee() {
		return fee;
	}

	public void setFee(FeeModel fee) {
		this.fee = fee;
	}

	public EligibilityModel getEligibility() {
		return eligibility;
	}

	public void setEligibility(EligibilityModel eligibility) {
		this.eligibility = eligibility;
	}

}
