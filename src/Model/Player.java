package Model;

import java.util.*;

public class Player {
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String name;
	private String address;
	private int playerNum;
	private boolean control = false;
	private boolean done = false;
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public void setNum(int num){
		this.playerNum = num;
	}
	
	public int getNum(){
		return this.playerNum;
	}
	
	public void setControl(boolean con){
		this.control = con;
	}
	
	public boolean getControl(){
		return this.control;
	}
	
	public void setDone(boolean done){
		this.done = done;
	}
	
	public boolean isDone(){
		return this.done;
	}
	
	public void drawCard(Card card){
		hand.add(card);
	}
	
	public void removeCard(int index){
		hand.remove(index);
	}
	
	public void removeCard(Card card){
		hand.remove(card);
	}
	
	public Card getCard(int index){
		return hand.get(index);
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	public void displayHand(){
		int i = 1;
		String j,k;
		for(Card c: hand){
			switch(c.getNumber()){
				case 11: j = "J";
					break;
				case 12: j = "Q";
					break;
				case 13: j = "K";
					break;
				case 14: j = "A";
					break;
				case 15: j = "2";
					break;
				default: j = Integer.toString(c.getNumber());
			}
			
			switch(c.getSuit()){
				case 1: k = "C";
					break;
				case 2: k = "S";
					break;
				case 3: k = "H";
					break;
				case 4: k = "D";
					break;
				default: k = "wat happened";
			}
			
			System.out.println("Card " + i + ": " +
							j + " " + k);
			i++;
		}
	}
}
