package Connection;

import Model.*;

public class ClientSide {
	private Player player;
	
	public ClientSide(){
		player = new Player();
	}
	
	public void testLang(){
		System.out.println("Displaying received cards");
		System.out.println("size of hand: " + player.getHand().size());
		player.displayHand();
		System.out.println("Port: " + player.getPort());
		System.out.println("Num: " + player.getNum());
		System.out.println("Diplayed na^^");
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
		System.out.println("added a card: " + cnum + csuit);
	}
	
	public int getPlayerNum(){
		return this.player.getNum();
	}
	
	public int getPlayerPort(){
		return this.player.getPort();
	}
}
