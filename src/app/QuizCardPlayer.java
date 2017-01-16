package app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

//this is the app that displays for the user to actually go through and guess 
// the flash cards
public class QuizCardPlayer {
	
	private JTextArea display;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private int currentCardIndex;
	private JFrame frame;
	private JButton nextButton;
	private boolean isShowAnswer;
	
	//build and display gui
	public void go(){
		
		//initialize the jframe
		frame = new JFrame("Quiz Card Player");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sansserif", Font.BOLD, 24);
		
		//create the display for the JText area
		display = new JTextArea(10, 20);
		display.setFont(bigFont);
		
		//it sets the JTextArea to wrap around the line
		display.setLineWrap(true);
		display.setEditable(false);
		
		//qscroller for the jscrollpane
		JScrollPane qScroller = new JScrollPane(display);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		nextButton = new JButton("Show Question");
		mainPanel.add(qScroller);
		mainPanel.add(nextButton);
		
		//listener for the next button
		nextButton.addActionListener(new NextCardListener());
		
		//the menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		//create a menu item that says load card set
		JMenuItem loadMenuItem = new JMenuItem("Load card set");
		
		//listener for the menu
		loadMenuItem.addActionListener(new OpenMenuListener());
		
		//set the menu bar in the frame
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(640,500);
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		QuizCardPlayer player = new QuizCardPlayer();
		player.go();
	}
	
	private void loadFile(File file){
		//must build an ArrayList of cards, by reading them from a text file
		// called from the OpenMenuListener event handler, reads the file one line at a 
		// time
		// and tells the make card() method to make a new card out of the line
		// one line in the file holds both the question and answer
		cardList = new ArrayList<QuizCard>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null){
				makeCard(line);
			}
			reader.close();
		} catch(Exception ex){
			System.out.println("coudln't read the card file");
			ex.printStackTrace();
		}
		showNextCard();
	}
	
	private void makeCard(String lineToParse){
		String[] result = lineToParse.split("/");
		QuizCard card = new QuizCard(result[0], result[1]);
		cardList.add(card);
		System.out.println("made a card");
	}
	
	private void showNextCard(){
		currentCard = cardList.get(currentCardIndex);
		currentCardIndex++;
		display.setText(currentCard.getQuestion());
		nextButton.setText("Show Answer");
		isShowAnswer = true;
	}
	
	public class NextCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//if this is a question show the answer
			//set a flag for whether we're viewing a question or answer
			if(isShowAnswer){
				//show the answer because they've seen the question
				display.setText("Next Card");
				nextButton.setText("Next Card");
				isShowAnswer = false;
			} else{
				//the flag for isShowAnswer is false 
				// we want to move to the next card
				if(currentCardIndex < cardList.size()){
					showNextCard();
				}else{
					//we can't see any more cards because there are no more
					//show the next Question
					display.setText("That was last Card");
					nextButton.setEnabled(false);
				}
			}
		}		
	}
	
	public class OpenMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//bring up a file dialog box
			//let the user navigate to choose a card set to open
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
		}
		
	}

}
