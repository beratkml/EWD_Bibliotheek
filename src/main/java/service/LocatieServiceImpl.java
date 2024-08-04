package service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Locatie;
import repository.LocatieRepository;

@Service
public class LocatieServiceImpl implements LocatieService {
	@Autowired
	private LocatieRepository locatieRepo;

	@Override
	public Locatie save(Locatie locatie) {
		return locatieRepo.save(locatie);
	}

	@Override
	public Set<Locatie> saveAllLocaties(Set<Locatie> locaties) {
		Iterable<Locatie> savedAuteurs = locatieRepo.saveAll(locaties);
		Set<Locatie> result = new HashSet<>();
		savedAuteurs.forEach(result::add);
		return result;
	}
}
