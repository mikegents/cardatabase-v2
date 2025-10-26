package com.example.cardatabase_v2;

import com.example.cardatabase_v2.DataAccessLayer.Car;
import com.example.cardatabase_v2.DataAccessLayer.CarRepository;
import com.example.cardatabase_v2.DataAccessLayer.Owner;
import com.example.cardatabase_v2.DataAccessLayer.OwnerRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@SpringBootApplication
@EnableJpaAuditing // enables @CreatedDate, @LastModifiedDate auditing
public class CardatabaseV2Application {
	private static final Logger log = (Logger) LoggerFactory.getLogger(CardatabaseV2Application.class);
	public static void main(String[] args) {
		SpringApplication.run(CardatabaseV2Application.class, args);
	}
	@Bean
	CommandLineRunner seed(OwnerRepository owners, CarRepository cars) {
		return args -> {
			// Owners
			Owner alice = owners.save(
					Owner.builder().firstName("Alice").lastName("Smith").email("alice@example.com").phone("+1-555-1111").build()
			);
			Owner bob = owners.save(
					Owner.builder().firstName("Bob").lastName("Jones").email("bob@example.com").phone("+1-555-2222").build()
			);
			// Cars (attach owner from the ManyToOne side)
			cars.save(Car.builder()
					.brand("Ford").model("Mustang").color("Red")
					.registrationNumber("ADF-1121").modelYear(2023).price(59000)
					.owner(alice)
					.build());
			cars.save(Car.builder()
					.brand("Toyota").model("Prius").color("Silver")
					.registrationNumber("KKO-0212").modelYear(2022).price(39000)
					.owner(alice)
					.build());
			cars.save(Car.builder()
					.brand("Toyota").model("Corolla").color("Blue")
					.registrationNumber("ABC-1234").modelYear(2020).price(20000)
					.owner(bob)
					.build());
			log.info("Seeded {} owners and {} cars", owners.count(), cars.count());
		};
	}
}
