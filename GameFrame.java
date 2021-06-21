import javax.swing.JFrame;

public class GameFrame extends JFrame {
     //Constructor for GameFrame
	GameFrame(){
		//GamePanel panel = new GamePanel();
		//this.add(panel);
		// ^^^ This works the same as the one below
		this.add(new GamePanel()); 
		this.setTitle("Snake...sssss");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
