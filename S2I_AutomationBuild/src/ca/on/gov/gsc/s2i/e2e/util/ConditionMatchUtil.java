package ca.on.gov.gsc.s2i.e2e.util;

import java.util.List;

import ca.on.gov.gsc.s2i.e2e.tool.GenerateRuleDataTool;

public class ConditionMatchUtil {

	//self boolean exception
	// \.[{(*+?^$|    ([{\^-=$!|]})?*+.
	public boolean match(String input, String condition) {
		String[] conditionArray = condition.split("&&");
		String[] inputArray = input.split("&&");
		boolean result = false;
		
		for(String conditionSingle:conditionArray) {
			conditionSingle = "&"+conditionSingle+"&";// |Q1:A5|Q3:A2|
			result = false;
			for (String inputSingle:inputArray) {// Q1:A1
				inputSingle = "&"+inputSingle+"&";
				if ( conditionSingle.indexOf(inputSingle)>=0 ) {
					result = true;
					break;
				}
			}
			if ( !result ) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean qualified(String input, Condition condition) {
		if ( this.match(input, condition.getFail()) ) {
			//TODO fail reason!!
			System.out.println("You can't go to "+condition.getUrl()+".");
			return false;
		}

		if ( !this.match(input, condition.getSuccess()) ) {
			System.out.println("You can't go to "+condition.getUrl()+".");
			return false;
		}

		System.out.println("You can go to "+condition.getUrl()+".");
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		GenerateRuleDataTool generateRuleDataTool = GenerateRuleDataTool.getInstance();
		List<ScenarioRule> ruleList = generateRuleDataTool.getRuleList();
		
		/*
		Condition condition = new Condition();
		
		condition.setFail("Q4:A1");
		condition.setSuccess("Q1:A5&Q3:A2&&Q1:A1");
		condition.setUrl("(1)MAG-FW");
		
		ConditionMatchUtil conditionMatchUtil = new ConditionMatchUtil();
		System.out.println("Should be can");
		conditionMatchUtil.qualified("Q1:A1&&Q1:A5",condition);
		conditionMatchUtil.qualified("Q1:A1&&Q3:A2",condition);
		conditionMatchUtil.qualified("Q1:A1&&Q1:A5&&Q3:A9",condition);
		System.out.println("Should be can't");
		conditionMatchUtil.qualified("Q4:A1&&Q3:A2",condition);
		conditionMatchUtil.qualified("Q3:A3",condition);*/
	}
}
