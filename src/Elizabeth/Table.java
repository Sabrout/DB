package Elizabeth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
class Database implements Serializable {
	
	String name;
	ArrayList<Table> tableList;
	
	public Database(String dbName) {
		
		this.name = dbName;
		tableList = new ArrayList<Table>();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}


@SuppressWarnings("serial")
public class Table implements Serializable {

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<Page> getPageList() {
		return pageList;
	}

	public void setPageList(ArrayList<Page> pageList) {
		this.pageList = pageList;
	}

	String tableName;
	ArrayList <Page> pageList;
	
	public Table(String name) {
		tableName = name;
		pageList = new ArrayList<Page>();
	}
	
	public void addNewPageToTable()
	{
		// create a new page and link it to the table. The default value is 200 entries per page
		// this should change to read from the metadata file the number of entries per page
		// CHANGED to be read from the metadata @Sabrout
		this.getPageList().add(new Page(DBApp.getMaximumRowsCountinPage()));
	}
	
//	public void readIntoNewTable(String table_Name) 
//	{
//		// this create a new table and inserts values in it from a file 
//		Table table = new Table(table_Name);
//		addNewPageToTable();
//		Page p = null;
//		try
//		{
//		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("/data/")));
//        p = (Page)ois.readObject();
//        ois.close();
//		}catch(Exception e) {e.printStackTrace();}
//		
//		table.pageList.set(0, p);
//		
//		
//	}
	
	public void readTableContent()
	{
		//this method read all files starting with the name of the calling table
		//make sure you create the table instance with the correct name to be able to read its files
		//i didn't specify the extension given to the files until we agree upon one
		
		String path = "/data/";
		File dataDirectory = new File(path);
		File[] allFiles = dataDirectory.listFiles();
		for (int i = 0; i < allFiles.length; i++) {
			if(allFiles[i].getName().startsWith(this.getTableName()))
			{
				try
				{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("/data/"+this.getTableName())));
		        this.getPageList().add((Page)ois.readObject());
		        ois.close();
				}catch(Exception e) {e.printStackTrace();}
			}
		}
		
	}
	
	public void writeAllPages()
	{
		//this method gets all pages of the calling table and write all the page files to bytes
		
		ArrayList<Page> tablePages = this.getPageList();
		for (int i = 0; i < tablePages.size(); i++) {
			Page p = tablePages.get(i);
			try
			{
			ObjectOutputStream oos = 
					new ObjectOutputStream(new FileOutputStream(new File("/data/"+this.getTableName()+"_"+i)));
	        oos.writeObject(p);
	        oos.close();
			} catch(Exception e){e.printStackTrace();}
			
		}
		
	}
	
	public void writeCertainPages(ArrayList<Integer> pageNumbers)
	{
		// self explanatory really
		
		for (int i = 0; i < pageNumbers.size(); i++) {
			Page p = this.getPageList().get(pageNumbers.get(i));
			try
			{
			ObjectOutputStream oos = 
					new ObjectOutputStream(
							new FileOutputStream(new File("/data/"+this.getTableName()+"_"+pageNumbers.get(i))));
	        oos.writeObject(p);
	        oos.close();
			} catch(Exception e){e.printStackTrace();}
			
		}
	}

}

class Page implements Serializable{

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int getAllowedEntries() {
		return allowedEntries;
	}

	public void setAllowedEntries(int allowedEntries) {
		this.allowedEntries = allowedEntries;
	}

	public Entry[] getContent() {
		return content;
	}

	public void setContent(Entry[] content) {
		this.content = content;
	}

	public ArrayList<Integer> getEmptyIndices() {
		return emptyIndices;
	}

	public void setEmptyIndices(ArrayList<Integer> emptyIndices) {
		this.emptyIndices = emptyIndices;
	}

	int allowedEntries;
	Entry[] content;
	ArrayList <Integer> emptyIndices;
	boolean dead;
	
	public Page(int allowed) {
		emptyIndices = new ArrayList<Integer>();
		allowedEntries = allowed;
		content = new Entry[allowed];
		for (int i = 0; i < allowed; i++) 
		{
			emptyIndices.add(i);
		}
	}
	
}

@SuppressWarnings("serial")
class Entry implements Serializable {
	
	String[] values;
	
	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public Entry(String[] v) {
		
		values = v;
		
	}
	
	
	
}
