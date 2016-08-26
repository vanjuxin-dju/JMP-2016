package com.epam.example.task01.impl;

public class Square extends Rhombus {

	@Override
	public void setAngle(double angle) {
		try {
			super.setAngle(90);
		} catch (Exception e) {
		}
	}
}
