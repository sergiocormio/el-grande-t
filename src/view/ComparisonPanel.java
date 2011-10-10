package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class ComparisonPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4107646989416522828L;

	public ComparisonPanel(){
		this.setLayout(new GridLayout(0,2));
		this.add(new ResultsPanel());
		this.add(new ResultsPanel());
	}
}
