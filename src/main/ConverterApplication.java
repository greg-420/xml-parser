package main;

import java.io.File;

public class ConverterApplication {
	public static void main (String[] args) {
		String path = "/home/pepejam/Code/xml-converter/properties";
		File file = new File(path);
		Converter.convert(file);
	}
}
