package opcje_w_grze;

import main.Gra;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class Start extends JPanel {

    private Border krawedzie = BorderFactory.createLineBorder(Color.ORANGE,5);
    private Border margines = new EmptyBorder(0, 10, 0, 10);
    private CompoundBorder border = new CompoundBorder(krawedzie, margines);

    public Start(){
        this.setPreferredSize(new Dimension(Gra.Szerokosc,Gra.Wysokosc));
        this.setBackground(new Color(23,45,64));
        this.setLayout(null);
        this.setFocusable(false);

        JLabel textJLabel = new JLabel("SNAKE", JLabel.CENTER);
        textJLabel.setFocusable(false);
        textJLabel.setBounds(0, 0, Gra.Szerokosc, Gra.Wysokosc/2);
        textJLabel.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/10));
        textJLabel.setForeground(Color.BLACK);
        this.add(textJLabel);

        JButton Graj=new JButton("GRAJ W POJEDYNKE");
        Graj.setFocusable(false);
        Graj.setBounds( Gra.Szerokosc/2-Gra.Szerokosc/20, 
                        Gra.Wysokosc/2,
                        Gra.Szerokosc/10, 
                        Gra.Wysokosc/20);
        Graj.setForeground(Color.BLACK);
        Graj.setBackground(new Color(255,174,25));
        Graj.setBorder(border);
        Graj.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Gra.panelStart.setVisible(false);
                Gra.panelGry.setVisible(true);
                Gra.StartGry=true;
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Graj.setBackground(new Color(230,149,0));
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Graj.setBackground(new Color(255,174,25));
                
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

        JButton Graj2=new JButton("GRAJ ZE ZNAJOMYMI");
        Graj2.setFocusable(false);
        Graj2.setBounds(Gra.Szerokosc/2-Gra.Szerokosc/20, 
                        Gra.Wysokosc/2+Gra.Wysokosc/20+Gra.Wysokosc/80, 
                        Gra.Szerokosc/10, 
                        Gra.Wysokosc/20);
        Graj2.setForeground(Color.BLACK);
        Graj2.setBackground(new Color(255,174,25));
        Graj2.setBorder(border);
        Graj2.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Gra.opcja=true;
                Gra.panelStart.setVisible(false);
                Gra.Multi=true;
                Gra.panelMulti.setVisible(true);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Graj2.setBackground(new Color(230,149,0));
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Graj2.setBackground(new Color(255,174,25));
                
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

        JButton Opcje=new JButton("USTAWIENIA");
        Opcje.setFocusable(false);
        Opcje.setBounds(Gra.Szerokosc/2-Gra.Szerokosc/20, 
                        Gra.Wysokosc/2+Gra.Wysokosc/20*2+Gra.Wysokosc/80*2, 
                        Gra.Szerokosc/10, 
                        Gra.Wysokosc/20);
        Opcje.setForeground(Color.BLACK);
        Opcje.setBackground(new Color(255,174,25));
        Opcje.setBorder(border);
        Opcje.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Gra.opcja=true;
                Gra.panelStart.setVisible(false);
                Gra.panelOpcje.setVisible(true);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Opcje.setBackground(new Color(230,149,0));
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Opcje.setBackground(new Color(255,174,25));
                
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {             
                Opcje.setContentAreaFilled(false);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {        
                Opcje.setContentAreaFilled(true);
            }
            
        });
        this.add(Opcje);

        JButton Autor=new JButton("Autor");
        Autor.setFocusable(false);
        Autor.setBounds(Gra.Szerokosc/2-Gra.Szerokosc/20, 
                        Gra.Wysokosc/2+Gra.Wysokosc/20*3+Gra.Wysokosc/80*3, 
                        Gra.Szerokosc/10, 
                        Gra.Wysokosc/20);
        Autor.setForeground(Color.BLACK);
        Autor.setBackground(new Color(255,174,25));
        Autor.setBorder(border);
        Autor.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Gra.opcja=true;
                Gra.panelStart.setVisible(false);
                Gra.panelAutor.setVisible(true);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Autor.setBackground(new Color(230,149,0));
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Autor.setBackground(new Color(255,174,25));
                
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {             
                Autor.setContentAreaFilled(false);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {        
                Autor.setContentAreaFilled(true);
            }
            
        });
        this.add(Autor);

        JButton Wyjscie=new JButton("WYJDZ");
        Wyjscie.setFocusable(false);
        Wyjscie.setBounds(Gra.Szerokosc/2-Gra.Szerokosc/20, 
                          Gra.Wysokosc/2+Gra.Wysokosc/20*4+Gra.Wysokosc/80*4, 
                          Gra.Szerokosc/10, 
                          Gra.Wysokosc/20);
        Wyjscie.setForeground(Color.BLACK);
        Wyjscie.setBackground(new Color(255,174,25));
        Wyjscie.setBorder(border);
        Wyjscie.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Wyjscie.setBackground(new Color(230,149,0));
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Wyjscie.setBackground(new Color(255,174,25));
                
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {             
                Wyjscie.setContentAreaFilled(false);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {        
                Wyjscie.setContentAreaFilled(true);
            }
            
        });
        this.add(Wyjscie);
    }
}
