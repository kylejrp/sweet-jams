package rogue.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

import rogue.entity.Entity;

public class MapGenerator {
	private static int MAX_SIZE = 100;

	public static EnvironmentEntity[][] generate(MapType type, int size) {
		
		// Initialized to null in case parseFile throws exception
		// Could probably be handled better with exceptions
		
		char[][] map = null;

		switch (type) {
		case TESTING:
			try {
				map = parseFile("MusicRogue/src/rogue/map/testmap.txt");
			} catch (IOException e) {
				Logger.getLogger(MapGenerator.class).log(Level.SEVERE,"Failed to parse map",e);
			}
			break;
		default:
			generate(MapType.TESTING, MAX_SIZE);
			break;
		}

		return map;
	}

	public static char[][] parseFile(String path) throws FileNotFoundException, IOException {
		char[][] map = new char[MAX_SIZE][MAX_SIZE];
		
		// Attempt to read the file, auto-close after
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			// Read the first line
			String line = reader.readLine();
			int row = 0;
			
			// For every non-empty line
			while (line != null && row < MAX_SIZE) {
				
				// Create a new row to store this line's input
				map[row] = new char[line.length()];
				
				
				int column = 0;
				// Read each character
				for (char ch : line.toCharArray()) {
					// Save the character at the current position
					map[row][column] = Character.toUpperCase(ch);
					column++;
				}
				line = reader.readLine();
				row++;
			}

		}
		
		return map;
	}

}
