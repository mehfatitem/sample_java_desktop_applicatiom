/* @file mebitech isbasvurusu soru-1 
 * @description Initial Desktop Application  
 * @date 28.10.2014 19:40
 * @author Mehmed Fatih Temiz temizfatih54@gmail.com
 */

/*
   Database operations with mysql in java
*/
package sample_dp_1.Db;


import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import sample_dp_1.Helpers.DbHelpers;
import sample_dp_1.Interfaces.IDb;

import com.mysql.jdbc.ResultSet;

public class Db extends DbHelpers implements IDb {
	private ResultSet res;// sonuc seti nesnemiz

	@Override
	// kullanici ekle
	public void kullaniciEkle(String ad, String soyad, String cinsiyet) throws Exception {
		Object[] data = new Object[]{"name,"+ad , "surname,"+soyad , "gender," + cinsiyet};
		String sorgu = createInsertSql("soru1.user", data);
		print(sorgu);
		if(this.querySql(sorgu)<1)
			print("Sorgu çalýþtýralamadý.");
		this.baglantiKapat();
	}

	@Override
	// kullanicilari tabloya listele
	public JTable kullaniciListele() throws Exception {
		String sorgu = createSelectSql("soru1.user", "all", "", "ORDER BY userID desc");
		print(sorgu);
		res = this.querySelectSql(sorgu);
		JTable tablo = new JTable();
		tablo.setModel(DbUtils.resultSetToTableModel(res));
		this.baglantiKapat();
		return tablo;
	}

	@Override
	// kullanici sil
	public void kullaniciSil(int id) throws Exception {
		String condition = id + " = userID";
		String sorgu = createDeleteSql("soru1.user", condition);
		print(sorgu);
		this.querySql(sorgu);
		this.baglantiKapat();
	}

	@Override
	// kullanici guncelle
	public void kullaniciGuncelle(int id, String ad, String soyad, String cinsiyet) throws Exception {
		Object[] data = new Object[]{"name,"+ad , "surname,"+soyad , "gender," + cinsiyet};
		String condition = id + " = userID ";
		String sorgu = createUpdateSql("soru1.user", data, condition);
		print(sorgu);
		this.querySql(sorgu);
		this.baglantiKapat();
	}

	@Override
	// veritabanimizda kayit olup olmadigini kontrol eder.
	public boolean kayitKontrol() throws Exception {
		String sorgu = createSelectSql("soru1.user", "all", "", "");
		print(sorgu);
		return this.isExist(sorgu);
	}

	@Override
	public boolean isExistUser(String name, String surname , String gender) throws Exception {
			String condition = convertToSqlString(name)  + " = name and " + convertToSqlString(surname) + " = surname and " + convertToSqlString(gender) + " = gender";
			String sorgu = createSelectSql("soru1.user", "all", condition , "");
			print(sorgu);
			return this.isExist(sorgu);
	}

	@Override
	public int getUserIdWithNameAndSurname(String name, String surname) {
		int userId = 0;
		try {
			String condition = convertToSqlString(name) + " = name and " + convertToSqlString(surname) + " = surname";
			String sorgu = createSelectSql("soru1.user", "userID", condition , "");
			print(sorgu);
			ResultSet rs = this.querySelectSql(sorgu);

			if (rs.next()) {
				userId = Integer.parseInt(rs.getString(1));
			}
			this.baglantiKapat();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return userId;
	}
}
