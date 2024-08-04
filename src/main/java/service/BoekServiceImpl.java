package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Auteur;
import domain.Boek;
import domain.Locatie;
import jakarta.servlet.http.HttpServletRequest;
import repository.AuteurRepository;
import repository.BoekRepository;
import repository.LocatieRepository;

@Service
public class BoekServiceImpl implements BoekService {
	@Autowired
	private BoekRepository boekRepo;

	@Autowired
	private AuteurRepository auteurRepo;

	@Autowired
	private LocatieRepository locatieRepo;

	@Override
	public Iterable<Boek> findAll() {
		return boekRepo.findAll();
	}

	@Override
	public Boek findById(Long id) {
		return boekRepo.findById(id).orElse(null);
	}

	@Override
	public Boek save(HttpServletRequest request, Boek boek) {

		String locatie1_plaatscode1 = request.getParameter("locatie1_plaatscode1");
		String locatie1_plaatscode2 = request.getParameter("locatie1_plaatscode2");
		String locatie1_plaatsnaam = request.getParameter("locatie1_plaatsnaam");

		String locatie2_plaatscode1 = request.getParameter("locatie2_plaatscode1");
		String locatie2_plaatscode2 = request.getParameter("locatie2_plaatscode2");
		String locatie2_plaatsnaam = request.getParameter("locatie2_plaatsnaam");

		String locatie3_plaatscode1 = request.getParameter("locatie3_plaatscode1");
		String locatie3_plaatscode2 = request.getParameter("locatie3_plaatscode2");
		String locatie3_plaatsnaam = request.getParameter("locatie3_plaatsnaam");

		String nieuweAuteur = request.getParameter("nieuweAuteur");

		List<Auteur> auteursToAdd = new ArrayList<>();
		Set<Locatie> locaties = new HashSet<>();

		addLocatieIfNotEmpty(locatie1_plaatscode1, locatie1_plaatscode2, locatie1_plaatsnaam, boek, locaties);
		addLocatieIfNotEmpty(locatie2_plaatscode1, locatie2_plaatscode2, locatie2_plaatsnaam, boek, locaties);
		addLocatieIfNotEmpty(locatie3_plaatscode1, locatie3_plaatscode2, locatie3_plaatsnaam, boek, locaties);

		if (!nieuweAuteur.isEmpty()) {
			Auteur param = new Auteur(nieuweAuteur);
			param.addBoeken(Arrays.asList(boek));
			auteursToAdd.add(param);
		}
		boek.addAuteurs(auteursToAdd);

		for (Locatie locatie : locaties) {
			locatie.setBoek(boek);
		}
		auteurRepo.saveAll(auteursToAdd);
		locatieRepo.saveAll(locaties);
		return boekRepo.save(boek);
	}

	@Override
	public List<Boek> findAllSortedByAantalSterren() {
		return boekRepo.findAllSortedByAantalSterren();
	}

	@Override
	public void delete(Long id) {
		boekRepo.deleteById(id);
	}

	@Override
	public Boek updateBook(Boek boek) {
		return boekRepo.save(boek);
	}

	private void addLocatieIfNotEmpty(String plaatscode1, String plaatscode2, String plaatsnaam, Boek boek,
			Set<Locatie> locaties) {
		if (!plaatscode1.isEmpty() && !plaatscode2.isEmpty() && !plaatsnaam.isEmpty()) {
			Locatie locatie = new Locatie(Integer.parseInt(plaatscode1), Integer.parseInt(plaatscode2), plaatsnaam);
			boek.addLocatie(locatie);
			locaties.add(locatie);
		}
	}

	@Override
	public Boek findByIsbn(String isbn) {
		return boekRepo.findByIsbn(isbn);
	}

}
