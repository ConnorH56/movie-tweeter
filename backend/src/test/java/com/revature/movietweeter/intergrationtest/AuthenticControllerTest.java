package com.revature.movietweeter.intergrationtest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.movietweeter.dto.SignUpDTO;
import com.revature.movietweeter.model.User;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthenticControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private EntityManagerFactory emf;
	
	@Autowired
	private ObjectMapper mapper;
	
	@BeforeEach
	public void setUp() {
		
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();
		
		User connor = new User("mike", "pass12345");
		session.persist(connor);
		
		tx.commit();
		
		session.close();
	}
	
	@Test
	public void testSignUp() throws Exception {
		
		SignUpDTO dto = new SignUpDTO("connor_huston", "pass12345");
		String jsonToSend = mapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup").content(jsonToSend)
				.contentType(MediaType.APPLICATION_JSON);
		
		this.mvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().is(201));
	}
	
//	@Test
//	public void testLogin() throws Exception {
//		
//		SignUpDTO dto = new SignUpDTO("connor_huston", "pass12345");
//		String jsonToSend = mapper.writeValueAsString(dto);
//		
//		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login").content(jsonToSend)
//				.contentType(MediaType.APPLICATION_JSON);
//		
//		this.mvc.perform(builder)
//			.andExpect(MockMvcResultMatchers.status().is(201));
//	}
	
//	@Test
//	public void testLoginStatus_loggedIn() throws Exception {
//		
//		User fakeUser = new User("leon", "67890");
//		fakeUser.setId(2);
//		
//		MockHttpSession session = new MockHttpSession();
//		session.setAttribute("currentuser", fakeUser);
//		
//		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/loginstatus").session(session);
//		
//		String expectedJsonUser = mapper.writeValueAsString(fakeUser);
//		
//		this.mvc.perform(builder)
//				.andExpect(MockMvcResultMatchers.status().is(200))
//				.andExpect(MockMvcResultMatchers.content().json(expectedJsonUser));
//	}

}
