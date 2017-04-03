package Model;

import java.util.*;

public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card>();
	
	public void addCard(Card card){
		deck.add(card);
	}
	
	public void deleteCard(Card card){
		deck.remove(card);
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	public void displayAll(){
		int i = 1;
		String j,k;
		for(Card c: deck){
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
