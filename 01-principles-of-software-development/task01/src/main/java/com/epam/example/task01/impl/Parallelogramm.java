package com.epam.example.task01.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.epam.example.task01.ParallelogrammFigure;

public class Parallelogramm implements ParallelogrammFigure {
	private double width;
	
	private double height;
	
	private double angle;
	
	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public double getAngle() {
		return angle;
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
	}

	@Override
	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public void setAngle(double angle) throws Exception {
		if (angle > 0 && angle < 180)
			this.angle = angle;
		else
			throw new Exception("This is not a parallelogramm!");
	}

	// every class must have only one reason to be changed
	@Override
	public double getArea() {
		return width * height * Math.sin(Math.toRadians(angle));
	}
	
	// it will be very hard to add another I/O formats
	@Override
	public void writeToFile(String fileName) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(fileName);
		writer.println(width);
		writer.println(height);
		writer.println(angle);
		writer.close();
	}

	@Override
	public void readFromFile(String fileName) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(fileName));
		width = reader.nextDouble();
		height = reader.nextDouble();
		angle = reader.nextDouble();
		reader.close();
	}

}
