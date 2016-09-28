package com.epam.example.ecinema.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	private static final String PASSWORD = "qwerty123456";
	private static final String LOGIN_SESSION_VALUE = "adminadmin";

	@RequestMapping(value={"", "/"}, method = RequestMethod.GET)
	public ModelAndView homePage(HttpServletResponse response, HttpSession session) throws IOException {
		Object sess = session.getAttribute("loginSession");
		if (sess != null) {
			return new ModelAndView("home.page");
		} else {
			return new ModelAndView("login.page");
		}
	}
	
	@RequestMapping(value={"", "/"}, method = RequestMethod.POST)
	public String login(@RequestParam(value="password", required=true) String password, HttpSession session, Model model) {
		if (PASSWORD.equals(password)) {
			session.setAttribute("loginSession", LOGIN_SESSION_VALUE);
			return "redirect:/";
		} else {
			model.addAttribute("errorMessage", "Wrong password!");
			return "login.page";
		}
	}
	
	@RequestMapping(value="/logout", method = {RequestMethod.GET, RequestMethod.POST})
	public String logout(HttpSession session) {
		session.removeAttribute("loginSession");
		return "redirect:/";
	}
}
