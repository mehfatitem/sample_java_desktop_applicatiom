package sample_dp_1.SqlCreator;

public class SqlCreator {
	/*Baglanti stringi */
	public String connection = "";
	
	/*Kurucu metodumuz*/
	public SqlCreator(){
		
	}
	
	
	/*Insert sql'i olustur */ /*Parametre olarak 1.parametre tablo adi , 2.parametre kolon adlari (dizi olarak) , 3.parametre veriler*/
	public String createInsertSql(String tableName ,  Object datas[]){
		String resultSql    = "";
		String columnForSql = "";
		String dataForSql   = "";
		resultSql           += "INSERT INTO " + tableName + " (";
		for(int i=0;i<datas.length;i++){
			String[] columnArray = ((String) datas[i]).split("\\,",-1); 
			columnForSql += columnArray[0]+",";
			if(datas[i].getClass().getName() == "java.lang.String"){
				dataForSql += "'" + columnArray[1] + "',";
			}else{
				dataForSql += columnArray[1]+ ",";
			}
		}
		columnForSql = columnForSql.substring(0, columnForSql.length()-1);
		dataForSql   = dataForSql.substring(0, dataForSql.length()-1);
		resultSql 	 += columnForSql;
		resultSql 	 += ") VALUES(" + dataForSql + ")";
		return resultSql;
	}
	
	public String createSelectSql(String tableName , String column , String condition , String order){
		if(column == "all"){
			column = "*";
		}
		if(condition.length()>0){
			condition = " WHERE " + condition;
		}
		return "SELECT " + column + " FROM " +  tableName + condition + " " + order;
	}
	
	public String createUpdateSql(String tableName , Object[] setColumn , String condition){
		String stringSetColumn = "";
		if(condition.length()>0){
			condition = " WHERE " + condition;
		}
		for(int i=0;i<setColumn.length;i++){
			String[] columnArray = ((String) setColumn[i]).split("\\,",-1); 
			if(setColumn[i].getClass().getName() == "java.lang.String"){
				stringSetColumn += columnArray[0] + " = '" + columnArray[1] + "' , ";
			}else{
				stringSetColumn += columnArray[0] + " = " + columnArray[1] + " , ";
			}
		}
		stringSetColumn = stringSetColumn.substring(0, stringSetColumn.length()-2);
		return "UPDATE " + tableName + " SET " + stringSetColumn + condition;
	}
	
	public String createDeleteSql(String tableName , String condition){
		if(condition.length()>0){
			condition = " WHERE " + condition;
		}
		return "DELETE FROM " + tableName + condition;
	}
}
