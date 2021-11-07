package nz.ac.canterbury.dataprovenancedemo;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.Arrays;

@SpringBootApplication
public class DataProvenanceDemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DataProvenanceDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DataProvenanceDemoApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			logger.info("Loaded springboot beans:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				logger.info(beanName);
			}
		};
	}

	/**
	 * Creates a TCP instance of the H2 in-memory database to be used by other processes
	 * @return TCP server allowing multiple connections on port 9093
	 * @throws SQLException If an exception is thrown during execution
	 */
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DB() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9093");
	}
}
