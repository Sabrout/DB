package Elizabeth;

import java.util.Hashtable;


public class DBAppTest {
	public static void main(String[] args) {
		
		
		try {
			DBApp x = new DBApp();
			
			// Initialization Test
			x.init();
			
			// createTable Test
			Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
			htblColNameType.put("ID", "java.lang.Integer");
			htblColNameType.put("Name", "java.lang.String");
			Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();
			String strKeyColName = "ID";
			x.createTable("Students", htblColNameType, htblColNameRefs, strKeyColName);
			x.createTable("Users", htblColNameType, htblColNameRefs, strKeyColName);
			
			// insertIntoTable Test
			Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", "01011010");
			htblColNameValue.put("Name", "Sabrout");
			x.insertIntoTable("Students", htblColNameValue);
			
			// createIndex Test
			x.createIndex("Students", "Name");
			
			
			// Creating Multi-Dim Index
//			Hashtable<String, String> htblColNames = new Hashtable<String, String>();
//			htblColNames.put("ID", "Names");
//			x.createMultiDimIndex("Students", htblColNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
