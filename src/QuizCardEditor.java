import java.util.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;


public class QuizCardEditor {
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private JFrame frame;
	
	
	public static void main(String[] args) {
	QuizCardEditor quiz = new QuizCardEditor();
	quiz.DoWork();

	}
	public void DoWork() {
		frame = new JFrame("Quiz card editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanseriff", Font.BOLD,24);  // ustawienia czcionki
		question = new JTextArea(6,20); // typowe ustaweinai dla text area
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);
		
		JScrollPane scrollQuestion = new JScrollPane(question);  //dalej ustawienia dla scrollpane dla textarea
		scrollQuestion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollQuestion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		
		JScrollPane scrollAnswer = new JScrollPane(answer);
		scrollAnswer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollAnswer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton buttonNext = new JButton("Next card");
		
		cardList = new ArrayList<QuizCard>();  //lista obiektow klasy quizcard
		
		JLabel questionLabel = new JLabel("Question");
		JLabel answerLabel = new JLabel("Answer");
		
		mainPanel.add(questionLabel);   // layout jak w teksice poniewaz sa elementy na tyle szerokie to jest jeden po drugim wysrodkowane.
		mainPanel.add(scrollQuestion);
		mainPanel.add(answerLabel);
		mainPanel.add(scrollAnswer);
		mainPanel.add(buttonNext);
		buttonNext.addActionListener(new NextCardListener());
		
		JMenuBar menu = new JMenuBar();  // tworzenie pasku menu 
		JMenu menuFile = new JMenu("file"); // tworzenie opcji file w pasku
		JMenuItem optionNew = new JMenuItem("new"); // tworzenie opcji nowy
		JMenuItem optionSave = new JMenuItem("save"); // tworzenie opcji zapisz
		 
		optionNew.addActionListener(new NewMenuListener());
		optionSave.addActionListener(new SaveMenuListener());
		
		menuFile.add(optionNew); // do opcji file dodajemy nowy i zapisz
		menuFile.add(optionSave);
		menu.add(menuFile); // do paska opcji dodajemy opcje file proste i superanckie
		
		frame.setJMenuBar(menu); // do ramki dodajemu pasek opcji
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);
	}
	
	public class NextCardListener implements ActionListener{
		public void actionPerformed(ActionEvent zd){
			QuizCard card = new QuizCard(question.getText(), answer.getText()); // klikajac nastepna kartke, dodajemy biezaca do listy i textarea
			cardList.add(card);
			cleanCard();
		}


	}
	
	public class SaveMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			QuizCard card = new QuizCard(question.getText(), answer.getText()); // tu jeszcze nie zapisujemy, dodajemy biezaca karte do listy
			cardList.add(card);
			
			JFileChooser dataFile = new JFileChooser(); // i wyswietlamy okno dialogowe wybieramy nazywamy plik i zapisujemy wywolujac metode saveFile
			dataFile.showSaveDialog(frame);
			saveFile(dataFile.getSelectedFile());
		}
	}
	
	public class NewMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			cardList.clear();
			cleanCard();	
		}
	}
	
	private void cleanCard(){
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}
	
	private void saveFile(File file){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file)); // tu zapisujemy odpalamy writera i przechodzimy po liscie
			for (QuizCard card : cardList) {
				writer.write(card.getQuestion() + "/");
				writer.write(card.getAnswer()	+ "\n");
			}
			writer.close(); 
		} catch(Exception ex) {
			System.out.println("File unable to save");
			ex.printStackTrace(); 
			}
	}

}
