package ca.on.gov.gsc.s2i.e2e.acsc.model;

import java.util.List;

public class Parent1Data {
	private String id;
	private String desc;

	private EligibilityModel eligibility;
	private FeeModel fee;
	private ParentModel parent;
	//TODO support child list             fileupload      address          other blank field
	private List<ChildModel> childList;

	private String paymentType;
	private String paymentAmount;
	private String downloadConsent;

	private String additionalSin;

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

	public ParentModel getParent() {
		return parent;
	}

	public void setParent(ParentModel parent) {
		this.parent = parent;
	}

	public List<ChildModel> getChildList() {
		return childList;
	}

	public void setChildList(List<ChildModel> childList) {
		this.childList = childList;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getDownloadConsent() {
		return downloadConsent;
	}

	public void setDownloadConsent(String downloadConsent) {
		this.downloadConsent = downloadConsent;
	}

	public String getAdditionalSin() {
		return additionalSin;
	}

	public void setAdditionalSin(String additionalSin) {
		this.additionalSin = additionalSin;
	}
	
}
