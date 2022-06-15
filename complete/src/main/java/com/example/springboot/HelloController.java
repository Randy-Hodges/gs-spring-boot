package com.example.springboot;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class HelloController {

	Counter visitCounter;

    public HelloController(MeterRegistry registry) {
        visitCounter = Counter.builder("visit_counter")
            .description("Number of visits to the site")
            .register(registry);
    }

	@GetMapping("/")
	public String index() {
        visitCounter.increment();
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/stuff")
	public String index2() {
		return "You requested the \'stuff\' route";
	}

	@GetMapping("/metrics")
	public ModelAndView index3(ModelMap model) {
		model.addAttribute("attribute", "redirectWithRedirectPrefix");
        return new ModelAndView("redirect:/actuator/prometheus", model);
	}

}


