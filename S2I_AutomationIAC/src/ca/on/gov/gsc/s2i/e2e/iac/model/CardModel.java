package ca.on.gov.gsc.s2i.e2e.iac.model;

public class CardModel {
	private String healthCardType;
	private String healthCardNumber;
	private String healthCardVersionCode;
	private String lastPostalCodeMOH;
	private String lastFourDigit;
	public String getHealthCardType() {
		return healthCardType;
	}
	public void setHealthCardType(String healthCardType) {
		this.healthCardType = healthCardType;
	}
	public String getHealthCardNumber() {
		return healthCardNumber;
	}
	public void setHealthCardNumber(String healthCardNumber) {
		this.healthCardNumber = healthCardNumber;
	}
	public String getHealthCardVersionCode() {
		return healthCardVersionCode;
	}
	public void setHealthCardVersionCode(String healthCardVersionCode) {
		this.healthCardVersionCode = healthCardVersionCode;
	}
	public String getLastPostalCodeMOH() {
		return lastPostalCodeMOH;
	}
	public void setLastPostalCodeMOH(String lastPostalCodeMOH) {
		this.lastPostalCodeMOH = lastPostalCodeMOH;
	}
	public String getLastFourDigit() {
		return lastFourDigit;
	}
	public void setLastFourDigit(String lastFourDigit) {
		this.lastFourDigit = lastFourDigit;
	}

}
