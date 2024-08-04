package com.springBoot_bibliotheek;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Auteur;
import domain.Boek;
import domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import service.BibliotheekService;

@Controller
@RequestMapping("/bibliotheek")
public class BibliotheekController {

	@Autowired
	private BibliotheekService bibliotheekService;

	@GetMapping
	public String boekenList(Model model, Authentication authentication) {
		List<String> listRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		model.addAttribute("boeken", bibliotheekService.findAll());
		model.addAttribute("username", authentication.getName());
		model.addAttribute("userListRoles", listRoles);
		return "boek";
	}

	@GetMapping(value = "/meestFavorieten")
	public String favorieteBoeken(Model model, Authentication auth) {
		List<String> listRoles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		model.addAttribute("username", auth.getName());
		model.addAttribute("userListRoles", listRoles);
		model.addAttribute("boeken", bibliotheekService.findAllSortedByAantalSterren());
		return "favorieteBoeken";
	}

	@GetMapping(value = "/{id}")
	public String show(@PathVariable Long id, Model model, Authentication auth) {
		List<String> listRoles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		model.addAttribute("username", auth.getName());
		model.addAttribute("userListRoles", listRoles);

		Boek boek = bibliotheekService.findById(id);
		if (boek == null) {
			return "redirect:/boek";
		}
		model.addAttribute("boek", boek);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = bibliotheekService.findByUsername(username);
		boolean isBookInFavorites = user.getFavorieten().stream()
				.anyMatch(favoriet -> favoriet.getBoek().getId().equals(id));
		boolean maxFavorieten = bibliotheekService.maxFavorieten(username);
		if (isBookInFavorites) {
			model.addAttribute("showFavoriteButton", true);
			model.addAttribute("favoriteButtonText", "Verwijder uit favorieten");
		} else {
			if (maxFavorieten) {
				model.addAttribute("showFavoriteButton", false);
			} else {
				model.addAttribute("showFavoriteButton", true);
				model.addAttribute("favoriteButtonText", "Voeg toe aan favorieten");
			}
		}

		return "boekDetail";
	}

	@GetMapping("/wijzigBoek")
	public String showChange(@RequestParam("id") Long id, Model model, Authentication auth) {
		List<String> listRoles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		model.addAttribute("username", auth.getName());
		model.addAttribute("userListRoles", listRoles);
		Boek boek = bibliotheekService.findById(id);
		if (boek == null) {
			return "redirect:/boek";
		}
		Logger logger = LoggerFactory.getLogger(this.getClass());
		model.addAttribute("auteurs", boek.getAuteurs());
		logger.warn(boek.getAuteurs() + "");
		model.addAttribute("boek", boek);
		return "wijzigBoek";
	}

	@PostMapping("/wijzigBoek")
	public String updateBook(@Valid Boek updatedBoek, BindingResult result,
			@RequestParam(value = "auteursIds", required = false) String[] auteursIds, @RequestParam("id") Long id,
			Model model) {
		if (result.hasErrors()) {
			return "wijzigBoek";
		}

		Boek existingBoek = bibliotheekService.findById(id);
		if (existingBoek == null) {
			return "redirect:/bibliotheek";
		}

		existingBoek.setNaam(updatedBoek.getNaam());
		existingBoek.setIsbn(updatedBoek.getIsbn());
		existingBoek.setAankoopprijs(updatedBoek.getAankoopprijs());
		existingBoek.setAantalSterren(updatedBoek.getAantalSterren());

		Set<Auteur> auteurs = new HashSet<>();
		if (auteursIds != null) {
			for (String auteurId : auteursIds) {
				try {
					Long auteurIdLong = Long.valueOf(auteurId);
					Auteur auteur = bibliotheekService.findByIdAuteur(auteurIdLong);
					if (auteur != null) {
						auteurs.add(auteur);
					}
				} catch (NumberFormatException e) {
				}
			}
		}
		existingBoek.setAuteurs(auteurs);
		bibliotheekService.updateBook(existingBoek);

		return "redirect:/bibliotheek";
	}

	@PostMapping(value = "/boekDetail")
	public String addFavorite(@RequestParam("bookId") Long bookId, Model model, RedirectAttributes redirectAttributes) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = bibliotheekService.findByUsername(username);
		boolean isBookInFavorites = user.getFavorieten().stream()
				.anyMatch(favoriet -> favoriet.getBoek().getId().equals(bookId));
		Boek book = bibliotheekService.findById(bookId);
		logger.warn(model + "");
		if (isBookInFavorites) {
			bibliotheekService.delete(book, user);
			user.removeFavorite(book);
			redirectAttributes.addFlashAttribute("successMessage", book.getNaam() + " werd  verwijderd uit favorieten");
		} else {
			user.addFavorite(book);
			redirectAttributes.addFlashAttribute("successMessage", book.getNaam() + " werd  toegevoegd aan favorieten");
		}
		model.addAttribute("book", book);
		bibliotheekService.save(user);
		return "redirect:/bibliotheek";
	}

	@GetMapping("/saveBook")
	public String saveBook(Model model, Authentication auth) {
		List<String> listRoles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		model.addAttribute("username", auth.getName());
		model.addAttribute("userListRoles", listRoles);
		Iterable<Auteur> alleAuteurs = bibliotheekService.findAllAuteur();
		model.addAttribute("auteurs", alleAuteurs);
		model.addAttribute("boek", new Boek());
		return "saveBook";
	}

	@PostMapping("/saveBook")
	public String saveBook(@Valid Boek boek, BindingResult result,
			@RequestParam(value = "auteurs", required = false) List<Long> auteursIds, Model model, Authentication auth,
			HttpServletRequest request) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		if (result.hasErrors()) {
			List<String> listRoles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
			model.addAttribute("username", auth.getName());
			model.addAttribute("userListRoles", listRoles);
			Iterable<Auteur> alleAuteurs = bibliotheekService.findAllAuteur();
			model.addAttribute("auteurs", alleAuteurs);
			model.addAttribute("boek", boek);
			logger.warn("" + result.getFieldError().getDefaultMessage());
			return "saveBook";
		}

		bibliotheekService.save(request, boek);

		return "redirect:/bibliotheek";
	}

}