package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import control.ViewController;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ViewController vc;
	private DeckPanel deckPanel;
	private FieldPanel fieldPanel;
	private JButton swapBtn;
	private JButton placeBtn;
	private JButton passBtn;
	private JButton quitBtn;
	private JButton howToSwapBtn;
	private JButton howToPlaceBtn;
	private JButton howToPassBtn;
	private JLabel smallTitle;
	private JLabel lineLbl;
	private JLabel bg;
	private JTextPane textBox;
	private ImageIcon bgimg;
	private ImageIcon lineimg;

	public GamePanel(ViewController vc) {

		this.vc = vc;
		initialize();
	}

	private void initialize() {

		setLayout(null);
		setSize(1366, 748);

		fieldPanel = new FieldPanel(vc);
		fieldPanel.setBounds(112, 103, 736, 123);

		deckPanel = new DeckPanel(vc);
		deckPanel.setBounds(112, 338, 736, 228);

		smallTitle = new JLabel("");
		smallTitle.setIcon(new ImageIcon(GamePanel.class.getResource("/img/smallTitle.png")));
		smallTitle.setBounds(10, 11, 132, 71);

		lineLbl = new JLabel("");
		lineimg = new ImageIcon(new ImageIcon(GamePanel.class.getResource("/img/lineLbl.png")).getImage()
				.getScaledInstance(977, 14, Image.SCALE_SMOOTH));
		lineLbl.setIcon(lineimg);
		lineLbl.setBounds(0, 295, 977, 14);

		swapBtn = new JButton("Swap");
		swapBtn.setFont(new Font("Roboto", Font.PLAIN, 16));
		swapBtn.setBackground(Color.WHITE);
		swapBtn.setBounds(112, 603, 89, 31);

		placeBtn = new JButton("Place");
		placeBtn.setFont(new Font("Roboto", Font.PLAIN, 16));
		placeBtn.setBackground(Color.WHITE);
		placeBtn.setBounds(322, 603, 89, 31);

		passBtn = new JButton("Pass");
		passBtn.setFont(new Font("Roboto", Font.PLAIN, 16));
		passBtn.setBackground(Color.WHITE);
		passBtn.setBounds(536, 603, 89, 31);

		quitBtn = new JButton("Quit");
		quitBtn.setFont(new Font("Roboto", Font.PLAIN, 16));
		quitBtn.setBackground(Color.WHITE);
		quitBtn.setBounds(759, 603, 89, 31);

		howToSwapBtn = new JButton("?");
		howToSwapBtn.setBackground(Color.WHITE);
		howToSwapBtn.setFont(new Font("Calibri", Font.PLAIN, 10));
		howToSwapBtn.setBounds(211, 608, 37, 23);

		howToPlaceBtn = new JButton("?");
		howToPlaceBtn.setBackground(Color.WHITE);
		howToPlaceBtn.setFont(new Font("Calibri", Font.PLAIN, 10));
		howToPlaceBtn.setBounds(421, 608, 37, 23);

		howToPassBtn = new JButton("?");
		howToPassBtn.setBackground(Color.WHITE);
		howToPassBtn.setFont(new Font("Calibri", Font.PLAIN, 10));
		howToPassBtn.setBounds(635, 608, 37, 23);

		textBox = new JTextPane();
		textBox.setFont(new Font("Consolas", Font.PLAIN, 17));
		textBox.setEditable(false);
		textBox.setBounds(976, 0, 387, 748);
		textBox.setText("Game has started...");

		bg = new JLabel("");
		bg.setBounds(0, 0, 1366, 748);
		bgimg = new ImageIcon(new ImageIcon(GamePanel.class.getResource("/img/bg.png")).getImage()
				.getScaledInstance(1366, 748, Image.SCALE_SMOOTH));
		bg.setIcon(bgimg);

		addComponents();
		addListeners();

	}

	private void addComponents() {
		add(fieldPanel);
		add(deckPanel);
		add(smallTitle);
		add(lineLbl);
		add(swapBtn);
		add(placeBtn);
		add(passBtn);
		add(quitBtn);
		add(howToSwapBtn);
		add(howToPlaceBtn);
		add(howToPassBtn);
		add(textBox);
		add(bg);

	}

	private void addListeners() {

		swapBtn.addActionListener(new SwapBtn());
		placeBtn.addActionListener(new PlaceBtn());
		passBtn.addActionListener(new PassBtn());
		quitBtn.addActionListener(new QuitBtn());
		howToSwapBtn.addActionListener(new HowToSwapBtn());
		howToPlaceBtn.addActionListener(new HowToPlaceBtn());
		howToPassBtn.addActionListener(new HowToPassBtn());

	}

	public void append(String text) {
/*
		Color c = Color.BLACK;

		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Consolas");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = textBox.getDocument().getLength();
		textBox.setCaretPosition(len);
		textBox.setCharacterAttributes(aset, false);
		textBox.replaceSelection(text);
*/
		String newText = textBox.getText() + "\n" + text;
		textBox.setText(newText);
	}

	public void showMessage(String text) {

		append(text + "\n");

	}
	
	public ArrayList<Integer> getCardFromDP() {
		return deckPanel.getCardIndeces();
	}
	
	public DeckPanel getDeckPanel(){
		return this.deckPanel;
	}
	
	class SwapBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			// if only 2 cards are selected and get the cards' indeces
			// then
			/*
			 * if (!(deckPanel.swapCards(2, 3))) {
			 * JOptionPane.showMessageDialog(null, "Invalid!", "ERROR",
			 * JOptionPane.ERROR_MESSAGE); }
			 */
		}

	}

	class PlaceBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			vc.setButton(true);
		}

	}

	class PassBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			vc.setPass(true);
		}

	}

	class QuitBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {

			if (JOptionPane.showConfirmDialog(null, "Are you sure?", "END GAME",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				vc.showPanelVC("StartPanel");

		}

	}

	class HowToSwapBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {

			JOptionPane.showMessageDialog(null,
					"To swap the order of two cards, select two\n" + "cards first then click the swap button.");

		}

	}

	class HowToPlaceBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {

			JOptionPane.showMessageDialog(null,
					"After selecting your card or combination,\n" + "click the place button.");

		}

	}

	class HowToPassBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {

			JOptionPane.showMessageDialog(null, "Click the pass button when you want\n" + "to pass the round.");

		}

	}
}
