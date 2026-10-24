package com.example.PinSoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class PinSoulApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./").ignoreIfMalformed().ignoreIfMissing().load();

		setIfAbsent("DB_URL", dotenv.get("DB_URL"));
		setIfAbsent("DB_USERNAME", dotenv.get("DB_USERNAME"));
		setIfAbsent("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(PinSoulApplication.class, args);
	}

	private static void setIfAbsent(String key, String dotenvVal) {
		String envVal = System.getenv(key);
		if (envVal != null && !envVal.isBlank()) {
			System.setProperty(key, envVal);
		} else if (dotenvVal != null && !dotenvVal.isBlank()) {
			System.setProperty(key, dotenvVal);
		}
	}
}
