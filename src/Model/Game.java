package Model;

import java.io.IOException;
import java.util.*;
import Connection.*;

public class Game {
	private int numOfPlayers;
	private int lastPlayerToPlace; //last player to place a combi on the field
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private Field field = new Field();
	private Server server;
	private int readyToPlay = 0;   //indicates whether game is ready to play by all players
	
	public Game(){}
	
	public Game(Player player1, Player player2) throws Exception{
		this.player1 = player1;
		player1.setNum(1);
		this.player2 = player2;
		player2.setNum(2);
		numOfPlayers = 2;
		
		server = new Server();
		//waiting for clients
		int tempPort;
		while(readyToPlay < 3){
			if((tempPort = server.receiveStuff(2)) != 0){      //receive stuff and send info to player
				readyToPlay++;
				
				if(readyToPlay == 1){
					server.sendPlayerInfo(player1);
					player1.setPort(tempPort);
				}
				else if(readyToPlay == 2){
					server.sendPlayerInfo(player2);
					player2.setPort(tempPort);
				}
				
				System.out.println("Someone joined");
				if(readyToPlay == 2)
					start(numOfPlayers);
			}
		}
	}
	
	public Game(Player player1, Player player2,
				Player player3, Player player4) throws Exception{
		this.player1 = player1;
		player1.setNum(1);
		this.player2 = player2;
		player2.setNum(2);
		this.player3 = player3;
		player3.setNum(3);
		this.player4 = player4;
		numOfPlayers = 4;
		player4.setNum(4);
		
		server = new Server();
		//waiting for clients
		int tempPort;
		while(readyToPlay < 5){
			if((tempPort = server.receiveStuff(2)) != 0){      //receive stuff and send info to player
				readyToPlay++;
				
				if(readyToPlay == 1){
					server.sendPlayerInfo(player1);
					player1.setPort(tempPort);
				}
				else if(readyToPlay == 2){
					server.sendPlayerInfo(player2);
					player2.setPort(tempPort);
				}
				else if(readyToPlay == 3){
					server.sendPlayerInfo(player3);
					player3.setPort(tempPort);
				}
				else if(readyToPlay == 4){
					server.sendPlayerInfo(player4);
					player4.setPort(tempPort);
				}
				
				System.out.println("Someone joined");
				if(readyToPlay == 4)
					start(numOfPlayers);
			}
		}
	}
	
	public void start(int numOfPlayers) throws IOException{		
		//lets see who goes first
		if(numOfPlayers == 4){
			if(player1.getHand().get(player1.getHand().size() - 1).equals(new Card(3,1))){
				server.printStatus("You go first", player1.getPort());
				server.printStatus("Player 1 goes first", player2.getPort());
				server.printStatus("Player 1 goes first", player3.getPort());
				server.printStatus("Player 1 goes first", player4.getPort());
				//System.out.println("Player 1 goes first");
				player1.setControl(true);
			}
			if(player2.getHand().get(player2.getHand().size() - 1).equals(new Card(3,1))){
				server.printStatus("Player 2 goes first", player1.getPort());
				server.printStatus("You go first", player2.getPort());
				server.printStatus("Player 2 goes first", player3.getPort());
				server.printStatus("Player 2 goes first", player4.getPort());
				//System.out.println("Player 2 goes first");
				player2.setControl(true);
			}
			if(player3.getHand().get(player3.getHand().size() - 1).equals(new Card(3,1))){
				server.printStatus("Player 3 goes first", player1.getPort());
				server.printStatus("Player 3 goes first", player2.getPort());
				server.printStatus("You go first", player3.getPort());
				server.printStatus("Player 3 goes first", player4.getPort());
				//System.out.println("Player 3 goes first");
				player3.setControl(true);
			}
			if(player4.getHand().get(player4.getHand().size() - 1).equals(new Card(3,1))){
				server.printStatus("Player 4 goes first", player1.getPort());
				server.printStatus("Player 4 goes first", player2.getPort());
				server.printStatus("Player 4 goes first", player3.getPort());
				server.printStatus("You go first", player4.getPort());
				//System.out.println("Player 4 goes first");
				player4.setControl(true);
			}
		}
		
		if(numOfPlayers == 2){
			if(player1.getHand().get(player1.getHand().size() - 1).equals(new Card(3,1))){
				server.printStatus("You go first", player1.getPort());
				server.printStatus("Player 1 goes first", player2.getPort());
				//System.out.println("Player 1 goes first");
				player1.setControl(true);
			}
			if(player2.getHand().get(player2.getHand().size() - 1).equals(new Card(3,1))){
				server.printStatus("Player 2 goes first", player1.getPort());
				server.printStatus("You go first", player2.getPort());
				//System.out.println("Player 2 goes first");
				player2.setControl(true);
			}
		}
		/*
		System.out.println("Set Player1 name: ");
		System.out.println("Set Player2 name: ");
		System.out.println("Set Player3 name: ");
		System.out.println("Set Player4 name: ");
		*/
		switch(numOfPlayers){
			case 2: 
				play2P();
				break;
			case 4: 
				play4P();
				break;
		}
	}
	
