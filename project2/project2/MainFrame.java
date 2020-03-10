//**************************************************************************************
// Name:        Longtin Hang
// Course:	   Java Development CSCI 3381 9:00am
// Assignment: Project 2
// Description: This program is read patient files and predict the patient progression
// 			   and update the file with newly added patients with GUI integration.
//**************************************************************************************


package project2;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainFrame {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Med Records");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 500);
		
		frame.getContentPane().add(new MainPanel());
		
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				MainPanel.doClose("./testwrite.csv");
			}
		});

	}

}
