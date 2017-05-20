package iot.gateway.cloud.connector.mqtt.connection;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;

import iot.gateway.cloud.connector.mqtt.config.ConnectionConfig;

public class MqttInboundConnection extends MqttConnection {

	private MqttCallback callback;

	public MqttInboundConnection(ConnectionConfig config, MqttCallback callback) throws MqttException {
		super(config);
		super.client.subscribe(config.getTopic());
		this.callback = callback;
		super.client.setCallback(this.callback);
	}

	@Override
	public boolean reconnect() throws MqttException {
		boolean reconnected = super.reconnect();
		if (reconnected) {
			super.client.subscribe(super.config.getTopic());
			super.client.setCallback(this.callback);
		}
		return reconnected;
	}

}