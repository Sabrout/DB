package Elizabeth;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StructureLine implements Serializable {
	private String TableName;
	private String ColumnName;
	private String ColumnType;
	private boolean Key;
	private boolean Indexed;
	private String References;
	
	public StructureLine(String TableName, String ColumnName, String ColumnType, boolean Key, boolean Indexed, String References) {
		this.TableName = TableName;
		this.ColumnName = ColumnName;
		this.ColumnType = ColumnType;
		this.Key = Key;
		this.Indexed = Indexed;
		this.References = References;
	}
	
	public String toString() {
	    return String.format(TableName) + "," + String.format(ColumnName) + "," + String.format(ColumnType) + "," + Boolean.toString(Key) 
	    		+ "," + Boolean.toString(Indexed) + "," + String.format(References);
	  }
	
	public boolean equals(StructureLine in) {
		if (this.TableName == in.TableName && this.ColumnName == in.ColumnName && this.ColumnType == in.ColumnType && this.Key == in.Key
				&& this.Indexed == in.Indexed && this.References == in.References) {
				return true;
		}
		return false;
	}

	public String getTableName() {
		return TableName;
	}

	public void setTableName(String tableName) {
		TableName = tableName;
	}

	public String getColumnName() {
		return ColumnName;
	}

	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}

	public String getColumnType() {
		return ColumnType;
	}

	public void setColumnType(String columnType) {
		ColumnType = columnType;
	}

	public boolean isKey() {
		return Key;
	}

	public void setKey(boolean key) {
		Key = key;
	}

	public boolean isIndexed() {
		return Indexed;
	}

	public void setIndexed(boolean indexed) {
		Indexed = indexed;
	}

	public String getReferences() {
		return References;
	}

	public void setReferences(String references) {
		References = references;
	}
	
//	public static void main(String[] args) {
//		StructureLine x = new StructureLine("TableName", "ColumnName", "ColumnType", false, false, "References");
//		System.out.println(x.toString());
//	}
}