package com.springBoot_bibliotheek;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import domain.Auteur;
import domain.Boek;
import service.BibliotheekService;

@SpringBootTest(classes = EwdOpdrachtBibliotheekApplication.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class RestControllerTest {
	@Mock
	private BibliotheekService mock;

	private BibliotheekRestController controller;
	private MockMvc mockMvc;

	private final long ID = 1;
	private final String NAME = "Test";
	private double prijs = 2.5;
	private final String ISBN = "9780439358071";

	@BeforeEach
	public void before() {
		MockitoAnnotations.openMocks(this);
		controller = new BibliotheekRestController();
		mockMvc = standaloneSetup(controller).build();
		ReflectionTestUtils.setField(controller, "bibliotheekService", mock);
	}

	private Boek aBook(String name, String isbn, double prijs) {
		Boek emp = new Boek(name, isbn, prijs);
		return emp;
	}

	private void performRest(String uri) throws Exception {
		mockMvc.perform(get(uri)).andExpect(status().isOk()).andExpect(jsonPath("$.naam").value(NAME));
	}

	@Test
	public void testBoekByISBN_OK() throws Exception {
		Mockito.when(mock.findByIsbn(ISBN)).thenReturn(aBook(NAME, ISBN, prijs));
		performRest("/rest/bibliotheek/9780439358071");
		Mockito.verify(mock).findByIsbn(ISBN);
	}

	@Test
	public void testGetAllBoeken_emptyBookList() throws Exception {
		Mockito.when(mock.findAll()).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/rest/bibliotheek")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		Mockito.verify(mock).findAll();
	}

	@Test
	public void testGetAllBooks_noEmptyList() throws Exception {
		Boek boek1 = aBook(NAME, ISBN, prijs);
		Boek boek2 = aBook("aaaa", "9780575087057", 25.5);
		List<Boek> listBoek = List.of(boek1, boek2);
		Mockito.when(mock.findAll()).thenReturn(listBoek);

		mockMvc.perform(get("/rest/bibliotheek")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty()).andExpect(jsonPath("$[0].naam").value(NAME));

		Mockito.verify(mock).findAll();
	}

	@Test
	public void testGetAllBooksFromAuthor_noEmptyList() throws Exception {
		Boek boek1 = aBook(NAME, ISBN, prijs);
		Boek boek2 = aBook("aaaa", "9780575087057", 25.5);
		List<Auteur> listAuteurs = List.of(new Auteur("abc"), new Auteur("aaa"));
		boek1.addAuteurs(listAuteurs);
		List<Boek> listBoek = List.of(boek1, boek2);
		Mockito.when(mock.findAll()).thenReturn(listBoek);
		mockMvc.perform(get("/rest/bibliotheek")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty()).andExpect(jsonPath("$[0].auteurs[0].naam").value("abc"))
				.andExpect(jsonPath("$[0].auteurs[1].naam").value("aaa"));

		Mockito.verify(mock).findAll();
	}
}