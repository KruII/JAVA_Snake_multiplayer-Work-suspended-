package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Przegrana extends JPanel {
    public Przegrana(){
        this.setPreferredSize(new Dimension(Gra.Szerokosc,Gra.Wysokosc));
        this.setBackground(new Color(23,45,64));
        this.setLayout(null);
        this.setFocusable(false);

        JLabel text = new JLabel("PRZEGRALES", JLabel.CENTER);
        text.setFocusable(false);
        text.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/10));
        text.setBounds(0, (Gra.Wysokosc/Gra.PunktyMapy)*2, Gra.Szerokosc, text.getFont().getSize());
        text.setForeground(Color.BLACK);
        this.add(text);

        JButton sprobojPonownie = new JButton("Zagraj Ponownie");
        sprobojPonownie.setFocusable(false);
        sprobojPonownie.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        sprobojPonownie.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/40));
        FontMetrics Metryka = getFontMetrics(sprobojPonownie.getFont());
        sprobojPonownie.setBounds((Gra.Szerokosc)/2-((Metryka.stringWidth("Zagraj Ponownie"))+Gra.Szerokosc/40)/2, Gra.Wysokosc/2, (Metryka.stringWidth("Zagraj Ponownie"))+Gra.Szerokosc/40, sprobojPonownie.getFont().getSize()+Gra.Wysokosc/40);
        sprobojPonownie.setForeground(Color.BLACK);
        sprobojPonownie.setBackground(new Color(100,17,100));
        sprobojPonownie.setContentAreaFilled(false);
        sprobojPonownie.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Gra.hosthostowanie=false;
                Gra.panelPrzegrana.setVisible(false);
                Gra.panelZwyciestwo.setVisible(false);
                Gra.prawo=false;
                Gra.lewo=false;
                Gra.gora=false;
                Gra.dol=false;
                Gra.brakruchu=true;
                Gra.pauza=false;
                Gra.pauzaserwera=false;
                Gra.StartGry=false;
                Gra.Multi=false;
                Gra.host=false;
                Gra.klient=false;
                Gra.zwyciestwo=false;
                Gra.opcja=false;
                Gramainwykonanie.dzialanie=true;
                for(int i=0;i<Gramainwykonanie.x.length;i++){
                    Gramainwykonanie.x[i]=((int)(Gra.Szerokosc/2/App.JEDNOSTA_WIELKOSCI_KWADRATU))*App.JEDNOSTA_WIELKOSCI_KWADRATU;
                    Gramainwykonanie.y[i]=((Gra.Wysokosc/2/App.JEDNOSTA_WIELKOSCI_KWADRATU))*App.JEDNOSTA_WIELKOSCI_KWADRATU;}
                Gramainwykonanie.Zjedzone=0;
                Gramainwykonanie.CZASOmierz.start();
                Gra.panelGry.setVisible(true);
                Gra.StartGry=true;
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                sprobojPonownie.setContentAreaFilled(false);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                sprobojPonownie.setContentAreaFilled(true);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                sprobojPonownie.setContentAreaFilled(false);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                sprobojPonownie.setContentAreaFilled(true);
            }
            
        });
        this.add(sprobojPonownie);

        JButton wyjdz2 = new JButton("Wyjdz Do Lobby");
        wyjdz2.setFocusable(false);
        wyjdz2.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        wyjdz2.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/40));
        wyjdz2.setBounds((Gra.Szerokosc)/2-((Metryka.stringWidth("Zagraj Ponownie"))+Gra.Szerokosc/40)/2, Gra.Wysokosc/2+(sprobojPonownie.getFont().getSize()+Gra.Wysokosc/40)*2, (Metryka.stringWidth("Zagraj Ponownie"))+Gra.Szerokosc/40, sprobojPonownie.getFont().getSize()+Gra.Wysokosc/40);
        wyjdz2.setForeground(Color.BLACK);
        wyjdz2.setBackground(new Color(100,17,100));
        wyjdz2.setContentAreaFilled(true);
        wyjdz2.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Gra.hosthostowanie=false;
                Gra.panelPrzegrana.setVisible(false);
                Gra.panelZwyciestwo.setVisible(false);
                Gra.prawo=false;
                Gra.lewo=false;
                Gra.gora=false;
                Gra.dol=false;
                Gra.brakruchu=true;
                Gra.pauza=false;
                Gra.pauzaserwera=false;
                Gra.StartGry=false;
                Gra.Multi=false;
                Gra.host=false;
                Gra.klient=false;
                Gra.zwyciestwo=false;
                Gra.opcja=false;
                Gra.panelStart.setVisible(true);
                Gramainwykonanie.dzialanie=true;
                for(int i=0;i<Gramainwykonanie.x.length;i++){
                    Gramainwykonanie.x[i]=((int)(Gra.Szerokosc/2/App.JEDNOSTA_WIELKOSCI_KWADRATU))*App.JEDNOSTA_WIELKOSCI_KWADRATU;
                    Gramainwykonanie.y[i]=((Gra.Wysokosc/2/App.JEDNOSTA_WIELKOSCI_KWADRATU))*App.JEDNOSTA_WIELKOSCI_KWADRATU;}
                Gramainwykonanie.Zjedzone=0;
                Gramainwykonanie.CZASOmierz.start();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                wyjdz2.setContentAreaFilled(false);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                wyjdz2.setContentAreaFilled(true);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                wyjdz2.setContentAreaFilled(false);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                wyjdz2.setContentAreaFilled(true);
            }
            
        });
        this.add(wyjdz2);

        JButton wyjdz = new JButton("Wyjdz Z Gry");
        wyjdz.setFocusable(false);
        wyjdz.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        wyjdz.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/40));
        wyjdz.setBounds((Gra.Szerokosc)/2-((Metryka.stringWidth("Zagraj Ponownie"))+Gra.Szerokosc/40)/2, Gra.Wysokosc/2+(sprobojPonownie.getFont().getSize()+Gra.Wysokosc/40)*4, (Metryka.stringWidth("Zagraj Ponownie"))+Gra.Szerokosc/40, sprobojPonownie.getFont().getSize()+Gra.Wysokosc/40);
        wyjdz.setForeground(Color.BLACK);
        wyjdz.setBackground(new Color(100,17,100));
        wyjdz.setContentAreaFilled(true);
        wyjdz.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                wyjdz.setContentAreaFilled(false);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                wyjdz.setContentAreaFilled(true);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                wyjdz.setContentAreaFilled(false);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                wyjdz.setContentAreaFilled(true);
            }
            
        });
        this.add(wyjdz);
    }
}