package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class Utils {
	
	public static final String FIELD_SEPARATOR = "[||]";
	
	public static final String MSG_FILE_BAD_FORMED_EXCEPTION = "Archivo mal formado";
	public static final String MSG_INVALID_MODEL_EXCEPTION = "Modelo inválido";
	
	
	public static PrintWriter getPrintWriter(String fileName) {
		File file = new File(fileName);
		try {
			return new PrintWriter(file);
		} catch (FileNotFoundException e) {
			System.out.println("File: " + fileName + " cannot be created");
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedReader getBufferedReader(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("File: " + fileName + " not found!");
		}
		
		try {
			return new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("File: " + fileName + " not found!");
			throw e;
		}
		
	}
}
