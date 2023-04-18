package main;
import java.awt.*;
import java.awt.event.*;
import java.beans.Visibility;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import opcje_w_grze.*;
import online.*;


public class Gra implements KeyListener{
    private final int FPS_SET = 60;
	private int UPS_SET = 10;
    public static boolean hosthostowanie;
    public static int Wysokosc,
                Szerokosc,
                inti=0,
                grawitacja=0,
                wysokoscmapy,
                wysokocgracza,
                szerokoscgracza,
                XRatio,YRatio,
                PunktyMapy,
                WysokoscU,
                SzerokoscU,
                WygranaPunkty=100;
    public static final int MaxPunkty= 20000000;
    public static boolean FullScreen;
    private JFrame ramka;
    private JButton Wyjscie;
    private String[] dane=new String[3];
    private GraphicsDevice device;
    public static Point compCoords;
    public static String komputer = System.getProperty("user.home");
    public static Gramainwykonanie panelGry;
    public static Start panelStart;
    public static Ucieczka panelUcieczka;
    public static Multi panelMulti;
    public static Zwyciestwo panelZwyciestwo;
    public static Przegrana panelPrzegrana;
    public static Opcje panelOpcje;
    public static Autor panelAutor;
    public static boolean prawo=false,
                    lewo=false,
                    gora=false,
                    dol=false,
                    brakruchu=true,
                    pauza=false,
                    pauzaserwera=false;
    public static boolean StartGry=false,
                          Multi=false,
                          host=false,
                          klient=false,
                          zwyciestwo=false,
                          opcja=false;
    public static Color glowa= Color.PINK,
                        tlow=Color.BLACK,
                        marchw=Color.ORANGE,
                        glowahost=Color.BLUE,
                        tlowhost=Color.WHITE,
                        glowaklient=Color.RED,
                        tlowklient=Color.WHITE,
                        tlo=Color.GREEN;

        Gra(){
            try {
                File myObj = new File(komputer+"\\AppData\\Roaming\\.Game\\options.");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                  dane[inti++] = myReader.nextLine();
                }
                myReader.close();
              } catch (FileNotFoundException e) {
                System.out.println("Fatal Error");
                System.exit(0);
                e.printStackTrace();
              }
            Szerokosc= Integer.parseInt(dane[0]);
            Wysokosc= Integer.parseInt(dane[1]);
            FullScreen=Boolean.parseBoolean(dane[2]);
            wysokoscmapy=Wysokosc/2;
            
            ramka = new JFrame("Gra");
            ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ramka.setBounds(Szerokosc/2,Wysokosc/2,Szerokosc,Wysokosc);
            ramka.setMinimumSize(new Dimension(App.relutionsS[0],App.relutionsW[0]));
            int width = Szerokosc;
            int widthn = Szerokosc;
            int height = Wysokosc;
            do{
            widthn  = width;
            width = height;
            if(widthn%height!=0){height  = widthn%height;}
            else{break;}
            }while(true);
            
            PunktyMapy=height;
            XRatio = Szerokosc / height;
            YRatio = Wysokosc / height;
            WysokoscU=Wysokosc;
            SzerokoscU=Szerokosc;
            //ramka.getContentPane().setBackground(new Color(20,20,20));
            //ramka.setLayout(null);
            ramka.setUndecorated(true);
            ramka.setLocationRelativeTo(null);
            ramka.setResizable(true);

            if(FullScreen==true){
                GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
                device = environment.getDefaultScreenDevice();
                device.setFullScreenWindow(ramka);}

            panelGry=new Gramainwykonanie();
            panelGry.setVisible(false);
            panelGry.setBounds(0, 0, Szerokosc, Wysokosc);
            ramka.add(panelGry);
            
            panelStart= new Start();
            panelStart.setVisible(true);
            panelStart.setBounds(0, 0, Szerokosc, Wysokosc);
            ramka.add(panelStart);

            panelUcieczka= new Ucieczka();
            panelUcieczka.setVisible(false);
            panelUcieczka.setBounds(0, 0, Szerokosc, Wysokosc);
            ramka.add(panelUcieczka);

            panelMulti= new Multi();
            panelMulti.setVisible(false);
            panelMulti.setBounds(0, 0, Szerokosc, Wysokosc);
            ramka.add(panelMulti);

            panelZwyciestwo= new Zwyciestwo();
            panelZwyciestwo.setVisible(false);
            panelZwyciestwo.setBounds(0, 0, Szerokosc, Wysokosc);
            ramka.add(panelZwyciestwo);

            panelPrzegrana= new Przegrana();
            panelPrzegrana.setVisible(false);
            panelPrzegrana.setBounds(0, 0, Szerokosc, Wysokosc);
            ramka.add(panelPrzegrana);
            
            panelOpcje= new Opcje();
            panelOpcje.setVisible(false);
            panelOpcje.setBounds(0, 0, Szerokosc, Wysokosc);
            ramka.add(panelOpcje);
            
            panelAutor= new Autor();
            panelAutor.setVisible(false);
            panelAutor.setBounds(0, 0, Szerokosc, Wysokosc);
            ramka.add(panelAutor);

            ramka.addKeyListener(this);
            /*try {
                new Hostowanie();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            //new Laczenie();

            //wysokocgracza=postacx.getY();
            //szerokoscgracza=postacx.getX();

            ramka.setVisible(true);
            //Gramainwykonanie.generatorjablek();

        }
        @Override
        public void keyPressed(KeyEvent e) {
            if(StartGry && !pauzaserwera){
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if(!dol && !pauza && !pauzaserwera){
                            brakruchu=false;
                            gora = true;
                            lewo=false;
                            prawo=false;}
                        break;
                    case KeyEvent.VK_S:
                        if(!gora && !pauza && !pauzaserwera){
                            brakruchu=false;
                            dol = true;
                            lewo=false;
                            prawo=false;}
                        break;
                    case KeyEvent.VK_A:
                        if(!prawo && !pauza && !pauzaserwera){
                            brakruchu=false;
                            lewo = true;
                            gora=false;
                            dol=false;}
                        break;
                    case KeyEvent.VK_D:
                        if(!lewo && !pauza && !pauzaserwera){
                            brakruchu=false;
                            prawo = true;
                            gora=false;
                            dol=false;}
                        break;
                    case KeyEvent.VK_ESCAPE:
                            if(!pauza){
                                pauza=true;
                                panelGry.setVisible(false);
                                panelUcieczka.setVisible(true);

                            }else if(pauza){
                                panelUcieczka.setVisible(false);
                                panelGry.setVisible(true);
                                pauza=false;}
                        break;
                }
            }
            else if(opcja){
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        opcja=false;
                        Multi=false;
                        host=false;
                        klient=false;
                        Laczenie.klientonline=false;

                        panelMulti.setVisible(false);
                        panelOpcje.setVisible(false);
                        panelAutor.setVisible(false);
                        panelStart.setVisible(true);
                        break;
                }
            }
            else{
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                }
            }       
        }
        @Override
        public void keyReleased(KeyEvent e) {
        }
        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
        }    
}
