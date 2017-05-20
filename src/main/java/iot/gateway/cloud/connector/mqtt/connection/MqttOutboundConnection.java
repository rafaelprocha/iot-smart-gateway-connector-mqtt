package iot.gateway.cloud.connector.mqtt.connection;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import iot.gateway.cloud.connector.mqtt.config.ConnectionConfig;

public class MqttOutboundConnection extends MqttConnection {

	public MqttOutboundConnection(ConnectionConfig config) throws MqttException {
		super(config);
	}

	public void publish(String simpleMessage) throws MqttException {
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setQos(this.config.getQos());
		mqttMessage.setPayload(simpleMessage.getBytes());
		this.publish(mqttMessage);
	}

	public void publish(byte[] message) throws MqttException {
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setQos(this.config.getQos());
		mqttMessage.setPayload(message);
		this.publish(mqttMessage);
	}

	private void publish(MqttMessage message) throws MqttException {
		super.reconnect();
		client.publish(this.config.getTopic(), message);
	}

	public void publish(String topic, byte[] payload, int qos, boolean retained) throws MqttException {
		client.publish(topic, payload, qos, retained);
	}

}