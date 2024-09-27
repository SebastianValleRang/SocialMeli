package co.mercadolibre.SocialMeli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocialMeliApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMeliApplication.class, args);
		String version = System.getProperty("java.version");
		System.out.println("Versiajgdfjhagfn de Java: " + version);

	}

}
