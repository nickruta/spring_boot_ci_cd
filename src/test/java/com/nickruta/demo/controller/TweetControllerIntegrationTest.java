package com.nickruta.demo.controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nickruta.demo.model.Tweet;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TweetControllerIntegrationTest {

	final String BASE_URL = "http://localhost:8080/";

	@Autowired
	private TweetController tweetController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(tweetController).build();
	}

	@Test
	public void testIsRetweetedAndStatus() throws Exception{
		//Mocking Controller
		tweetController = mock(TweetController.class);
		
		Long test_long = new Long(42424242);
		Tweet tweet = new Tweet("1", "none", false, "none", "none",
        		"none", "none", "none", "none", "none", test_long,
    			"none", false, "none", "none","none", test_long, 
    			"none", "none", "Sun Sep 22 01:21:26 +0000 2019","none", false, "none", "none", "none", false, "none", 
    			"none", "none","none", "none", "none", "none");
		
		boolean isRetweeted = tweetController.isRetweeted(tweet);
		assertThat(isRetweeted, is(false));
		
//		Tweet tweetTrue = new Tweet("1", "none", true, "none", "none",
//        		"none", "none", "none", "none", "none", test_long,
//    			"none", true, "none", "none","none", test_long, 
//    			"none", "none", "Sun Sep 22 01:21:26 +0000 2019","none", true, "none", "none", "none", true, "none", 
//    			"none", "none","none", "none", "none", "none");
//		
//		isRetweeted = tweetController.isRetweeted(tweetTrue);
//		assertThat(isRetweeted, is(true));

		this.mockMvc.perform(get("/tweets")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isOk());
	}

	@Test
	public void contextLoads() {
	}
}