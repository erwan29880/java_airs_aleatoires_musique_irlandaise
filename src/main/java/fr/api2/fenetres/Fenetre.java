package fr.api2.fenetres;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;


/**
 * Fenêtre qui affiche le message d'erreur en cas de plantage programme
 * voir services.Api()
 */
public class Fenetre extends JFrame{
    
    public Fenetre(String message) {
        this.setTitle("api requester");
		this.setSize(600, 100);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());

        JLabel label = new JLabel(message);
        pane.add(label);
        this.setContentPane(pane);
        
        this.setVisible(true);

    }
}
