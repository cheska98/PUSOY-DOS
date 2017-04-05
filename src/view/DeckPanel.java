package view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import control.ViewController;

public class DeckPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ViewController vc;

	private JLabel[] card;
	private LineBorder yellow;
	private LineBorder white;
	private int height = 70;
	private int width = 58;
	private int x = 33;
	private int y = 23;
	private int i = 0;
	private int number = 1;
	private int suitNumber = 1;
	private String img = "/img/";
	private String suitImg = null;
	private String suitChar = null;
	private String png = ".png";
	private String resource = null;
	private int selected = 0;

	public DeckPanel(ViewController vc) {

		this.vc = vc;
		//initialize();

	}

	public void initialize() {

		setLayout(null);
		setOpaque(false);
		setSize(736, 228);

		yellow = new LineBorder(Color.YELLOW, 3);
		white = new LineBorder(Color.WHITE, 3);

		card = new JLabel[13];

		for (i = 0; i < 7; i++) {
			card[i] = new JLabel();
			card[i].setBounds(x, y, width, height);
			x += 102;
			card[i].setBorder(white);
			add(card[i]);
		}

		x = 82;
		y = 128;
		for (int i = 7; i < 13; i++) {
			card[i] = new JLabel();
			card[i].setBounds(x, y, width, height);
			x += 102;
			card[i].setBorder(white);
			add(card[i]);
		}

		setCards();
		addListeners();

	}

	public void setCards() {
		for (int a = 0; a < 13; a++) {
			number = vc.getNumber(a);
			suitNumber = vc.getSuit(a);
			switch (suitNumber) {
			case 1:
				suitImg = "clubs/";
				suitChar = "C";
				break;
			case 2:
				suitImg = "spades/";
				suitChar = "S";
				break;
			case 3:
				suitImg = "hearts/";
				suitChar = "H";
				break;
			case 4:
				suitImg = "diamonds/";
				suitChar = "D";
				break;
			}

			resource = img + suitImg + Integer.toString(number) + suitChar + png;
			card[a].setIcon(new ImageIcon(DeckPanel.class.getResource(resource)));

		}

	}

	public void removeCard(int a) {
		card[a].setIcon(null);
	}

	public int getIsSelected() {
		return selected;
	}

	public ArrayList<Integer> getCardIndeces() {
		ArrayList<Integer> indeces = new ArrayList<Integer>();
		for (int j = 0; j < 13; j++) {
			LineBorder b = (LineBorder) card[j].getBorder();
			if (b.getLineColor() == Color.YELLOW) {
				indeces.add(j+1);   //to match the display echos bastaaa
			}
		}
		indeces.add(999); //pang end lang
		return indeces;
	}

	/*
	 * public boolean swapCards(int a, int b) { if (card[a].getIcon() != null &&
	 * card[b].getIcon() != null) { Icon imgA = card[a].getIcon(); Icon imgB =
	 * card[b].getIcon(); card[a].setIcon(imgB); card[b].setIcon(imgA); return
	 * true; } return false; }
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
			if (b.getLineColor() == Color.WHITE) {
				card[i].setBorder(yellow);
				selected++;
			} else {
				card[i].setBorder(white);
				selected--;
			}

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
