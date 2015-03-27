package Elizabeth;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Table
{
	String tableName;
	ArrayList <Page> pageList;
	int pagesNum;
	countPage countPage;
	int entryNum;

	public Table(String name) throws ClassNotFoundException
	{
		tableName = name;
		pageList = new ArrayList<Page>();

		try
		{
			ObjectInputStream ois = 
					new ObjectInputStream(new FileInputStream(new File("data/" + tableName + "_count")));
			countPage = (countPage) ois.readObject();
			ois.close();

			pagesNum = countPage.getPageCounter();
			entryNum = countPage.getEntryCounter();
		}
		catch(IOException e)
		{
			countPage = new countPage();
			pagesNum = countPage.getPageCounter();
			entryNum = countPage.getEntryCounter(); //TODO not sure what to do with entry counter
		}
	}

	public void addNewPageToTable()
	{
		// create a new page and link it to the table.
		
		pageList.add(new Page(DBApp.getMaximumRowsCountinPage(), pagesNum + 1));
		pagesNum++;
	}

	public void readTableContent()
	{
		//this method reads all files starting with the name of the called table
		//make sure you create the table instance with the correct name to be able to read its files
		//i didn't specify the extension given to the files until we agree upon one

		File dataDirectory = new File("data/");
		File[] allFiles = dataDirectory.listFiles();

		for (int i = 0; i < allFiles.length; i++)
		{
			if(allFiles[i].getName().startsWith(this.getTableName()))
			{
				try
				{
					ObjectInputStream ois = 
							new ObjectInputStream(new FileInputStream
									(new File("data/" + allFiles[i].getName() + "_" + i)));
					pageList.add((Page) ois.readObject());
					ois.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
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
						new ObjectOutputStream(new FileOutputStream
								(new File("data/" + this.getTableName() + "_" + i)));
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

			@SuppressWarnings("unused")
			Page testingPage = readOnePage(page_number);
			//ERROR HERE
//			if(!testingPage.isDead())
//			{
//				ObjectOutputStream oos = 
//						new ObjectOutputStream(
//								new FileOutputStream(new File("data/"+this.getTableName()+"_"+page_number)));
//				oos.writeObject(p);
//				oos.close();
//			}
		} catch(Exception e){e.printStackTrace();}

	}

	public void deleteEntry(int entryNumber, int pageNumber) 
	{
		// this reads the page number given relative to that table and deletes an entry then rewrite the page 
		Page p = this.readOnePage(pageNumber);
		p.getContent()[entryNumber] = null;
		//ERROR HERE
		//p.getEmptyIndices().add(entryNumber);
		this.writeCertainPage(p, pageNumber);
	}

	// Setters and getters

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{

		// we should handle table renaming here

		this.tableName = tableName;
	}

	public ArrayList<Page> getPageList()
	{
		return pageList;
	}

	public void setPageList(ArrayList<Page> pageList)
	{
		this.pageList = pageList;
	}

	public int getpagesNum()
	{
		return pagesNum;
	}

	public void setpagesNum(int pagesNum)
	{
		this.pagesNum = pagesNum;
	}
}