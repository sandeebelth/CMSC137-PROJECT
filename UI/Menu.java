import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class Menu{
	public static void main(String args[]){
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}


class MainFrame extends JFrame implements ActionListener{
	Image img;
	JPanel controlPanel;
	JPanel options;
	JLabel title;
	final JButton hostgame;
	JButton joingame;
	JButton closegame;
	JDialog dialog;
	JButton addAddress;
	JButton creategame;
	int counter = 0;


	public MainFrame(){
		Container c = getContentPane();		
		
		//gametitle.setFont(new Font("Papyrus", Font.BOLD, 60));
		JPanel mainPanel = new JPanel();
		//mainPanel.setLayout(new FlowLayout());

		JLabel gametitle = new JLabel();
		gametitle.setIcon(new ImageIcon("images/bg.jpg"));
		gametitle.setVisible(true);
		gametitle.setHorizontalAlignment(SwingConstants.CENTER);
		gametitle.setBounds(0, 0, 640, 480);

		mainPanel.add(gametitle);		
		//mainPanel.setOpaque(false);

		//hostgame = new JButton(new ImageIcon("images/hostgame.png"));
		//hostgame.setFocusPainted(false);
		//hostgame.setBounds(50,50,50,50);
		hostgame = new JButton("Host game");
		mainPanel.add(hostgame);
		hostgame.addActionListener(this);

		joingame = new JButton("Join game");
		mainPanel.add(joingame);
		joingame.addActionListener(this);

		closegame = new JButton("Exit");
		mainPanel.add(closegame);
		closegame.addActionListener(this);

		mainPanel.add(gametitle);
		
		//mainPanel.add(joingame);
		//mainPanel.add(closegame);

		//img = Toolkit.getDefaultToolkit().createImage("images/image.jpg");
		//setContentPane(new JLabel(new ImageIcon("images/image.jpg")));

		c.add(mainPanel);
		setTitle("UNINVITED");
		setSize(600,372);
		setLocation(0,0);
	}

	/*public void paint(Graphics g)
    {
        // Draws the img to the BackgroundPanel.
        g.drawImage(img, 0, 0, null);
    }*/

	public void createGame(){
		JFrame frame = new JFrame("HOST GAME");
      	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      	
      	JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        
        JTextArea textArea = new JTextArea("At least three (3) IP addresses must be added before a game can be created.", 10, 50);
        //textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(Font.getFont(Font.SANS_SERIF));

        JScrollPane scroller = new JScrollPane(textArea);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel inputpanel = new JPanel();
        inputpanel.setLayout(new FlowLayout());
        JTextField input = new JTextField(20);

        addAddress = new JButton("Add IP address");
        addAddress.addActionListener(this);

        JPanel createpanel = new JPanel();
        createpanel.setLayout(new FlowLayout());
        creategame = new JButton("CREATE GAME");
        creategame.setEnabled(false);


        panel.add(scroller);
        inputpanel.add(input);
        inputpanel.add(addAddress);
        createpanel.add(creategame);
        panel.add(inputpanel);
        panel.add(creategame);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setResizable(false);
        input.requestFocus();
	}

	public void joinGame(){
		JFrame frame = new JFrame("JOIN");
      	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      	
      	JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);

        JTextArea textArea = new JTextArea(" Enter your IP address to join.", 2, 50);
        textArea.setEditable(false);
        textArea.setFont(Font.getFont(Font.SANS_SERIF));

        JScrollPane scroller = new JScrollPane(textArea);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel inputpanel = new JPanel();
        inputpanel.setLayout(new FlowLayout());
        JTextField input = new JTextField(20);

        JButton join = new JButton("JOIN GAME");
        
        panel.add(scroller);
        inputpanel.add(input);
        inputpanel.add(join);
        panel.add(inputpanel);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setResizable(false);
        input.requestFocus();
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == hostgame){
			System.out.println("Creating game...");
			createGame();
		}

		if(e.getSource() == joingame){
			System.out.println("Joining game...");
			joinGame();
		}

		if(e.getSource() == addAddress){
			counter++;
			System.out.println(counter);

			if(counter >= 3){
				creategame.setEnabled(true);
			}
		}

		if(e.getSource() == closegame){
			System.out.println("Good bye.");
			System.exit(0);
		}
	}


}