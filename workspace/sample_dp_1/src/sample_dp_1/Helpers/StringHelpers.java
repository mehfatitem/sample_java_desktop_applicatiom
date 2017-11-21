package sample_dp_1.Helpers;

import sample_dp_1.Interfaces.IStringHelpers;

public class StringHelpers  implements IStringHelpers {
	
	public StringHelpers() {
		
	}
	
	@Override
	public void print(Object val) {
		System.out.println(val.toString());
	}
	
	@Override
	public String prepareSqlString(String val) {
		return val.replaceAll("([\"'])", "\\\\$1");
	}
	
	@Override
	public String convertToSqlString(Object val) {
		val = prepareSqlString(val.toString());
		val = "'" + val + "'";
		return val.toString();
	}
}
