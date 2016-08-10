package com.epam.example.classloading;

import org.apache.log4j.Logger;

import com.epam.example.classloading.animals.Animal;
import com.epam.example.classloading.animals.Cat;
import com.epam.example.classloading.animals.Dog;

public class App {
	static Logger LOG = Logger.getLogger(App.class);
	private static final String ANIMAL_INTERFACE = Animal.class.getName();
	private static final String CAT_CLASS = Cat.class.getName();
	private static final String DOG_CLASS = Dog.class.getName();
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ClassLoader cl = new AnimalClassLoader();
		((AnimalClassLoader)cl).defineClass(ANIMAL_INTERFACE, Animal.class);
		((AnimalClassLoader)cl).defineClass(CAT_CLASS, Cat.class);
		((AnimalClassLoader)cl).defineClass(DOG_CLASS, Dog.class);
		Thread.currentThread().setContextClassLoader(cl);
		
		Animal[] animals = new Animal[8];
		for (int i = 0; i < animals.length; i++) {
			if (i % 2 == 0) {
				animals[i] = (Animal)Thread.currentThread().getContextClassLoader().loadClass(CAT_CLASS).newInstance();
				LOG.info("Cat created");
			} else {
				animals[i] = (Animal)Thread.currentThread().getContextClassLoader().loadClass(DOG_CLASS).newInstance();
				LOG.info("Dog created");
			}
		}
		LOG.info("Let's go through the animals");
		for (Animal animal: animals) {
			LOG.info("Animal is printing the messages");
			animal.play();
			animal.voice();
		}
	}

}
