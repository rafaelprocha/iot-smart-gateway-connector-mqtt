package iot.gateway.cloud.connector.mqtt.exception;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttConnNotFoundException extends MqttException {

	private static final long serialVersionUID = 2628581819376181047L;

	public static final int REASON_CODE_CONNECTION_NOT_FOUND = 9999;

	public MqttConnNotFoundException() {
		super(REASON_CODE_CONNECTION_NOT_FOUND);
	}

}