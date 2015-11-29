/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;

import com.lucas.services.benchmark.BenchmarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
public class HomeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	
	private final BenchmarkService benchmarkService; 
	
	@Inject
	public HomeController(BenchmarkService benchmarkService) {
		this.benchmarkService = benchmarkService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String rootContext() {
		return "index";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		LOG.info("Welcome home! The client locale is {}.", locale); 

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		boolean boo = benchmarkService.authenticate("bob", "doesn't matter");
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("inOrOut", boo );
		
		return "home";
	}
	
}
