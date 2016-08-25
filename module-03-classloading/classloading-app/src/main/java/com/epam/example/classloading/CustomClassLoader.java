package com.epam.example.classloading;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class CustomClassLoader extends URLClassLoader {
	private static final Logger LOG = Logger.getLogger(CustomClassLoader.class);
	private Map<String, Class<?>> classes = new HashMap<>();

	public CustomClassLoader(URL[] urls) {
		super(urls);
	}

	private void defineClass(String name, Class<?> clazz) {
        classes.put(name, clazz);
    }

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> result = findLoadedClass(name);
        if (result != null) {
        	LOG.info(name + " found from loaded classes");
        	defineClass(result.getName(), result);
            return result;
        }
        try {
            result = findSystemClass(name);
        } catch (Exception e) {
            // Ignore
        }
        if (result != null) {
        	LOG.info(name + " found from system classes");
        	defineClass(result.getName(), result);
            return result;
        }
        result = super.findClass(name);
        if (result != null) {
        	LOG.info(name + " found from superclass classloader");
        	defineClass(result.getName(), result);
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
		Class<?> clazz = null;
		try {
			clazz = findClass(name);
		} catch (ClassNotFoundException ex) {
			clazz = super.loadClass(name, resolve);
			defineClass(clazz.getName(), clazz);
		}
		return clazz;
	}

	@Override
	public String toString() {
		return "Custom classloader for animals";
	}
	

}
