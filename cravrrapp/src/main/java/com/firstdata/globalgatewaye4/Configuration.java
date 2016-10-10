package com.firstdata.globalgatewaye4;

/**
 * @author Steve Copous <steve.copous@firstdata.com>
 * <p>INTENDED FOR DEMONSTRATION PURPOSES ONLY.  NOT FOR PRODUCTION USE.</p>
 */
public class Configuration {

	public String gateway_id;
	public String password;
	public String keyId;
	public String hmacKey;
	
	/**
	 * @return Returns the version of the GGe4 API to be used.
	 */
	public static String apiVersion() {
		return "v12";
	}
	
	/**
	 * Constructor to create a new {@link #Configuration} object.
	 * 
	 * This object will retain all the credential information for accessing a specific terminal
	 * in the GGe4 environment.
	 * 
	 * @param gateway_id ID of the terminal to be used to process the transaction.  AKA ExactID
	 * @param password API Password of the GGe4 terminal.
	 * @param keyId HMAC Key ID for the terminal.
	 * @param hmacKey HMAC Key value for the terminal.
	 */
	public Configuration(String gateway_id, String password, String keyId, String hmacKey) {
		this.gateway_id = gateway_id;
		this.password = password;
		this.keyId = keyId;
		this.hmacKey = hmacKey;
	}
}
