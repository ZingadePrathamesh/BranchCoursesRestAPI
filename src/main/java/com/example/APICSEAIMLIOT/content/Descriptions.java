package com.example.APICSEAIMLIOT.content;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Descriptions {
	
	MessageSource messageSource;
	public Descriptions(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}



	@GetMapping(path = "/about-us")
	public String aboutUs() {
		Locale locale = LocaleContextHolder.getLocale();
		String message = messageSource.getMessage("about.us.description", null, "default", locale);
		return message;
	}
}
