package iot.gateway.cloud.connector.mqtt.connection;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import iot.gateway.cloud.connector.mqtt.config.ConnectionConfig;

public abstract class MqttConnection {

	protected MqttClient client;
	protected ConnectionConfig config;

	public MqttConnection(ConnectionConfig config) throws MqttException {
		super();
		this.config = config;
		this.connect();
	}

	private void connect() throws MqttException {
		this.client = new MqttClient(this.config.getServerURI(), this.config.getClientId());
		if (this.config.getOptions() == null) {
			client.connect();
		} else {
			client.connect(this.config.getOptions());
		}
	}

	public void disconnect() throws MqttException {
		client.disconnect();
	}

	public void disconnect(long quiesceTimeout) throws MqttException {
		client.disconnect(quiesceTimeout);
	}

	public void disconnectForcibly() throws MqttException {
		client.disconnectForcibly();
	}

	public void disconnectForcibly(long disconnectTimeout) throws MqttException {
		client.disconnectForcibly(disconnectTimeout);
	}

	public void disconnectForcibly(long quiesceTimeout, long disconnectTimeout) throws MqttException {
		client.disconnectForcibly(quiesceTimeout, disconnectTimeout);
	}

	public boolean reconnect() throws MqttException {
		if (!client.isConnected()) {
			this.close();
			this.connect();
			return true;
		}
		return false;

	}

	public void close() throws MqttException {
		client.close();
	}

}
