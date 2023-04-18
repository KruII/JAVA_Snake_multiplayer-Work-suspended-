package opcje_w_grze;

import javax.swing.*;

import main.Gra;

import java.awt.*;
import java.awt.event.*;


public class Ucieczka extends JPanel {
    public Ucieczka(){
        this.setPreferredSize(new Dimension(Gra.Szerokosc,Gra.Wysokosc));
        this.setBackground(new Color(23,45,64));
        this.setLayout(null);
        this.setFocusable(false);

        JButton opcje = new JButton("WZNOW");
        opcje.setFocusable(false);
        opcje.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        opcje.setBounds(Gra.Szerokosc/2-Gra.Szerokosc/10, Gra.Wysokosc/10, Gra.Szerokosc/5,Gra.Wysokosc/10);
        opcje.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/40));
        opcje.setForeground(Color.BLACK);
        opcje.setBackground(new Color(13,17,23));
        opcje.setContentAreaFilled(false);
        opcje.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Gra.panelUcieczka.setVisible(false);
                Gra.panelGry.setVisible(true);
                Gra.pauza=false;
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {opcje.setContentAreaFilled(true);}

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {opcje.setContentAreaFilled(false);}

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {opcje.setContentAreaFilled(false);}

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {opcje.setContentAreaFilled(true);}
            
        });
        this.add(opcje);

        JButton opcje2 = new JButton("Opcje");
        opcje2.setFocusable(false);
        opcje2.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        opcje2.setBounds(Gra.Szerokosc/2-Gra.Szerokosc/10, (Gra.Wysokosc/10)*2+(Gra.Wysokosc/20), Gra.Szerokosc/5,Gra.Wysokosc/10);
        opcje2.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/40));
        opcje2.setForeground(Color.BLACK);
        opcje2.setBackground(new Color(13,17,23));
        opcje2.setContentAreaFilled(false);
        opcje2.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Gra.panelUcieczka.setVisible(false);
                Gra.panelGry.setVisible(true);
                Gra.pauza=false;
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {opcje.setContentAreaFilled(true);}

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {opcje.setContentAreaFilled(false);}

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {opcje.setContentAreaFilled(false);}

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {opcje.setContentAreaFilled(true);}
            
        });
        this.add(opcje2);
    }
}
