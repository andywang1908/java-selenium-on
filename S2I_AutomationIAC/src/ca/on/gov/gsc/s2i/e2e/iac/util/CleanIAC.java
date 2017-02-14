package ca.on.gov.gsc.s2i.e2e.iac.util;

import ca.ontario.health.iac.util.Exception_Exception;
import ca.ontario.health.iac.util.IACCleanWSService;

public class CleanIAC {
	/**/
	public static void main(String[] args) {
		System.out.println("fdsa11");
		
		IACCleanWSService wsClient = new IACCleanWSService();
		try {
			//9984152588
			//2222222222
			wsClient.getIACCleanWSPort().cleanHealthNumber("9954113321");
		} catch (Exception_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("fdsa22");
		
	}
}
