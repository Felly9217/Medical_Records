package project2;

import java.awt.*;
import javax.swing.*;

import patientpredictor.Patient;
import patientpredictor.PatientCollection;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class MainPanel extends JPanel{
	private final int WIDTH = 800, HEIGHT = 500;
	private JPanel panel;
	private JPanel panel_1;
	private JSplitPane splitPane;
	
	private static PatientCollection myPats;
	private Patient p;
	private SearchComboBox s;
	private OpenAll o;	
	
	private JComboBox comboBox;
	
	private JButton btnEdit;
	private JButton btnDone;
	private JButton btnRemove;
	
	private JRadioButton rdbtnDp;
	private JRadioButton rdbtnCr;
	
	private JTable displayAllPatients;
	private JScrollPane scrollPane;
	private String[][] data;
	private String[] columnNames;
	
	private JTable displayAPatient;
	private String[][] aData;
	private JScrollPane scrollPane2;
	
	public MainPanel() {
		myPats = new PatientCollection();
		setLayout(null);

		
	    setPreferredSize (new Dimension(WIDTH, HEIGHT));
	    setBackground (Color.white);
	    
	    // panel 1
	    panel = new JPanel();
	    
	    // panel 2
	    panel_1 = new JPanel();
	    
	    s = new SearchComboBox();
	    o = new OpenAll();
	    columnNames = new String[]{"ID",
                "Predict",
                "Result",
                "Protein 2358",
                "Protein 3697"};
	    
	    
	    //open all button and display all in panel1 
	    JButton btnOpenFile = new JButton("Open All Patients");
	    btnOpenFile.addActionListener(new OpenAll());
	    btnOpenFile.setBounds(5, 100, 135, 23);
	    panel.add(btnOpenFile);
	    
	    
	    
	    // search button to display Combox box to get a patient ID
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
	    
	    JMenuItem mntmAdd = new JMenuItem("Add New File");
	    mntmAdd.addActionListener(new AddNewFile());
	    mnFile.add(mntmAdd);
	 
	    
	    
	    // add panels
	    //add(panel);
	    panel.setLayout(null);
	    //add(panel_1);
	    panel_1.setLayout(null);
	    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, panel_1);
	    splitPane.setBounds(0, 22, WIDTH, HEIGHT);
	    splitPane.setDividerLocation(150);
	    splitPane.setEnabled(false);
	    add(splitPane);
	    
	}
	
	public static void doClose() {
		myPats.writingToFile("./textwrite.csv");
	}
	
	public void tableForOnePatient() {
		aData = new String[1][5];
	    Patient text = myPats.getPatient(comboBox.getSelectedItem().toString());
	    aData[0] = new String[]{text.getId(), 
    			text.getPredict(),
    			text.getResult(),
    			Double.toString(text.getGenome().get(2358)),
    			Double.toString(text.getGenome().get(3697))};
	    displayAPatient = new JTable(aData, columnNames);
	    displayAPatient.setFillsViewportHeight(true);
	    displayAPatient.setDefaultEditor(Object.class, null);
	    displayAPatient.getTableHeader().setReorderingAllowed(false);
	    scrollPane2 = new JScrollPane(displayAPatient);
	    scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollPane2.setBounds(50, 120, 555, 38);
	    panel_1.add(scrollPane2);
	}
	
	
	private class OpenAll implements ActionListener{
		public void actionPerformed(ActionEvent e) {
    		panel_1.removeAll();
    		data = new String[myPats.getIds().size()][5];
    		int i = 0;
    		   for(String myInt : myPats.getIds()) {
    		    	data[i] = new String[]{myPats.getPatient(myInt).getId(), 
    		    			myPats.getPatient(myInt).getPredict(),
    		    			myPats.getPatient(myInt).getResult(),
    		    			Double.toString(myPats.getPatient(myInt).getGenome().get(2358)),
    		    			Double.toString(myPats.getPatient(myInt).getGenome().get(3697))};
    		    	i++;
    		    }
    		    displayAllPatients = new JTable(data, columnNames);
    		    displayAllPatients.setFillsViewportHeight(true);
    		    displayAllPatients.setDefaultEditor(Object.class, null);
    		    displayAllPatients.getTableHeader().setReorderingAllowed(false);
    		    scrollPane = new JScrollPane(displayAllPatients);
    		    scrollPane.setBounds(50, 66, 550, 300);
    		    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    		    panel_1.add(scrollPane);
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
    	    		tableForOnePatient();
    	    	    panel_1.add(btnEdit);
    	    	    panel_1.add(comboBox);
    	    	    revalidate();
    	    	    repaint();
    	    	}
    	    });
    	    ArrayList<String> ids = myPats.getIds();
    	    comboBox.setModel(new DefaultComboBoxModel(ids.toArray()));
    	    comboBox.setBounds(50, 66, 224, 23);
    	    tableForOnePatient();
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
    	    	rdbtnCr.setSelected(false);
    	    } else if (p.getResult().equals("CR")){
    	    	rdbtnCr.setSelected(true);
    	    	rdbtnDp.setSelected(false);
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
    		panel_1.remove(scrollPane2);
    		tableForOnePatient();
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
     	    	myPats.removePatient(comboBox.getSelectedItem().toString());
            	s.actionPerformed(e);
            }
		}
	}
	
	
	private class AddNewFile implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser("./");
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				myPats.addPatientsFromFile(jfc.getSelectedFile().toString());  		
			}
			o.actionPerformed(e);
		}
	}
}
