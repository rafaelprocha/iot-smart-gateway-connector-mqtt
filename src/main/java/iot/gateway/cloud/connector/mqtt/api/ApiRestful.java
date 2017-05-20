package iot.gateway.cloud.connector.mqtt.api;

import java.net.URI;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import iot.gateway.cloud.connector.mqtt.config.ConnectionConfig;
import iot.gateway.cloud.connector.mqtt.config.NamedPipeConfig;
import iot.gateway.cloud.connector.mqtt.exception.MqttConnNotFoundException;

@Controller
public class ApiRestful {

	@Autowired
	private ApiClient client;

	@RequestMapping(value = "/inbound/connections", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createInboundConnection(@RequestBody ConnectionConfig config) {
		try {
			client.createInboundConnection(config);
			return ResponseEntity.created(new URI("/inbound/connections/".concat(config.getName()))).build();
		} catch (MqttException mqttEx) {
			Logger.debug(mqttEx);
			return new ResponseEntity<>(mqttEx.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			Logger.error(e);
			return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/outbound/connections", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createOutboundConnection(@RequestBody ConnectionConfig config) {
		try {
			client.createOutboundConnection(config);
			return ResponseEntity.created(new URI("/outbound/connections/".concat(config.getName()))).build();
		} catch (MqttException mqttEx) {
			Logger.debug(mqttEx);
			return new ResponseEntity<>(mqttEx.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			Logger.error(e);
			return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/outbound/connections/{name}/messages", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> publishMessage(@PathVariable(name = "name") String name,
			@RequestBody String message) {
		try {
			client.publishMessage(name, message);
			return ResponseEntity.ok().build();
		} catch (MqttConnNotFoundException e) {
			Logger.debug(e);
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			Logger.error(e);
			return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/inbound/pipes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createNamedPipe(@RequestBody NamedPipeConfig config) {
		try {
			client.createNamedPipe(config);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			Logger.error(e);
			return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
