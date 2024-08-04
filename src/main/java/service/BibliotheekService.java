package service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Auteur;
import domain.Boek;
import domain.Locatie;
import domain.User;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class BibliotheekService {
	@Autowired
	private AuteurServiceImpl auteurService;
	@Autowired
	private BoekServiceImpl boekService;
	@Autowired
	private FavorietenServiceImpl favorietService;
	@Autowired
	private LocatieServiceImpl locatieService;
	@Autowired
	private UserServiceImpl userService;

	public Auteur findByIdAuteur(Long id) {
		return auteurService.findByIdAuteur(id);
	}

	public Iterable<Auteur> findAllAuteur() {
		return auteurService.findAllAuteur();
	}

	public Auteur save(Auteur auteur) {
		return auteurService.save(auteur);
	}

	public List<Auteur> saveAll(List<Auteur> auteurs) {
		return auteurService.saveAll(auteurs);
	}

	public Auteur findByNaam(String naam) {
		return auteurService.findByNaam(naam);
	}

	public Iterable<Boek> findAll() {
		return boekService.findAll();
	}

	public Boek findById(Long id) {
		return boekService.findById(id);
	}

	public Boek save(HttpServletRequest request, Boek boek) {
		return boekService.save(request, boek);
	}

	public List<Boek> findAllSortedByAantalSterren() {
		return boekService.findAllSortedByAantalSterren();
	}

	public void delete(Boek book, User gebruiker) {
		favorietService.delete(book, gebruiker);
	}

	public Locatie save(Locatie locatie) {
		return locatieService.save(locatie);
	}

	public Set<Locatie> saveAllLocaties(Set<Locatie> locaties) {
		return locatieService.saveAllLocaties(locaties);
	}

	public User save(User user) {
		return userService.save(user);
	}

	public User findByUsername(String username) {
		return userService.findByUsername(username);
	}

	public void deleteBoek(Long id) {
		boekService.delete(id);
	}

	public Boek updateBook(Boek boek) {
		return boekService.updateBook(boek);
	}

	public List<Boek> findAllBooksByAuteurId(Long auteurId) {
		return auteurService.findAllBooksByAuteurId(auteurId);
	}

	public Boek findByIsbn(String isbn) {
		return boekService.findByIsbn(isbn);
	}

	public boolean maxFavorieten(String username) {
		User user = userService.findByUsername(username);
		int max = user.getMaxAantalFavorieten();
		return user.getFavorieten().size() >= max;
	}
}
