package iot.gateway.cloud.connector.mqtt.exception;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttConnectionExistsException extends MqttException {

	private static final long serialVersionUID = 6867460277648700303L;

	public static final int REASON_CODE_CONNECTION_EXISTS = 9997;

	public MqttConnectionExistsException() {
		super(REASON_CODE_CONNECTION_EXISTS);
	}

	public String getMessage() {
		return "Connection already exists";
	}
}
