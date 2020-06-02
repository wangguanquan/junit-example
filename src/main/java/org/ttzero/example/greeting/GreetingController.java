package org.ttzero.example.greeting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ttzero.example.index.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GreetingController {

	private final GreetingService service;

	public GreetingController(GreetingService service) {
		this.service = service;
	}

	@GetMapping("/greeting")
	public String greeting() {
		return service.greet();
	}

	@PostMapping("/putting")
    public String putting(@RequestBody Auth auth) throws JsonProcessingException {
	    return new ObjectMapper().writeValueAsString(auth);
    }
}
