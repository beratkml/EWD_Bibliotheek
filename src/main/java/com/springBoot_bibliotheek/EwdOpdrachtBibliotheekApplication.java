package com.springBoot_bibliotheek;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import perform.PerformRestBibliotheek;
import service.AuteurServiceImpl;
import service.BibliotheekService;
import service.BoekServiceImpl;
import service.FavorietenServiceImpl;
import service.LocatieServiceImpl;
import service.UserServiceImpl;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan({ "domain" })
public class EwdOpdrachtBibliotheekApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(EwdOpdrachtBibliotheekApplication.class, args);
		try {
			new PerformRestBibliotheek();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/bibliotheek");
	}

	@Bean
	BibliotheekService bibliotheekService() {
		return new BibliotheekService();
	}

	@Bean
	AuteurServiceImpl auteurServiceImpl() {
		return new AuteurServiceImpl();
	}

	@Bean
	BoekServiceImpl boekServiceImpl() {
		return new BoekServiceImpl();
	}

	@Bean
	UserServiceImpl userServiceImpl() {
		return new UserServiceImpl();
	}

	@Bean
	FavorietenServiceImpl favorietenServiceImpl() {
		return new FavorietenServiceImpl();
	}

	@Bean
	LocatieServiceImpl locatieServiceImpl() {
		return new LocatieServiceImpl();
	}

	@Bean
	SessionLocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}
}
