package Elizabeth;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class DBApp {

	private static int MaximumRowsCountinPage;
	private static int KDTreeN;

	public void init() throws IOException {
		MaximumRowsCountinPage = ConfigFile.loadConfigFile().get(0);
		KDTreeN = ConfigFile.loadConfigFile().get(1);
		//Database database = new Database("DB");   Not sure if initializing a new DB every time we run the application
		

	}

	public void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
					throws DBAppException, IOException {

		FileWriter fileWriter = new FileWriter("metadata.csv");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		Iterator<Entry<String, String>> iterator = htblColNameType.entrySet()
				.iterator();

		while (iterator.hasNext()) {
			Entry<String, String> current = iterator.next();
			printWriter.println(strTableName + ", " + current.getKey() + ", "
					+ current.getValue() + ", "
					+ current.getKey().equals(strKeyColName) + ", " + current.getKey().equals(strKeyColName) 
					+ ", " + (htblColNameRefs.containsKey(current.getKey())?htblColNameRefs.get(current.getKey()): "null"));

		}
		
		printWriter.flush();
		printWriter.close();
	
        Table table = new Table(strTableName);
        table.addNewPageToTable();
        table.writeAllPages();

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
	}

	@SuppressWarnings("rawtypes")
	public Iterator selectFromTable(String strTable,
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
		
	}
}
