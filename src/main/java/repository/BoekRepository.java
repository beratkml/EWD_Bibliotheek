package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import domain.Boek;

public interface BoekRepository extends CrudRepository<Boek, Long> {

	@Query("SELECT b FROM Boek b ORDER BY b.aantalSterren DESC,b.naam ASC")
	List<Boek> findAllSortedByAantalSterren();

	Boek findByIsbn(String ISBN);
}
