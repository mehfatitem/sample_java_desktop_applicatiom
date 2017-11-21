package sample_dp_1.Helpers;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import sample_dp_1.Interfaces.IDbHelpers;
import sample_dp_1.SqlCreator.SqlCreator;

public class DbHelpers extends SqlCreator implements IDbHelpers {
	
	private Connection baglanti = null; // Bagalanti nesnemiz
	private String url = "jdbc:mysql://127.0.0.1/";// URL
	private String veriTabani = "soru1";// Veritabani ismimiz

	private String properties = "??useUnicode=yes&characterEncoding=UTF-8"; // turkce
																			// karakter
																			// icin
	private String driver = "com.mysql.jdbc.Driver";// connector driver
	private String kullanici = "root";// veritabani kullanici adi
	private String sifre = ""; // veritabani sifresi


	// baglanti acma metodu
	@Override
	public Statement baglantiAc() throws Exception {
		Class.forName(driver).newInstance(); // surucumuzu burda aliyoruz.
		baglanti = (Connection) DriverManager.getConnection(url + veriTabani + properties, kullanici, sifre);
		return (Statement) baglanti.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);// baglanti																											// nesne
	}

	@Override
	// baglantiyi kapatma metodu
	public void baglantiKapat() throws Exception {
		baglanti.close();
	}
	
	@Override
	public ResultSet querySelectSql(String sql) throws Exception {
		Statement stm = this.baglantiAc();
		return (ResultSet) stm.executeQuery(sql);
	}

	@Override
	public void querySql(String sql) throws Exception {
		Statement stm = this.baglantiAc();
		stm.executeUpdate(sql);
	}

	@Override
	public boolean isResultSetNothing(java.sql.ResultSet rs) throws SQLException {
		int count = 0;
		boolean result = false;
		
		try{
			while(rs.next())
				count++;
			
			if(count>0)
				result = true;
			
			this.baglantiKapat();
		}catch(Exception ex){
			 
		}
		return result;
	}

	@Override
	public boolean isExist(String sql) throws Exception {
		ResultSet rs = this.querySelectSql(sql);
		return this.isResultSetNothing(rs);
	}
}
