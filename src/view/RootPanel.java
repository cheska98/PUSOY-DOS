package view;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class RootPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private CardLayout cl;
	
	public RootPanel() {
		
		cl = new CardLayout();
		this.setLayout(cl);
		
	}
	
	public void addPanel(JPanel panel, String name) {
		
		this.add(panel, name);
		
	}
	
	public void showPanel(String name) {
		
		cl.show(this, name);
		
	}
}
