package Elizabeth;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


class Database{
	
	// the sole purpose of this class is to keep track of all the table instances created
	
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


public class Table{

	String tableName;
	ArrayList <Page> pageList;
	int numberOfPages;
	
	public Table(String name) {
		tableName = name;
		pageList = new ArrayList<Page>();
		numberOfPages = 0;
	}
	
	public void addNewPageToTable()
	{
		// create a new page and link it to the table.
		
		this.getPageList().add(new Page(DBApp.getMaximumRowsCountinPage()));
		this.setNumberOfPages(this.getNumberOfPages()+1);
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
				ObjectInputStream ois = 
						new ObjectInputStream(new FileInputStream(new File("/data/"+allFiles[i].getName())));
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
	
	public Page readOnePage(int page_number) 
	{
		
		/*this method catches the exception if the page doesn't exist, but you should remove the try and catch
		when you handle it in the main class */
		// read a page from the calling table with certain page number in the table arraylist
		/* most probably you will have to call this after  initializing a table and calling the method
		   readTableContent	 */
		
		Page returnPage = null;
		
		try
		{
			ObjectInputStream ois = 
					new ObjectInputStream(new FileInputStream(new File("/data/"+this.getTableName()+"_"+page_number)));
			returnPage = (Page) ois.readObject();
			ois.close();
		}catch(Exception e) {e.printStackTrace();}
		
		
		return returnPage;
	}
	
	public void writeCertainPage(Page p, int page_number) 
	{
		/*this method catches the exception if the page doesn't exist, but you should remove the try and catch
		when you handle it in the main class */
		
		//reads a certain page from the table and then overwrite it with the new page you called the method with

		try
		{

			Page testingPage = readOnePage(page_number);
			if(!testingPage.isDead())
			{
				ObjectOutputStream oos = 
						new ObjectOutputStream(
								new FileOutputStream(new File("/data/"+this.getTableName()+"_"+page_number)));
				oos.writeObject(p);
				oos.close();
			}
		} catch(Exception e){e.printStackTrace();}

	}
	
	public void deleteEntry(int entryNumber, int pageNumber) 
	{
		// this reads the page number given relative to that table and deletes an entry then rewrite the page 
		Page p = this.readOnePage(pageNumber);
		p.getContent()[entryNumber] = null;
		p.getEmptyIndices().add(entryNumber);
		this.writeCertainPage(p, pageNumber);
	}
	
	// Setters and getters
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		
		// we should handle table renaming here
		
		this.tableName = tableName;
	}

	public ArrayList<Page> getPageList() {
		return pageList;
	}

	public void setPageList(ArrayList<Page> pageList) {
		this.pageList = pageList;
	}
	
	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

}

@SuppressWarnings("serial")
class Page implements Serializable{

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
	
	// Setters and getters
	
	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
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

	
	
}

class Entry{
	
	String[] values;
	
	public Entry(String[] v) {
		
		values = v;
		
	}
	
	// Setters and getters
	
	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	
	
	
}
