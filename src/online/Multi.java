package online;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import main.Gra;

public class Multi extends JPanel {
    public Multi(){
        this.setPreferredSize(new Dimension(Gra.Szerokosc,Gra.Wysokosc));
        this.setBackground(new Color(23,45,64));
        this.setLayout(null);
        this.setFocusable(false);


        JButton Graj=new JButton("HOSTUJ");
        Graj.setFocusable(false);
        Graj.setBorder(null);
        Graj.setBounds(0, 0, Gra.Szerokosc/2, Gra.Wysokosc);
        Graj.setForeground(Color.white);
        Graj.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/20));
        Graj.setBackground(new Color(10,10,10));
        Graj.setContentAreaFilled(false);
        Graj.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(!Gra.host && !Gra.klient){
                    Gra.host=true;
                    Hostowanie hostowanie = new Hostowanie();
                    hostowanie.start();}

            }

            

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Graj.setContentAreaFilled(true);
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Graj.setContentAreaFilled(false);
                
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {             
                Graj.setContentAreaFilled(false);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {        
                Graj.setContentAreaFilled(true);
            }
            
        });
        this.add(Graj);

        JButton Graj2=new JButton("POLACZ");
        Graj2.setFocusable(false);
        Graj2.setBorder(null);
        Graj2.setBounds(Gra.Szerokosc/2, 0, Gra.Szerokosc/2, Gra.Wysokosc);
        Graj2.setForeground(Color.white);
        Graj2.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/20));
        Graj2.setBackground(new Color(10,10,10));
        Graj2.setContentAreaFilled(false);
        Graj2.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(!Gra.klient && !Gra.host){
                    Laczenie.klientonline=true;
                    Laczenie laczenie = new Laczenie();
                    laczenie.start();}
                
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Graj2.setContentAreaFilled(true);
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Graj2.setContentAreaFilled(false);
                
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {             
                Graj2.setContentAreaFilled(false);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {        
                Graj2.setContentAreaFilled(true);
            }
            
        });
        this.add(Graj2);


    }
}
