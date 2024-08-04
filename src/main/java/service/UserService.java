package service;

import domain.User;

public interface UserService {
	public User save(User user);

	public User findByUsername(String username);
}
