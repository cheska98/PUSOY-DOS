package control;

import java.util.ArrayList;

import Model.Card;
import Model.Player;
import view.DeckPanel;
import view.GamePanel;
import view.MainFrame;
import view.StartPanel;

public class ViewController {

	private MainFrame mf;
	private StartPanel startPanel;
	private GamePanel gamePanel;
	private Player player;
	private ArrayList<Card> deck = new ArrayList<Card>();
	private boolean buttonIsClicked = false;

	public ViewController(Player player) {

		this.player = player;
		deck = player.getHand();
		mf = new MainFrame(this);
		mf.setTitle("WELCOME TO PUSOY!!");
		startPanel = mf.getStartPanel();
		gamePanel = mf.getGamePanel();
	}
	
	public void updateTitle(String name){
		player.setName(name);
		mf.setTitle("Player " + Integer.toString(player.getNum()) + " - " + player.getName());
	}

	public void showPanelVC(String name) {
		mf.showPanelMF(name);
	}
	
	public int getNumber(int i) {
		return deck.get(i).getNumber();
	}
	
	public int getSuit(int i) {
		return deck.get(i).getSuit();
	}
	
	public boolean playersTurn() {
		return player.getControl();
	}
	
	public void addText(String text) {
		gamePanel.showMessage(text);
	}
	
	public ArrayList<Integer> getCardIndeces() {
		return gamePanel.getCardFromDP();
	}
	
	public boolean buttonIsClicked(){
		return this.buttonIsClicked;
	}
	
	public DeckPanel getDeckPanel(){
		return this.gamePanel.getDeckPanel();
	}
	
	public void setButton(boolean b) {
		buttonIsClicked = b;
	}
}
