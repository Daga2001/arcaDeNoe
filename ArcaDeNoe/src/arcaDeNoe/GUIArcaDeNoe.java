/*
 * Programación Interactiva
 * Autor: David Alberto Guzmán - 201942789
 * MiniProyecto 2: Juego arca de Noe.
 */

package arcaDeNoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

import misComponentes.Titulos;

// TODO: Auto-generated Javadoc
/**
 * The Class GUIArcaDeNoe. The class shows the GUI of the game with music and sound effects, allowing 
 * exit or restart the game for a better experience.
 */
public class GUIArcaDeNoe extends JFrame {
	
	//attributes
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The image. */
	private ImageIcon image;
	
	/** The score panel. */
	private JPanel buttonsPanel, cardsPanel, scorePanel;
	
	/** The exit. */
	private JButton exit;
	
	/** The control game. */
	private ControlArcaDeNoe controlGame;
	
	/** The escucha. */
	private Escuchas escucha;
	
	/** The score. */
	private JTextField score;
	
	/** The cards. */
	private ArrayList<Card> cards;
	
	/** The number cards. */
	private int numberCards, delayTimerF;
	
	/** The clip. */
	private Clip mainMusic, scored, gameOver, fail, newRound, moveCard, rollOver;
	
	/** The audio in. */
	private AudioInputStream audioIn;
	
	/** The khaki. */
	private Color goldenYellow, oldGold, goldenBrown, metallicGold, peru, khaki;
	
	/** The compound. */
	private Border personalizatedLine, compound;
	
	/** The timer. */
	private Timer timer, timerF, timerStart;
	
	/** The myGUI */
	private JFrame myGUI;
	
	/** The isGameStarted */
	private boolean isGameStarted;
	
