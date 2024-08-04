package service;

import java.util.List;

import domain.Boek;
import jakarta.servlet.http.HttpServletRequest;

public interface BoekService {
	public Iterable<Boek> findAll();

	public Boek findById(Long id);

	public Boek save(HttpServletRequest request, Boek boek);

	public List<Boek> findAllSortedByAantalSterren();

	public void delete(Long id);

	public Boek updateBook(Boek boek);

	public Boek findByIsbn(String isbn);

}
