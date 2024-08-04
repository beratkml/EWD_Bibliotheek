package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Auteur;
import domain.Boek;
import repository.AuteurRepository;

@Service
public class AuteurServiceImpl implements AuteurService {
	@Autowired
	private AuteurRepository auteurRepo;

	@Override
	public Auteur findByIdAuteur(Long id) {
		return auteurRepo.findById(id).orElse(null);
	}

	@Override
	public Iterable<Auteur> findAllAuteur() {
		return auteurRepo.findAll();
	}

	@Override
	public Auteur save(Auteur auteur) {
		return auteurRepo.save(auteur);
	}

	@Override
	public List<Auteur> saveAll(List<Auteur> auteurs) {
		return auteurs;
	}

	@Override
	public Auteur findByNaam(String naam) {
		return auteurRepo.findByNaam(naam);
	}

	@Override
	public List<Boek> findAllBooksByAuteurId(Long auteurId) {
		return auteurRepo.findAllBooksByAuteurId(auteurId);
	}

}
