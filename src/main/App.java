package main;
import javax.swing.*;
import javax.swing.JComboBox.KeySelectionManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static java.lang.Math.*;

public class App implements ActionListener {
    private static final int Wysokosc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()/2,
    Szerokosc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth()/2,
    WysokoscW = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()/4,
    SzerokoscS = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth()/4;
    private JFrame ramka;
    private JPanel panel,obraz,grafika,sterowanie;
    private Font czcionka = new Font("Biting My Nails",Font.BOLD,20);
    private Font czcionka2 = new Font("Bahnschift",Font.BOLD,20);
    private Border krawedzie = BorderFactory.createLineBorder(Color.ORANGE,5);
    private Border margines = new EmptyBorder(0, 10, 0, 10);
    private CompoundBorder border = new CompoundBorder(krawedzie, margines);
    private static Point compCoords;
    private JButton Wyjscie,Graj;
    private JComboBox<String> grafikaComboBox,resolutionComboBox;
    private JCheckBox PelnyEkran, WczytanieZapisu;
    private boolean mouseOver;
    private int Clicked=0,i=0,poprzedni=0,teraz=0,GrafikaW=Wysokosc*2,GrafikaS=Szerokosc*2,inti=0;;
    private String[] relutions= new String[100],JakoscGrafiki={"Najlepsza "},dane=new String[3];;
    public static int[] relutionsS= new int[100],relutionsW= new int[100];
    private ImageIcon TrueIcon,FalseIcon;
    private static String komputer = System.getProperty("user.home");
    public static int JEDNOSTA_WIELKOSCI_KWADRATU = 20,
                      PREDKOSC_KOSMICZNA_WESZA_KWADRATOWEGO_W_MILISEKUNDACH = 100,
                      CZESCICIALA_WESZA = 5;
        
