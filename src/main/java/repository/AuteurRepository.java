package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import domain.Auteur;
import domain.Boek;

public interface AuteurRepository extends CrudRepository<Auteur, Long> {
	Auteur findByNaam(String naam);

	@Query("SELECT b FROM Boek b JOIN b.auteurs a WHERE a.id = :auteurId")
	List<Boek> findAllBooksByAuteurId(@Param("auteurId") Long auteurId);

}
