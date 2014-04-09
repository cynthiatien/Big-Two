import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Card implements Comparable<Card>{
	public int cardNum;
	int suit;
	int num;
	ArrayList<String>position;
//	final static String[] suits = {"Club", "Spade", "Heart", "Diamond"};
//	final static String[] types = {"Ace", "2", "3","4","5","6","7","8", "9","10", "Jack", "Queen", "King"};
	ImageIcon c;
	Image card;
	String id;
	int intID;
	int posX, deltaX,deltaY;
	int posY;
	boolean clicked;
	
	public Card (int num, int suit){
		this.num = num;
		this.suit = suit;
		id = String.valueOf(num) + String.valueOf(suit);
		intID = Integer.parseInt(id);
		String url = "images/" +id + ".png";
		c= new ImageIcon(url);
		card = c.getImage();
		clicked = false;
		posY = 500;
	}
	
	 @Override
	 public int compareTo(Card c){
		 return this.intID - c.intID ;
	 }
	 
	 public void draw(Graphics g, int x, int dx, int dy){
		 posX = x;
		 deltaX = dx;
		 deltaY=dy;
		 Graphics2D g2D = (Graphics2D) g;
		 g2D.drawImage(card, posX, posY, dx,dy,null);
	 }
	 
	 public void setCardNum(int theCard) {
	        cardNum = theCard;
	    }
	 
	 
}