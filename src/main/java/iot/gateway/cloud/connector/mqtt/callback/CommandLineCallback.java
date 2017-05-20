package iot.gateway.cloud.connector.mqtt.callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.pmw.tinylog.Logger;

public class CommandLineCallback implements MqttCallback {

	private String commandLine;

	public CommandLineCallback(String commandLine) {
		super();
		this.commandLine = commandLine.trim().concat(" ");
	}

	public void connectionLost(Throwable cause) {
		Logger.warn(cause.getMessage());
	}

	public void messageArrived(String topic, MqttMessage message) {
		Logger.debug("Message: ".concat(message.toString()));

		try {
			Process ps = Runtime.getRuntime().exec(this.commandLine.concat(message.toString()));
			try (BufferedReader in = new BufferedReader(new InputStreamReader(ps.getErrorStream()))) {
				String line;
				while ((line = in.readLine()) != null) {
					Logger.error(line);
				}
			}
		} catch (IOException e) {
			Logger.error(e);
		}
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		Logger.warn("Token: ".concat(token.toString()));
	}
}