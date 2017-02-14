package ca.on.gov.gsc.s2i.e2e.acsc.model;

public class Parent2PayerData {
	private String id;
	private String desc;

	private String identificationNumber;
	private String proceed;
	private String optReason;
	private String taxIsFiled;
	//special
	private String proofIncome;
	private String complexIncome;
	private String incomeIsSeasonal;
	
	private String sin;
	private String birthday;
	private String email;
	private String language;
	private String benefit;
	private String benefitAmt;
										
	private FeeModel fee;
	
	private String payType;
	private String payNo;

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

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getProceed() {
		return proceed;
	}

	public void setProceed(String proceed) {
		this.proceed = proceed;
	}

	public String getOptReason() {
		return optReason;
	}

	public void setOptReason(String optReason) {
		this.optReason = optReason;
	}

	public String getProofIncome() {
		return proofIncome;
	}

	public void setProofIncome(String proofIncome) {
		this.proofIncome = proofIncome;
	}

	public String getTaxIsFiled() {
		return taxIsFiled;
	}

	public void setTaxIsFiled(String taxIsFiled) {
		this.taxIsFiled = taxIsFiled;
	}

	public String getComplexIncome() {
		return complexIncome;
	}

	public void setComplexIncome(String complexIncome) {
		this.complexIncome = complexIncome;
	}

	public String getIncomeIsSeasonal() {
		return incomeIsSeasonal;
	}

	public void setIncomeIsSeasonal(String incomeIsSeasonal) {
		this.incomeIsSeasonal = incomeIsSeasonal;
	}

	public String getSin() {
		return sin;
	}

	public void setSin(String sin) {
		this.sin = sin;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getBenefit() {
		return benefit;
	}

	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}

	public String getBenefitAmt() {
		return benefitAmt;
	}

	public void setBenefitAmt(String benefitAmt) {
		this.benefitAmt = benefitAmt;
	}

	public FeeModel getFee() {
		return fee;
	}

	public void setFee(FeeModel fee) {
		this.fee = fee;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	
}
