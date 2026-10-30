package com.example.PinSoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class PinSoulApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./").ignoreIfMalformed().ignoreIfMissing().load();

		// 1. Map Railway's PORT environment variable to Spring Boot's server.port
		String port = System.getenv("PORT");
		if (port != null && !port.isBlank()) {
			System.setProperty("server.port", port);
		}

		// 2. Resolve Database properties (supporting DB_URL, MYSQL_URL, or individual Railway MySQL variables)
		resolveDatabaseProperties(dotenv);

		SpringApplication.run(PinSoulApplication.class, args);
	}

	private static void resolveDatabaseProperties(Dotenv dotenv) {
		String dbUrl = getVal("DB_URL", dotenv);
		String mysqlHost = getVal("MYSQLHOST", dotenv);

		if (dbUrl != null && !dbUrl.isBlank()) {
			// Convert mysql:// into jdbc:mysql:// if needed
			if (dbUrl.startsWith("mysql://")) {
				dbUrl = "jdbc:" + dbUrl;
			}
			System.setProperty("DB_URL", dbUrl);
			System.setProperty("spring.datasource.url", dbUrl);
		} else if (mysqlHost != null && !mysqlHost.isBlank()) {
			String mysqlPort = getVal("MYSQLPORT", dotenv);
			if (mysqlPort == null || mysqlPort.isBlank()) mysqlPort = "3306";
			String mysqlDatabase = getVal("MYSQLDATABASE", dotenv);
			if (mysqlDatabase == null || mysqlDatabase.isBlank()) mysqlDatabase = "railway";

			String generatedUrl = "jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "/" + mysqlDatabase
					+ "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			System.setProperty("DB_URL", generatedUrl);
			System.setProperty("spring.datasource.url", generatedUrl);
		}

		String dbUser = getVal("DB_USERNAME", dotenv);
		if (dbUser == null || dbUser.isBlank()) {
			dbUser = getVal("MYSQLUSER", dotenv);
		}
		if (dbUser != null && !dbUser.isBlank()) {
			System.setProperty("DB_USERNAME", dbUser);
			System.setProperty("spring.datasource.username", dbUser);
		}

		String dbPassword = getVal("DB_PASSWORD", dotenv);
		if (dbPassword == null || dbPassword.isBlank()) {
			dbPassword = getVal("MYSQLPASSWORD", dotenv);
		}
		if (dbPassword != null && !dbPassword.isBlank()) {
			System.setProperty("DB_PASSWORD", dbPassword);
			System.setProperty("spring.datasource.password", dbPassword);
		}
	}

	private static String getVal(String key, Dotenv dotenv) {
		String envVal = System.getenv(key);
		if (envVal != null && !envVal.isBlank()) {
			return envVal;
		}
		return dotenv != null ? dotenv.get(key) : null;
	}
}
