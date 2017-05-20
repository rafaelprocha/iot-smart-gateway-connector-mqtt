package iot.gateway.cloud.connector.mqtt.exception;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttClientIdExistsException extends MqttException {

	private static final long serialVersionUID = -4172820842153071933L;

	public static final int REASON_CODE_CLIENT_ID_EXISTS = 9998;

	public MqttClientIdExistsException() {
		super(REASON_CODE_CLIENT_ID_EXISTS);
	}

	public String getMessage() {
		return "Client ID already exists";
	}
}
