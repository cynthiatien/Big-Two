import java.awt.Graphics;
import java.util.ArrayList;


public class Player {
	ArrayList<Card>pile;
	ArrayList<Card>selected;
	
	public Player(){
		pile = new ArrayList<Card>();
		selected = new ArrayList<Card>();
	}
	
	public ArrayList<Card> sort(ArrayList<Card>p){
		int numTimes = p.size();
		for (int i = 0; i < numTimes; i++){
			for (int j = i+1; j < numTimes; j++){
				Card one = p.get(i);
				Card two = p.get(j);
				int compare = one.compareTo(two); 
				if (compare > 0){
					p.set(i, two);
					p.set(j, one); 
				}
			}
		}
		return p;
	}
	
	public void draw(Graphics g, int x, int y){
		int width = 60;
		int height = 100;
		for(Card c: this.pile){
			c.draw(g,x,width,height);
			x+=15;
	}
		
	}
}
