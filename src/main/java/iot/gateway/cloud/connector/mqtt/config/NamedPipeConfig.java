package iot.gateway.cloud.connector.mqtt.config;

public class NamedPipeConfig {

	private String namedPipe;
	private String connectionName;

	public NamedPipeConfig() {
		super();
	}

	public String getNamedPipe() {
		return namedPipe;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public void setNamedPipe(String namedPipe) {
		this.namedPipe = namedPipe;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

}
