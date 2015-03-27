package Elizabeth;

class Entry
{
	String[] values;
	int entryIndex;
	boolean deleted;
	
	public Entry(String[] v, int n)
	{
		entryIndex = n;
		values = v;
		deleted = false;
	}
	
	public int getEntryIndex()
	{
		return entryIndex;
	}
	
	public void setEntryIndex(int entryIndex)
	{
		this.entryIndex = entryIndex;
	}
	
	public String[] getValues()
	{
		return values;
	}

	public void setValues(String[] values)
	{
		this.values = values;
	}
}
