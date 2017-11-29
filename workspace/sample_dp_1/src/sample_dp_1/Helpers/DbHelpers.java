package sample_dp_1.Helpers;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileReader;
import java.io.Reader;


import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import sample_dp_1.Entities.DbProperties;
import sample_dp_1.Entities.RootObject;
import sample_dp_1.Interfaces.IDbHelpers;
import sample_dp_1.SqlCreator.SqlCreator;

public class DbHelpers extends SqlCreator implements IDbHelpers {

	private Connection baglanti = null; // Bagalanti nesnemiz
	
	private String url;
	private String veriTabani;
	private String properties;
	private String driver;
	private String kullanici;
	private String sifre;
	
	private static String propFilePath = "C:////Users//mehfa//workspace//sample_dp_1//src//config//db_properties.json";
	
	public  DbHelpers() {
		DbProperties dbProp = this.fillDbProperties();
		this.url = dbProp.getUrl();
		this.driver = dbProp.getDriver();
		this.kullanici = dbProp.getUsername();
		this.sifre = dbProp.getPassword();
		this.veriTabani = dbProp.getDatabasename();
		this.properties = dbProp.getProperties();	
	}
	
	// baglanti acma metodu
	@Override
	public Statement baglantiAc() throws Exception {
		Class.forName(driver).newInstance(); // surucumuzu burda aliyoruz.
		baglanti = (Connection) DriverManager.getConnection(url + veriTabani + properties, kullanici, sifre);
		return (Statement) baglanti.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);// baglanti
																													// //
																													// nesne
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
	public int querySql(String sql) throws Exception {
		Statement stm = this.baglantiAc();
		return stm.executeUpdate(sql);
	}

	@Override
	public boolean isResultSetNothing(java.sql.ResultSet rs) throws SQLException {
		int count = 0;
		boolean result = false;

		try {
			while (rs.next())
				count++;

			if (count > 0)
				result = true;

			this.baglantiKapat();
		} catch (Exception ex) {

		}
		return result;
	}

	@Override
	public boolean isExist(String sql) throws Exception {
		ResultSet rs = this.querySelectSql(sql);
		return this.isResultSetNothing(rs);
	}

	@Override
	public DbProperties fillDbProperties() {
		DbProperties dbProp = null;
		try{
			Gson gson = new Gson();
			Reader reader = new FileReader(propFilePath);
			
			RootObject ro = gson.fromJson(reader, RootObject.class);
			
			dbProp = ro.getDbProperteis();
		}catch(Exception ex){
			
		}
		return dbProp;
	}
}
