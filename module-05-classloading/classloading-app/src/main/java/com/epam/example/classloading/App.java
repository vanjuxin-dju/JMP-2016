package com.epam.example.classloading;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import com.epam.example.classloading.animals.Animal;

public class App {
	private static final Logger LOG = Logger.getLogger(App.class);
	
	private static final String PATH = "\\..\\classloading-api-impl\\target\\classloading-api-impl-0.0.1-SNAPSHOT.jar";
	
	private static final String CLASS_EXT = ".class";
	
	private static final String PACKAGE = Animal.class.getPackage().getName();
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		File file = new File(System.getProperty("user.dir") + PATH);
		
		ClassLoader cl = new CustomClassLoader(new URL[] { file.toURI().toURL() });
		Thread.currentThread().setContextClassLoader(cl);
		
		List<String> classNames = loadClassNames(file.getCanonicalPath());
		
		Animal[] animals = new Animal[8];
		for (int i = 0; i < animals.length; i++) {
			animals[i] = (Animal)cl.loadClass(classNames.get(i % classNames.size())).newInstance();
			LOG.info("Created animal number " + i);
		}
		LOG.info("Let's go through the animals");
		for (Animal animal: animals) {
			LOG.info("Animal is printing the messages");
			animal.play();
			animal.voice();
		}
	}
	
	private static List<String> loadClassNames(String path) throws IOException {
		List<String> classNames = new ArrayList<String>();
		ZipInputStream zip = new ZipInputStream(new FileInputStream(path));
		for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
		    if (!entry.isDirectory() && entry.getName().endsWith(CLASS_EXT)) {
		        String className = entry.getName().replace('/', '.');
		        if (className.startsWith(PACKAGE))
		        	classNames.add(className.substring(0, className.length() - CLASS_EXT.length()));
		    }
		}
		zip.close();
		return classNames;
	}

}