	public void play2P() throws IOException{	
		int prevPlayer;
		
		if(player1.getControl())
			prevPlayer = 2;
		else
			prevPlayer = 1;
		
		while((player1.getHand().size() != 0)&&(player2.getHand().size() != 0)){
			if(prevPlayer == 2){
				server.printStatus("Your turn", player1.getPort());
				server.printStatus("Player 1's turn", player2.getPort());
				//System.out.println("Player 1's turn");
				playerTurn(player1);
				prevPlayer = 1;
			}
			else{
				server.printStatus("Player 2's turn", player1.getPort());
				server.printStatus("Your turn", player2.getPort());
				//System.out.println("Player 2's turn");
				playerTurn(player2);
				prevPlayer = 2;
			}
		}
		
		if(player1.getHand().size() == 0){
			server.printStatus("You Win!", player1.getPort());
			server.printStatus("Player 1 Wins!", player2.getPort());
			//System.out.println("Player 1 Wins!");
		}
		else if(player2.getHand().size() == 0){
			server.printStatus("Player 2 Wins!", player1.getPort());
			server.printStatus("You Win!", player2.getPort());
			//System.out.println("Player 2 Wins!");
		}
	}
	
	public void play4P() throws IOException{
		int prevPlayer;
		int justFinished = 0;
		
		if(player1.getControl())
			prevPlayer = 4;
		else if(player2.getControl())
			prevPlayer = 1;
		else if(player3.getControl())
			prevPlayer = 2;
		else 
			prevPlayer = 3;
		
		while(!onlyOneLeft()){
			if(prevPlayer == 4){
				justFinished = process4P(player1, justFinished);
				prevPlayer = 1;
			}
			else if(prevPlayer == 1){
				justFinished = process4P(player2, justFinished);
				prevPlayer = 2;
			}
			else if(prevPlayer == 2){
				justFinished = process4P(player3, justFinished);
				prevPlayer = 3;
			}
			else{
				justFinished = process4P(player4, justFinished);
				prevPlayer = 4;
			}
		}
		
		if(!player1.isDone()){
			server.printStatus("You Lost :((", player1.getPort());
			server.printStatus("Player 1 Lost", player2.getPort());
			server.printStatus("Player 1 Lost", player3.getPort());
			server.printStatus("Player 1 Lost", player4.getPort());
			//System.out.println("Player 1 Lost!");
		}
		else if(!player2.isDone()){
			server.printStatus("Player 2 Lost", player1.getPort());
			server.printStatus("You Lost :((", player2.getPort());
			server.printStatus("Player 2 Lost", player3.getPort());
			server.printStatus("Player 2 Lost", player4.getPort());
			//System.out.println("Player 2 Lost!");
		}
		else if(!player3.isDone()){
			server.printStatus("Player 3 Lost", player1.getPort());
			server.printStatus("Player 3 Lost", player2.getPort());
			server.printStatus("You Lost :((", player3.getPort());
			server.printStatus("Player 3 Lost", player4.getPort());
			//System.out.println("Player 3 Lost!");
		}
		else if(!player4.isDone()){
			server.printStatus("Player 4 Lost", player1.getPort());
			server.printStatus("Player 4 Lost", player2.getPort());
			server.printStatus("Player 4 Lost", player3.getPort());
			server.printStatus("You Lost :((", player4.getPort());
			//System.out.println("Player 4 Lost!");
		}
	}
	
