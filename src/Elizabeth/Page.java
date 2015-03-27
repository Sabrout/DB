package Elizabeth;

import java.io.Serializable;

@SuppressWarnings("serial")
class Page implements Serializable{
	
	private Entry[] content;
	private int entriesNum;
	private int pageNum;
	private boolean full;
	
	public Page(int allowed, int pageNum)
	{
		content = new Entry[allowed];
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
	
	public boolean addEntry(Entry newbie)
	{
		for(int i = 0; i < content.length; i++) //Array.length = lastIndex + 1
		{
			if(content[i] == null)
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
	
	public boolean deleteEntry()
	{
		return false; //TODO
	}
	
	public Entry[] getContent()
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

