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

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import sample_dp_1.Db.Db;
import sample_dp_1.Enums.GenderType;
import sample_dp_1.Enums.UserMessage;
import sample_dp_1.Helpers.GeneralHelpers;
import sample_dp_1.Interfaces.IForm;

public class BaseForm extends JFrame implements IForm {
	private static final long serialVersionUID = 1L;

	// Gerekli Componentlerimizin oluþturdugu nesneler
	private JFrame jframe = new JFrame();
	private JButton jkaydet = new JButton();
	private JButton jgoruntu = new JButton();
	private JTextField jad = new JTextField(UserMessage.NamePlaceHolder.toString());
	private JTextField jsoyad = new JTextField(UserMessage.SurnamePlaceHolder.toString());
	private static String[] eleman = { GenderType.MALE.toString(), GenderType.FEMALE.toString() };
	private JList<String> jcinsiyet = new JList<String>(eleman);
	// Class neslerimiz
	private Db db = new Db();
	private GeneralHelpers gh = new GeneralHelpers();

	private Dimension size = new Dimension(330, 300);// JFrame boyutu icin

	public BaseForm() throws Exception {

		sonIslem();
		// Kaydet butonunun click olaylarinin gerceklestigi yer
		jkaydet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cinsiyet;
				try {
					cinsiyet = jcinsiyet.getSelectedValue().toString();
				} catch (Exception e) {
					cinsiyet = null;
				}
				try {
					cinsiyet = gh.degerAta(cinsiyet);
					if (db.isExistUser(jad.getText().trim(), jsoyad.getText().trim(),cinsiyet)) {
						JOptionPane.showMessageDialog(jframe, UserMessage.UserExist);
						return;
					}
					if (jad.getText().equals("") || jad.getText().equals(null) || jsoyad.getText().equals("")
							|| jsoyad.getText().equals(null)) {
						JOptionPane.showMessageDialog(jframe, UserMessage.EmptyArea);
					} else {
						db.kullaniciEkle(jad.getText().trim(), jsoyad.getText().trim(), cinsiyet);
						JOptionPane.showMessageDialog(jframe, UserMessage.RecordIsSuccess);
					}
					cinsiyet = null;
				} catch (Exception e) {
					if (cinsiyet == null) {
						JOptionPane.showMessageDialog(jframe, UserMessage.EmptyArea);
					}
					e.getStackTrace();
				}
			}
		});
		// Kayit Listesi paneline gecis icin bu butonu kullaniyoruz
		jgoruntu.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try {
					DisplayForm gf = new DisplayForm();
					gf.show();
					gf.hide();
					jgoruntu.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	// Form olusturmak icin kullanilan metot
	public void formOlustur() {
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.getContentPane().setBackground(Color.DARK_GRAY);
		jframe.getContentPane().setLayout(null);
		jframe.setTitle("Veri Kayýt");
		jkaydet.setBounds(60, 200, 110, 25);
		jkaydet.setText("Kaydet");
		jgoruntu.setBounds(180, 200, 110, 25);
		jgoruntu.setText("Liste Formu");
		jad.setBounds(100, 40, 110, 25);
		gh.textFieldWithPlaceHolder(jad, UserMessage.NamePlaceHolder.toString());
		jsoyad.setBounds(100, 80, 110, 25);
		gh.textFieldWithPlaceHolder(jsoyad, UserMessage.SurnamePlaceHolder.toString());
		jcinsiyet.setBounds(100, 120, 110, 40);
		jframe.getContentPane().add(jad);
		jframe.getContentPane().add(jsoyad);
		jframe.getContentPane().add(jcinsiyet);
		jframe.getContentPane().add(jkaydet);
		jframe.getContentPane().add(jgoruntu);
		jframe.pack();
		jframe.setSize(size);
		jframe.setResizable(false);
		jframe.setVisible(true);
	}

	@Override
	// Son islem metodumuz
	public void sonIslem() throws Exception {
		this.formOlustur();
	}

	// Ana metodumuz
	public static void main(String args[]) throws Exception {
		new BaseForm();// Bu da Ana sinifimizin kurucu metodu
	}
}
