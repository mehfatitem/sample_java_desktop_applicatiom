package sample_dp_1.Interfaces;


import javax.swing.JTable;

public interface IDb {

	public void kullaniciEkle(String ad, String soyad, String cinsiyet) throws Exception;

	public JTable kullaniciListele() throws Exception;

	public void kullaniciSil(int id) throws Exception;

	public void kullaniciGuncelle(int id, String ad, String soyad, String cinsiyet) throws Exception;

	public boolean kayitKontrol() throws Exception;

	public boolean isExistUser(String name, String surname, String gender) throws Exception;

	public int getUserIdWithNameAndSurname(String name, String surname);
}
