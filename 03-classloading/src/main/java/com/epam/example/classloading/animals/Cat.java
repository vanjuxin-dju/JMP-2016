package com.epam.example.classloading.animals;

public class Cat implements Animal {

	@Override
	public void play() {
		System.out.println("Cat plays");
	}

	@Override
	public void voice() {
		System.out.println("Meow");
	}

}
