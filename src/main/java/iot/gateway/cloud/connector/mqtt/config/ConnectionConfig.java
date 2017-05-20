package iot.gateway.cloud.connector.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class ConnectionConfig {

	private String name;
	private String topic;
	private String clientId;
	private String userName;
	private String password;
	private String[] serverURIs;
	private int qos;
	private MqttConnectOptions options;
	private CallbackConfig callbackConfig;

	public ConnectionConfig() {
		super();
	}

	public String getTopic() {
		return topic;
	}

	public String getClientId() {
		return clientId;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String[] getServerURIs() {
		return serverURIs;
	}

	public String getServerURI() {
		return serverURIs[0];
	}

	public String getName() {
		return name;
	}

	public int getQos() {
		return qos;
	}

	public MqttConnectOptions getOptions() {
		return options;
	}

	public CallbackConfig getCallbackConfig() {
		return callbackConfig;
	}
}
