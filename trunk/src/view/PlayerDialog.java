package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


import model.dto.Player;

public class PlayerDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8425500129667700319L;
	private Player player;
	private Container content;

	public PlayerDialog(JFrame fr, Player player){
		super(fr,"Jugador",true);
		this.player = player;
		content = getContentPane();
		content.setLayout(new BorderLayout());
		content.add(new JLabel(player.getName()));
		pack();
		
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
