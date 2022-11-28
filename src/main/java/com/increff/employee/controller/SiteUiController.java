package com.increff.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteUiController extends AbstractUiController {

	// WEBSITE PAGES
	@RequestMapping(value = "")
	public ModelAndView index() {
		return mav("index.html");
	}

	@RequestMapping(value = "/site/login")
	public ModelAndView login() {
		return mav("login.html");
	}

	@RequestMapping(value = "/site/logout")
	public ModelAndView logout() {
		return mav("logout.html");
	}

	@RequestMapping(value = "/site/product")
	public ModelAndView pricing() {
		return mav("product.html");
	}

	@RequestMapping(value = "/site/brand")
	public ModelAndView features() {
		return mav("brand.html");
	}

	@RequestMapping(value = "/site/inventory")
	public ModelAndView inventory() {
		return mav("inventory.html");
	}

	@RequestMapping(value = "/site/order")
	public ModelAndView order() {
		return mav("order.html");
	}

}
