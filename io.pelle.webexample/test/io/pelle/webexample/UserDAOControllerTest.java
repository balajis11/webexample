package io.pelle.webexample;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
public class UserDAOControllerTest extends BaseTest
{

	private static MediaType MEDIA_TYPE = MediaType.parseMediaType("application/json;charset=UTF-8");

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void getAllUsers() throws Exception
	{
		this.mockMvc.perform(get("/user/all").accept(MEDIA_TYPE)).andExpect(status().isOk()).andExpect(content().contentType(MEDIA_TYPE))
				.andExpect(jsonPath("$").isArray());
	}

}