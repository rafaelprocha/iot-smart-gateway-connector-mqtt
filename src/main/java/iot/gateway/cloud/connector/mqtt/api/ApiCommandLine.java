package iot.gateway.cloud.connector.mqtt.api;

import java.io.IOException;

public class ApiCommandLine {

	private ApiCommandLine() {
	}

	public static void post(String connName, String message) throws IOException {
		ApiNamedPipe.write(ApiNamedPipe.DEFAULT_PIPE.concat(connName), message.getBytes());
	}

}