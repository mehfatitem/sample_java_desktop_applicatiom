/* @file mebitech isbasvurusu soru-1 
 * @description Initial Desktop Application  
 * @date 28.10.2014 19:40
 * @author Mehmed Fatih Temiz temizfatih54@gmail.com
 */

/*
   Database operations with mysql in java
*/
package sample_dp_1.Db;

import java.sql.DriverManager;

import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import sample_dp_1.Helpers.GeneralHelpers;
import sample_dp_1.SqlCreator.SqlCreator;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class Db {
	private Connection baglanti = null; // Bagalanti nesnemiz
	private String url = "jdbc:mysql://127.0.0.1/";// URL
	private String veriTabani = "soru1";// Veritabani ismimiz

	private String properties = "??useUnicode=yes&characterEncoding=UTF-8"; // turkce
																			// karakter
																			// icin
	private String driver = "com.mysql.jdbc.Driver";// connector driver
	private String kullanici = "root";// veritabani kullanici adi
	private String sifre = ""; // veritabani sifresi
	private ResultSet res;// sonuc seti nesnemiz

	private GeneralHelpers go = new GeneralHelpers();
	private SqlCreator sc = new SqlCreator();

	// baglanti acma metodu
	public Statement baglantiAc() throws Exception {
		Class.forName(driver).newInstance(); // surucumuzu burda aliyoruz.
		baglanti = (Connection) DriverManager.getConnection(url + veriTabani + properties, kullanici, sifre);
		return (Statement) baglanti.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);// baglanti
																													// nesne
	}

	// baglantiyi kapatma metodu
	public void baglantiKapat() throws Exception {
		baglanti.close();
	}

	// kullanici ekle
	public void kullaniciEkle(String ad, String soyad, String cinsiyet) throws Exception {
		Statement stm = this.baglantiAc();
		Object[] data = new Object[]{"name,"+ad , "surname,"+soyad , "gender," + cinsiyet};
		String sorgu = sc.createInsertSql("soru1.user", data);
		go.print(sorgu);
		stm.executeUpdate(sorgu);
		this.baglantiKapat();
	}

	// kullanicilari tabloya listele
	public JTable kullaniciListele() throws Exception {
		Statement stm = this.baglantiAc();
		String sorgu = sc.createSelectSql("soru1.user", "all", "", "ORDER BY userID desc");
		go.print(sorgu);
		res = (ResultSet) stm.executeQuery(sorgu);
		JTable tablo = new JTable();
		tablo.setModel(DbUtils.resultSetToTableModel(res));
		this.baglantiKapat();
		return tablo;
	}

	// kullanici sil
	public void kullaniciSil(int id) throws Exception {
		Statement stm = this.baglantiAc();
		String condition = go.convertToSqlString(id) + " = userID";
		String sorgu = sc.createDeleteSql("soru1.user", condition);
		go.print(sorgu);
		stm.executeUpdate(sorgu);
		this.baglantiKapat();
	}

	// kullanici guncelle
	public void kullaniciGuncelle(int id, String ad, String soyad, String cinsiyet) throws Exception {
		Statement stm = this.baglantiAc();
		Object[] data = new Object[]{"name,"+ad , "surname,"+soyad , "gender," + cinsiyet};
		String condition = go.convertToSqlString(id) + " = userID ";
		String sorgu = sc.createUpdateSql("soru1.user", data, condition);
		go.print(sorgu);
		stm.executeUpdate(sorgu);
		this.baglantiKapat();
	}

	// veritabanimizda kayit olup olmadigini kontrol eder.
	public boolean kayitKontrol() throws Exception {
		boolean onay = true;
		int sayac = 0;
		Statement stm = this.baglantiAc();
		String sorgu = sc.createSelectSql("soru1.user", "all", "", "");
		go.print(sorgu);
		ResultSet rs = (ResultSet) stm.executeQuery(sorgu);
		while (rs.next()) {
			sayac++;
		}
		if (sayac == 0) {
			onay = false;
		}
		this.baglantiKapat();
		return onay;
	}

	public boolean isExist(String name, String surname , String gender) {
		boolean result = false;
		try {
			int sayac = 0;
			Statement stm = this.baglantiAc();
			String condition = go.convertToSqlString(name)  + " = name and " + go.convertToSqlString(surname) + " = surname and " + go.convertToSqlString(gender) + " = gender";
			String sorgu = sc.createSelectSql("soru1.user", "all", condition , "");
			go.print(sorgu);
			ResultSet rs = (ResultSet) stm.executeQuery(sorgu);
			while (rs.next()) {
				sayac++;
			}
			if (sayac > 0)
				result = true;
			else
				result = false;
			this.baglantiKapat();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public int getUserIdWithNameAndSurname(String name, String surname) {
		int userId = 0;
		try {
			Statement stm = this.baglantiAc();
			String condition = go.convertToSqlString(name) + " = name and " + go.convertToSqlString(surname) + " = surname";
			String sorgu = sc.createSelectSql("soru1.user", "userID", condition , "");
			go.print(sorgu);
			ResultSet rs = (ResultSet) stm.executeQuery(sorgu);

			if (rs.next()) {
				userId = Integer.parseInt(rs.getString(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return userId;
	}

}
