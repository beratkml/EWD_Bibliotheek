package service;

import java.util.Set;

import domain.Locatie;

public interface LocatieService {
	public Locatie save(Locatie locatie);

	public Set<Locatie> saveAllLocaties(Set<Locatie> locaties);
}
