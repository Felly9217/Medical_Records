package project2;

import java.awt.*;
import javax.swing.*;

import patientpredictor.Patient;
import patientpredictor.PatientCollection;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class MainPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	private ButtonGroup btnGrp;
	private JRadioButton rdbtnDp;
	private JRadioButton rdbtnCr;
	
	private JTable displayAllPatients;
	private JScrollPane scrollPane;
	private JLabel lblAllPatientRecords;
	private String[][] data;
	private String[] columnNames;
	
	private JTable displayAPatient;
	private JLabel lblPatientIdentification;
	private String[][] aData;
	private JScrollPane scrollPane2;
	
	private FrontPage front;
	private JLabel lblWelcomeToMed;
	private JLabel lblDeveloperLongtinHang;
	private JLabel label2;
	private ImageIcon tempImage2;
	private Image image2;
	
	public MainPanel() {
		/*
		 * MainPanel initializations
		 */
		myPats = new PatientCollection();
		setLayout(null);
	    setPreferredSize (new Dimension(WIDTH, HEIGHT));
	    setBackground (Color.white);
	    
	    /*
	     *  panel 1
	     */
	    panel = new JPanel();
	    panel.setBackground(Color.white);
	     
	    /*
	     *  add the children hospital logo
	     */
	    JLabel label = new JLabel();
	    label.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		front.createFrontPage();
	    	}
	    });
	    ImageIcon tempImage = new ImageIcon(MainPanel.class.getResource("/resources/ark-logo.png"));
	    Image image = tempImage.getImage().getScaledInstance(140, 70, java.awt.Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(image));
        Dimension size = label.getPreferredSize();
        label.setBounds(5, 250, size.width, size.height);
        panel.add(label);
	    
	    /*
	     *  panel 2 initializations
	     */
	    panel_1 = new JPanel();
	    panel_1.setBackground(new Color(32, 115, 149));
	    s = new SearchComboBox();
	    o = new OpenAll();
	    front = new FrontPage();
	    columnNames = new String[]{"ID",
                "Predict",
                "Result",
                "Protein 2358",
                "Protein 3697"};
	    
	    /*
	     * create the front page
	     */
	    
	    front.createFrontPage();
	    
	    /*
	     * add open all button to panel
	     */
	    JButton btnOpenFile = new JButton("Open All Patients");
	    btnOpenFile.addActionListener(new OpenAll());
	    btnOpenFile.setBounds(5, 100, 135, 23);
	    panel.add(btnOpenFile);
	    
	    /*
	     * Add search button to panel
	     */
	    comboBox = new JComboBox();
	    JButton btnSearch = new JButton("Search");
	    btnSearch.addActionListener(new SearchComboBox());
	    btnSearch.setBounds(5, 140, 135, 23);
	    panel.add(btnSearch);
	    
	    /*
	     * Edit button
	     */
	    btnEdit = new JButton("Edit");
	    btnEdit.addActionListener(new EditButton());
	    btnEdit.setBounds(50, 250, 85, 23);
	    
	    /*
	     * done button
	     */
	    btnDone = new JButton("Done");
	    btnDone.addActionListener(new DoneButton());
	    btnDone.setBounds(50, 250, 85, 23);   
	    
	    /*
	     * remove button
	     */
	    btnRemove = new JButton("Remove");
	    btnRemove.addActionListener(new RemoveButton());
	    btnRemove.setBounds(50, 285, 85, 23);   
	        
	    /*
	     * adding CR and DP radio Buttons	    
	     */
	    rdbtnDp = new JRadioButton("DP");
	    rdbtnDp.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		p.setResult("DP");
	    	}
	    });
	    rdbtnDp.setBounds(157, 250, 53, 23);
	    
	    rdbtnCr = new JRadioButton("CR");
	    rdbtnCr.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		p.setResult("CR");
	    	}
	    });
	    rdbtnCr.setBounds(157, 285, 53, 23);
	    
	    btnGrp = new ButtonGroup();
	    btnGrp.add(rdbtnCr);
	    btnGrp.add(rdbtnDp);
	    
	    /*
	     * The menu bar
	     */
	    JMenuBar menuBar = new JMenuBar();
	    menuBar.setBounds(0, 0, WIDTH, 22);
	    add(menuBar);
	    
	    JMenu mnFile = new JMenu("File");
	    menuBar.add(mnFile);
	    
	    JMenuItem mntmAdd = new JMenuItem("Add New File");
	    mntmAdd.addActionListener(new AddNewFile());
	    mnFile.add(mntmAdd);
	    
	    JMenuItem mntmSaveAs = new JMenuItem("Save As");
	    mntmSaveAs.addActionListener(new SaveToFile());
	    
	    JMenuItem mntmSave = new JMenuItem("Save");
	    mntmSave.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		doClose("./testwrite.csv");
	    	}
	    });
	    mnFile.add(mntmSave);
	    mnFile.add(mntmSaveAs);
	    
	    /*
	     * Adding panel and panel1 to splitPane
	     */
	    panel.setLayout(null);
	    panel_1.setLayout(null);
	    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, panel_1);
	    splitPane.setBounds(0, 22, WIDTH, HEIGHT);
	    splitPane.setDividerLocation(150);
	    splitPane.setEnabled(false);
	    add(splitPane);	    
	}
	
	/*
	 * Write to file
	 */
	public static void doClose(String filename) {
		myPats.writingToFile(filename);
	}
	
	/*
	 * create a table with one row to display for one patient
	 */
	public void tableForOnePatient() {
		aData = new String[1][5];
	    p = myPats.getPatient(comboBox.getSelectedItem().toString());
	    aData[0] = new String[]{p.getId(), 
    			p.getPredict(),
    			p.getResult(),
    			Double.toString(p.getGenome().get(2358)),
    			Double.toString(p.getGenome().get(3697))};
	    displayAPatient = new JTable(aData, columnNames);
	    displayAPatient.setFillsViewportHeight(true);
	    displayAPatient.setDefaultEditor(Object.class, null);
	    displayAPatient.getTableHeader().setReorderingAllowed(false);
	    scrollPane2 = new JScrollPane(displayAPatient);
	    scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollPane2.setBounds(50, 155, 555, 38);
	    panel_1.add(scrollPane2);
	}
	
	/*
	 * create the front page
	 */
	private class FrontPage{
		public FrontPage() {
		}
		
		public void createFrontPage() {
			panel_1.removeAll();
		    lblWelcomeToMed = new JLabel("<html>Welcome to Med Records!");
		    lblWelcomeToMed.setFont(new Font("Tahoma", Font.BOLD, 33));
		    lblWelcomeToMed.setForeground(Color.WHITE);
		    lblWelcomeToMed.setBounds(110, 45, 513, 93);
		    panel_1.add(lblWelcomeToMed);
		    
		    lblDeveloperLongtinHang = new JLabel("Developed by Longtin Hang");
		    lblDeveloperLongtinHang.setFont(new Font("Tahoma", Font.BOLD, 20));
		    lblDeveloperLongtinHang.setForeground(Color.WHITE);
		    lblDeveloperLongtinHang.setBounds(190, 165, 310, 45);
		    panel_1.add(lblDeveloperLongtinHang);
		    
		    label2 = new JLabel();
		    tempImage2 = new ImageIcon (MainPanel.class.getResource("/resources/giphy.gif"));
		    image2 = tempImage2.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_DEFAULT);
	        label2.setIcon(new ImageIcon(image2));
	        label2.setBounds(275, 285, 100, 100);
	        panel_1.add(label2);
	        repaint();
		}
	}
	
	/*
	 * create open all page to display all the patients with their info
	 */
	private class OpenAll implements ActionListener{
		public void actionPerformed(ActionEvent e) {
    		panel_1.removeAll();
    		
    		/*
    		 * Table for all patients
    		 */
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
    		    scrollPane.setBounds(50, 66, 540, 360);
    		    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    		    
    		    /*
    		     * Jlabel
    		     */
    		    lblAllPatientRecords = new JLabel("All Patient Records");
    		    lblAllPatientRecords.setFont(new Font("Tahoma", Font.BOLD, 30));
    		    lblAllPatientRecords.setForeground(Color.WHITE);
    		    lblAllPatientRecords.setBounds(50, 20, 512, 43);
    		    panel_1.add(lblAllPatientRecords);
    		    panel_1.add(scrollPane);
    	    revalidate();
    	    repaint();
		}
		
	}
	
	/*
	 * creating the search page to search, edit, or remove patients
	 */
	private class SearchComboBox implements ActionListener{
		public void actionPerformed(ActionEvent e) {
    		panel_1.removeAll();
    		
    		/*
    		 * comboBox and table to display
    		 */
    	    comboBox.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		panel_1.removeAll();
    	    		tableForOnePatient();
    	    	    panel_1.add(btnEdit);
    	    	    panel_1.add(comboBox);
    	    	    panel_1.add(lblPatientIdentification);
    	    	    revalidate();
    	    	    repaint();
    	    	}
    	    });
    	    ArrayList<String> ids = myPats.getIds();
    	    comboBox.setModel(new DefaultComboBoxModel(ids.toArray()));
    	    comboBox.setBounds(50, 75, 224, 23);
    	    tableForOnePatient();
    	    
    	    /*
    	     * JLabel
    	     */
    	    lblPatientIdentification = new JLabel("Patient Identification");
    	    lblPatientIdentification.setForeground(Color.WHITE);
    	    lblPatientIdentification.setFont(new Font("Tahoma", Font.BOLD, 21));
    	    lblPatientIdentification.setBackground(Color.BLACK);
    	    lblPatientIdentification.setBounds(50, 35, 394, 51);
    	    
    	    panel_1.add(lblPatientIdentification);
    	    panel_1.add(btnEdit);
    	    panel_1.add(comboBox);
    	    revalidate();
    	    repaint();
		}		
	}
	
	/*
	 * creating the edited page
	 */
	private class EditButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
    		panel_1.remove(btnEdit);
    		p = myPats.getPatient(comboBox.getSelectedItem().toString());
    	    if(p.getResult().equals("DP")) {
    	    	rdbtnDp.setSelected(true);
    	    } else if (p.getResult().equals("CR")){
    	    	rdbtnCr.setSelected(true);
    	    } else {
    	    	btnGrp.clearSelection();
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
	
	/*
	 * create the done page
	 */
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
	
	/*
	 * create remove page and also remove the patient
	 */
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
	
	/*
	 * Add new file to the backend and update the GUI
	 */
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
	
	/*
	 * save as to file
	 */
	private class SaveToFile implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser("./");
			JButton save = new JButton("Save to file");
			JTextArea textArea = new JTextArea(24, 80);
		    int retval = fileChooser.showSaveDialog(save);
		    if (retval == JFileChooser.APPROVE_OPTION) {
		    	doClose(fileChooser.getSelectedFile().toString() + ".csv");
		    }
		}	
	}
}
