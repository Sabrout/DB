package Elizabeth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class countPage implements Serializable
{
	private static int pageCounter;
	private static int TupleCounter;
	
	public countPage()
	{
		pageCounter = 0;
		TupleCounter = 0;
	}
	
	public void write(String tableName)
	{
		ObjectOutputStream oos;
		
		try
		{
			oos = new ObjectOutputStream(
					new FileOutputStream(new File("data/" + tableName + "_count")));
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

	public int getTupleCounter()
	{
		return TupleCounter;
	}

	public void incrementTupleCounter()
	{
		TupleCounter++;
	}
	
	public void decrementTupleCounter()
	{
		TupleCounter--;
	}
}
