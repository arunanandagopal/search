package com.netpay.filesystem.search;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;

import com.netpay.filesystem.search.helper.TextFileLoaderHelper;

@SpringBootApplication
public class SearchSpringBootApplication {

	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
	TextFileLoaderHelper textFileLoaderHelper;
	
    private static final Logger log = LoggerFactory.getLogger(SearchSpringBootApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SearchSpringBootApplication.class, args);
	}
	
	 @PostConstruct
	    private void init() {
	        log.info("InitDemoApplication initialization logic ...");
	        try {
				textFileLoaderHelper.read(resourceLoader.getResource("classpath:testfile.txt").getFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

}
