package com.springBoot_bibliotheek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Boek;
import jakarta.annotation.security.PermitAll;
import service.BibliotheekService;

@RestController
@RequestMapping("/rest")
public class BibliotheekRestController {
	@Autowired
	private BibliotheekService bibliotheekService;

	@GetMapping(value = "/bibliotheek")
	public Iterable<Boek> boekenList(Model model, Authentication authentication) {
		return bibliotheekService.findAll();
	}

	@GetMapping(value = "/bibliotheek/{isbn}")
	public Boek getBoekByIDBN(@PathVariable("isbn") String isbn, Model model, Authentication authentication) {
		return bibliotheekService.findByIsbn(isbn);
	}

	@GetMapping(value = "/bibliotheek/auteur={author}")
	@PermitAll
	Iterable<Boek> getBooksWithAuthor(@PathVariable("author") Long author) {
		return bibliotheekService.findAllBooksByAuteurId(author);
	}

}
