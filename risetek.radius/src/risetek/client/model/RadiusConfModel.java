package risetek.client.model;

public class RadiusConfModel {

	private String authPort;
	private String acctPort;
	private String secretKey;
	private String version;
	private String maxUser;
	
	public RadiusConfModel(String authPort, String acctPort, String secretKey,
			String version, String maxUser){
		
		this.authPort = authPort;
		this.acctPort = acctPort;
		this.secretKey = secretKey;
		this.version = version;
		this.maxUser = maxUser;
	}
	public String getAuthPort(){
		return authPort;
	}
	public String getAcctPort(){
		return acctPort;
	}
	public String getSecretKey(){
		return secretKey;
	}
	public String getVersion(){
		return version;
	}
	public String getMaxUser(){
		return maxUser;
	}
}
