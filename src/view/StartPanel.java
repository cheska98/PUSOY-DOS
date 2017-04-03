package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.ViewController;

public class StartPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ViewController vc;

	private JLabel startTitle;
	private JButton startPlayingBtn;
	private JButton howToPlayBtn;
	private JButton quitBtn;
	private JLabel bg;
	private ImageIcon bgimg;
	private ImageIcon startTitleimg;

	public StartPanel(ViewController pc1) {

		this.vc = pc1;
		initialize();

	}

	private void initialize() {

		setLayout(null);
		setSize(1366, 768);

		startTitle = new JLabel("");
		startTitleimg = new ImageIcon(new ImageIcon(StartPanel.class.getResource("/img/startTitle.png")).getImage()
				.getScaledInstance(1366, 220, Image.SCALE_SMOOTH));
		startTitle.setIcon(startTitleimg);
		startTitle.setBounds(0, 100, 1366, 220);

		startPlayingBtn = new JButton("Start Playing");
		startPlayingBtn.setBackground(Color.WHITE);
		startPlayingBtn.setFont(new Font("Roboto", Font.PLAIN, 17));
		startPlayingBtn.setBounds(575, 406, 165, 37);

		howToPlayBtn = new JButton("How To Play");
		howToPlayBtn.setFont(new Font("Roboto", Font.PLAIN, 17));
		howToPlayBtn.setBackground(Color.WHITE);
		howToPlayBtn.setBounds(575, 474, 165, 37);

		quitBtn = new JButton("Quit");
		quitBtn.setFont(new Font("Roboto", Font.PLAIN, 17));
		quitBtn.setBackground(Color.WHITE);
		quitBtn.setBounds(575, 542, 165, 37);

		bg = new JLabel("");
		bgimg = new ImageIcon(new ImageIcon(StartPanel.class.getResource("/img/bg.png")).getImage()
				.getScaledInstance(1366, 768, Image.SCALE_SMOOTH));
		bg.setIcon(bgimg);
		bg.setBounds(0, 0, 1366, 768);

		addComponents();
		addListeners();

	}

	private void addComponents() {

		add(startTitle);
		add(startPlayingBtn);
		add(howToPlayBtn);
		add(quitBtn);
		add(bg);

	}

	private void addListeners() {

		startPlayingBtn.addActionListener(new StartPlayingBtn());
		howToPlayBtn.addActionListener(new HowToPlayBtn());
		quitBtn.addActionListener(new QuitBtn());

	}

	class StartPlayingBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {

			vc.showPanelPC1("GamePanel");
			JOptionPane.showMessageDialog(null, "You are Player 1.");
		}

	}

	class HowToPlayBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {

			javax.swing.UIManager.put("OptionPane.font", new Font("Arial", Font.PLAIN, 10));
			Object[] choices = { "Suit Order", "Card Combinations", "Dealing and Playing" };
			String s = (String) JOptionPane.showInputDialog(null, "Choose:", "How To Play", JOptionPane.PLAIN_MESSAGE,
					null, choices, "Suit Order");

			switch (s) {

			case "Suit Order":
				JOptionPane.showMessageDialog(null,
						"From highest to lowest:\n" + "Diamonds (D), Hearts (H), Spades (S), and Clubs (C),\n"
								+ "with 2D being the highest card and 3C the lowest.\n\n"
								+ "source: https://www.pinoygame.net/news/detail/6/\n"
								+ "               Pusoy-Dos-rules-on-Pinoygame-net.html");
				break;
			case "Card Combinations":
				JOptionPane.showMessageDialog(null,
						"1. Single card: Cards rank from 2 (highest) to 3 (lowest).\n"
								+ "   Between cards of the same rank, the higher suit beats\n"
								+ "   the lower suit. That is, a 5H beats a 5C.\n"
								+ "2. Pair: A pair of equally ranked cards. Between pairs\n"
								+ "   of the same rank, the pair with the higher suit wins.\n"
								+ "   That is, a 7C-7D beats a 7S-7H.\n"
								+ "3. Three of a kind: Three equally ranked cards. This is\n"
								+ "   a variation of game play and may be excluded or\n"
								+ "   included as a valid card combination.\n"
								+ "4. Five-card hand: Any five-card combination following\n"
								+ "   the poker hand rankings. From highest to lowest, valid\n"
								+ "   poker hands include:\n" + "   -> Royal Flush (10-J-Q-K-A with the same suits)\n"
								+ "   -> Straight Flush (any straight cards with the same\n" + "       suit)\n"
								+ "   -> Four of a Kind (plus an additional card/a Kicker)\n"
								+ "   -> Full House (any thee cards of the same number with\n"
								+ "       any two cards of the same number)\n" + "   -> Flush (same suit)\n"
								+ "   -> Straight (any straight cards)\n"
								+ "A combination can only be beaten by a better combination\n"
								+ "with the same number of cards: A single card can be\n"
								+ "beaten only by a single card, a pair by a pair, a three\n"
								+ "of a kind by a three of a kind, and a five-card hand by\n" + "a five-card hand.\n\n"
								+ "source: https://www.pinoygame.net/news/detail/6/\n"
								+ "               Pusoy-Dos-rules-on-Pinoygame-net.html");
				break;
			case "Dealing and Playing":
				JOptionPane.showMessageDialog(null,
						"- The dealer shuffles the deck and then deals one card at a\n"
								+ "   time counter-clockwise until each player has 13 cards\n"
								+ "   (52 cards / 4 players = 13 cards per player).\n"
								+ "- The game begins when the player holding the lowest card,\n"
								+ "   which is the 3C depending upon the suit order being played,\n"
								+ "   plays that card or a valid card combination including that\n"
								+ "   card. The card combination should be placed faced up in the\n"
								+ "   center of the table. The next person must play a higher\n"
								+ "   combination of the same number of cards or pass (play no\n"
								+ "   cards). If all players pass, the person who last put down\n"
								+ "   a card combination starts a new round by playing any card\n"
								+ "   or valid card combination.\n"
								+ "- All players are entitled to know the number of cards each\n"
								+ "   player has in hand at any time, and you must answer\n"
								+ "   truthfully if asked.\n\n" + "source: https://www.pinoygame.net/news/detail/6/\n"
								+ "               Pusoy-Dos-rules-on-Pinoygame-net.html");
				break;
			default:
				JOptionPane.showMessageDialog(null,
						"From highest to lowest:\n" + "Diamonds (D), Hearts (H), Spades (S), and Clubs (C),\n"
								+ "with 2D being the highest card and 3C the lowest.\n\n"
								+ "source: https://www.pinoygame.net/news/detail/6/\n"
								+ "               Pusoy-Dos-rules-on-Pinoygame-net.html");

			}

		}
	}

	class QuitBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {

			System.exit(0);

		}
	}

}
