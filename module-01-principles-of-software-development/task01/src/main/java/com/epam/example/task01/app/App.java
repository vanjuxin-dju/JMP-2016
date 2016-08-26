package com.epam.example.task01.app;

import com.epam.example.task01.impl.Parallelogramm;
import com.epam.example.task01.impl.Rectangle;
import com.epam.example.task01.impl.Rhombus;
import com.epam.example.task01.impl.Square;

public class App {
	// the class is used instead of interface
	private static double area(Parallelogramm figure, double width, double height, double angle) throws Exception {
		figure.setWidth(width);
		figure.setHeight(height);
		figure.setAngle(angle);
		return figure.getArea();
	}

	public static void main(String[] args) throws Exception {
		// all parallelogramms have 5 cm width, 4 cm height, and 30 degrees angle
		// area = 5 * 4 * sin(30) = 20 * 0.5 = 10
		// output:
		// 9.999999999999998
		// 20.0
		// 7.999999999999999
		// 16.0
		
		System.out.println(area(new Parallelogramm(), 5, 4, 30)); // ~10 correct
		
		System.out.println(area(new Rectangle(), 5, 4, 30)); // ~20 ??? where is angle 30 degrees?
		
		System.out.println(area(new Rhombus(), 5, 4, 30)); // ~8 ??? where is width 5 cm?
		
		System.out.println(area(new Square(), 5, 4, 30)); // ~16 ??? where are width 5 cm and angle 30 degrees?
		
		// rectangles, rhombuses, and squares must not be inherited from parallelogramms. these must be separated entities.
	}

}
