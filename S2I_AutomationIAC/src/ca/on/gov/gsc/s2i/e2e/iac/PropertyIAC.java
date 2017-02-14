package ca.on.gov.gsc.s2i.e2e.iac;

import ca.on.gov.gsc.s2i.e2e.util.PropertyUtil;

public class PropertyIAC extends PropertyUtil {
	public static PropertyUtil getInstance() {
		return getInstance("/ca/on/gov/gsc/s2i/e2e/iac/IACBase.properties");
	}
}
