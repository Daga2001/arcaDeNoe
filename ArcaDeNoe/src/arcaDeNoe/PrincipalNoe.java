/*
 * Programaci�n Interactiva
 * Autor: David Alberto Guzm�n - 201942789
 * MiniProyecto 2: Juego arca de Noe.
 */

package arcaDeNoe;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

// TODO: Auto-generated Javadoc
/**
 * The Class PrincipalNoe. This is the class with the main method.
 */
public class PrincipalNoe{

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		try {
			String javaLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(javaLookAndFeel);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Hubo un da�o en la JVM");
		};
		
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				GUIArcaDeNoe miVista = new GUIArcaDeNoe();
				@SuppressWarnings("unused")
				InstructionsGUI screen = new InstructionsGUI(miVista);
			}
			
		});

	}

}
