package iot.gateway.cloud.connector.mqtt.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import iot.gateway.cloud.connector.mqtt.config.ConnectionConfig;
import iot.gateway.cloud.connector.mqtt.connection.MqttInboundConnection;
import iot.gateway.cloud.connector.mqtt.connection.MqttOutboundConnection;
import iot.gateway.cloud.connector.mqtt.exception.MqttClientIdExistsException;
import iot.gateway.cloud.connector.mqtt.exception.MqttConnectionExistsException;

@Component
public class MqttFactory {

	private Map<String, MqttOutboundConnection> outboundMap = new HashMap<>();
	private Map<String, MqttInboundConnection> inboundMap = new HashMap<>();
	private Set<String> cliendIds = new HashSet<>();

	private static final int RESTABILISH_CONNECTION_INTERVAL = 1000;

	private void checkAvaliability(ConnectionConfig config, Map map) throws MqttException {
		if (cliendIds.contains(config.getClientId())) {
			throw new MqttClientIdExistsException();
		}
		if (map.containsKey(config.getName())) {
			throw new MqttConnectionExistsException();
		}
	}

	public MqttOutboundConnection createOutboundConnection(ConnectionConfig config) throws MqttException {
		checkAvaliability(config, outboundMap);
		MqttOutboundConnection connection = new MqttOutboundConnection(config);
		outboundMap.put(config.getName(), connection);
		cliendIds.add(config.getClientId());

		return connection;
	}

	public MqttInboundConnection createInboundConnection(ConnectionConfig config, MqttCallback callback)
			throws MqttException {
		checkAvaliability(config, inboundMap);
		MqttInboundConnection connection = new MqttInboundConnection(config, callback);
		inboundMap.put(config.getName(), connection);
		cliendIds.add(config.getClientId());
		return connection;
	}

	public MqttOutboundConnection getOutboundConnection(String connectionName) {
		return outboundMap.get(connectionName);
	}

	public MqttInboundConnection getInboundConnection(String connectionName) {
		return inboundMap.get(connectionName);
	}

	@Scheduled(fixedRate = RESTABILISH_CONNECTION_INTERVAL)
	public void restabilishConnection() throws MqttException {
		for (MqttInboundConnection inboundConn : inboundMap.values()) {
			inboundConn.reconnect();
		}
	}

}