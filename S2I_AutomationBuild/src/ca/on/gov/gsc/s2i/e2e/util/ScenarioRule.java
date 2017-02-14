package ca.on.gov.gsc.s2i.e2e.util;

public class ScenarioRule implements Cloneable {
	String ministry;
	String program;
	String scenario;
	String ruleFail;
	String ruleSuccess;

	public String getMinistry() {
		return ministry;
	}

	public void setMinistry(String ministry) {
		this.ministry = ministry;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getRuleFail() {
		return ruleFail;
	}

	public void setRuleFail(String ruleFail) {
		this.ruleFail = ruleFail;
	}

	public String getRuleSuccess() {
		return ruleSuccess;
	}

	public void setRuleSuccess(String ruleSuccess) {
		this.ruleSuccess = ruleSuccess;
	}

	public ScenarioRule clone() throws CloneNotSupportedException {
		return (ScenarioRule) super.clone();
	}

	@Override
	public String toString() {
		/*
		return "ScenarioRule [ministry=" + ministry + ", program=" + program + ", scenario=" + scenario + ", ruleFail="
				+ ruleFail + ", ruleSuccess=" + ruleSuccess + "]";*/

		return "	"+ program + "	" + ruleSuccess + "	" + ruleFail;
	}
}
