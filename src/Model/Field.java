package Model;

import java.util.*;

public class Field {
	private ArrayList<Card> combination = new ArrayList<Card>();
	private int combiNum;      //indicates if combi is singles/pair/5's etc.
	private boolean isFirstDrop = true;   //verify if first placement has 3C
	
	public void clearCombi(){
		combination.clear();
	}
	
	//checks if combi is valid just the way it is
	//this method is called if player has control
	//does not contain the method for updating the combi
	//so call it after u check if its valid
	public boolean isValidNew(ArrayList<Card> combination){
		int newCombiNum = combination.size();
		
		switch(newCombiNum){
			case 1:
				//welp
				break;
			case 2:
				if(!isSameNum(combination))
					return false;
				break;
			case 3:
				if(!isSameNum(combination))
					return false;
				break;
			case 4:
				if(!isSameNum(combination))
					return false;
				break;
			case 5:
				if(what5CardCombiIsThis(combination) == 0)
					return false;
				break;
			default:
		}
		
		if(isFirstDrop){
			for(Card c: combination){
				if(c.equals(new Card(3,1))){
					isFirstDrop = false;
					return true;
				}
			}
			return false;
		}
		
		return true;
	}
	
	//checks if combination set by player is higher than prev combi
	//unless combi has been cleared
	//NOTE: checking of "if combi is clear" is to be made in Game.java
	//ALSO NOTE: Indicate in Game.java that combination must have at least 1 card before 
	//going to Field.java
	//isValidFight() is also calling the method for updating the combi
	public boolean isValidFight(ArrayList<Card> combination){
		combiNum = this.combination.size();
		int newCombiNum = combination.size();
		
		if(!isValidNew(combination))
			return false;
		
		if(combiNum != newCombiNum)
			return false;
		
		//comparing the combis and checking if the combis are legit
		switch(combiNum){
			case 1: 
				if(this.combination.get(0).compareTo(combination.get(0)) > 0)
					return false;
				break;
			case 2:
				if(getHighestCard(this.combination).compareTo(getHighestCard(combination)) > 0)
					return false;
				break;
			case 3:
				if(getHighestCard(this.combination).compareTo(getHighestCard(combination)) > 0)
					return false;
				break;
			case 4:
				if(getHighestCard(this.combination).compareTo(getHighestCard(combination)) > 0)
					return false;
				break;
			case 5:
				if(what5CardCombiIsThis(this.combination) > what5CardCombiIsThis(combination))
					return false;
				if(what5CardCombiIsThis(this.combination) == what5CardCombiIsThis(combination)){
					//determine which combi is higher
					//this method requires that the combis being compared is the same kind of combi
					if(newCombiIsLower(combination) == 1)
						return false;
				}
				break;
			default:
		}
		//if all is well then
		this.update(combination);
		return true;
	}
	
	public void update(ArrayList<Card> combination){
		clearCombi();
		this.combination = combination;
	}
	
	public boolean isSameNum(ArrayList<Card> combination){
		int combiNum = combination.size();
		int baseNum = combination.get(0).getNumber();
		while(combiNum != 0){
			if(!(combination.get(combiNum - 1).getNumber() == baseNum))
				return false;
			combiNum--;
		}

		return true;
	}
	
	public boolean isStraight(ArrayList<Card> combination){
		combination.sort(new Card());
		Card initial = combination.get(0);
		//NOTE: straight combis involving 2 and 3 haven't been coded yet 
		//parang ung combi na A,2,3,4,5 next time na yun hehe
		int i = 0;
		//this sorted list starts from the highest number
		for(Card c: combination){
			if(!((c.getNumber() + i) == initial.getNumber()))
				return false;
			i++;
		}
		return true;
	}
	
	public boolean isFlush(ArrayList<Card> combination){
		int suit = combination.get(0).getSuit();
		for(Card c: combination){
			if(!(suit == c.getSuit()))
				return false;
		}
		return true;
	}
	
	public boolean isFullHouse(ArrayList<Card> combination){
		combination.sort(new Card());
		Card initial = combination.get(0);
		int i = 0;  //counter for mismatch (should end in 1 if FullHouse)
		int j = 0;  //counter for match (should be 3 or 2)
		int k = 0;	//another counter for match (trio match and pair match)
		
		for(Card c: combination){
			if(!(c.getNumber() == initial.getNumber())){
				initial = c;
				i++;
				k++;
			}
			else{
				if(i == 1)
					k++;
				else
					j++;
			}
		}
		//this println is for determing the trios and pair
		//System.out.println("j: " + j + "\nk: " + k + "\ni: " + i);
		
		return ((i == 1) && 
			   (((j == 3) && (k == 2)) ||
				((j == 2) && (k == 3))));
	}
	
