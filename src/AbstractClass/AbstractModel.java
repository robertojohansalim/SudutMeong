package AbstractClass;

import com.mysql.jdbc.ResultSet;

import Connect.Connect;

public abstract class AbstractModel {

	protected Connect connect;
	protected String tableName;
	
	public AbstractModel(String tableName) {
		connect = new Connect();
		this.tableName = tableName;
	}
	
	protected abstract AbstractModel fill(ResultSet rs);

}
