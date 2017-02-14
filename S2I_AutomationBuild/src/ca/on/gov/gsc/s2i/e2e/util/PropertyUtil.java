package ca.on.gov.gsc.s2i.e2e.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil {
	private final static Map<String,PropertyUtil> instanceMap = new HashMap<String,PropertyUtil>();
	private final Properties properties  = new Properties();
	protected static PropertyUtil getInstance(String path) {
		PropertyUtil instance = instanceMap.get(path);
		if (instance==null) {
			instance = new PropertyUtil(path);
			instanceMap.put(path, instance);
		}

		return instance;
	}
	public static PropertyUtil path(String path) {
		PropertyUtil instance = instanceMap.get(path);
		if (instance==null) {
			instance = new PropertyUtil(path);
			instanceMap.put(path, instance);
		}

		return instance;
	}

	protected PropertyUtil()
	{
	}
	protected PropertyUtil(String path)
	{
		InputStream inputStream = this.getClass().getResourceAsStream(path);

		try {
			properties.load(inputStream);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getProperty(String key) {
		String result = properties.getProperty(key);
		return result;
	}

}
