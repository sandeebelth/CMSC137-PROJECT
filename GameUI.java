import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class GameUI{
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
	JButton newgame;
	JButton closegame;
	JDialog dialog;

	public MainFrame(){
		Container c = getContentPane();
		JPanel mainPanel = new JPanel();
		makeWindow();

		c.add(mainPanel, BorderLayout.CENTER);
		setSize(700,700);
		setLocation(0,0);
	}

	public void makeWindow(){
		controlPanel = new JPanel(new BorderLayout());
		options = new JPanel();
		title = new JLabel("               	                     UNINVITED");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		newgame = new JButton("Start game");
		closegame = new JButton("Close");

		newgame.addActionListener(this);
		closegame.addActionListener(this);

		controlPanel.add(title, BorderLayout.NORTH);
		options.add(newgame);
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
		if(e.getSource() == newgame){
			System.out.println("Creating game...");
			dialog.setVisible(false);
		}

		if(e.getSource() == closegame){
			System.out.println("Good bye.");
			System.exit(0);
		}
	}


}

