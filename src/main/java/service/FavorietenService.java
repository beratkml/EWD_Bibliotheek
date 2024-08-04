package service;

import domain.Boek;
import domain.User;

public interface FavorietenService {
	public void delete(Boek book, User user);
}
