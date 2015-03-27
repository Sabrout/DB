package Elizabeth;

public class countPage
{
	private static int pageCounter;
	private static int entryCounter;
	
	public countPage()
	{
		pageCounter = 0;
		entryCounter = 0;
	}

	public int getPageCounter()
	{
		return pageCounter;
	}

	public void incrementPageCounter()
	{
		pageCounter++;
	}
	
	public void decrementPageCounter()
	{
		pageCounter--;
	}

	public int getEntryCounter()
	{
		return entryCounter;
	}

	public void incrementEntryCounter()
	{
		entryCounter++;
	}
	
	public void decrementEntryCounter()
	{
		entryCounter--;
	}
}
