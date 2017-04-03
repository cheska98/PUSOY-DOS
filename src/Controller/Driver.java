package Controller;

import java.util.*;
import Model.*;

public class Driver {
	public static void main(String args[]){
		Deck deck = new Deck();
		initializeDeck(deck);
		//deck.displayAll();
		
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		Player player4 = new Player();
		
		//prompt how many players are participating
		int partcipants = 2;
		giveHand(player1, deck, partcipants);
		giveHand(player2, deck, partcipants);
		//giveHand(player3, deck, partcipants);
		//giveHand(player4, deck, partcipants);
		
		player1.getHand().sort(new Card());
		player2.getHand().sort(new Card());
		//player3.getHand().sort(new Card());
		//player4.getHand().sort(new Card());
		
		System.out.println("\nPlayer 1");
		player1.displayHand();
		System.out.println("\nPlayer 2");
		player2.displayHand();
//		System.out.println("\nPlayer 3");
//		player3.displayHand();
//		System.out.println("\nPlayer 4");
//		player4.displayHand();
		
		new Game(player1, player2);
		//new Game(player1, player2, player3, player4);
		
		/*
		//testing combination stuff
		ArrayList<Card> test = new ArrayList<Card>();
		Field field = new Field();
		test.add(new Card(9, 4));
		test.add(new Card(7, 4));
		test.add(new Card(9, 4));
		test.add(new Card(9, 4));
		test.add(new Card(7, 4));
		if(field.isStraight(test))
			System.out.println("Dang Straight");
		if(field.isFlush(test))
			System.out.println("Floosh");
		if(field.isQuadra(test))
			System.out.println("One of a Kind");
		if(field.isFullHouse(test))
			System.out.println("Full House");
			*/
	}


	public static void initializeDeck(Deck deck){
		for(int i = 3; i < 16; i++){
			deck.addCard(new Card(i, 1));
			deck.addCard(new Card(i, 2));
			deck.addCard(new Card(i, 3));
			deck.addCard(new Card(i, 4));
		}
	}
	
	public static void giveHand(Player player, Deck deck, int playerNum){
		Random rand = new Random();
		for(int j = 0; j < (52/playerNum); j++){
			Card test = new Card();
			int i = deck.getDeck().size();
			test = deck.getDeck().get(rand.nextInt(i));
			//System.out.println(test.getNumber() + " " + test.getSuit());
			player.drawCard(test);
			deck.deleteCard(test);
			//System.out.println("\n");
			//deck.displayAll();
		}
	}
}