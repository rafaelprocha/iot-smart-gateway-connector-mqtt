package iot.gateway.cloud.connector.mqtt.callback;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.pmw.tinylog.Logger;

import iot.gateway.cloud.connector.mqtt.api.ApiNamedPipe;

public class NamedPipeCallback implements MqttCallback {

	private String namedPipe;

	public NamedPipeCallback(String namedPipe) {
		super();
		this.namedPipe = namedPipe;
	}

	public void connectionLost(Throwable cause) {
		Logger.warn("Connection Lost: ".concat(cause.getMessage()));
	}

	public void messageArrived(String topic, MqttMessage message) {
		try {
			ApiNamedPipe.write(namedPipe, message.getPayload());
		} catch (IOException e) {
			Logger.error(e);
		}
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		Logger.warn("Token: ".concat(token.toString()));
	}
}