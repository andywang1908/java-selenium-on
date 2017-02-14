package ca.on.gov.gsc.s2i.e2e.bf;

import ca.on.gov.gsc.s2i.e2e.util.PropertyUtil;

public class PropertyBF extends PropertyUtil {
	public static PropertyUtil getInstance() {
		return getInstance("/ca/on/gov/gsc/s2i/e2e/bf/BFBase.properties");
	}
}
