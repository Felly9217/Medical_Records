package project2;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainFrame {
	public static void main(String[] args) {
		JFrame frame = new JFrame("MainFrame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 500);
		
		frame.getContentPane().add(new MainPanel());
		
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				MainPanel.doClose();
			}
		});

	}

}
