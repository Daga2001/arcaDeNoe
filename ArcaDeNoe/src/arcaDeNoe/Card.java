/*
 * Programación Interactiva
 * Autor: David Alberto Guzmán - 201942789
 * MiniProyecto 2: Juego arca de Noe.
 */

package arcaDeNoe;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

// TODO: Auto-generated Javadoc
/**
 * The Class Card. This class is useful to make cards for the game, which have the same design of their back-side and different
 * designs in their front-side.
 */
public class Card extends JButton{

	//attributes
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The face down image. */
	private static ImageIcon faceDownImage;
	
	/** The design. */
	private int design;
	
	/** The face down. */
	private boolean faceDown;
	
	/** The revealed image. */
	private ImageIcon revealedImage;
	
	//methods
	
	/**
	 * Instantiates a new card. This is the constructor of the class, it gives values to the attributes.
	 *
	 * @param image the image. This is the image what we'll set to the card.
	 * @param disenho the disenho. This is the number of the design's card.
	 * @param compound the compound. It represents the border of the card.
	 */
	public Card(ImageIcon image, int disenho, Border compound) {
		revealedImage = image;
		design = disenho;
		setBorder(compound);
		setBackground(Color.WHITE);
		setIcon(image);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		faceDown = true;
	}
	
	/**
	 * Sets the face down image.
	 *
	 * @param image the new face down image. It's the image for all the facedown cards
	 */
	public static void setFaceDownImage(ImageIcon image) {
		faceDownImage = image;
	}
	
	/**
	 * Gets the design.
	 *
	 * @return the design
	 */
	public int getDesign() {
		return design;
	}
	
	/**
	 * Sets a card face down.
	 */
	public void setFaceDown() {
		faceDown = true;
		setIcon(faceDownImage);
	}
	
	/**
	 * Reveals a card.
	 */
	public void revealCard() {
		faceDown = false;
		setIcon(revealedImage);
	}

	/**
	 * Checks if a card is face down or revealed.
	 *
	 * @return true, if is face down
	 */
	public boolean isFaceDown() {
		return faceDown;
	}
	
}
