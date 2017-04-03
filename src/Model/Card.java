package Model;

import java.util.*;

public class Card implements Comparator<Card>, Comparable<Card>{
	private int number;
	private int suit;
	
	public Card() {}
	
	public Card(int num, int suit){
		setNumber(num);
		setSuit(suit);
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getSuit() {
		return suit;
	}
	public void setSuit(int suit) {
		this.suit = suit;
	}
	
	//overriding the compare method
	@Override
	public int compare(Card a, Card b){
		if((a.getNumber() - b.getNumber()) == 0)
			return b.getSuit() - a.getSuit();
		else
			return b.getNumber() - a.getNumber();
	}
	
	@Override
	public int compareTo(Card c){
		if((c.getNumber() - this.getNumber()) == 0)
			return this.getSuit() - c.getSuit();
		else
			return this.getNumber() - c.getNumber();
	}
	
	public boolean equals(Card card){
		return ((card.getNumber() == this.number)&&(card.getSuit() == this.suit));
	}
}
