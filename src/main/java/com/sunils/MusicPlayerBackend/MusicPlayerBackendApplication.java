package com.sunils.MusicPlayerBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

import static java.lang.System.exit;

@SpringBootApplication
public class MusicPlayerBackendApplication {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Invalid Arguments!");
			exit(1);
		}
		SpringApplication app = new SpringApplication(MusicPlayerBackendApplication.class);
		Properties properties = new Properties();
		properties.put("spring.datasource.url","jdbc:mysql://"+args[0]+":3306/MusicPlayer");
		app.setDefaultProperties(properties);
		app.run(args);
	}

}
