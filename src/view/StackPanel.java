package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StackPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel[] card;
	private int height = 70;
	private int width = 58;
	private int x = 145;
	private int y = 25;

	public StackPanel() {
		
		initialize();
		
	}
	
	private void initialize() {
		
		setLayout(null);
		setOpaque(false);
		setSize(736, 123);
		
		card = new JLabel[5];
		
		for (int i = 0; i < 5; i++) {
			card[i] = new JLabel();
			card[i].setBounds(x, y, width, height);
			x+=102;
			add(card[i]);
		}
		
		setCards();
		
	}
	
	private void setCards() {
		
		card[0].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/diamonds/11D.png")));
		card[1].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/diamonds/12D.png")));
		card[2].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/diamonds/13D.png")));
		card[3].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/hearts/11H.png")));
		card[4].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/hearts/12H.png")));
	
	}
	
	public void removeCards() {
		for (int i = 0; i < 5; i++)
			card[i].setIcon(null);
	}

}
