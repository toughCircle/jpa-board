package hello.springboardjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class SpringBoardJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoardJpaApplication.class, args);
	}

}
