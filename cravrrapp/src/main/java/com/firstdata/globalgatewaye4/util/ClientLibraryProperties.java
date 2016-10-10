package com.firstdata.globalgatewaye4.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.firstdata.globalgatewaye4.exceptions.UnexpectedException;

/**
 * @author Steve Copous <steve.copous@firstdata.com>
 *
 */
public class ClientLibraryProperties {
	
	public static final String GATEWAY_PROPERTIES_FILE = "globalgatewaye4.properties";
	public static final String VERSION_PROPERTY_NAME = "globalgatewaye4.gateway.version";
	
	private final Properties properties = loadProperties(GATEWAY_PROPERTIES_FILE);
	
	public String version() {
		return properties.getProperty(VERSION_PROPERTY_NAME);
	}
	
	private Properties loadProperties(String propertyFile) {
		try {
			Properties p = new Properties();
			InputStream stream = getClass().getClassLoader().getResourceAsStream(propertyFile);
		
			p.load(stream);
			return p;
		} catch (IOException e) {
			throw new UnexpectedException("Couldn't load " + GATEWAY_PROPERTIES_FILE + " unable to continue.", e);
		}
	}
}
