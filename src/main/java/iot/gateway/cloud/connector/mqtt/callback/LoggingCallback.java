package iot.gateway.cloud.connector.mqtt.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.pmw.tinylog.Logger;

public class LoggingCallback implements MqttCallback {

	public void connectionLost(Throwable cause) {
		Logger.info("Connection Lost: ".concat(cause.getMessage()));
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		Logger.info("Message: ".concat(message.toString()));
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		Logger.info("Token: ".concat(token.toString()));
	}
}