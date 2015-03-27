package Elizabeth;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Table
{
	String name;
	ArrayList <Page> pageList;
	int pagesNum;
	countPage countPage;
	int TupleNum;
	ArrayList<String> columnNames;

	public Table(String name) throws ClassNotFoundException
	{
		this.name = name;
		pageList = new ArrayList<Page>();

		try
		{
			ObjectInputStream ois = 
					new ObjectInputStream(new FileInputStream(new File("data/" + name + "_count")));
			countPage = (countPage) ois.readObject();
			ois.close();

			pagesNum = countPage.getPageCounter();
			TupleNum = countPage.getTupleCounter();
		}
		catch(IOException e)
		{
			countPage = new countPage();
			pagesNum = countPage.getPageCounter();
			TupleNum = countPage.getTupleCounter(); //TODO not sure what to do with Tuple counter
			countPage.write(name);
		}
	}

	public void addPage()
	{
		pageList.add(new Page(DBApp.getMaximumRowsCountinPage(), pagesNum + 1));
		pagesNum++;
	}

	public void readTableContent()
	{
		File dataDirectory = new File("data/");
		File[] allFiles = dataDirectory.listFiles();

		for (int i = 0; i < allFiles.length; i++)
		{
			if(allFiles[i].getName().startsWith(name) && !allFiles[i].getName().equals(name + "_count"))
			{
				try
				{
					ObjectInputStream ois = 
							new ObjectInputStream(new FileInputStream
									(new File("data/" + allFiles[i].getName())));
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

	public void writePages()
	{
		for (int i = 0; i < pageList.size(); i++)
		{
			pageList.get(i).write(name);
		}
	}

	public Page readPage(int pageNumber) 
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
					new ObjectInputStream(new FileInputStream
							(new File("data/" + name + "_" + pageNumber)));
			returnPage = (Page) ois.readObject();
			ois.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return returnPage;
	}

	public ArrayList<String[]> getColumnData(String columnName)
	{
		ArrayList<String[]> returnArray = new ArrayList<String[]>();
		int i;
		
		for (i = 0; i < columnNames.size(); i++)
		{
			if(columnNames.get(i).equals(columnName))
				break;
		}
		
		if(i == columnNames.size())
		{
			return null;
		}
		
		for (int j = 0; j < pageList.size(); j++)
		{
			for (int k = 0; k < pageList.get(j).getEntriesNum(); k++)
			{
				Tuple[] entries = pageList.get(j).getContent();
				returnArray.add(new String[] {entries[k].getColumn(i), 
				                "" + pageList.get(j).getPageNum(), "" + k});
			}
		}
		return returnArray;
		/*
		 * returnArray is the returned array, of type ArrayList<String[]>
		 * each list inside the returnArray is mapped as follows:
		 * String[0] is the values of this column
		 * String[1] is the page number
		 * String[2] is the entry number(index)
		 */
	
	}
	
	public ArrayList<String> getColumnNames()
	{
		columnNames = new ArrayList<String>();
		try
		{
			String splitBy = ", ";
			BufferedReader br = new BufferedReader(new FileReader("data/metadata.csv"));
			String line;
			while((line = br.readLine()) != null)
			{
				String[] b = line.split(splitBy);
				if(b[0].equals(name))
				{
					columnNames.add(b[1]);
				}
			}
			br.close();
		}
		catch (Exception e)
		{
			System.out.println("couldn't read csv column names");
		}
		
		return columnNames;
	}
	
	public String getName()
	{
		return name;
	}

	public ArrayList<Page> getPageList()
	{
		return pageList;
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