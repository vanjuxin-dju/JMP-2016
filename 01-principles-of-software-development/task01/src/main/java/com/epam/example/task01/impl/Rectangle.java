package com.epam.example.task01.impl;

public class Rectangle extends Parallelogramm {

	@Override
	public void setAngle(double angle) {
		try {
			super.setAngle(90);
		} catch (Exception e) {
		}
	}

}
