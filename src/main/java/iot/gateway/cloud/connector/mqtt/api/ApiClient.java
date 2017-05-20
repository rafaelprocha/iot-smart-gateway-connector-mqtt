package iot.gateway.cloud.connector.mqtt.api;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.gateway.cloud.connector.mqtt.config.ConnectionConfig;
import iot.gateway.cloud.connector.mqtt.config.NamedPipeConfig;
import iot.gateway.cloud.connector.mqtt.connection.MqttOutboundConnection;
import iot.gateway.cloud.connector.mqtt.exception.MqttConnNotFoundException;
import iot.gateway.cloud.connector.mqtt.factory.MqttFactory;

@Component
public class ApiClient {

	@Autowired
	private MqttFactory factory;

	@Autowired
	private ApiNamedPipe namedPipe;

	public void createInboundConnection(ConnectionConfig config) throws MqttException {
		factory.createInboundConnection(config, config.getCallbackConfig().getCallback());
	}

	public void createOutboundConnection(ConnectionConfig config) throws MqttException, IOException {
		factory.createOutboundConnection(config);
		
		NamedPipeConfig pipeConfig = new NamedPipeConfig();
		pipeConfig.setNamedPipe(ApiNamedPipe.DEFAULT_PIPE.concat(config.getName()));
		pipeConfig.setConnectionName(config.getName());
		createNamedPipe(pipeConfig);
	}

	public void publishMessage(String name, String message)
			throws MqttException {
		MqttOutboundConnection conn = factory.getOutboundConnection(name);
		if (conn == null) {
			throw new MqttConnNotFoundException();
		}
		conn.publish(message);
	}

	public void createNamedPipe(NamedPipeConfig config) throws IOException {
		namedPipe.create(config, this);
	}
}
