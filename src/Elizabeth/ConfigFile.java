package Elizabeth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigFile {


	public static ArrayList<Integer> loadConfigFile () throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("config/DBApp.properties"));
		ArrayList<Integer> values = new ArrayList<>(); // values extracted from files
		try {
			String line = "";
			line = br.readLine();
			while (line != null) {
				String value = line.substring(line.indexOf('=')+1).trim();
				values.add(Integer.parseInt(value));
				line = br.readLine();
			}      
		} finally {
			br.close();
		}
		return values;
	}

	public static void main(String[] args) throws IOException {
		loadConfigFile();
	}
}
