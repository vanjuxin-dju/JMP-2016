package com.epam.example.classloading.animals.impl;

import org.apache.log4j.Logger;

import com.epam.example.classloading.animals.Animal;

public class Cat implements Animal {
	private static final Logger LOGGER = Logger.getLogger(Cat.class);
	
	@Override
	public void play() {
		LOGGER.info("Cat plays");
	}

	@Override
	public void voice() {
		LOGGER.info("Meow");
	}

}
