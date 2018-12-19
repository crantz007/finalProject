package com.kante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class huntingGui extends JFrame {
    private JTable huntingDataTable;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JSpinner animalSpeciesSpinner;
    private JSpinner genderSpinner;
    private JTextField iDtextField;
    private JTextField animalNametextField;
    private JButton addNewHunterButton;
    private JButton deleteHunterButton;
    private JButton quitButton;
    private JPanel mainPanel;


    private hunterDataBase db;
    private DefaultTableModel tableModel;
    private Vector columnNames;

    huntingGui(hunterDataBase db){
        this.db =db;

        setContentPane(mainPanel);
        pack();
        setTitle("HUnting Database Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        configurableTable();
        // set up the the species spinner
        ArrayList<String> list = new ArrayList<>();
        list.add(hunterDataBase.ANIMAL_SPECIE_BIRDS);
        list.add(hunterDataBase.ANIMAL_SPECIE_RODENTS);
        list.add(hunterDataBase.ANIMAL_SPECIE_HERBIVOROUS);
        animalSpeciesSpinner.setModel(new SpinnerListModel(list));
        setVisible(true);

        //event handlers for add , delete and quit button
        addNewHunterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHunter();

            }
        });
        deleteHunterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSlectedHunter();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                huntingGui.this.dispose();
            }
        });
    }


private void addHunter() {
    // get hunter
    int idData = Integer.parseInt(iDtextField.getText());
    if (idData < 0) {
        System.out.println("ID number needs to be superior than 0");
    }

    String firstNameData= firstNameTextField.getText();
    if(firstNameData==null  || firstNameData.trim().equals("")){
        JOptionPane.showMessageDialog(mainPanel,"Please enter a name for first name");
        return;
    }
    String lastNameData= lastNameTextField.getText();

    if(lastNameData ==null || lastNameData.trim().equals("")){
        JOptionPane.showMessageDialog(mainPanel,"Please enter a last name");
        return;
    }
    String animalNameData= animalNametextField.getText();
    if(animalNameData== null || animalNameData.trim().equals("")){
        JOptionPane.showMessageDialog(mainPanel,"Please enter a species name");
        return;
    }




    String speciesData = (String)(animalSpeciesSpinner.getValue());
    String genderData = ( String)(genderSpinner.getValue());

    db.addHunter(idData,firstNameData,lastNameData,animalNameData,speciesData,genderData);

    updateTable();


}
private  void  deleteSlectedHunter(){
        int currentRow = huntingDataTable.getSelectedRow();

        if(currentRow==-1){
            JOptionPane.showMessageDialog(mainPanel,"Please select a hunter to delete");
        }
        else {
            db.deleteHunter(currentRow);
            updateTable();
        }
}
private void configurableTable() {
    // set up jTable
    huntingDataTable.setGridColor(Color.BLACK);


    huntingDataTable.setAutoCreateRowSorter(true);

    columnNames = db.getColumNames();
    Vector data = db.getAllHunters();

    tableModel = new DefaultTableModel(data, columnNames) {

        @Override
        public boolean isCellEditable(int row, int col) {
            return (col == 3);
        }

        @Override
        public void setValueAt(Object val, int row, int col) {
            int id = (int) getValueAt(row, 0);


        }
    };
    huntingDataTable.setModel(tableModel);



}
private  void updateTable(){
    Vector data= db.getAllHunters();
    tableModel.setDataVector(data,columnNames);
    }
}





