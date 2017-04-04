package Connection;

import java.util.*;

import Model.Card;
import Model.Player;

public class ClientSide {
	private Player player;
	public ArrayList<Integer> indexes = new ArrayList<Integer>();
	
	public ClientSide(){
		player = new Player();
	}
	
	public void testLang(){
//		System.out.println("Displaying received cards");
//		System.out.println("size of hand: " + player.getHand().size());
//		player.displayHand();
//		System.out.println("Port: " + player.getPort());
		System.out.println("Player Num: " + player.getNum());
//		System.out.println("Diplayed na^^");
	}
	
	public void setPlayerNum(int num){
		player.setNum(num);
	}
	
	public void setPlayerControl(String control){
		if(control.equalsIgnoreCase("true"))
			player.setControl(true);
		else
			player.setControl(false);
	}
	
	public void setPlayerPort(int port){
		player.setPort(port);
	}
	
	public void addCard(int cnum, int csuit){
		player.getHand().add(new Card(cnum, csuit));
		//System.out.println("added a card: " + cnum + csuit);
	}
	
	public Card getCard(int index){
		return player.getCard(index - 1);
	}
	
	public void removeCard(Card c){
		player.removeCard(c);
	}
	
	public int getPlayerNum(){
		return this.player.getNum();
	}
	
	public int getPlayerPort(){
		return this.player.getPort();
	}
	
	public void addIndex(int i){
		indexes.add(i);
	}
	
	public void clearList(){
		indexes.clear();
	}
	
	public void displayHand(){
		this.player.displayHand();
	}
}
