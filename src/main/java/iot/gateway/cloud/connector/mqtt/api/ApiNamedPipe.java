package iot.gateway.cloud.connector.mqtt.api;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.pmw.tinylog.Logger;
import org.springframework.stereotype.Component;

import iot.gateway.cloud.connector.mqtt.config.NamedPipeConfig;

@Component
public class ApiNamedPipe {

	private static final String CREATE_PIPE_COMMAND = "mkfifo ";
	private static final String PIPE_CONNECTION_MODE_READING = "r";
	private static final int THREAD_EXEC_INITIAL_DELAY = 1;
	private static final int THREAD_EXEC_INTERVAL = 1;
	private static final String PIPE_CONNECTION_MODE_WRITING = "rw";

	public static final String DEFAULT_PIPE = "/tmp/";

	private ApiClient client;

	public void create(NamedPipeConfig config, ApiClient client) throws IOException {
		this.client = client;
		Runtime.getRuntime().exec(CREATE_PIPE_COMMAND.concat(config.getNamedPipe()));
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(new NamedPipeExecutor(config), THREAD_EXEC_INITIAL_DELAY,
				THREAD_EXEC_INTERVAL, TimeUnit.MILLISECONDS);
	}

	public static void write(String namedPipe, byte[] message) throws IOException {
		try (RandomAccessFile pipe = new RandomAccessFile(namedPipe, PIPE_CONNECTION_MODE_WRITING)) {
			pipe.write(message);
			pipe.close();
		}
	}

	class NamedPipeExecutor implements Runnable {

		private NamedPipeConfig config;

		public NamedPipeExecutor(NamedPipeConfig config) {
			super();
			this.config = config;
		}

		@Override
		public void run() {
			try (RandomAccessFile pipe = new RandomAccessFile(config.getNamedPipe(), PIPE_CONNECTION_MODE_READING)) {
				String message = pipe.readLine();
				client.publishMessage(config.getConnectionName(), message);
			} catch (Exception e) {
				Logger.error(e);
			}
		}
	}

}
