package mogikanensoftware.xml.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BootServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootServiceApplication.class, args);
	}
}
