package nz.ac.canterbury.dataprovenancedemo;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
public class DataProvenanceDemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DataProvenanceDemoApplication.class);

	public static void main(String[] args) {
		//DEBUG
		System.out.println("Hello, world from the main app!");
		printClassLoaderChain(DataProvenanceDemoApplication.class);
//		nz.ac.wgtn.veracity.provenance.injector.tracker.jee.ProvenanceTrackerFilter dummy;
////		System.out.println("(in main app) DEBUG_testCrossAppCommunication=" + nz.ac.wgtn.veracity.provenance.injector.tracker.jee.ProvenanceTrackerFilter.class.getFields());
//		System.out.println("(in main app) DEBUG_testCrossAppCommunication=" + nz.ac.wgtn.veracity.provenance.injector.instrumentation.ProvenanceAgent.class.getFields());
////		System.out.println("(in main app) DEBUG_testCrossAppCommunication=" + nz.ac.wgtn.veracity.provenance.injector.tracker.jee.ProvenanceTrackerFilter.DEBUG_testCrossAppCommunication);
//		System.out.println("(in main app) DEBUG_testCrossAppCommunication=" + nz.ac.wgtn.veracity.provenance.injector.instrumentation.ProvenanceAgent.DEBUG_testCrossAppCommunication);
//		System.out.println("We could at least declare a local var of type nz.ac.wgtn.veracity.provenance.injector.tracker.jee.ProvenanceTrackerFilter!");

		SpringApplication.run(DataProvenanceDemoApplication.class, args);
	}

	//DEBUG
	static void printClassLoaderChain(Class c) {
		System.out.println("ClassLoader chain for " + c + ":");
		for (ClassLoader cl = c.getClassLoader(); cl != null; cl = cl.getClass().getClassLoader()) {
			Class cc = cl.getClass();
			System.out.println("loader=" + cl + ", class=" + cc + ", name=" + cc.getName() + ", canonicalName=" + cc.getCanonicalName() + ", simpleName=" + cc.getSimpleName() + ", typeName=" + cc.getTypeName());
		}
		System.out.println("(null, meaning the bootstrap ClassLoader)\nThe end.");
	}


	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

//			logger.info("Loaded springboot beans:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				logger.info(beanName);
//			}
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
