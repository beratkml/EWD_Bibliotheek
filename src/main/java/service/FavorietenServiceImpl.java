package service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Boek;
import domain.Favoriet;
import domain.User;
import repository.FavorietRepository;
import repository.GebruikerRepository;

@Service
public class FavorietenServiceImpl implements FavorietenService {
	@Autowired
	private FavorietRepository favorietRepo;

	@Autowired
	private GebruikerRepository userRepo;

	@Override
	public void delete(Boek book, User gebruiker) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		Set<Favoriet> favorieten = new HashSet<>(gebruiker.getFavorieten());
		logger.warn(favorieten.toString() + "");
		if (gebruiker != null) {
			for (Favoriet favoriet : favorieten) {
				gebruiker.removeFavorite(book);
				favorietRepo.delete(favoriet);
			}
			userRepo.save(gebruiker);
		}
	}
}
