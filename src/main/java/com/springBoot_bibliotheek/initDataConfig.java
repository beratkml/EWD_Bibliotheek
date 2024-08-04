package com.springBoot_bibliotheek;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import domain.Auteur;
import domain.Authority;
import domain.Boek;
import domain.Locatie;
import domain.User;
import repository.AuteurRepository;
import repository.BoekRepository;
import repository.GebruikerRepository;
import repository.LocatieRepository;

@Component
public class initDataConfig implements CommandLineRunner {

	@Autowired
	private BoekRepository boekRepo;

	@Autowired
	private AuteurRepository auteurRepo;

	@Autowired
	private GebruikerRepository userRepo;

	@Autowired
	private LocatieRepository locatieRepo;

	@Override
	public void run(String... args) {
		Locatie locatie1 = new Locatie(60, 120, "boeki");
		Locatie locatie2 = new Locatie(80, 250, "abc");
		Locatie locatie3 = new Locatie(70, 150, "aaa");
		Locatie locatie4 = new Locatie(55, 100, "abcabc");
		Locatie locatie5 = new Locatie(60, 150, "locatieL");

		Auteur auteur1 = new Auteur("J.K. Rowling");
		Auteur auteur2 = new Auteur("Stephen King");
		Auteur auteur3 = new Auteur("Agatha Christie");

		Boek boek1 = new Boek("Harry Potter and the Philosopher's Stone", "9789027439642", 10.99);
		Boek boek2 = new Boek("Harry Potter and the Chamber of Secrets", "9780575087057", 11.99);
		Boek boek3 = new Boek("Murder on the Orient Express", "9780679864417", 9.99);
		Boek boek4 = new Boek("To Kill a Mockingbird", "9780061120084", 7.99);
		Boek boek5 = new Boek("The Great Gatsby", "9780743273565", 8.99);

		auteur1.addBoeken(Arrays.asList(boek1, boek2));
		auteur2.addBoeken(Arrays.asList(boek2, boek4));
		auteur3.addBoeken(Arrays.asList(boek1, boek3, boek5));

		boek1.addAuteurs(Arrays.asList(auteur1, auteur2, auteur3));
		boek2.addAuteurs(Arrays.asList(auteur1));
		boek3.addAuteurs(Arrays.asList(auteur3));
		boek4.addAuteurs(Arrays.asList(auteur2));
		boek5.addAuteurs(Arrays.asList(auteur3));

		boek1.addLocatie(locatie1);
		locatie1.setBoek(boek1);
		boek2.addLocatie(locatie2);
		locatie2.setBoek(boek2);
		boek3.addLocatie(locatie3);
		locatie3.setBoek(boek3);
		boek4.addLocatie(locatie4);
		locatie4.setBoek(boek4);
		boek5.addLocatie(locatie5);
		locatie5.setBoek(boek5);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String userPassword = passwordEncoder.encode("user");
		String adminPassword = passwordEncoder.encode("admin");
		String beratPassword = passwordEncoder.encode("berat");

		User user = new User("user", userPassword, true);
		User admin = new User("admin", adminPassword, true);
		User berat = new User("berat", beratPassword, true);

		Authority userAuthority = new Authority();
		userAuthority.setUsername(user.getUsername());
		userAuthority.setAuthority("ROLE_USER");

		Authority beratAuthority = new Authority();
		beratAuthority.setUsername(berat.getUsername());
		beratAuthority.setAuthority("ROLE_USER");

		Authority adminAuthority = new Authority();
		adminAuthority.setUsername(admin.getUsername());
		adminAuthority.setAuthority("ROLE_ADMIN");

		user.getAuths().add(userAuthority);
		berat.getAuths().add(beratAuthority);
		admin.getAuths().add(adminAuthority);

		user.setMaxAantalFavorieten(2);
		berat.setMaxAantalFavorieten(3);
		admin.setMaxAantalFavorieten(4);

		boekRepo.saveAll(Arrays.asList(boek1, boek2, boek3, boek4, boek5));
		auteurRepo.saveAll(Arrays.asList(auteur1, auteur2, auteur3));

		locatieRepo.saveAll(Arrays.asList(locatie1, locatie2, locatie3, locatie4, locatie5));

		userRepo.save(berat);
		userRepo.save(user);
		userRepo.save(admin);
	}

}