	public boolean isQuadra(ArrayList<Card> combination){
		Card initial = combination.get(0);
		int i = 0;  //counter for mismatch (should not exceed 1 if quadra)
		//lets see if the initial card is the 4 of a kind
		//if not then replace dat card with the potential 4 of a kind
		if(!(combination.get(1).getNumber() == initial.getNumber()))
			initial = combination.get(1);
			
		for(Card c: combination){
			if(!(c.getNumber() == initial.getNumber()))
				i++;
		}
		
		return(!(i > 1));
	}
	
	public int what5CardCombiIsThis(ArrayList<Card> combination){
		//6 - royal flush
		//5 - straight flush
		//4 - quadra
		//3 - full house
		//2 - flush
		//1 - straight
		//0 - wtf
		if(isStraight(combination)&&isFlush(combination)&&
			(getHighestCard(combination).getNumber()==15))
			return 6;
		if(isStraight(combination)&&isFlush(combination))
			return 5;
		if(isQuadra(combination))
			return 4;
		if(isFullHouse(combination))
			return 3;
		if(isFlush(combination))
			return 2;
		if(isStraight(combination))
			return 1;
		return 0;
	}
	
	public int newCombiIsLower(ArrayList<Card> combination){
		
		switch(what5CardCombiIsThis(combination)){
			case 1:
				if(getHighestCard(this.combination).compareTo(getHighestCard(combination)) > 0)
					return 1;
				break;
				
			case 2:
				if(getHighestCard(this.combination).getSuit() > combination.get(0).getSuit())
					return 1;
				else if(getHighestCard(this.combination).getSuit() == combination.get(0).getSuit()){
					if(getHighestCard(this.combination).getNumber() > combination.get(0).getNumber())
						return 1;
				}
				break;
				
			case 3:
				//determine which is the trio of each and compare which has the higher num
				int initial; 
				int extraContainer = 0;
				int i = 0;  //counter for possible trio
				int newCombiCardNum, currCombiCardNum;
				
				//for the newCombi
				initial = combination.get(0).getNumber();
				for(Card c: combination){
					if(c.getNumber() == initial)
						i++;
					else
						extraContainer = c.getNumber();
				}
				
				if(i == 3)
					newCombiCardNum = initial;
				else
					newCombiCardNum = extraContainer;
				
				i = 0;
				extraContainer = 0;
				//for the currCombi
				initial = this.combination.get(0).getNumber();
				for(Card c: this.combination){
					if(c.getNumber() == initial)
						i++;
					else
						extraContainer = c.getNumber();
				}
				
				if(i == 3)
					currCombiCardNum = initial;
				else
					currCombiCardNum = extraContainer;
				
				if(currCombiCardNum > newCombiCardNum)
					return 1;
				break;
				
			case 4:
				//determine which is the quadro of each and compare which has the higher num
				int start; 
				int extra = 0;
				int a = 0;  //counter for possible quadro
				int newCardNum, currCardNum;
				
				//for the newCombi
				start = combination.get(0).getNumber();
				for(Card c: combination){
					if(c.getNumber() == start)
						a++;
					else
						extra = c.getNumber();
				}
				
				if(a == 4)
					newCardNum = start;
				else
					newCardNum = extra;
				
				a = 0;
				extra = 0;
				//for the currCombi
				start = this.combination.get(0).getNumber();
				for(Card c: this.combination){
					if(c.getNumber() == start)
						a++;
					else
						extra = c.getNumber();
				}
				
				if(a == 4)
					currCardNum = start;
				else
					currCardNum = extra;
				
				if(currCardNum > newCardNum)
					return 1;
				break;
				
			case 5:
				if(getHighestCard(this.combination).compareTo(getHighestCard(combination)) > 0)
					return 1;
				break;
				
			case 6:
				if(getHighestCard(this.combination).compareTo(getHighestCard(combination)) > 0)
					return 1;
				break;
				
		}
		
		return 0;
	}
	
	public Card getHighestCard(ArrayList<Card> combination){
		combination.sort(new Card());
		return combination.get(0);
	}
	
	public ArrayList<Card> getCurrCombi(){
		return combination;
	}
	
	public void displayCurrCombi(){
		String j,k;
		System.out.println("Current cards on field:");
		for(Card c: combination){
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
			System.out.print(j + " " + k + "\n");
		}
	}
}
