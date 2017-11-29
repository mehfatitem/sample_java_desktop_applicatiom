package sample_dp_1.Interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import sample_dp_1.Entities.DbProperties;

public interface IDbHelpers {
	
	public Statement baglantiAc() throws Exception;

	public void baglantiKapat() throws Exception;
	
	public ResultSet querySelectSql(String sql) throws Exception;
	
	public int querySql(String sql) throws Exception;
	
	public boolean isResultSetNothing(ResultSet rs) throws SQLException;
	
	public boolean isExist(String sql) throws Exception;
	
	public DbProperties fillDbProperties();
}
