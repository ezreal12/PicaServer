package com.server.pica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainCont {
	@RequestMapping("test")
	public String test() {
		return "test";
	}
	@RequestMapping("")
	public String home() {
		return "home";
	}
}
