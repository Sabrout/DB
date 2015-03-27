package Elizabeth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
class Page implements Serializable{
	
	private Tuple[] content;
	private int entriesNum;
	private int pageNum;
	private boolean full;
	
	public Page(int allowed, int pageNum)
	{
		content = new Tuple[allowed];
		entriesNum = 0;
		full = false;
		this.pageNum = pageNum;
	}
	
	public int getEntriesNum()
	{
		return entriesNum;
	}
	
	public void incrementEntriesNum()
	{
		entriesNum++;
	}
	
	public void decrementEntriesNum()
	{
		entriesNum--;
	}
	
	public boolean addTuple(Tuple newbie)
	{
		for(int i = 0; i < content.length; i++) //Array.length = lastIndex + 1
		{
			if(content[i] == null || content[i].isDeleted())
			{
				content[i] = newbie;
				incrementEntriesNum();
				if (i == (content.length - 1))
				{
					full = true;
				}
				return true;
			}
		}
		System.out.println("page is full"); //testing purposes
		return false;
	}
	
	public void write(String tableName)
	{
		ObjectOutputStream oos;
		
		try
		{
			oos = new ObjectOutputStream(
					new FileOutputStream(new File("data/" + tableName + "_" + pageNum)));
			oos.writeObject(this);
			oos.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean deleteTuple()
	{
		return false; //TODO
	}
	
	public Tuple[] getContent()
	{
		return content;
	}
	
	public int getPageNum()
	{
		return pageNum;
	}
	
	public void setPageNum(int num)
	{
		pageNum = num;
	}
	
	public boolean isFull()
	{
		return full;
	}
}

