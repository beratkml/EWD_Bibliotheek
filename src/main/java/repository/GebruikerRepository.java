package repository;

import org.springframework.data.repository.CrudRepository;

import domain.User;

public interface GebruikerRepository extends CrudRepository<User, String> {
	User findByUsername(String username);
}