    App(){
        ramka = new JFrame("Gra");
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.setBounds(SzerokoscS,WysokoscW,Szerokosc,Wysokosc);
        ramka.getContentPane().setBackground(new Color(20,20,20));
        ramka.setLayout(null);
        ramka.setUndecorated(true);
        ramka.setLocationRelativeTo(null);
        
        ramka.setShape(new RoundRectangle2D.Double(1, 1, Szerokosc, Wysokosc, Wysokosc/10, Wysokosc/10));
        ramka.setResizable(false);

        panel = new JPanel();
        panel.setBackground(new Color(10,10,10));
        panel.setBounds(0,0,Szerokosc,Wysokosc/16);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        compCoords=null;
        panel.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {}

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {}

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {}

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                compCoords=e.getPoint();}

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                compCoords=null;}
        });
        panel.addMouseMotionListener(new MouseMotionListener(){

            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                ramka.setLocation(currCoords.x - compCoords.x,currCoords.y - compCoords.y);
                
            }

            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {}
        });

        Wyjscie=new JButton("×");
        Wyjscie.setFocusable(false);
        Wyjscie.setBorder(null);
        Wyjscie.setHorizontalTextPosition(JButton.CENTER);
        Wyjscie.setVerticalTextPosition(JButton.TOP);
        Wyjscie.setForeground(Color.white);
        Wyjscie.setFont(new Font("Bahnschift",Font.BOLD,30));
        Wyjscie.setBorder(new EmptyBorder(-Wysokosc/64, 10, 10, 10));
        Wyjscie.setBackground(null);
        Wyjscie.setContentAreaFilled(false);
        Wyjscie.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Wyjscie.setForeground(Color.red);
                System.exit(0);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Wyjscie.setForeground(Color.red);
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Wyjscie.setForeground(Color.white);
                
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                Wyjscie.setForeground(Color.white);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {}
            
        });

        panel.add(Wyjscie);

        obraz = new JPanel();
        obraz.setBackground(new Color(10,10,10));
        obraz.setBounds(Szerokosc/40,Wysokosc/10,Szerokosc/3+Szerokosc/10,(Wysokosc/5)*2);
        obraz.setLayout(new FlowLayout(FlowLayout.CENTER));

        grafika = new JPanel();
        grafika.setBackground(new Color(10,10,10));
        grafika.setBounds(Szerokosc/40,Wysokosc/2+Wysokosc/15,Szerokosc/3+Szerokosc/10,(Wysokosc/5)*2);
        grafika.setLayout(new FlowLayout(FlowLayout.CENTER));

        for(DisplayMode mode : GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayModes()){
            teraz = (mode.getWidth());
            if(poprzedni==teraz){i--;}
            else{relutions[i] = (mode.getWidth() + " × " + mode.getHeight());
                relutionsS[i]=mode.getWidth();
                relutionsW[i]=mode.getHeight();};
            poprzedni = (mode.getWidth());
            i++;
        };
        String[] relutions2= new String[i];
        for(int b=0; b<relutions2.length;b++){relutions2[b]=relutions[b];}
        grafikaComboBox = new JComboBox<String>(relutions2);
        grafikaComboBox.setFocusable(false);
        grafikaComboBox.setBorder(null);
        grafikaComboBox.setForeground(Color.white);
        grafikaComboBox.setFont(new Font("Bahnschift",Font.BOLD,30));
        grafikaComboBox.setBorder(new EmptyBorder(10, 10, 10, 10));
        grafikaComboBox.setBackground(new Color(10,10,10));
        grafikaComboBox.setKeySelectionManager(new KeySelectionManager(){

            @Override
            public int selectionForKey(char aKey, ComboBoxModel aModel) {
                // TODO Auto-generated method stub
                return 0;
            }
            
        });
        grafikaComboBox.setMaximumRowCount(100);
        grafikaComboBox.setUI(
            new BasicComboBoxUI() {
              protected JButton createArrowButton() {
                return new JButton("") {
                    @Override
                        public void setBorder(Border border) {
                            super.setBorder(null);
                            super.setBackground(null);}
                   
                           
                    @Override
                    public void setText(String text) {
                        super.setText("▲");
                        super.setForeground(Color.white);
                        super.setVisible(true);
                        super.setFont(czcionka2);
                        super.setHorizontalTextPosition(JButton.CENTER);
                        super.setVerticalTextPosition(JButton.TOP);
                        super.setContentAreaFilled(false);
                    }
                };
              }
              protected JScrollBar scrollBar(){
                  return new JScrollBar(){
                    
                  };
              }
            });
        grafikaComboBox.setEditable(false);
        grafikaComboBox.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                GrafikaS=(relutionsS[grafikaComboBox.getSelectedIndex()]);
                GrafikaW=(relutionsW[grafikaComboBox.getSelectedIndex()]);
            }

        });
        grafikaComboBox.setSelectedIndex(relutions2.length-1);

        grafika.add(grafikaComboBox);

        resolutionComboBox = new JComboBox<String>(JakoscGrafiki);
        resolutionComboBox.setFocusable(false);
        resolutionComboBox.setBorder(null);
        resolutionComboBox.setForeground(Color.white);
        resolutionComboBox.setFont(new Font("Bahnschift",Font.BOLD,30));
        resolutionComboBox.setBorder(new EmptyBorder(10, 10, 10, 10));
        resolutionComboBox.setBackground(new Color(10,10,10));
        resolutionComboBox.setUI(
            new BasicComboBoxUI() {
              protected JButton createArrowButton() {
                return new JButton("") {
                    @Override
                        public void setBorder(Border border) {
                            super.setBorder(null);
                            super.setBackground(null);}
                   
                           
                    @Override
                    public void setText(String text) {
                        super.setText("▲");
                        super.setForeground(Color.white);
                        super.setVisible(true);
                        super.setFont(czcionka2);
                        super.setHorizontalTextPosition(JButton.CENTER);
                        super.setVerticalTextPosition(JButton.TOP);
                        super.setContentAreaFilled(false);}};}});
        resolutionComboBox.setEditable(false);
        resolutionComboBox.setEnabled(true);

        grafika.add(resolutionComboBox);

        Graj=new JButton("GRAJ");
        Graj.setFocusable(false);
        Graj.setBorder(null);
        Graj.setForeground(Color.white);
        Graj.setFont(new Font("Bahnschift",Font.BOLD,30));
        Graj.setBorder(new EmptyBorder(10, 10, 10, 10));
        Graj.setBackground(new Color(10,10,10));
        Graj.setContentAreaFilled(false);
        Graj.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new File(komputer+"\\AppData\\Roaming\\.Game").mkdirs();
                try{
                    FileWriter plik = new FileWriter(komputer+"\\AppData\\Roaming\\.Game\\options."); 
                    plik.write(GrafikaS+"\n"+GrafikaW+"\n"+PelnyEkran.isSelected());
                    plik.close();
                }catch(IOException b){
                    b.printStackTrace();
                }
                Gra myWindow = new Gra();
                ramka.dispose();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Graj.setForeground(Color.green);
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Graj.setForeground(Color.white);
                
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {             
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {        
            }
            
        });

        grafika.add(Graj);

        FalseIcon = new ImageIcon(new ImageIcon("lib\\False.png").getImage().getScaledInstance(Szerokosc/32, Szerokosc/32, Image.SCALE_DEFAULT));
        TrueIcon = new ImageIcon(new ImageIcon("lib\\True.png").getImage().getScaledInstance(Szerokosc/32, Szerokosc/32, Image.SCALE_DEFAULT));

        PelnyEkran = new JCheckBox("FullScreen");
        PelnyEkran.setFocusable(false);
        PelnyEkran.setBorder(null);
        PelnyEkran.setForeground(Color.white);
        PelnyEkran.setFont(new Font("Bahnschift",Font.BOLD,20));
        PelnyEkran.setBorder(new EmptyBorder(10, 10, 10, 10));
        PelnyEkran.setBackground(new Color(10,10,10));
        PelnyEkran.setContentAreaFilled(false);
        PelnyEkran.setIcon(FalseIcon);
        PelnyEkran.setSelectedIcon(TrueIcon);
        PelnyEkran.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(grafikaComboBox.isEnabled()==false){
                    grafikaComboBox.setEnabled(true);}
                else{grafikaComboBox.setEnabled(false);
                    grafikaComboBox.setSelectedIndex(relutions2.length-1);}
            }
            
        });
        
        grafika.add(PelnyEkran);

        WczytanieZapisu = new JCheckBox("Wczytac Ustawienia");
        WczytanieZapisu.setFocusable(false);
        WczytanieZapisu.setBorder(null);
        WczytanieZapisu.setForeground(Color.white);
        WczytanieZapisu.setFont(new Font("Bahnschift",Font.BOLD,20));
        WczytanieZapisu.setBorder(new EmptyBorder(10, 10, 10, 10));
        WczytanieZapisu.setBackground(new Color(10,10,10));
        WczytanieZapisu.setContentAreaFilled(false);
        WczytanieZapisu.setIcon(FalseIcon);
        WczytanieZapisu.setEnabled(true);
        WczytanieZapisu.setSelectedIcon(TrueIcon);
        try {
            File myObj = new File(komputer+"\\AppData\\Roaming\\.Game\\options.");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                dane[inti++] = myReader.nextLine();
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            WczytanieZapisu.setEnabled(false);
            e.printStackTrace();
          }
        WczytanieZapisu.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(PelnyEkran.isEnabled()==false){
                    PelnyEkran.setEnabled(true);
                    if(PelnyEkran.isSelected()==false){
                        grafikaComboBox.setEnabled(true);}}
                else{PelnyEkran.setEnabled(false);
                    PelnyEkran.setSelected(Boolean.parseBoolean(dane[2]));
                    grafikaComboBox.setEnabled(false);}
                    grafikaComboBox.setSelectedItem(Integer.parseInt(dane[0])+" × "+Integer.parseInt(dane[1]));
            }
            
        });
        
        grafika.add(WczytanieZapisu);

        sterowanie = new JPanel();
        sterowanie.setBackground(new Color(10,10,10));
        sterowanie.setBounds((Szerokosc/19)*10,Wysokosc/10,(Szerokosc/83)*40,Wysokosc/2+Wysokosc/3+Wysokosc/30);
        sterowanie.setLayout(new FlowLayout(FlowLayout.CENTER));

        ramka.add(grafika);
        ramka.add(sterowanie);
        ramka.add(obraz);
        ramka.add(panel);
        ramka.setVisible(true);
        
    }

    public static void main(String[] args) throws Exception {
        App kalkulatorApp = new App();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}