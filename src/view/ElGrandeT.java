package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class ElGrandeT {
	private JFrame jf;
	
	public ElGrandeT(){
		initializeFrame();
		jf.pack();
		jf.setVisible(true);
	}
	
	private void initializeFrame() {
		jf = new JFrame("El Grande T");
		jf.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ElGrandeT();
	}
}
