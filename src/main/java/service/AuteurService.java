package service;

import java.util.List;

import domain.Auteur;
import domain.Boek;

public interface AuteurService {
	public Auteur findByIdAuteur(Long id);

	public Auteur findByNaam(String naam);

	public Iterable<Auteur> findAllAuteur();

	public Auteur save(Auteur auteur);

	public List<Auteur> saveAll(List<Auteur> auteurs);

	public List<Boek> findAllBooksByAuteurId(Long auteurId);
}
