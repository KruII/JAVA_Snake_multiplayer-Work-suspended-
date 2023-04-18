package main;

import javax.swing.JPanel;

import online.Hostowanie;
import online.Laczenie;
import online.Multi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class Gramainwykonanie extends JPanel implements ActionListener{

    private Random losowa;
    public static final int x[]=new int[Gra.MaxPunkty],
                            y[] = new int[Gra.MaxPunkty];
    public static int Zjedzone,
                      MARCHW_X,
                      MARCHW_Y;
    public static boolean dzialanie=false;
    static Timer CZASOmierz;    

    Gramainwykonanie(){
        losowa = new Random();
        this.setPreferredSize(new Dimension(Gra.Szerokosc,Gra.Wysokosc));
        this.setBackground(Gra.tlo);
        this.setFocusable(false);
		PoczatekGry();
    }


    private void PoczatekGry() {
        x[0]=((int)(Gra.Szerokosc/2/App.JEDNOSTA_WIELKOSCI_KWADRATU))*App.JEDNOSTA_WIELKOSCI_KWADRATU;
        y[0]=((Gra.Wysokosc/2/App.JEDNOSTA_WIELKOSCI_KWADRATU))*App.JEDNOSTA_WIELKOSCI_KWADRATU;

        RESP_MARCHWI();
        dzialanie=true;
        CZASOmierz = new Timer(App.PREDKOSC_KOSMICZNA_WESZA_KWADRATOWEGO_W_MILISEKUNDACH, this);
        CZASOmierz.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Maluj(g);
    };

    private void Maluj(Graphics g) {
        if(dzialanie){
            g.setColor(Gra.tlo);
            g.fillRect(0, 0, Gra.Szerokosc, Gra.Wysokosc);
            g.setColor(Gra.marchw);
            g.fillOval(MARCHW_X, MARCHW_Y, App.JEDNOSTA_WIELKOSCI_KWADRATU, App.JEDNOSTA_WIELKOSCI_KWADRATU);

            for(int i=0;i<App.CZESCICIALA_WESZA;i++){
                if(i==0){
                    g.setColor(Gra.glowa);
                    g.fillRect(x[i], y[i], App.JEDNOSTA_WIELKOSCI_KWADRATU, App.JEDNOSTA_WIELKOSCI_KWADRATU);
                }else{
                    g.setColor(Gra.tlow);
                    g.fillRect(x[i], y[i], App.JEDNOSTA_WIELKOSCI_KWADRATU, App.JEDNOSTA_WIELKOSCI_KWADRATU);
                }
            }
            
            if(Gra.host){
                for(int i=0;i<Hostowanie.dlugoscweza;i++){
                    if(i==0){
                        g.setColor(Gra.glowahost);
                        g.fillRect(Hostowanie.xweza[i], Hostowanie.yweza[i], App.JEDNOSTA_WIELKOSCI_KWADRATU, App.JEDNOSTA_WIELKOSCI_KWADRATU);
                    }else{
                        g.setColor(Gra.tlowhost);
                        g.fillRect(Hostowanie.xweza[i], Hostowanie.yweza[i], App.JEDNOSTA_WIELKOSCI_KWADRATU, App.JEDNOSTA_WIELKOSCI_KWADRATU);
                    }
                }
            }
            if(Gra.klient){
                for(int i=0;i<Laczenie.dlugoscweza;i++){
                    if(i==0){
                        g.setColor(Gra.glowaklient);
                        g.fillRect(Laczenie.xweza[i], Laczenie.yweza[i], App.JEDNOSTA_WIELKOSCI_KWADRATU, App.JEDNOSTA_WIELKOSCI_KWADRATU);
                    }else{
                        g.setColor(Gra.tlowklient);
                        g.fillRect(Laczenie.xweza[i], Laczenie.yweza[i], App.JEDNOSTA_WIELKOSCI_KWADRATU, App.JEDNOSTA_WIELKOSCI_KWADRATU);
                    }
                }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial Black",Font.BOLD,30));
            FontMetrics Metryka = getFontMetrics(g.getFont());
            g.drawString("Wynik: "+Zjedzone, (Gra.Szerokosc-Metryka.stringWidth("Wynik: "+ Zjedzone))/2, g.getFont().getSize());
        }
        else{
            Gra.hosthostowanie=false;
            Gra.panelPrzegrana.setVisible(true);
            Gra.panelGry.setVisible(false);
        }
        if(Zjedzone>=Gra.WygranaPunkty){
            Gra.hosthostowanie=false;
            dzialanie=false;
            Gra.panelZwyciestwo.setVisible(true);
            Gra.panelGry.setVisible(false);
        }
    }

    private void RESP_MARCHWI() {
        MARCHW_X = losowa.nextInt((int)(Gra.Szerokosc/App.JEDNOSTA_WIELKOSCI_KWADRATU))*App.JEDNOSTA_WIELKOSCI_KWADRATU;
		MARCHW_Y = losowa.nextInt((int)(Gra.Wysokosc/App.JEDNOSTA_WIELKOSCI_KWADRATU))*App.JEDNOSTA_WIELKOSCI_KWADRATU;
    }

    private void teKocieRuchyTranslacja(){
        if(!Gra.pauzaserwera){
            if(!Gra.brakruchu && !Gra.pauza ){
                for(int i = App.CZESCICIALA_WESZA;i>0;i--){
                    x[i]=x[i-1];
                    y[i]=y[i-1];
                }
            }
            if(Gra.gora && !Gra.pauza){
                y[0]-=App.JEDNOSTA_WIELKOSCI_KWADRATU;
            }
            if(Gra.dol && !Gra.pauza){
                y[0]+=App.JEDNOSTA_WIELKOSCI_KWADRATU;
            }
            if(Gra.lewo && !Gra.pauza){
                x[0]-=App.JEDNOSTA_WIELKOSCI_KWADRATU;
            }
            if(Gra.prawo && !Gra.pauza){
                x[0]+=App.JEDNOSTA_WIELKOSCI_KWADRATU;
            }
        }
    }

    private void sprawdzenieGluszkiJabluszki(){
        if((x[0] == MARCHW_X) && (y[0] == MARCHW_Y)) {
			App.CZESCICIALA_WESZA++;
		    Zjedzone++;
			RESP_MARCHWI();
		}
    }

    private void szukanieProblemowKolizji(){
        if(!Gra.brakruchu){
            for(int i = App.CZESCICIALA_WESZA;i>0;i--) {
                if((x[0] == x[i])&& (y[0] == y[i])) {
                    dzialanie = false;
                }
            }
        }
        
        if(x[0] < 0) {
			dzialanie = false;
		}    
		if(x[0] > Gra.Szerokosc) {
			dzialanie = false;
		}
		if(y[0] < 0) {
			dzialanie = false;
		}
		if(y[0] > Gra.Wysokosc) {
			dzialanie = false;
		}
		if(!dzialanie) {
			CZASOmierz.stop();
		}
    }

    @Override
	public void actionPerformed(ActionEvent e) {
		
		if(dzialanie) {
			teKocieRuchyTranslacja();
			sprawdzenieGluszkiJabluszki();
			szukanieProblemowKolizji();
		}
		repaint();
	}
}
