import javax.swing.JFrame;


public class BigTwo {
	public BigTwo(){
		JFrame jf = new JFrame();
		jf.add(new Screen());
		jf.setTitle("Welcome to Big Two!");
		jf.pack();
		jf.setLocation(333,34);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BigTwo();
	}

}
