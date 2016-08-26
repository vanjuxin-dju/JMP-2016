package com.epam.example.classloading.animals.impl;

import org.apache.log4j.Logger;

import com.epam.example.classloading.animals.Animal;

public class Dog implements Animal {
	private static final Logger LOGGER = Logger.getLogger(Dog.class);
	
	@Override
	public void play() {
		LOGGER.info("Dog plays");
	}

	@Override
	public void voice() {
		LOGGER.info("Woff");
	}

}
