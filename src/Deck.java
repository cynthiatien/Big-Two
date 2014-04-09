import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	ArrayList<Card> cards;
	int topCard = 52;

	public Deck(){	
		cards = new ArrayList<Card>();
		addAll();
		Collections.shuffle(cards);
		
	}

	public void addCard(int num, int suit){
		cards.add(new Card(num, suit));
	}
	
	public void remove(int num, int suit){
		cards.set(findCard(num,suit), new Card(0,0));
	}
	
	public int findCard(int num, int suit){
		int index = -1;
		for(Card x: cards){
			if(x.num == num && x.suit == suit){
				index = cards.indexOf(x);
			}
		}	
		return index;
	}

	public void addAll(){
		for(int i =1; i<14; i++){
			for(int j =1; j<5; j++){
				cards.add(new Card(i,j));
			}
		}
	}
	
//	
//	public Card dealCard(){
//		Card dealtCard;
//		if(topCard<cards.size()){
//			dealtCard = deck[topCard];
//			topCard++;
//		}
//		else
//			dealtCard = null;
//		return dealtCard;	
//	}
}