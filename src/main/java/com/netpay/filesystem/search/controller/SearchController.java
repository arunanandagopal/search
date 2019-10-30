package com.netpay.filesystem.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netpay.filesystem.search.repository.SearchRepository;

/**
 * Main Controller
 * 
 * @author aruna
 *
 */
@RestController
public class SearchController {
	@Autowired
	private SearchRepository searchRepository;

	@GetMapping("/directory")
	public Model getdirectory(@RequestParam(value = "search", required = false) String search, Model model) {
		model.addAttribute("directories", searchRepository.findByPath(search + "*%@"));
		return model;
	}

}