	/**
	 * Instantiates a new GUI arca de noe. This is the constructor of the class, it gives values to the attributes.
	 */
	public GUIArcaDeNoe() {
		try	{
			initGUI();
		}
		catch(Exception e) {
			System.out.print("Error! an image couldn't be found ");
		}
		//default window configuration
		this.setUndecorated(true);
		this.pack();
		this.setBackground(Color.WHITE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.isVisible();
	}
	
	/**
	 * Inits the GUI.
	 */
	private void initGUI() {
		
		//define window icon
		image = new ImageIcon(getClass().getClassLoader().getResource("yu-gi-oh_icon.png"));
		this.setIconImage(image.getImage());
		
		//make listener
		escucha = new Escuchas();
		
		//make other things
		myGUI = this;
		isGameStarted = false;
		numberCards = 4;
		timer = new Timer(2*1000, escucha);
		timerF = new Timer(2*25, escucha);
		timerStart = new Timer(0, escucha);
		timer.start();
		timerStart.start();
		cards = new ArrayList<Card>(numberCards);
		GridBagConstraints constraints = new GridBagConstraints();
		image = new ImageIcon(getClass().getClassLoader().getResource("background.jpg"));
		Card.setFaceDownImage(image);
		controlGame = new ControlArcaDeNoe();
		goldenYellow = new Color(255,223,0);
		oldGold = new Color(207,181,59); 
		goldenBrown = new Color(153,101,21);
		metallicGold = new Color(212,175,55);
		peru = new Color(205,133,63);
		khaki = new Color(240,230,140);
		personalizatedLine = BorderFactory.createLineBorder(goldenYellow);
		compound = BorderFactory.createCompoundBorder(personalizatedLine, personalizatedLine);
		Font font = new Font(Font.SERIF, Font.BOLD+Font.ITALIC,20);
		
		try {
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("yugioh.wav"));
			mainMusic = AudioSystem.getClip();
			mainMusic.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("scored.wav"));
			scored = AudioSystem.getClip();
			scored.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("revealCard.wav"));
			moveCard = AudioSystem.getClip();
			moveCard.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("gameOver.wav"));
			gameOver = AudioSystem.getClip();
			gameOver.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("newRound.wav"));
			newRound = AudioSystem.getClip();
			newRound.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("rollover-sound-effect.wav"));
			rollOver = AudioSystem.getClip();
			rollOver.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("pointsDrop.wav"));
			fail = AudioSystem.getClip();
			fail.open(audioIn);
		} 
		
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error! no se encontró el archivo de música o está dańado.");
		}
		
		
		
		//make GUI
		
		//title
		Titulos titulo = new Titulos("Empareja Las Cartas", 30, oldGold);
		titulo.setBorder(compound);
		this.add(titulo, BorderLayout.NORTH);
		
		//game zone - card's panel
		cardsPanel = new JPanel();
		cardsPanel.setBackground(peru);
		cardsPanel.setLayout(new GridLayout(2,2));
		uploadCards();
		this.add(cardsPanel, BorderLayout.CENTER);
		
		//button's panel and score's panel
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BorderLayout());
		scorePanel = new JPanel();
		scorePanel.setLayout(new GridLayout());
		
		Titulos puntuacion = new Titulos("puntuación", 20, oldGold);
		puntuacion.setBorder(compound);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		scorePanel.add(puntuacion, constraints);
		
		score = new JTextField(4);
		score.setText(String.valueOf(controlGame.getScore()));
		score.setHorizontalAlignment(JTextField.CENTER);
		score.setBackground(khaki);
		score.setForeground(goldenBrown);
		score.setBorder(compound);
		score.setFont(font);
		score.setEditable(false);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		scorePanel.add(score, constraints);
		
		buttonsPanel.add(scorePanel, BorderLayout.NORTH);
		
		exit = new JButton("salir");
		exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		exit.setFocusPainted(false);
		exit.setForeground(goldenBrown);
		exit.setFont(font);
		exit.setBackground(metallicGold);
		exit.setBorder(compound);
		exit.addActionListener(escucha);
		exit.addMouseListener(escucha);
		buttonsPanel.add(exit, BorderLayout.SOUTH);
		
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
		
	}
	
	/**
	 * Make cards. Generate cards and stock the cards in the array list "cards".
	 *
	 * @param numberOfCards the number of cards on the field. 
	 */
	private void makeCards(int numberOfCards) {
		
		Card card;
		Card card2;
		
		for(int nCard = 0; nCard < ( (numberOfCards/2)); nCard++) {
			
			Random random = new Random();
			int numberDesign = random.nextInt(12) + 1;
			image = new ImageIcon(getClass().getClassLoader().getResource("img" + String.valueOf(numberDesign) + ".jpg"));
			card = new Card(image, numberDesign, compound);
			card2 = new Card(image, numberDesign, compound);
			
			if(isInList(cards, card)) {
				
				while(isInList(cards, card)) {
					random = new Random();
					numberDesign = random.nextInt(12) + 1;
					image = new ImageIcon(getClass().getClassLoader().getResource("img" + String.valueOf(numberDesign) + ".jpg"));
					card = new Card(image, numberDesign, compound);
					card2 = new Card(image, numberDesign, compound);
				}
				cards.add(card);
				cards.add(card2);
			}
			else {
				cards.add(card);
				cards.add(card2);
			}
			
		}
		
	}
	
	/**
	 * Re order. Change the order of the elements of an array, in this case we'll use to chance the order of the cards.
	 *
	 * @param cards the cards. It must be used the array list "cards".
	 */
	private void reOrder(ArrayList<Card> cards) {
		
		ArrayList<Card> modelCards = new ArrayList<Card>(cards.size());
		
		for(int nElement = 0; nElement < cards.size(); nElement++) {
			
			modelCards.add(cards.get(nElement));
			
		}

		Card theCard;
		
		cards.clear();
		
		while(modelCards.size() > 0) {
			
			Random random = new Random();
			int choosenCard = random.nextInt(modelCards.size()) + 1;
			
			theCard = modelCards.get(choosenCard-1);

			cards.add(theCard);
			modelCards.remove(choosenCard-1);
			
		}
		
	}
	
	/**
	 * Resize cards panel. As the name says, resize the cards panel and centers the GUI.
	 */
	private void resizeCardsPanel() {
		cardsPanel.setLayout(new GridLayout(numberCols(cards.size()), numberRows(cards.size())));
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Number cols. Determinates the number of cols for the GridLayout of cardsPanel.
	 *
	 * @param number the number. the number of cards.
	 * @return the int
	 */
	private int numberCols(int number) {
		int x = 1;
		int y = 1;
		
		while(true) {
			if(x*y <= number) {
				
				if(x*(y+1) <= number) {
					x++;
					y++;
				}
				else {
					return x;
				}
			}
			else {
				return x;
			}
		}
	}
	
	/**
	 * Number rows. Determinates the number of rows for the GridLayout of cardsPanel.
	 *
	 * @param number the number. the number of cards.
	 * @return the int
	 */
	private int numberRows(int number) {
		int x = 1;
		int y = 1;
		
		while(true) {
			if(x*y <= number) {
				
				if(x*(y+1) <= number) {
					x++;
					y++;
				}
				else {
					return y+1;
				}
			}
			else {
				return y;
			}
		}
	}
	
	/**
	 * Checks if is in list. We'll use this method to check if a card is in the array list "cards".
	 *
	 * @param cards the cards. It must be used the array list "cards".
	 * @param card the card. One card that we want to check in the array list.
	 * @return true, if is in list
	 */
	private boolean isInList(ArrayList<Card> cards, Card card) {
		
		for(int nElement = 0; nElement < cards.size(); nElement++) {
			
			if(cards.get(nElement).getDesign() == card.getDesign()) {
				return true;
			}
			
		}
		
		return false;
	}
	
	/**
	 * Upload cards. After make and order the cards, sets all of them in the cardsPanel to make them visible.
	 */
	private void uploadCards() {
		
		makeCards(numberCards);
		reOrder(cards);
		
		for(int nCard = 0; nCard < cards.size(); nCard++) {
			
			cards.get(nCard).addMouseListener(escucha);
			cards.get(nCard).setFaceDown();
			cardsPanel.add(cards.get(nCard));
			cardsPanel.repaint();
			cardsPanel.revalidate();
			
		}
		
	}
	
	/**
	 * Sets the face down every card. Sets every card in the field face down.
	 */
	private void setFaceDownEveryCard() {
		
		for(int nCard = 0; nCard < cards.size(); nCard++) {
			
			cards.get(nCard).setFaceDown();
			
		}
		
	}
	
	/**
	 * Hide every revealed card. 
	 */
	private void hideEveryRevealedCard() {
		
		for(int nCard = 0; nCard < cards.size(); nCard++) {
			
			if(!cards.get(nCard).isFaceDown()) {
				
				cards.get(nCard).setVisible(false);
				cards.get(nCard).setEnabled(false);
				cards.remove(nCard);
				nCard--;
			
			}
			
		}
		
	}
	
	/**
	 * Erase every card in the field.
	 */
	private void eraseEveryCard() {
		
		cards.clear();
		
		for(int nCard = 0; nCard < cardsPanel.getComponentCount(); nCard++) {
			
			cardsPanel.remove(nCard);
			nCard--;
			
		}
		
	}
	
	/**
	 * Are there two revealed cards. Checks if there are two revealed cards in the field.
	 *
	 * @return true, if successful
	 */
	private boolean areThereTwoRevealedCards() {
		
		int counter = 0;
		
		for(int nCard = 0; nCard < cards.size(); nCard++) {
			
			if(!cards.get(nCard).isFaceDown()) {
				counter++;

				if(counter >= 2) {
					return true;
				}
				
			}
			
		}
		
		return false;
		
	}
	
	/**
	 * Two revealed cards equals. Checks if there are two equivalent revealed cards in the field.
	 *
	 * @return true, if successful
	 */
	private boolean twoRevealedCardsEquals() {
		
		Card card1, card2;
		
		for(int nCard1 = 0; nCard1 < cards.size(); nCard1++) {
			
			if(!cards.get(nCard1).isFaceDown()) {
				card1 = cards.get(nCard1);
				
				for(int nCard2 = nCard1+1; nCard2 < cards.size(); nCard2++) {
					
					if(!cards.get(nCard2).isFaceDown()) {
						card2 = cards.get(nCard2);
						
						if(card1.getDesign() == card2.getDesign()) {
							return true;
						}
						
					}
					
				}
				
				return false;
				
			}
			
		}
		
		return false;
		
	}
	
	/**
	 * Matrix effect. Makes the matrix effect in the field of the score.
	 */
	private void matrixEffect() {
		
		Random random = new Random();
		
		score = (JTextField) scorePanel.getComponent(1);
		score.setText(String.valueOf( random.nextInt(9) ) + String.valueOf( random.nextInt(9) ) +
		String.valueOf( random.nextInt(9) ) + String.valueOf( random.nextInt(9) ));
		
	}

	/**
	 * The Class Escuchas. The listener use ActionListener for the interaction with exit button and the timer. 
	 * Also use MouseAdapter to allow the user interacts with the cards in the field.
	 */
	private class Escuchas extends MouseAdapter implements ActionListener {

		/**
		 * Action performed.
		 *
		 * @param event the event
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			
			if(event.getSource() == exit) {
				System.exit(0);
			}
			
			else if(event.getSource() == timerF) {
				matrixEffect();
				delayTimerF += timerF.getDelay();
				
				if(delayTimerF >= 1800) {
					delayTimerF = 0;
					timerF.stop();
					score.setText(String.valueOf(controlGame.getScore()));
					
				}
				
			}
			
			else if(event.getSource() == timerStart) {
				if(myGUI.isVisible() && !isGameStarted) {
					isGameStarted = true;
					mainMusic.start();
					mainMusic.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}
			
			else if(event.getSource() == timer) {
				
				controlGame.checkState(twoRevealedCardsEquals(), areThereTwoRevealedCards(), cards.size());
				
				score = (JTextField) scorePanel.getComponent(1);
				
				
				//states: 1 = fail, 2 = score, 3 = round over, 4 = game over, 5 = nothing happens;
				switch(controlGame.getStateGame()) {
				
				case 1: fail.flush();
						fail.setFramePosition(0);
						fail.start(); 
						setFaceDownEveryCard();
						timerF.restart();
						timerF.start();
						
						break;
						
				case 2: score.setText(String.valueOf(controlGame.getScore()));
						scored.flush();
						scored.setFramePosition(0);
						scored.start();
						hideEveryRevealedCard();
						break;
				
				}
			
				controlGame.checkState(twoRevealedCardsEquals(), areThereTwoRevealedCards(), cards.size());

				switch(controlGame.getStateGame()) {
				
				case 3: if(numberCards < 16) {
							numberCards += 2;
						}
						score.setText(String.valueOf(controlGame.getScore()));
						newRound.flush();
						newRound.setFramePosition(0);
						newRound.start();
						eraseEveryCard();
						uploadCards();
						resizeCardsPanel();
						break;
						
				case 4:	score.setText(String.valueOf(controlGame.getScore()));
						mainMusic.stop();
						gameOver.flush();
						gameOver.setFramePosition(0);
						gameOver.start();
						String[] alternatives = {"Sí", "No"};
						image = new ImageIcon(getClass().getClassLoader().getResource("end.jpg"));
						String answer = (String) JOptionPane.showInputDialog(null, "Volver a Jugar?", "                                  "
																			+ "Perdiste!", 
																			JOptionPane.QUESTION_MESSAGE, image, 
																			alternatives, "Sí");
						
						if(answer == "Sí") {
							score.setText(String.valueOf(controlGame.getScore()));
							mainMusic.flush();
							mainMusic.setFramePosition(0);
							mainMusic.start();
							mainMusic.loop(Clip.LOOP_CONTINUOUSLY);
							timerF.stop();
							numberCards = 4;
							eraseEveryCard();
							uploadCards();
							resizeCardsPanel();
						}
						
						else {
							String titulo = "                                               HASTA LUEGO!";
							image = new ImageIcon(getClass().getClassLoader().getResource("final.png"));
							JOptionPane.showMessageDialog(null, "Espero que vuelvas a jugar pronto!" , titulo, JOptionPane.INFORMATION_MESSAGE, image);
							System.exit(0);
						}
						
						break;
				
				}
				
			}
			
		}
		
		public void mouseEntered(MouseEvent e) {
			if(e.getSource().getClass() == JButton.class) {
				rollOver.setFramePosition(0);
				rollOver.start();
				e.getComponent().setBackground(new Color(255,221,153));
			}
		}
		
		public void mouseExited(MouseEvent e) {
			if(e.getSource().getClass() == JButton.class) {
				e.getComponent().setBackground(metallicGold);
			}
		}
		
		/**
		 * Mouse clicked. When is used on a card, reveals that card.
		 *
		 * @param eventMouse the event mouse
		 */
		@Override
		public void mouseClicked(MouseEvent eventMouse) {
			
			if(areThereTwoRevealedCards()) {
				//nothing happens
			}
			
			else {
				timer.restart();
				Card cart = (Card) eventMouse.getSource();
				cart.revealCard();
				moveCard.flush();
				moveCard.setFramePosition(0);
				moveCard.start();
			}
			
		}
		
	}
	
	
}
