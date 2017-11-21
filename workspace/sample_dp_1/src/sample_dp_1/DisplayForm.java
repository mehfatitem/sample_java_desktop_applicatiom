/* @file mebitech isbasvurusu soru-1 
 * @description Initial Desktop Application  
 * @date 28.10.2014 19:40
 * @author Mehmed Fatih Temiz temizfatih54@gmail.com
 */

/*
   Database operations with mysql in java
*/

package sample_dp_1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import sample_dp_1.Db.Db;
import sample_dp_1.Enums.GenderType;
import sample_dp_1.Enums.UserMessage;
import sample_dp_1.Helpers.GeneralHelpers;
import sample_dp_1.Interfaces.IForm;


public class DisplayForm extends JFrame implements IForm {
	// Gerekli Componentlerimizin oluþturdugu nesneler
	private static final long serialVersionUID = 1L;
	private JFrame jframe = new JFrame();
	private JTable tablo = new JTable(){
		private static final long serialVersionUID = 1L;

		@Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false	
	       return false;
	    }
	};
	private JButton jgoster = new JButton();
	private JButton jguncelle = new JButton();
	private JButton jsil = new JButton();
	private JButton jgeri = new JButton();
	private JScrollPane jp = new JScrollPane();
	private JTextField jad = new JTextField(UserMessage.NamePlaceHolder.toString());
	private JTextField jsoyad = new JTextField(UserMessage.SurnamePlaceHolder.toString());
	private Dimension size = new Dimension(500, 600);// JFrame boyutu icin
	private static String[] eleman = { GenderType.MALE.toString(), GenderType.FEMALE.toString() };
	private static String [] value = {GenderType.MALEId.toString(),GenderType.MALE.toString() , GenderType.FEMALEId.toString() , GenderType.FEMALE.toString()};
	private int selectedIndex = 0;
	private JList<String> jcinsiyet = new JList<String>(eleman);
	
	// Class neslerimiz
	private Db db = new Db();
	private GeneralHelpers gh = new GeneralHelpers();

	// Kurucu metodumuz
	public DisplayForm() throws Exception {
		sonIslem();
		jgeri.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try{
					jsil.setVisible(false);
					jguncelle.setVisible(false);
					jad.setVisible(false);
					jsoyad.setVisible(false);
					jcinsiyet.setVisible(false);
					jgoster.setVisible(true);
					jgeri.setVisible(false);
					jp.setVisible(false);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		// Goster butonunun click olaylarinin gerceklestigi yer
		jgoster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (db.kayitKontrol() == false) {
						JOptionPane.showMessageDialog(jframe, UserMessage.ThereIsNoRecord);
					} else {
						tablo = db.kullaniciListele();
						jp = new JScrollPane(tablo);
						jp.setBounds(40, 50, 400, 300);
						jframe.getContentPane().add(jp);
						jsil.setVisible(false);
						jguncelle.setVisible(false);
						jad.setVisible(false);
						jsoyad.setVisible(false);
						jcinsiyet.setVisible(false);
						jgoster.setVisible(false);
						jgeri.setVisible(true);
						
						tablo = gh.getTableWithSetArea(tablo, value);
						tablo.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								if (e.getClickCount() == 1) {
									jsil.setVisible(true);
									jguncelle.setVisible(true);
									jad.setVisible(true);
									jsoyad.setVisible(true);
									jcinsiyet.setVisible(true);
									jad.setText(tablo.getValueAt(tablo.getSelectedRow(), 1).toString());
									jsoyad.setText(tablo.getValueAt(tablo.getSelectedRow(), 2).toString());
									
									selectedIndex = gh.getSelectedIndexTable(tablo, value);
									jcinsiyet.setSelectedIndex(selectedIndex);
								} 
							}
						});
					}
					db.baglantiKapat();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// guncelleme butonunun click olaylarinin gerceklestigi yer
		jguncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object objId = tablo.getValueAt(tablo.getSelectedRow(), 0);
				

				String ad = jad.getText().trim();
				String soyad = jsoyad.getText().trim();
				
				int Id = db.getUserIdWithNameAndSurname(ad, soyad);
				if(Id == 0){
					try{
						Id = (int) objId;
					}catch(Exception ex){
						JOptionPane.showMessageDialog(jframe, UserMessage.UpdateIsFailed);
						return;
					}
				}	
				
				String cinsiyet;
				try {
					cinsiyet = jcinsiyet.getSelectedValue().toString();
				} catch (Exception e) {
					cinsiyet = null;
				}
				try {
					cinsiyet = gh.degerAta(cinsiyet);
					if (db.isExistUser(jad.getText().trim(), jsoyad.getText().trim() , cinsiyet)) {
						JOptionPane.showMessageDialog(jframe, UserMessage.UserExist);
						return;
					}
					if (jad.getText().equals("") || jad.getText().equals(null) || jsoyad.getText().equals("")
							|| jsoyad.getText().equals(null)) {
						JOptionPane.showMessageDialog(jframe, UserMessage.EmptyArea);
					} else {
						db.kullaniciGuncelle(Id, ad, soyad, cinsiyet);
						jp.setVisible(false);
						jsil.setVisible(false);
						jguncelle.setVisible(false);
						jad.setVisible(false);
						jsoyad.setVisible(false);
						jcinsiyet.setVisible(false);
						jgoster.setVisible(true);
						jgeri.setVisible(false);
						tablo = db.kullaniciListele();
						jgoster.doClick();
						JOptionPane.showMessageDialog(jframe, UserMessage.UpdateIsSuccess);
					}
					cinsiyet = null;
				} catch (Exception e) {
					if (cinsiyet == null) {
						JOptionPane.showMessageDialog(jframe, UserMessage.EmptyArea);
					}
				}
			}
		});
		// sil butonunun click olaylarinin gerceklestigi yer
		jsil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object id = tablo.getValueAt(tablo.getSelectedRow(), 0);
				int Id = (int) (id);
				try {
					db.kullaniciSil(Id);
					jguncelle.setVisible(false);
					jp.setVisible(false);
					jsil.setVisible(false);
					jguncelle.setVisible(false);
					jad.setVisible(false);
					jsoyad.setVisible(false);
					jcinsiyet.setVisible(false);
					jgoster.setVisible(true);
					jgeri.setVisible(false);
					JOptionPane.showMessageDialog(jframe, UserMessage.DeleteIsSuccess);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	// Formumuzu burada tasarliyoruz.
	public void formOlustur() {
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.getContentPane().setBackground(Color.DARK_GRAY);
		jframe.getContentPane().setLayout(null);
		jframe.setTitle("Veri Göster");
		jgoster.setBounds(40, 10, 110, 25);
		jgoster.setText("Göster");
		jguncelle.setBounds(140, 500, 110, 25);
		jguncelle.setText("Güncelle");
		jguncelle.setVisible(false);
		jsil.setBounds(260, 500, 110, 25);
		jsil.setText("Sil");
		jsil.setVisible(false);
		jgeri.setBounds(380, 500, 110, 25);
		jgeri.setText("Geri");
		jgeri.setVisible(false);
		jad.setBounds(100, 360, 110, 25);
		gh.textFieldWithPlaceHolder(jad, UserMessage.NamePlaceHolder.toString());
		jad.setVisible(false);
		jsoyad.setBounds(100, 400, 110, 25);
		gh.textFieldWithPlaceHolder(jsoyad, UserMessage.NamePlaceHolder.toString());
		jsoyad.setVisible(false);
		jcinsiyet.setBounds(100, 440, 110, 40);
		jcinsiyet.setVisible(false);
		jframe.getContentPane().add(jgoster);
		jframe.getContentPane().add(jguncelle);
		jframe.getContentPane().add(jsil);
		jframe.getContentPane().add(jgeri);
		jframe.getContentPane().add(jad);
		jframe.getContentPane().add(jsoyad);
		jframe.getContentPane().add(jcinsiyet);
		jframe.pack();
		jframe.setSize(size);
		jframe.setResizable(false);
		jframe.setVisible(true);
	}

	@Override
	// Ekstra metot yazmak istersek , onuda en son olarak bu metodun icinde
	// cagiriyoruz. En son bu metoduda kurucu metot icinde cagariyoruz
	public void sonIslem() throws Exception {
		this.formOlustur();
	}
}
