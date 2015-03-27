package Elizabeth;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
class Tuple implements Serializable
{
	String[] values;
	boolean deleted;
	
	public Tuple(String[] v)
	{
		values = Arrays.copyOf(v, v.length);
		deleted = false;
	}
	
	public String[] getValues()
	{
		return values;
	}

	public void setValues(String[] values)
	{
		this.values = values;
	}
	
	public boolean isDeleted()
	{
		return deleted;
	}
	
	public String getColumn(int colNumber)
	{
		return values[colNumber];
	}
	
}
