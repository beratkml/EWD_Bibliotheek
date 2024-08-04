package com.springBoot_bibliotheek;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
	@GetMapping
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {

		if (error != null) {
			model.addAttribute("error", "Gebruikersnaam of wachtwoord is ongeldig!");
		}
		if (logout != null) {
			model.addAttribute("msg", "U bent succesvol uitgelogd");
		}
		return "login/login";
	}
}
