package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.dto.StatisticalInformation;

public class StatisticsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4127624670821246999L;
	private StatisticalInformation statisticalInfo;
	private JLabel objectiveLabel;
	private JLabel timeLabel;
	private JLabel budgetLabel;
	private JLabel finalCostLabel;
	private JLabel totalProfitLabel;
	private JLabel difCostLabel;
	
	public StatisticsPanel(){
		generatePanel();
	}

	public void setStatisticalInformation(StatisticalInformation info){
		statisticalInfo = info;
		objectiveLabel.setText("Objetivo: Maximizar " + statisticalInfo.getUserInputData().getSkillToMax());
		timeLabel.setText("Tiempo de cómputo: " + statisticalInfo.getTime() + "ms.");
		budgetLabel.setText("Presupuesto: $" + statisticalInfo.getUserInputData().getBudget());
		finalCostLabel.setText("Costo final: $" + statisticalInfo.getFinalCost());
		totalProfitLabel.setText("Beneficio obtenido: " + statisticalInfo.getTotalProfit());
		long dif = statisticalInfo.getUserInputData().getBudget() - statisticalInfo.getFinalCost();
		difCostLabel.setText("Presupuesto sobrante: $"+ dif);
	}
	
	private void generatePanel() {
		this.setBorder(new TitledBorder("Estadísticas"));
		this.setLayout(new GridLayout(0,2));
		objectiveLabel = new JLabel("Objetivo: ");
		this.add(objectiveLabel);
		timeLabel = new JLabel("Tiempo de cómputo: ");
		this.add(timeLabel);
		budgetLabel = new JLabel("Presupuesto: ");
		this.add(budgetLabel);
		finalCostLabel = new JLabel("Costo final: ");
		this.add(finalCostLabel);
		totalProfitLabel = new JLabel("Beneficio obtenido: ");
		totalProfitLabel.setForeground(new Color(50,50,255));
		this.add(totalProfitLabel);
		difCostLabel = new  JLabel("Presupuesto sobrante: ");
		this.add(difCostLabel);
	}
	
}
