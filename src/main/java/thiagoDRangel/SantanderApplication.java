package thiagoDRangel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SantanderApplication {

	public static void main(String[] args) {

		SpringApplication.run(SantanderApplication.class, args);
		System.out.println("Server on the PORT 8080");
	}

}
