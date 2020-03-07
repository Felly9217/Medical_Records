package project2;

import java.awt.*;
import javax.swing.*;

import patientpredictor.Patient;
import patientpredictor.PatientCollection;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class MainPanel extends JPanel{
	private final int WIDTH = 800, HEIGHT = 500;
	private JPanel panel;
	private JPanel panel_1;
	
	private static PatientCollection myPats;
	private Patient p;
	
	private JTextField textField;
	private JTextArea textArea;
	private JScrollPane scroll;
	private JComboBox comboBox;
	
	private JButton btnEdit;
	private JButton btnDone;
	private JButton btnRemove;
	
	private JRadioButton rdbtnDp;
	private JRadioButton rdbtnCr;
	
	
	public MainPanel() {
		myPats = new PatientCollection();
		setLayout(null);
	
		
	    setPreferredSize (new Dimension(WIDTH, HEIGHT));
	    setBackground (Color.white);
	    
	    // panel 1
	    panel = new JPanel();
	    panel.setBounds(0, 0, 150, HEIGHT);
	    
	    // panel 2
	    panel_1 = new JPanel();
	    panel_1.setBounds(150, 22, 650, HEIGHT);
	    
	    
	    
	    //open all button and display all in panel1 
	    textArea = new JTextArea();
		textArea.setEditable(false);
		scroll = new JScrollPane (textArea);
	    JButton btnOpenFile = new JButton("Open All Patients");
	    btnOpenFile.addActionListener(new OpenAll());
	    btnOpenFile.setBounds(5, 100, 135, 23);
	    panel.add(btnOpenFile);
	    
	    
	    
	    // search button to display Combox box to get a patient ID
	    textField = new JTextField();
	    textField.setBounds(50, 120, 555, 23);
	    comboBox = new JComboBox();
	    JButton btnSearch = new JButton("Search");
	    btnSearch.addActionListener(new SearchComboBox());
	    btnSearch.setBounds(5, 140, 135, 23);
	    panel.add(btnSearch);
	    
	    
	    
	    //Edit button
	    btnEdit = new JButton("Edit");
	    btnEdit.addActionListener(new EditButton());
	    btnEdit.setBounds(50, 175, 85, 23);
	    
	    
	    
	    // done button
	    btnDone = new JButton("Done");
	    btnDone.addActionListener(new DoneButton());
	    btnDone.setBounds(50, 175, 85, 23);   
	    
	    
	    
	    // remove button
	    btnRemove = new JButton("Remove");
	    btnRemove.addActionListener(new RemoveButton());
	    btnRemove.setBounds(50, 210, 85, 23);
	    
	    
	    
	    // radio Buttons	    
	    rdbtnDp = new JRadioButton("DP");
	    rdbtnDp.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		p.setResult("DP");
	    	}
	    });
	    rdbtnDp.setBounds(157, 175, 53, 23);
	    
	    rdbtnCr = new JRadioButton("CR");
	    rdbtnCr.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		p.setResult("CR");
	    	}
	    });
	    rdbtnCr.setBounds(157, 210, 53, 23);
	    
	    ButtonGroup btnGrp = new ButtonGroup();
	    btnGrp.add(rdbtnCr);
	    btnGrp.add(rdbtnDp);
	    
	    
	    
	    // Menu bar
	    JMenuBar menuBar = new JMenuBar();
	    menuBar.setBounds(0, 0, WIDTH, 22);
	    add(menuBar);
	    
	    JMenu mnFile = new JMenu("File");
	    menuBar.add(mnFile);
	    
	    JMenuItem mntmAdd = new JMenuItem("Add");
	    mntmAdd.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		myPats.addPatientsFromFile("./newdata.csv");
	    	}
	    });
	    mnFile.add(mntmAdd);
	 
	    
	    
	    // add panels
	    add(panel);
	    panel.setLayout(null);
	    add(panel_1);
	    panel_1.setLayout(null);

	    
	}
	
	public static void doClose() {
		myPats.writingToFile("./textwrite.csv");
	}
	
	private class OpenAll implements ActionListener{
		public void actionPerformed(ActionEvent e) {
    		panel_1.removeAll();
    		textArea.setText(myPats.toString());
    	    scroll.setBounds(50, 66, 600, 300);
    	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	    panel_1.add(scroll);
    	    revalidate();
    	    repaint();
		}
		
	}
	
	
	private class SearchComboBox implements ActionListener{
		public void actionPerformed(ActionEvent e) {
    		panel_1.removeAll();
    	    comboBox.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		panel_1.removeAll();
    	    	    Patient text = myPats.getPatient(comboBox.getSelectedItem().toString());
    	    	    textField.setText(text.toString());
    	    	    panel_1.add(textField);
    	    	    panel_1.add(btnEdit);
    	    	    panel_1.add(comboBox);
    	    	    revalidate();
    	    	    repaint();
    	    	}
    	    });
    	    ArrayList<String> ids = myPats.getIds();
    	    comboBox.setModel(new DefaultComboBoxModel(ids.toArray()));
    	    Patient text = myPats.getPatient(comboBox.getSelectedItem().toString());
    	    textField.setText(text.toString());
    	    comboBox.setBounds(50, 66, 224, 23);
    	    panel_1.add(textField);
    	    panel_1.add(btnEdit);
    	    panel_1.add(comboBox);
    	    revalidate();
    	    repaint();
		}		
	}
	
	
	private class EditButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
    		panel_1.remove(btnEdit);
    		p = myPats.getPatient(comboBox.getSelectedItem().toString());
    	    if(p.getResult().equals("DP")) {
    	    	rdbtnDp.setSelected(true);
    	    } else {
    	    	rdbtnCr.setSelected(false);
    	    }
    		rdbtnDp.setVisible(true);
    		rdbtnCr.setVisible(true);
    		panel_1.add(btnDone);
    		panel_1.add(btnRemove);
    		panel_1.add(rdbtnDp);
    		panel_1.add(rdbtnCr);
    		repaint();	
		}
	}
	
	
	private class DoneButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
    		panel_1.remove(btnDone);
    		panel_1.remove(btnRemove);
    		panel_1.remove(rdbtnDp);
    		panel_1.remove(rdbtnCr);
    	    Patient text = myPats.getPatient(comboBox.getSelectedItem().toString());
    	    textField.setText(text.toString());
    		panel_1.add(textField);
    		panel_1.add(btnEdit);
    		repaint();		
		}
	}
	
	
	private class RemoveButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
    		JFrame frame = new JFrame("Remove Patient");
            int result = JOptionPane.showConfirmDialog(frame,"Are you sure?", "Remove Patient",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if(result == JOptionPane.YES_OPTION){
                panel_1.removeAll();
     	    	myPats.removePatient(comboBox.getSelectedItem().toString());
    	    	ArrayList<String> ids = myPats.getIds();
    	    	comboBox.setModel(new DefaultComboBoxModel(ids.toArray()));
	    	    Patient text = myPats.getPatient(comboBox.getSelectedItem().toString());
	    	    textField.setText(text.toString());
	    	    panel_1.add(textField);
    	    	panel_1.add(btnEdit);
    	    	panel_1.add(comboBox);
    	    	repaint();	
            }
		}
	}
}
