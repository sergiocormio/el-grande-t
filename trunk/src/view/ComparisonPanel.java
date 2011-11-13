package view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class ComparisonPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4107646989416522828L;

	public ComparisonPanel(){
		this.setLayout(new GridLayout(0,2));
		ResultsPanel r1 = new ResultsPanel();
		setResultSize(r1);
		this.add(r1);
		ResultsPanel r2 = new ResultsPanel();
		setResultSize(r2);
		this.add(r2);
	}
	
	//avoid image panel enlarge window
	private void setResultSize(ResultsPanel r){
		Dimension size = new Dimension(465, 625);
		r.setSize(size);
		r.setMaximumSize(size);
		r.setMinimumSize(size);
		r.setPreferredSize(size);
		
	}
}
