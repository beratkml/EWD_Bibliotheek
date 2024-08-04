package perform;

import org.springframework.web.reactive.function.client.WebClient;

import domain.Boek;
import reactor.core.publisher.Mono;

public class PerformRestBibliotheek {
	private final String SERVER_URI = "http://localhost:8080/rest";

	private WebClient webClient = WebClient.create();

	public PerformRestBibliotheek() throws Exception {
		System.out.println("\n------- GET ALL -------");
		getAllBoeken();
		System.out.println("\n------- GET 9789027439642 ------- ");
		getBoekByISBN("9789027439642");
		System.out.println("\n------- GET ALL BOEKEN AUTEUR ID 3 ------- ");
		getAllBookByAuthorID(Long.valueOf(3));
	}

	private void getAllBoeken() {
		webClient.get().uri(SERVER_URI + "/bibliotheek").retrieve().bodyToFlux(Boek.class).flatMap(e -> {
			printEmpData(e);
			return Mono.empty();
		}).blockLast();
	}

	private void getBoekByISBN(String isbn) {
		getBoek(SERVER_URI + "/bibliotheek/" + isbn);
	}

	private void getAllBookByAuthorID(Long id) {
		webClient.get().uri(SERVER_URI + "/bibliotheek/auteur=" + id).retrieve().bodyToFlux(Boek.class).flatMap(e -> {
			printEmpData(e);
			return Mono.empty();
		}).blockLast();
	}

	private void getBoek(String uri) {
		webClient.get().uri(uri).retrieve().bodyToMono(Boek.class).doOnSuccess(emp -> printEmpData(emp)).block();
	}

	private void printEmpData(Boek emp) {
		System.out.printf("%s \n", emp.toString());
	}
}
