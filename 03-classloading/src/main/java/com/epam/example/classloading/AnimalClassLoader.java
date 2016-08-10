package com.epam.example.classloading;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class AnimalClassLoader extends ClassLoader {
	static Logger LOG = Logger.getLogger(AnimalClassLoader.class);
	private Map<String, Class<?>> classes = new HashMap<>();

	public AnimalClassLoader() {
		this(AnimalClassLoader.class.getClassLoader());
	}

	public AnimalClassLoader(ClassLoader parent) {
		super(parent);
	}
	
	public void defineClass(String name, Class<?> clazz) {
        classes.put(name, clazz);
    }

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> result = findLoadedClass(name);
        if (result != null) {
        	LOG.info(name + " found from loaded classes");
            return result;
        }
        try {
            result = findSystemClass(name);
        } catch (Exception e) {
            // Ignore
        }
        if (result != null) {
        	LOG.info(name + " found from system classes");
            return result;
        }
        result = classes.get(name);
        if (result == null) {
            throw new ClassNotFoundException(name);
        }
        LOG.info(name + " found from classes defined in a map");
        return result;
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		LOG.info("Loading class " + name);
		try {
			return findClass(name);
		} catch (ClassNotFoundException ex) {
			return super.loadClass(name, resolve);
		}
	}

	@Override
	public String toString() {
		return "Custom classloader for animals";
	}
	
	
}
