package com.springBoot_bibliotheek;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = EwdOpdrachtBibliotheekApplication.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class EwdOpdrachtBibliotheekApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getLoginPage() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("login/login"));
	}

	@WithMockUser(username = "user", roles = { "USER" })
	@Test
	public void testAccessForUser() throws Exception {
		mockMvc.perform(get("/bibliotheek/saveBook")).andExpect(status().isForbidden());
	}

	@WithMockUser(username = "admin", roles = { "ADMIN" })
	@Test
	public void testAccessForAdmin() throws Exception {
		mockMvc.perform(get("/bibliotheek/saveBook")).andExpect(status().isOk());
	}
}