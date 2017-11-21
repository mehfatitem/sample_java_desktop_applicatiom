package sample_dp_1.Interfaces;

import javax.swing.JTable;
import javax.swing.JTextField;

public interface IGeneralHelpers {

	public String degerAta(String metin);

	public JTable getTableWithSetArea(JTable table, String[] value);

	public int getSelectedIndexTable(JTable table, String[] value);

	public void textFieldWithPlaceHolder(JTextField textBox, String placeHolder);
}
