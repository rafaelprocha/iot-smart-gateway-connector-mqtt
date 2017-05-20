package iot.gateway.cloud.connector.mqtt.exception;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttConfigCallBackException extends MqttException {

	private static final long serialVersionUID = 2628581819376181047L;
	
	public static final int REASON_CODE_CALLBACK_MALFORMED = 9980;
	
	public MqttConfigCallBackException() {
		super(REASON_CODE_CALLBACK_MALFORMED);
	}

}
