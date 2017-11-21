/* @file mebitech isbasvurusu soru-1 
 * @description Initial Desktop Application  
 * @date 28.10.2014 19:40
 * @author Mehmed Fatih Temiz temizfatih54@gmail.com
 */

/*
   Database operations with mysql in java
*/
package sample_dp_1.Helpers;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import sample_dp_1.Enums.GenderType;
import sample_dp_1.Interfaces.IGeneralHelpers;

public class GeneralHelpers extends StringHelpers  implements IGeneralHelpers{
	// Gelen cinsiyet parametresini tinyint olarak kaydetmek için bu metodu
	// kullandik.

	private int selectedIndex = 0;

	private static String header[] = { "Kullancý ID", "Ad", "Soyad", "Cinsiyet" };

	@Override
	public String degerAta(String metin) {
		if (metin.equals(GenderType.MALE.toString())) {
			metin = GenderType.MALEId.toString();
		} else if (metin.equals(GenderType.FEMALE.toString())) {
			metin = GenderType.FEMALEId.toString();
		} else {
			metin = null;
		}
		return metin;
	}

	@Override
	public JTable getTableWithSetArea(JTable table, String[] value) {

		int row = table.getRowCount();
		int column = table.getColumnCount();

		try {
			for (int i = 0; i < row; i++) {
				if (i < column) {
					TableColumn tcolumn = table.getTableHeader().getColumnModel().getColumn(i);
					tcolumn.setHeaderValue(header[i]);
				}

				if (table.getModel().getValueAt(i, column - 1).toString().trim().equals(value[0]))
					table.setValueAt(value[1], i, column - 1);
				else if (table.getModel().getValueAt(i, column - 1).toString().trim().equals(value[2]))
					table.setValueAt(value[3], i, column - 1);
				print(table.getModel().getValueAt(i, column - 1).toString().trim() + " [" + i + " , " + (column - 1)
						+ "]");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return table;
	}

	@Override
	public int getSelectedIndexTable(JTable table, String[] value) {
		try {
			if (table.getValueAt(table.getSelectedRow(), 3).toString().equals(value[1]))
				selectedIndex = Integer.parseInt(value[0]);
			else if (table.getValueAt(table.getSelectedRow(), 3).toString().equals(value[3]))
				selectedIndex = Integer.parseInt(value[2]);
		} catch (Exception ex) {
			print(ex.getMessage() + ex.getStackTrace());
		}
		return selectedIndex;
	}

	@Override
	public void textFieldWithPlaceHolder(JTextField textBox, String placeHolder) {
		textBox.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textBox.getText().equals(placeHolder)) {
					textBox.setText("");
					textBox.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textBox.getText().isEmpty()) {
					textBox.setForeground(Color.GRAY);
					textBox.setText(placeHolder);
				}
			}
		});
	}
}
