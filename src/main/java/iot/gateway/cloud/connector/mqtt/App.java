package iot.gateway.cloud.connector.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import iot.gateway.cloud.connector.mqtt.api.ApiCommandLine;

@EnableAutoConfiguration
@EnableScheduling
@ComponentScan("iot.gateway.cloud.connector.mqtt")
public class App {

	public static void main(String[] args) throws Exception {
		if ((args.length == 3) && ("--cmd".equals(args[0]))) {
			ApiCommandLine.post(args[1], args[2]);
		} else {
			SpringApplication.run(App.class, args);
		}
	}

}
