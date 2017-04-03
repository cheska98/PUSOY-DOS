package view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class DeckPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel[] card;
	private LineBorder yellow;
	private LineBorder white;
	private int height = 70;
	private int width = 58;
	private int x = 33;
	private int y = 23;
	private int i = 0;
	
	public DeckPanel() {
		
		initialize();
		
	}
	
	private void initialize() {
		
		setLayout(null);
		setOpaque(false);
		setSize(736, 228);
		
		yellow = new LineBorder(Color.YELLOW, 3);
		white = new LineBorder(Color.WHITE, 3);
		
		card = new JLabel[13];
		
		for (i = 0; i < 7; i++) {
			card[i] = new JLabel();
			card[i].setBounds(x, y, width, height);
			x+=102;
			card[i].setBorder(white);
			add(card[i]);
		}
		
		x = 82;
		y = 128;
		for (int i = 7; i < 13; i++) {
			card[i] = new JLabel();
			card[i].setBounds(x, y, width, height);
			x+=102;
			card[i].setBorder(white);
			add(card[i]);
		}
		
		setCards();
		addListeners();
		
	}
	
	private void setCards() {
		
		card[0].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/diamonds/11D.png")));
		card[1].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/diamonds/12D.png")));
		card[2].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/diamonds/13D.png")));
		card[3].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/hearts/11H.png")));
		card[4].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/hearts/12H.png")));
		card[5].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/hearts/13H.png")));
		card[6].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/spades/11S.png")));
		card[7].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/spades/12S.png")));
		card[8].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/spades/13S.png")));
		card[9].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/clubs/11C.png")));
		card[10].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/clubs/12C.png")));
		card[11].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/clubs/13C.png")));
		card[12].setIcon(new ImageIcon(DeckPanel.class.getResource("/img/clubs/1C.png")));
		
	}
	
	public void removeCard(int a) {
		card[a].setIcon(null);
	}
	/*
	public boolean swapCards(int a, int b) {
		if (card[a].getIcon() != null && card[b].getIcon() != null) {
			Icon imgA = card[a].getIcon();
			Icon imgB = card[b].getIcon();
			card[a].setIcon(imgB);
			card[b].setIcon(imgA);
			return true;
		}
		return false;
	}
	*/
	private void addListeners() {
		for (i = 0; i < 13; i++)
			card[i].addMouseListener(new CardBorder());
	}
	
	class CardBorder implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent evt) {
			
			for (int j = 0; j < 13; j++)
				if (evt.getSource() == card[j])
					i = j;
			// if card has not been placed yet
			LineBorder b = (LineBorder) card[i].getBorder();
			if (b.getLineColor() == Color.WHITE)
				card[i].setBorder(yellow);
			else
				card[i].setBorder(white);
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}

	}
}
