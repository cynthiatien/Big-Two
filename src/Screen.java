
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Screen extends JPanel implements MouseListener {
	Deck d;
	Player p1, p2, p3, p4;
	ArrayList<Player>allPlayers;
	ImageIcon bg = new ImageIcon("images/background.jpg");
	Image background = bg.getImage();
	Font font = new Font("Arial", Font.BOLD, 20);
	Font font1 = new Font("Arial", Font.BOLD, 16);
	Font font2 = new Font("Arial", Font.BOLD, 48);
	private int turn;
	JButton submit = new JButton("Submit");
	JButton pass = new JButton("Pass");
	JButton restart = new JButton("Restart");
	JButton start= new JButton("Start");
	JButton resume = new JButton("Resume");
	ImageIcon e = new ImageIcon("images/end.jpg");
	Image end = e.getImage();
	ImageIcon q = new ImageIcon("images/question.jpg");
	ImageIcon s = new ImageIcon("images/start.jpg");
	Image startImage = s.getImage();
	JButton question = new JButton(q);
	int numCardsDealt = 0;
	String player = "p" +String.valueOf(turn);
	int x1, x2, x3, x4;
	ArrayList<Card>curr;
	ArrayList<Card>currIn;
	ImageIcon cbh = new ImageIcon("images/CARD_BACK_HOR.png");
	Image cardBackH = cbh.getImage();
	ImageIcon cbv = new ImageIcon("images/CARD_BACK_VER.png");
	Image cardBackV = cbv.getImage();
	Graphics2D g2D;
	JFrame frame = new JFrame();
	boolean change;
	boolean started;
	boolean firstClick;
	boolean questionMode;
	int numPasses;
	
	
	public Screen(){
		turn =0;
		d = new Deck();
		p1 = new Player();
		p2 = new Player();
		p3 = new Player();
		p4 = new Player();
		allPlayers = new ArrayList<Player>();
		allPlayers.add(0, p1);
		allPlayers.add(1, p2);
		allPlayers.add(2, p3);
		allPlayers.add(3, p4);
		addMouseListener(this);
		for(Card c: d.cards){
			if(numCardsDealt >=0 && numCardsDealt <=12){
				p1.pile.add(c);
			}if(numCardsDealt >=13 && numCardsDealt <=25){
				p2.pile.add(c);
			}if(numCardsDealt >=26 && numCardsDealt <=38){
				p3.pile.add(c);
			}if(numCardsDealt >=39 && numCardsDealt <=51){
				p4.pile.add(c);
			}numCardsDealt++;
		}
		getPlayer().sort(getPlayer().pile);
		setLayout(null);
		setFocusable(true);
		setPreferredSize(new Dimension(700,600));
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				submit();
			}
		});
		pass.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pass();
			}
		});
		submit.setBounds(600,500, 100,50);
		submit.setForeground(Color.BLUE);
		submit.setFont(font1);
		submit.setVisible(false);
		this.add(submit);
		pass.setBounds(600,550, 100,50);
		pass.setForeground(Color.BLUE);
		pass.setFont(font1);
		pass.setVisible(false);
		this.add(pass);
		question.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				question();
			}
		});
		question.setBounds(650, 0, 50, 50);
		question.setVisible(false);
		this.add(question);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				started = true;
				repaint();
			}
		});
		start.setBounds(600,550, 100,50);
		start.setForeground(Color.BLUE);
		start.setFont(font1);
		start.setVisible(false);
		this.add(start);
		currIn = new ArrayList<Card>();
		change = false;
		started = false;
		questionMode = false;
		firstClick = true;
		numPasses = 0;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g2D = (Graphics2D) g;
		if(started){
			g2D.drawImage(background, 0, 0, 700, 600, null);
			g2D.setFont(font);
			g2D.setColor(Color.WHITE);
			g2D.drawString("Player " + (turn%4 +1) + "'s turn", 10, 20);
			pass.setVisible(true);
			submit.setVisible(true);
			question.setVisible(true);
			start.setVisible(false);
			drawPile(g);
			drawBackPiles(g);
			if(change){
				drawIn();
			}
			if(hasWon()){
				endGame();
			}
		}
		else if(started == false){
			if(questionMode)
				start.setVisible(false);
			else
				start.setVisible(true);
			helpScreen();
		}
	}
	
	public void drawPile(Graphics g){
		g2D = (Graphics2D) g;
		int x = 200;
		int y = 500;
		getPlayer().draw(g2D,x,y);
	}
	
	public void drawBackPiles(Graphics g){
		g2D= (Graphics2D) g;
			int y =200;
			int sizeOne = allPlayers.get((turn+1)%4).pile.size();
			int sizeTwo = allPlayers.get((turn+2)%4).pile.size();
			int sizeThree = allPlayers.get((turn+3)%4).pile.size();
			while(sizeOne>0){
				g2D.drawImage(cardBackH, 600, y+10, 100,60,null);
				y +=15;
				sizeOne--;
			}
			int x = 200;
			while(sizeTwo>0){
				g2D.drawImage(cardBackV, x+10, 0, 60,100,null);
				x +=15;
				sizeTwo--;
			}
			y=200;
			while(sizeThree>0){
				g2D.drawImage(cardBackH, 0, y+10, 100,60,null);
				y +=15;
				sizeThree--;
			}
		
	}
	
	public void drawIn(){
		int x = 250;
		getPlayer().sort(currIn);
		for(Card c: currIn){
			g2D.drawImage(c.card, x, 300, 60,100,null);
			x +=15;
		}
	}
	
	public void helpScreen(){
		g2D.drawImage(startImage, 0, 0, 700, 600, null);
	}
	public void submit(){
		numPasses = 0;
		change = false;
		this.legal();
		}
	
	public void legal(){
		int z = curr.size();
		if(z==1 || z==2 || z==5){
			if(currIn.size() == 0){
				if(z ==1)
					move(curr);	
				else if(z==2){
					if(curr.get(0).num==(curr.get(1).num))
						move(curr);
					else
						JOptionPane.showMessageDialog(frame, "Must be a pair");
				}	
				else if(z==5){
					if(curr.get(0).num == (curr.get(1).num-1) && curr.get(0).num == (curr.get(2).num-2) && curr.get(0).num == (curr.get(3).num-3) && curr.get(0).num == (curr.get(4).num - 4))
						move(curr);
					else if((curr.get(0).suit) == (curr.get(1).suit) && curr.get(0).suit == (curr.get(2).suit) && curr.get(0).suit == (curr.get(3).suit) && curr.get(0).suit == (curr.get(4).suit))
						move(curr);
					else if(((curr.get(0).num) == curr.get(1).num && curr.get(0).num == curr.get(2).num && curr.get(3).num == curr.get(4).num) || ((curr.get(2).num) == curr.get(3).num && curr.get(2).num == curr.get(4).num && curr.get(0).num == curr.get(1).num))
						move(curr);
					else
						JOptionPane.showMessageDialog(frame,"Must be valid five-card hand");
				}
			}
			else{
				if(currIn.size() == z){	
					if(z ==1){
						if(curr.get(0).compareTo(currIn.get(0))>0)
							move(curr);
						else
							JOptionPane.showMessageDialog(frame, "Card too small");
					}		
					else if(z == 2){
						if(curr.get(0).num==(curr.get(1).num)){
							if(curr.get(0).compareTo(currIn.get(0))>0)
								move(curr);
							else
								JOptionPane.showMessageDialog(frame, "Must be a higher pair");
						}	
						else
						JOptionPane.showMessageDialog(frame, "Must be a pair");
					}
					else if(z == 5){
						if(curr.get(0).num == (curr.get(1).num-1) && curr.get(0).num == (curr.get(2).num-2) && curr.get(0).num == (curr.get(3).num-3) && curr.get(0).num == (curr.get(4).num - 4)){
							if(curr.get(4).compareTo(currIn.get(4)) > 1){
								move(curr);
							}
							else{
								JOptionPane.showMessageDialog(frame, "Hand not high enough");
							}
						}
						else if((curr.get(0).suit) == (curr.get(1).suit) && curr.get(0).suit == (curr.get(2).suit) && curr.get(0).suit == (curr.get(3).suit) && curr.get(0).suit == (curr.get(4).suit)){
							if(currIn.get(0).num == (currIn.get(1).num-1) && currIn.get(0).num == (currIn.get(2).num-2) && currIn.get(0).num == (currIn.get(3).num-3) && currIn.get(0).num == (currIn.get(4).num - 4)){
								move(curr);
							}
							else if((currIn.get(0).suit) == (currIn.get(1).suit) && currIn.get(0).suit == (currIn.get(2).suit) && currIn.get(0).suit == (currIn.get(3).suit) && currIn.get(0).suit == (currIn.get(4).suit)){
								if(currIn.get(4).compareTo(curr.get(4))<1){
									move(curr);
								}
								else{
									JOptionPane.showMessageDialog(frame, "Hand not high enough");
								}
							}
							else{
								JOptionPane.showMessageDialog(frame, "Hand not high enough");
							}
						}
						else if(((curr.get(0).num) == curr.get(1).num && curr.get(0).num == curr.get(2).num && curr.get(3).num == curr.get(4).num) || ((curr.get(2).num) == curr.get(3).num && curr.get(2).num == curr.get(4).num && curr.get(0).num == curr.get(1).num)){
							if(currIn.get(0).num == (currIn.get(1).num-1) && currIn.get(0).num == (currIn.get(2).num-2) && currIn.get(0).num == (currIn.get(3).num-3) && currIn.get(0).num == (currIn.get(4).num - 4)){
								move(curr);
							}
							else if((currIn.get(0).suit) == (currIn.get(1).suit) && currIn.get(0).suit == (currIn.get(2).suit) && currIn.get(0).suit == (currIn.get(3).suit) && currIn.get(0).suit == (currIn.get(4).suit)){
								move(curr);
							}
							if(currIn.get(0).num == (currIn.get(1).num-1) && currIn.get(0).num == (currIn.get(2).num-2) && currIn.get(0).num == (currIn.get(3).num-3) && currIn.get(0).num == (currIn.get(4).num - 4)){
								drawIn();
							}
							else if((currIn.get(0).suit) == (currIn.get(1).suit) && currIn.get(0).suit == (currIn.get(2).suit) && currIn.get(0).suit == (currIn.get(3).suit) && currIn.get(0).suit == (currIn.get(4).suit)){
								drawIn();
							}
							else if(((currIn.get(0).num) == currIn.get(1).num && currIn.get(0).num == currIn.get(2).num && currIn.get(3).num == currIn.get(4).num) || ((currIn.get(2).num) == currIn.get(3).num && currIn.get(2).num == currIn.get(4).num && currIn.get(0).num == currIn.get(1).num)){
								if(((curr.get(0).num) == curr.get(1).num && curr.get(0).num == curr.get(2).num && curr.get(3).num == curr.get(4).num)){
									if(((currIn.get(0).num) == currIn.get(1).num && currIn.get(0).num == currIn.get(2).num && currIn.get(3).num == currIn.get(4).num)){
										if(curr.get(0).compareTo(currIn.get(0))>1){
											move(curr);
										}
										else{
											JOptionPane.showMessageDialog(frame, "Hand not high enough");
										}
									}
									else if(((currIn.get(2).num) == currIn.get(3).num && currIn.get(2).num == currIn.get(4).num && currIn.get(0).num == currIn.get(1).num)){
										if(curr.get(0).compareTo(currIn.get(2))>1){
											move(curr);
										}	
										else{
											JOptionPane.showMessageDialog(frame, "Hand not high enough");
										}
									}
								}
							else{
								JOptionPane.showMessageDialog(frame, "Hand not high enough");
							}
							}
						else if(curr.get(0).num == curr.get(1).num && curr.get(0).num == curr.get(2).num && curr.get(0).num == curr.get(3).num || curr.get(1).num == curr.get(2).num && curr.get(1).num == curr.get(3).num && curr.get(1).num == curr.get(4).num){
							if(currIn.get(0).num == (currIn.get(1).num-1) && currIn.get(0).num == (currIn.get(2).num-2) && currIn.get(0).num == (currIn.get(3).num-3) && currIn.get(0).num == (currIn.get(4).num - 4)){
								drawIn();
							}
							else if((currIn.get(0).suit) == (currIn.get(1).suit) && currIn.get(0).suit == (currIn.get(2).suit) && currIn.get(0).suit == (currIn.get(3).suit) && currIn.get(0).suit == (currIn.get(4).suit)){
								drawIn();
							}
							else if(((currIn.get(0).num) == currIn.get(1).num && currIn.get(0).num == currIn.get(2).num && currIn.get(3).num == currIn.get(4).num) || ((currIn.get(2).num) == currIn.get(3).num && currIn.get(2).num == currIn.get(4).num && currIn.get(0).num == currIn.get(1).num)){
								drawIn();
							}
							else if(currIn.get(0).num == currIn.get(1).num && currIn.get(0).num == currIn.get(2).num && currIn.get(0).num == currIn.get(3).num || currIn.get(1).num == currIn.get(2).num && currIn.get(1).num == currIn.get(3).num && currIn.get(1).num == currIn.get(4).num){
								if(curr.get(0).num == curr.get(1).num && curr.get(0).num == curr.get(2).num && curr.get(0).num == curr.get(3).num){
									if(currIn.get(0).num == currIn.get(1).num && currIn.get(0).num == currIn.get(2).num && currIn.get(0).num == currIn.get(3).num){
										if(curr.get(0).compareTo(currIn.get(0)) > 1){
											drawIn();
										}
										else{
											JOptionPane.showMessageDialog(frame, "Hand not high enough");
										}
									}
									
								}
								else if(curr.get(1).num == curr.get(2).num && curr.get(1).num == curr.get(3).num && curr.get(1).num == curr.get(4).num){
									if(currIn.get(1).num == currIn.get(2).num && currIn.get(1).num == currIn.get(3).num && currIn.get(1).num == currIn.get(4).num){
										if(curr.get(1).compareTo(currIn.get(1)) > 1){
											drawIn();
										}
										else{
											JOptionPane.showMessageDialog(frame, "Hand not high enough");
										}
									}
								}
									
							}
						}
						else if(curr.get(0).num == (curr.get(1).num-1) && curr.get(0).num == (curr.get(2).num-2) && curr.get(0).num == (curr.get(3).num-3) && curr.get(0).num == (curr.get(4).num - 4) && (curr.get(0).suit) == (curr.get(1).suit) && curr.get(0).suit == (curr.get(2).suit) && curr.get(0).suit == (curr.get(3).suit) && curr.get(0).suit == (curr.get(4).suit)){
							if(currIn.get(0).num == (currIn.get(1).num-1) && currIn.get(0).num == (currIn.get(2).num-2) && currIn.get(0).num == (currIn.get(3).num-3) && currIn.get(0).num == (currIn.get(4).num - 4)){
								drawIn();
							}
							else if((currIn.get(0).suit) == (currIn.get(1).suit) && currIn.get(0).suit == (currIn.get(2).suit) && currIn.get(0).suit == (currIn.get(3).suit) && currIn.get(0).suit == (currIn.get(4).suit)){
								drawIn();
							}
							else if(((currIn.get(0).num) == currIn.get(1).num && currIn.get(0).num == currIn.get(2).num && currIn.get(3).num == currIn.get(4).num) || ((currIn.get(2).num) == currIn.get(3).num && currIn.get(2).num == currIn.get(4).num && currIn.get(0).num == currIn.get(1).num)){
								drawIn();
							}
							else if(currIn.get(0).num == currIn.get(1).num && currIn.get(0).num == currIn.get(2).num && currIn.get(0).num == currIn.get(3).num || currIn.get(1).num == currIn.get(2).num && currIn.get(1).num == currIn.get(3).num && currIn.get(1).num == currIn.get(4).num){
								drawIn();
							}
							else if(currIn.get(0).num == (currIn.get(1).num-1) && currIn.get(0).num == (currIn.get(2).num-2) && currIn.get(0).num == (currIn.get(3).num-3) && currIn.get(0).num == (currIn.get(4).num - 4) && (currIn.get(0).suit) == (currIn.get(1).suit) && currIn.get(0).suit == (currIn.get(2).suit) && currIn.get(0).suit == (currIn.get(3).suit) && currIn.get(0).suit == (currIn.get(4).suit)){
								if(curr.get(4).compareTo(currIn.get(4)) > 1){
									drawIn();
								}
								else{
									JOptionPane.showMessageDialog(frame, "Hand not high enough");
								}
							}	
						}
						}
					}	
				}
				else
					JOptionPane.showMessageDialog(frame, "Number of cards doesn't match");				
			}
			}		
		else
			JOptionPane.showMessageDialog(frame, "Invalid number of cards.");
}			
	
	public void move(ArrayList<Card>curr){
		change = true;
		currIn = curr;
		for(Card c: getPlayer().selected)
			getPlayer().pile.remove(c);
		getPlayer().selected = new ArrayList<Card>();
		turn++;
		getPlayer().sort(getPlayer().pile);
		repaint();
	}
	
	public void pass(){
		if(currIn.size() == 0)
			JOptionPane.showMessageDialog(frame, "Must play cards.");
		else{
			turn++;
			getPlayer().sort(getPlayer().pile);
			numPasses++;
			if(numPasses>2){
				getPlayer().selected = new ArrayList<Card>();
				currIn = new ArrayList<Card>();
				numPasses = 0;
			}
			repaint();
		}
	}
	
	public void question(){
		if(firstClick){
			started = false;
			g2D.drawImage(startImage, 0, 0, 700, 600, null);
			submit.setVisible(false);
			pass.setVisible(false);
			start.setVisible(false);
			resume.setVisible(true);
			repaint();
			firstClick = false;
			questionMode=true;
		}
		else{
			started = true;
			firstClick = true;
			repaint();
			questionMode = false;
		}
	}
	
	public boolean hasWon(){
		return getPlayer().pile.size() == 0;
	}
	
	public void endGame(){
		g2D.drawImage(end, 0, 0, 700, 600, null);
		restart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new Screen();
			}
		});
		g2D.setColor(Color.RED);
		g2D.setFont(font2);
		g2D.drawString("Player " + ((turn+1)%4) +" has Won!", 150, 200);
		pass.setVisible(false);
		submit.setVisible(false);
		question.setVisible(false);
		restart.setBounds(600,500, 100,50);
		restart.setForeground(Color.BLUE);
		restart.setVisible(true);
		restart.setFont(font1);
		this.add(restart);
	}
	
	public Player getPlayer(){
		return allPlayers.get(turn%4);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println(x +" " + y);
		for(Card c: getPlayer().pile){
			if(c.posX<x && x<c.posX+ 15 && c.posY<y && y<c.posY +c.deltaY)
				if(c.clicked == false){
					c.posY -= 10;
					getPlayer().selected.add(c);
					curr = getPlayer().selected;
					c.clicked = true;
					repaint();		
				}
				else if(c.clicked == true){
					c.posY +=10;
					getPlayer().selected.remove(c);
					curr = getPlayer().selected;
					c.clicked = false;
					repaint();
				}
		}
		change = true;
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	
}
