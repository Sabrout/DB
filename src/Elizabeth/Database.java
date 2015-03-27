package Elizabeth;

import java.util.ArrayList;

class Database
{
	String name;
	ArrayList<Table> tableList;
	
	public Database(String dbName)
	{	
		this.name = dbName;
		tableList = new ArrayList<Table>();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public Table getTable(String tableName)
	{
		for(int i = 0; i < tableList.size(); i++)
		{
			if(tableList.get(i).getTableName().equals(tableName))
			{
				return tableList.get(i);
			}
		}
		return null;
	}
	
	public boolean addTable(Table table)
	{
		for (int i = 0; i < tableList.size(); i++)
		{
			if(tableList.get(i).getTableName().equals(table.getTableName()))
				return false;
		}
		
		tableList.add(table);
		return true;
	}
	
	public boolean deleteTable()
	{
		return false; //TODO IN-Case needed
	}	
}

