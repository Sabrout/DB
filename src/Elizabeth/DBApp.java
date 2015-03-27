package Elizabeth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class DBApp {

	private static int MaximumRowsCountinPage;
	private static int KDTreeN;
	Database database;

	public void init() {
		try {
			
			MaximumRowsCountinPage = ConfigFile.loadConfigFile().get(0);
			KDTreeN = ConfigFile.loadConfigFile().get(1);
			
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		database = new Database("DB");   
		// Not sure if initializing a new DB every time we run the application

		// Then Read form CSV and create Table instances with the Table names in CSV
		ArrayList<String> tables = new ArrayList<String>();
		try {
			String splitBy = ", ";
			BufferedReader br = new BufferedReader(new FileReader("data/metadata.csv"));
			String line;
			while((line = br.readLine()) != null) {
				String[] b = line.split(splitBy);
				tables.add(b[0]);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// HashSet helps to remove Duplicates
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(tables);
		tables.clear();
		tables.addAll(hs);
		
		for (int i = 0; i < tables.size(); i++) {
			try {
				database.addTable(new Table(tables.get(i)));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
					throws DBAppException, IOException, ClassNotFoundException {
		
		
		// adjust the addTable with the boolean thingy
		Table table = new Table(strTableName);
		if (database.addTable(table)) {
			table.addNewPageToTable();
			table.writeAllPages();

			FileWriter fileWriter = new FileWriter("data/metadata.csv", true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			Iterator<Entry<String, String>> iterator = htblColNameType.entrySet()
					.iterator();
			
			while (iterator.hasNext()) {
				Entry<String, String> current = iterator.next();
				printWriter.append(strTableName + ", " + current.getKey() + ", "
						+ current.getValue() + ", "
						+ current.getKey().equals(strKeyColName) + ", " + current.getKey().equals(strKeyColName) 
						+ ", " + (htblColNameRefs.containsKey(current.getKey())?htblColNameRefs.get(current.getKey()): "null") + "\n");

			}

			printWriter.flush();
			printWriter.close();	
		}
		
	}

	public void createIndex(String strTableName, String strColName)
			throws DBAppException {
	}

	public void createMultiDimIndex(String strTableName,
			Hashtable<String, String> htblColNames) throws DBAppException {
	}

	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue) throws DBAppException {
	}

	public void deleteFromTable(String strTableName,
			Hashtable<String, String> htblColNameValue, String strOperator)
					throws DBEngineException {
		// Remove from CSV
		// Delete Entries
	}

	public Iterator<?> selectFromTable(String strTable,
			Hashtable<String, String> htblColNameValue, String strOperator)
					throws DBEngineException {
		return null;
	}

	public void saveAll() throws DBEngineException {
	}

	public static int getMaximumRowsCountinPage() {
		return MaximumRowsCountinPage;
	}

	public static void setMaximumRowsCountinPage(int maximumRowsCountinPage) {
		MaximumRowsCountinPage = maximumRowsCountinPage;
	}

	public static int getKDTreeN() {
		return KDTreeN;
	}

	public static void setKDTreeN(int kDTreeN) {
		KDTreeN = kDTreeN;
	}

	public static void main(String[] args) {
		try {
			DBApp x = new DBApp();
			x.init();
			Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
			htblColNameType.put("ID", "java.lang.Integer");
			htblColNameType.put("Name", "java.lang.String");
			Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();
			String strKeyColName = "ID";
			x.createTable("Students", htblColNameType, htblColNameRefs, strKeyColName);
			x.createTable("Users", htblColNameType, htblColNameRefs, strKeyColName);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
