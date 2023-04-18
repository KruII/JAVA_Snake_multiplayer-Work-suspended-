package opcje_w_grze;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.Border;

import main.*;

public class Autor extends JPanel {
    public Autor(){
        this.setPreferredSize(new Dimension(Gra.Szerokosc,Gra.Wysokosc));
        this.setBackground(new Color(23,45,64));
        this.setLayout(null);
        this.setFocusable(false);

        JLabel textJLabel = new JLabel("Autor", JLabel.CENTER);
        textJLabel.setFocusable(false);
        textJLabel.setBounds(0, 0, Gra.Szerokosc, Gra.Wysokosc/4);
        textJLabel.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/10));
        textJLabel.setForeground(Color.BLACK);
        this.add(textJLabel);

        JButton gitHub = new JButton("GitHub");
        gitHub.setFocusable(false);
        gitHub.setBorder(BorderFactory.createLineBorder(new Color(23,45,64),5));
        gitHub.setBounds(0, Gra.Wysokosc/4, Gra.Szerokosc/2, Gra.Wysokosc/2-Gra.Wysokosc/4);
        gitHub.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/10));
        gitHub.setForeground(Color.BLACK);
        gitHub.setBackground(new Color(13,17,23));
        gitHub.setContentAreaFilled(false);
        gitHub.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URL("https://github.com/KruII").toURI());
                } catch (Exception e1) {
                }
            }

            

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                gitHub.setContentAreaFilled(true);
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                gitHub.setContentAreaFilled(false);
                
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {             
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {        
            }
            
        });
        this.add(gitHub);

        JButton discord = new JButton("Discord");
        discord.setFocusable(false);
        discord.setBorder(BorderFactory.createLineBorder(new Color(23,45,64),5));
        discord.setBounds(Gra.Szerokosc/2, Gra.Wysokosc/4, Gra.Szerokosc/2, Gra.Wysokosc/2-Gra.Wysokosc/4);
        discord.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/10));
        discord.setForeground(Color.BLACK);
        discord.setBackground(new Color(88,101,242));
        discord.setContentAreaFilled(false);
        discord.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/mq6fXhrFUb").toURI());
                } catch (Exception e1) {
                }
            }

            

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                discord.setContentAreaFilled(true);
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                discord.setContentAreaFilled(false);
                
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {             
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {        
            }
            
        });
        this.add(discord);

    }
}
