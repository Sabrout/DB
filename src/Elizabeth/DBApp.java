package Elizabeth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import LinearHash.DB_Type;
import LinearHash.LinearHashTable;
import src.Datei.KDTree;

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


		Table table = new Table(strTableName);
		if (database.addTable(table)) {
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
			table.addPage();

			printWriter.flush();
			printWriter.close();	
		}

	}

	public void createIndex(String strTableName, String strColName)
			throws DBAppException {
		
		try {
			Table table = new Table(strTableName);
			LinearHashTable Hash = new LinearHashTable((float) 0.8, 1);
			String csvFile = "data/metadata.csv";
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
				while (line != null) {
		 
				      
					String[] split = line.split(cvsSplitBy);
					line = br.readLine();
					if(split[0].equals(strTableName)&&split[1].equals(strColName)){
						table.readTableContent();
						ArrayList<String[]> data = table.getColumnData(strColName);
						int i = 0;
						while(data.size()>i){
							 String [] info = data.get(i);
							 DB_Type.DB_String key = new DB_Type.DB_String();
							 key.str = info[0];
							 DB_Type.DB_String value = new DB_Type.DB_String();
							 value.str = info[1]+","+info[2];
							 Hash.put(key, value);
							 
							i++;
						
						}
										
					}	
				}
				br.close();
				
				ObjectOutputStream oos;

				try
				{
					oos = new ObjectOutputStream(
							new FileOutputStream(new File("data/" + "mIndex_" + table.getName())));
					oos.writeObject(Hash);
					oos.close();
				}
				catch (FileNotFoundException z)
				{
					z.printStackTrace();
				}
				catch (IOException z)
				{
					z.printStackTrace();
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void createMultiDimIndex(String strTableName,
			Hashtable<String, String> htblColNames) throws DBAppException
	{
		Table table = database.getTable(strTableName);
		Enumeration<String> e = htblColNames.keys();
		ArrayList<String> colNames = new ArrayList<String>();
		ArrayList<ArrayList<String[]>> colData = new ArrayList<ArrayList<String[]>>();

		for(int i = 0; e.hasMoreElements(); i++)
		{
			colNames.add(e.nextElement());
			colNames.add(htblColNames.get(colNames.get(i)));
		}

		if(colNames.size() != DBApp.KDTreeN)
		{
			System.out.println("NOPE");
			return;
			
		}
			
		for (int i = 0; i < colNames.size(); i++)
		{
			colData.add(table.getColumnData(colNames.get(i)));
		}
		
		KDTree tree = new KDTree(colNames.size());
		String[] keys = new String[KDTreeN];
		String location = "";		
		int i;
		int j;
		
		System.out.println(colData.get(0).size() + ", " + colData.get(0).size() + ", " + colData.size());
		
		for(i = 0; i < colData.get(i).size(); i++)
		{
			for (j = 0; j < colData.size(); j++)
			{				
				System.out.println(i + ", " + j);
				 keys[j] = colData.get(j).get(i)[0];
			}
			location = colData.get(j).get(i)[1] + ", " + colData.get(j).get(i)[2];
			System.out.println("created location string");
			tree.insert(keys, location);
			System.out.println("inserted smthng");
		}
		
		ObjectOutputStream oos;

		try
		{
			oos = new ObjectOutputStream(
					new FileOutputStream(new File("data/" + "mIndex_" + table.getName())));
			oos.writeObject(tree);
			oos.close();
		}
		catch (FileNotFoundException z)
		{
			z.printStackTrace();
		}
		catch (IOException z)
		{
			z.printStackTrace();
		}
		
		System.out.println(tree);
	}
	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue) throws DBAppException {

		Table table = database.getTable(strTableName);
		table.readTableContent();

		Iterator<Entry<String, String>> iterator = htblColNameValue.entrySet()
				.iterator();
		ArrayList<String> columnNames = new ArrayList<String>(table.getColumnNames());

		if(htblColNameValue.size() == columnNames.size())
		{
			String [] valuesForTuple = new String[columnNames.size()];
			for (int i = 0; i < columnNames.size() && iterator.hasNext(); i++)
			{
				Entry<String, String> current = iterator.next();
				if (!current.getKey().equals(columnNames.get(i)))
				{
					return;
				}
				valuesForTuple[i] = current.getValue();
			}

			boolean flag = false;
			
			for (int i = 0; i < table.getPageList().size(); i++)
			{
				if (!table.getPageList().get(i).isFull())
				{
					table.getPageList().get(i).addTuple(new Tuple(valuesForTuple));
					flag = true;
				}
			}

			if(!flag)
			{
				table.addPage();
				table.getPageList().get(table.getPageList().size() - 1).addTuple(new Tuple(valuesForTuple));
			}


			table.writePages();
		}

		else
		{
			System.out.println("Not equal sizes hash and columns");
		}
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
		// Every single operation that edits the pages writes the pages immediately after finishing what it started
		// So you can say it auto-saves which means we don't need to use saveAll() every time you want to close the application
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

	public Database getDatabase() {
		return database;
	}
}
