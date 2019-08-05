package com.project.program;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

public class Main {

public static final String pkgName = "provaPkg";
public static final String pkgPath = "~/R/win-library/3.6/provaPkg";
public static final String midiPath = "C:/Users/giova/Documents/try/sothis.mid";

	public static String readInput() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String name = reader.readLine();
		return name;  
	}
	
	public static void installRPkg(Rengine re, String pkgName, String pkgPath) {
		re.eval("installPkg <- function(){ if (require(provaPkg) != TRUE){ "
				+ "install.packages(\"" + pkgPath + "\", repos = NULL, type=\"source\");"
				+ "}"
				+ "else {require(" + pkgName + ");}"
				+ "}");
		re.eval("installPkg()");

	}
	
	//nrows = the number of the rows that getNotesByMidi() return;
	public static void ReadMidiByPkg(Rengine re) throws Exception {
		installRPkg(re,pkgName,pkgPath);
		System.out.println("Inserisci il path del file midi da leggere:");
		String pathInput = readInput();
		System.out.println("Inserisci il n. di righe da visualizzare:");
		String rowsInput = readInput();
		re.eval("df <- getNotesByPath(\"" + pathInput + "\"," + rowsInput + ")");
	}
	
	
	
	public static void main(String[] args) {
		Rengine re=new Rengine(args, false, new TextConsole());
				try {
					ReadMidiByPkg(re);
					re.end();
					System.out.println("Premi un tasto per uscire");
					readInput();
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

}
