package ca.on.gov.gsc.s2i.e2e.acsc;

import ca.on.gov.gsc.s2i.e2e.util.PropertyUtil;

public class PropertyACSC extends PropertyUtil {
	public static PropertyUtil getInstance() {
		return getInstance("/ca/on/gov/gsc/s2i/e2e/acsc/ACSCBase.properties");
	}
}
