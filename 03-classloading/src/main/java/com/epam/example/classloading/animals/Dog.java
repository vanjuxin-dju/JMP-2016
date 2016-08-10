package com.epam.example.classloading.animals;

public class Dog implements Animal {

	@Override
	public void play() {
		System.out.println("Dog plays");
	}

	@Override
	public void voice() {
		System.out.println("Woff");
	}

}
