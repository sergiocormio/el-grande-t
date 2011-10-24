package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class Utils {
	
	public static final String FIELD_SEPARATOR = "[||]";
	public static final String FIELD_SEPARATOR_REGEXP = "\\[\\|\\|\\]";
	
	public static final String EXP_FILE_BAD_FORMED_MANDATORY = "Archivo mal formado - Faltan datos mandatorios";
	public static final String EXP_FILE_BAD_FORMED_STAT = "Archivo mal formado - Problema en datos estadisticos";
	public static final String EXP_INVALID_MODEL = "Modelo inválido";
	public static final String EXP_PLAYER_ALREADY_EXISTS = "El jugador ya existe";
	public static final String EXP_SKILL_ALREADY_EXISTS = "La habilidad ya existe";
	public static final String EXP_OLD_SKILL_NOT_EXISTS = "La habilidad a modificar no existe";;
	
	
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
