package repository;

import org.springframework.data.repository.CrudRepository;

import domain.Favoriet;

public interface FavorietRepository extends CrudRepository<Favoriet, Long> {

}