	//also returns if player just finished
	public int process4P(Player player, int justFinished) throws IOException{
		if(!player.isDone()){
			server.printStatus("Player " + player.getNum() +"'s turn", player.getPort());
			//System.out.println("Player " + player.getNum() + "'s turn");
			if(justFinished == 1){
				justFinished = 0;
				server.printStatus("You have control", player.getPort());
				//System.out.println("You have control");
			}
			playerTurn(player);
			if(player.getHand().size()==0){
				justFinished = 1;
				player.setDone(true);
				field.clearCombi();
			}
		}
		return justFinished;
	}
	
	//returns true if there is one player left
	public boolean onlyOneLeft(){
		int ctr = 0;
	
		if(player1.isDone())
			ctr++;
		if(player2.isDone())
			ctr++;
		if(player3.isDone())
			ctr++;
		if(player4.isDone())
			ctr++;
			
		return (ctr >= 3);
	}
	
	public void playerTurn(Player player) throws IOException{
		//@SuppressWarnings("resource")
		//Scanner sc = new Scanner(System.in);
		ArrayList<Card> playerPicks = new ArrayList<Card>();
		boolean invalidFlag = false;
		
		if(lastPlayerToPlace == player.getNum()){
			server.printStatus("You have control", player.getPort());
			//System.out.println("You have control");
			player.setControl(true);
			field.clearCombi();
		}
		
		System.out.println("Player " + player.getNum() + ":");      //should be changed to player name time
		field.displayCurrCombi();
		server.sendField(field);
		
		int pick;
		do{
			//this is in driver output
			player.displayHand();
			//this is for client
			server.showThatHand(player.getPort());
			pick = 999;
			playerPicks.clear();
			
			if(player.getControl())
				field.clearCombi();
			noControl(numOfPlayers);
			
			do{
				server.printStatus("Select the card/s you will pick\n" + 
								   "Enter 999 to end and 0 to pass", player.getPort());
				//System.out.println("Select the card/s you will pick");
				//System.out.println("Enter 999 to end and 0 to pass");     //in the gui this has got to change boi
				
				pick = server.receiveResponse();
				System.out.println("Pick: " + pick);
				//pick = sc.nextInt();
				//sc.nextLine();
				if((pick != 999)&&(pick != 0))
					playerPicks.add(player.getCard(pick - 1));    //coz in the display it doesnt start with 0
				
			}while((pick != 999)&&(pick != 0));
			
			if(pick != 0){
				if(field.isValidNew(playerPicks)){
					if(field.getCurrCombi().size() != 0){
						if(field.isValidFight(playerPicks)){     //meron nang pang update ng field dito
							for(int i = playerPicks.size(); i > 0; i--)
								player.removeCard(playerPicks.get(i-1));							
							invalidFlag = false;
							lastPlayerToPlace = player.getNum();
						}
						else{
							server.printStatus("Boi di pwede yan invalid combi men", player.getPort());
							//System.out.println("Boi di pwede yan invalid combi men");
							invalidFlag = true;
						}
					}
					else{
						for(int i = playerPicks.size(); i > 0; i--)
							player.removeCard(playerPicks.get(i-1));
						field.update(playerPicks);
						invalidFlag = false;
						lastPlayerToPlace = player.getNum();
					}
				}
				else{
					server.printStatus("Boi di pwede yan invalid combi men", player.getPort());
					//System.out.println("Boi di pwede yan invalid combi men");
					invalidFlag = true;
				}
			}
		}while(invalidFlag && (pick != 0));
		
		//starting from this part its all cause of network 
		if(pick != 0){    //if hindi nagpass ung player, ibiig sabihin may valid combi na nalagay
			//field
			System.out.println("removing");
			server.removalOfCards(player.getPort());
		}
	}
	
	public void noControl(int numOfPlayers){
		if(numOfPlayers > 0){
			player1.setControl(false);
			player2.setControl(false);
			if(numOfPlayers == 4){
				player3.setControl(false);
				player4.setControl(false);
			}
		}
	}
	
}
