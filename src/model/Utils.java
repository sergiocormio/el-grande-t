package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Utils {
	
	public static final String FIELD_SEPARATOR = "[||]";
	public static final String FIELD_SEPARATOR_REGEXP = "\\[\\|\\|\\]";
	
	public static final String EXP_FILE_BAD_FORMED_MANDATORY = "Archivo mal formado - Las primeras 3 columnas del archivo deben ser:\n\nNOMBRE\nPOSICION\nPRECIO";
	public static final String EXP_FILE_BAD_FORMED_STAT = "Archivo mal formado - Problema en datos estadisticos";
	public static final String EXP_INVALID_MODEL = "Modelo inv�lido";
	public static final String EXP_PLAYER_ALREADY_EXISTS = "El jugador ya existe";
	public static final String EXP_SKILL_ALREADY_EXISTS = "La habilidad ya existe";
	public static final String EXP_OLD_SKILL_NOT_EXISTS = "La habilidad a modificar no existe";
	public static final String EXP_CLUB_ALREADY_EXISTS = "El club ya existe";
	public static final String EXP_CLUB_NOT_EXIST = "El club a modificar no existe";
	
	
	public static PrintWriter getPrintWriter(String fileName) {
		File file = new File(fileName);
		try {
			return new PrintWriter(file,"ISO-8859-1");
		} catch (FileNotFoundException e) {
			System.out.println("File: " + fileName + " cannot be created");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
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
			return new BufferedReader(new InputStreamReader (new FileInputStream(file),"ISO-8859-1"));
		} catch (FileNotFoundException e) {
			System.out.println("File: " + fileName + " not found!");
			throw e;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
