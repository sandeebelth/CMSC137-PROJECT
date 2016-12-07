import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class Menu{
	public static void main(String args[]){
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}


class MainFrame extends JFrame implements ActionListener{
	JPanel controlPanel;
	JPanel options;
	JLabel title;
	JButton hostgame;
	JButton joingame;
	JButton closegame;
	JDialog dialog;

	public MainFrame(){
		Container c = getContentPane();
		JPanel mainPanel = new JPanel();
		makeWindow();

		c.add(mainPanel, BorderLayout.CENTER);
		setTitle("UNINVITED");
		setSize(500,300);
		setLocation(450,250);
	}

	public void makeWindow(){
		controlPanel = new JPanel(new BorderLayout());
		options = new JPanel();
		title = new JLabel("               	                     UNINVITED");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		hostgame = new JButton("Host game");
		joingame = new JButton("Join game");
		closegame = new JButton("Exit");

		hostgame.addActionListener(this);
		joingame.addActionListener(this);
		closegame.addActionListener(this);

		controlPanel.add(title, BorderLayout.NORTH);
		options.add(hostgame);
		options.add(joingame);
		options.add(closegame);
		controlPanel.add(options, BorderLayout.CENTER);
		dialog = new JDialog();
		dialog.setTitle("OPTION");
		dialog.setModal(true);
		dialog.getContentPane().add(controlPanel, BorderLayout.SOUTH); 	// add components here
		dialog.pack();
		dialog.setSize(520,120);
		dialog.setLocation(300,300);
      	dialog.setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == hostgame){
			System.out.println("Creating game...");
			dialog.setVisible(false);
		}

		if(e.getSource() == joingame){
			System.out.println("Joining game...");
			dialog.setVisible(false);
		}

		if(e.getSource() == closegame){
			System.out.println("Good bye.");
			System.exit(0);
		}
	}


}