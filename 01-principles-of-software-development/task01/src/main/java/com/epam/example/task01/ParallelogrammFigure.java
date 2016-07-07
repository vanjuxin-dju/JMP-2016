package com.epam.example.task01;

import java.io.FileNotFoundException;

public interface ParallelogrammFigure {
	
	double getWidth();
	
	double getHeight();
	
	double getAngle();
	
	void setWidth(double value);
	
	void setHeight(double value);
	
	void setAngle(double value) throws Exception;
	
	// calculations must be separated from entities
	double getArea();
	
	// I/O functions must be in a separate interface
	void writeToFile(String fileName) throws FileNotFoundException;
	
	void readFromFile(String fileName) throws FileNotFoundException;
	
}
