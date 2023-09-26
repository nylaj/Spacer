package utils;

import javax.swing.*;
import java.awt.*;

public class Windowed extends JFrame {
    public Component comp;
    public Windowed(Component comp, String title){
        super(title);
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }

}
