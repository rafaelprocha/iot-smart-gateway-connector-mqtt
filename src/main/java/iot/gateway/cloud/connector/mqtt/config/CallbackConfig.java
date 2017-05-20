package iot.gateway.cloud.connector.mqtt.config;

import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttCallback;

import iot.gateway.cloud.connector.mqtt.callback.CommandLineCallback;
import iot.gateway.cloud.connector.mqtt.callback.NamedPipeCallback;
import iot.gateway.cloud.connector.mqtt.exception.MqttConfigCallBackException;

public class CallbackConfig {

	public static final String TYPE_NAMED_PIPE = "namedPipe";
	public static final String ATT_NAMED_PIPE = "name";

	public static final String TYPE_CMD = "commandLine";
	public static final String ATT_CMD = "command";

	private String type;

	private Map<String, String> attributes;

	public CallbackConfig() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public MqttCallback getCallback() throws MqttConfigCallBackException {
		if (TYPE_CMD.equals(type)) {
			String cmd = attributes.get(ATT_CMD);
			if (cmd == null) {
				throw new MqttConfigCallBackException();
			}
			return new CommandLineCallback(cmd);
		} else if (TYPE_NAMED_PIPE.equals(type)) {
			String pipe = attributes.get(ATT_NAMED_PIPE);
			if (pipe == null) {
				throw new MqttConfigCallBackException();
			}
			return new NamedPipeCallback(pipe);
		} else {
			throw new MqttConfigCallBackException();
		}

	}

}
