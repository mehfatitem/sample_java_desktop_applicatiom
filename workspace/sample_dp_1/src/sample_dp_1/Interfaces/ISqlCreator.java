package sample_dp_1.Interfaces;

public interface ISqlCreator {

	public String createInsertSql(String tableName, Object datas[]);

	public String createSelectSql(String tableName, String column, String condition, String order);

	public String createUpdateSql(String tableName, Object[] setColumn, String condition);

	public String createDeleteSql(String tableName, String condition);
}
