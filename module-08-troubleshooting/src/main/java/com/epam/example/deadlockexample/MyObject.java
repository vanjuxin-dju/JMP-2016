package com.epam.example.deadlockexample;

public class MyObject {
	private String objectName;
	
	public MyObject(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
}
