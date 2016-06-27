package com.egfavre;

import com.egfavre.services.EventsRepository;
import com.egfavre.services.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CalendarSpringApplication.class)
@WebAppConfiguration
public class CalendarSpringApplicationTests {

	@Autowired
	WebApplicationContext wac;
	MockMvc mockMvc;
	@Before
	public void before(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Autowired
	UserRepository users;
	@Autowired
	EventsRepository events;

	@Test
	public void testLoggin() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login")
						.param("username", "Alice")
						.param("password", "mypass")
		);
		Assert.assertTrue(users.count() == 1);
	}

	@Test
	public void testCreateEvent() throws Exception {
		testLoggin();

		mockMvc.perform(
				MockMvcRequestBuilders.post("/create-event")
				.param("description", "this is my event")
				.param("time", LocalDateTime.now().toString())
				.sessionAttr("username","Alice")
		);

		Assert.assertTrue(events.count() == 1);
	}
}
