package app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

//handles the creating and building of the list of flash cards
//contains the save functionality
//designates a proper folder to save all the serialization information

public class QuizCardBuilder {	
	
	//Constants
	private static final String FONT = "sansserif";
	private static final int FONT_SIZE = 24;
	
	
	private JFrame frame;
	private JPanel mainPanel;
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	
	//build and display the gui
	public void go(){
		
		frame = new JFrame("FlashCard App");
		mainPanel = new JPanel();
		Font bigFont = new Font(FONT, Font.BOLD, FONT_SIZE);
		
		//question area
		question = new JTextArea(6,20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);
		
		//set the scroll bar for the question pane
		JScrollPane qScroller = new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//answer area
		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		
		//set the scroll bar for the answer area
		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//add the buttoms
		JButton nextButton = new JButton("Next Card");
		cardList = new ArrayList<QuizCard>();
		JLabel qLabel = new JLabel("Question:");
		JLabel aLabel = new JLabel("Answer:");
		
		//add all these features onto the main panel
		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(nextButton);		
		
		//handles the action when we click the next button
		nextButton.addActionListener(new NextCardListener());
		
		//creates the menu bar for the Jframe
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		
		
		newMenuItem.addActionListener(new NewMenuListener());
		saveMenuItem.addActionListener(new SaveMenuListener());
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);		
	}
	
	private void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();		
	}
	
	//next card listener
	public class NextCardListener implements ActionListener{

		//the next button we want to save the current card that we were working on
		// add that card to the list of cards that we have
		// then call clear call which makes the current question field null
		@Override
		public void actionPerformed(ActionEvent ev) {
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);
			clearCard();			
		}
	}
	
	public class NewMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class SaveMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	//execute the main call
	public static void main(String[] args){
		QuizCardBuilder builder = new QuizCardBuilder();
		builder.go();
	}

}
