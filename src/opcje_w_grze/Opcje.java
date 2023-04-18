package opcje_w_grze;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.SliderUI;

import org.w3c.dom.css.RGBColor;

import main.*;

public class Opcje extends JPanel{
    JColorChooser chooser;
    Object transparencySlider;
    int pomocnicza=100;
    public Opcje(){
        this.setPreferredSize(new Dimension(Gra.Szerokosc,Gra.Wysokosc));
        this.setBackground(new Color(23,45,64));
        this.setLayout(null);
        this.setFocusable(false);

        JLabel textJLabel = new JLabel("USTAWIENIA", JLabel.CENTER);
        textJLabel.setFocusable(false);
        textJLabel.setBounds(0, 0, Gra.Szerokosc, Gra.Wysokosc/4);
        textJLabel.setFont(new Font("Biting My Nails",Font.BOLD,Gra.Szerokosc/10));
        textJLabel.setForeground(Color.BLACK);
        this.add(textJLabel);

        JPanel ustawienia = new JPanel();
        ustawienia.setBounds(0, Gra.Wysokosc/4, Gra.Szerokosc, Gra.Wysokosc/4+Gra.Wysokosc/2);
        ustawienia.setBackground(Color.BLACK);
        ustawienia.setLayout(null);

        JPanel colorPicker = new JPanel();
        colorPicker.setBounds(0, 0, Gra.Szerokosc/2-1, ustawienia.getHeight());
        colorPicker.setBackground(new Color(23,45,64));
        colorPicker.setLayout(null);
            
            JLabel textJLabel2 = new JLabel("Kolory", JLabel.CENTER);
            textJLabel2.setFocusable(false);
            textJLabel2.setBounds(0, 0, colorPicker.getWidth(), colorPicker.getHeight()/10);
            textJLabel2.setFont(new Font("Biting My Nails",Font.BOLD,colorPicker.getWidth()/10));
            textJLabel2.setForeground(Color.BLACK);
            colorPicker.add(textJLabel2);

            JButton glowaWeza = new JButton("GLOWA");
            glowaWeza.setFocusable(false);
            glowaWeza.setBorder(BorderFactory.createLineBorder(Gra.glowa,5));
            glowaWeza.setBounds(colorPicker.getWidth()/40, colorPicker.getHeight()/10+colorPicker.getHeight()/20, colorPicker.getWidth()/2-colorPicker.getWidth()/40, colorPicker.getHeight()/10);
            glowaWeza.setFont(new Font("Biting My Nails",Font.BOLD,colorPicker.getWidth()/20));
            glowaWeza.setForeground(Color.BLACK);
            glowaWeza.setBackground(new Color(13,17,23));
            glowaWeza.setContentAreaFilled(false);
            glowaWeza.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Gra.glowa=chooser.getColor();
                    glowaWeza.setBorder(BorderFactory.createLineBorder(Gra.glowa,5));
                }
    
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                
            });
            colorPicker.add(glowaWeza);

            JButton tlowWeza = new JButton("TLOW");
            tlowWeza.setFocusable(false);
            tlowWeza.setBorder(BorderFactory.createLineBorder(Gra.tlow,5));
            tlowWeza.setBounds(colorPicker.getWidth()/2+colorPicker.getWidth()/80, colorPicker.getHeight()/10+colorPicker.getHeight()/20, colorPicker.getWidth()/2-colorPicker.getWidth()/40, colorPicker.getHeight()/10);
            tlowWeza.setFont(new Font("Biting My Nails",Font.BOLD,colorPicker.getWidth()/20));
            tlowWeza.setForeground(Color.BLACK);
            tlowWeza.setBackground(new Color(13,17,23));
            tlowWeza.setContentAreaFilled(false);
            tlowWeza.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Gra.tlow=chooser.getColor();
                    tlowWeza.setBorder(BorderFactory.createLineBorder(Gra.tlow,5));
                }
    
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                
            });
            colorPicker.add(tlowWeza);

            JButton glowaWezaH = new JButton("GLOWA H");
            glowaWezaH.setFocusable(false);
            glowaWezaH.setBorder(BorderFactory.createLineBorder(Gra.glowahost,5));
            glowaWezaH.setBounds(colorPicker.getWidth()/40, (colorPicker.getHeight()/10+colorPicker.getHeight()/20)*2, colorPicker.getWidth()/2-colorPicker.getWidth()/40, colorPicker.getHeight()/10);
            glowaWezaH.setFont(new Font("Biting My Nails",Font.BOLD,colorPicker.getWidth()/20));
            glowaWezaH.setForeground(Color.BLACK);
            glowaWezaH.setBackground(new Color(13,17,23));
            glowaWezaH.setContentAreaFilled(false);
            glowaWezaH.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Gra.glowahost=chooser.getColor();
                    glowaWezaH.setBorder(BorderFactory.createLineBorder(Gra.glowahost,5));
                }
    
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                
            });
            colorPicker.add(glowaWezaH);

            JButton tlowWezaH = new JButton("TLOW H");
            tlowWezaH.setFocusable(false);
            tlowWezaH.setBorder(BorderFactory.createLineBorder(Gra.tlowhost,5));
            tlowWezaH.setBounds(colorPicker.getWidth()/2+colorPicker.getWidth()/80, (colorPicker.getHeight()/10+colorPicker.getHeight()/20)*2, colorPicker.getWidth()/2-colorPicker.getWidth()/40, colorPicker.getHeight()/10);
            tlowWezaH.setFont(new Font("Biting My Nails",Font.BOLD,colorPicker.getWidth()/20));
            tlowWezaH.setForeground(Color.BLACK);
            tlowWezaH.setBackground(new Color(13,17,23));
            tlowWezaH.setContentAreaFilled(false);
            tlowWezaH.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Gra.tlowhost=chooser.getColor();
                    tlowWezaH.setBorder(BorderFactory.createLineBorder(Gra.tlowhost,5));
                }
    
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                
            });
            colorPicker.add(tlowWezaH);

            JButton glowaWezaK = new JButton("GLOWA K");
            glowaWezaK.setFocusable(false);
            glowaWezaK.setBorder(BorderFactory.createLineBorder(Gra.glowaklient,5));
            glowaWezaK.setBounds(colorPicker.getWidth()/40, (colorPicker.getHeight()/10+colorPicker.getHeight()/20)*3, colorPicker.getWidth()/2-colorPicker.getWidth()/40, colorPicker.getHeight()/10);
            glowaWezaK.setFont(new Font("Biting My Nails",Font.BOLD,colorPicker.getWidth()/20));
            glowaWezaK.setForeground(Color.BLACK);
            glowaWezaK.setBackground(new Color(13,17,23));
            glowaWezaK.setContentAreaFilled(false);
            glowaWezaK.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Gra.glowaklient=chooser.getColor();
                    glowaWezaK.setBorder(BorderFactory.createLineBorder(Gra.glowaklient,5));
                }
    
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                
            });
            colorPicker.add(glowaWezaK);

            JButton tlowWezaK = new JButton("TLOW K");
            tlowWezaK.setFocusable(false);
            tlowWezaK.setBorder(BorderFactory.createLineBorder(Gra.tlowklient,5));
            tlowWezaK.setBounds(colorPicker.getWidth()/2+colorPicker.getWidth()/80, (colorPicker.getHeight()/10+colorPicker.getHeight()/20)*3, colorPicker.getWidth()/2-colorPicker.getWidth()/40, colorPicker.getHeight()/10);
            tlowWezaK.setFont(new Font("Biting My Nails",Font.BOLD,colorPicker.getWidth()/20));
            tlowWezaK.setForeground(Color.BLACK);
            tlowWezaK.setBackground(new Color(13,17,23));
            tlowWezaK.setContentAreaFilled(false);
            tlowWezaK.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Gra.tlowklient=chooser.getColor();
                    tlowWezaK.setBorder(BorderFactory.createLineBorder(Gra.tlowklient,5));
                }
    
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                
            });
            colorPicker.add(tlowWezaK);

            JButton background = new JButton("TLO");
            background.setFocusable(false);
            background.setBorder(BorderFactory.createLineBorder(Gra.tlo,5));
            background.setBounds(colorPicker.getWidth()/40, (colorPicker.getHeight()/10+colorPicker.getHeight()/20)*4, colorPicker.getWidth()/2-colorPicker.getWidth()/40, colorPicker.getHeight()/10);
            background.setFont(new Font("Biting My Nails",Font.BOLD,colorPicker.getWidth()/20));
            background.setForeground(Color.BLACK);
            background.setBackground(new Color(13,17,23));
            background.setContentAreaFilled(false);
            background.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Gra.tlo=chooser.getColor();
                    background.setBorder(BorderFactory.createLineBorder(Gra.tlo,5));
                }
    
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                
            });
            colorPicker.add(background);

            JButton mARCHW = new JButton("MARCHW");
            mARCHW.setFocusable(false);
            mARCHW.setBorder(BorderFactory.createLineBorder(Gra.marchw,5));
            mARCHW.setBounds(colorPicker.getWidth()/2+colorPicker.getWidth()/80, (colorPicker.getHeight()/10+colorPicker.getHeight()/20)*4, colorPicker.getWidth()/2-colorPicker.getWidth()/40, colorPicker.getHeight()/10);
            mARCHW.setFont(new Font("Biting My Nails",Font.BOLD,colorPicker.getWidth()/20));
            mARCHW.setForeground(Color.BLACK);
            mARCHW.setBackground(new Color(13,17,23));
            mARCHW.setContentAreaFilled(false);
            mARCHW.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Gra.marchw=chooser.getColor();
                    mARCHW.setBorder(BorderFactory.createLineBorder(Gra.marchw,5));
                }
    
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {}
    
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                
            });
            colorPicker.add(mARCHW);



        ustawienia.add(colorPicker);

        JPanel colorPicker2 = new JPanel();
        colorPicker2.setBounds(Gra.Szerokosc/2+2, 0, Gra.Szerokosc/2-1, ustawienia.getHeight()/2-1);
        colorPicker2.setBackground(new Color(23,45,64));
        colorPicker2.setLayout(null);

        UIManager.put("Panel.background", new ColorUIResource(new Color(23,45,64)));
        chooser = new JColorChooser();
        chooser.setBounds(0, 0, colorPicker2.getWidth(), colorPicker2.getHeight());
        chooser.setFocusable(false);
        AbstractColorChooserPanel[] colorPanels = chooser.getChooserPanels();
        for (int i = 0; i < colorPanels.length; i++) {
            AbstractColorChooserPanel cp = colorPanels[i];
            cp.setBackground(new Color(23,45,64));
            try {
                Field f = cp.getClass().getDeclaredField("panel");
                f.setAccessible(true);
                Object colorPanel = f.get(cp);

                Field f2 = colorPanel.getClass().getDeclaredField("spinners");
                f2.setAccessible(true);
                Object sliders = f2.get(colorPanel);

                
                for(int b=0; b<4; b++){
                transparencySlider = java.lang.reflect.Array.get(sliders, b);
                if (i == colorPanels.length - 1){
                    transparencySlider = java.lang.reflect.Array.get(sliders, 4-b);}
                Method setVisible = transparencySlider.getClass().getDeclaredMethod(
                    "setVisible", boolean.class);
                setVisible.setAccessible(true);
                setVisible.invoke(transparencySlider, false);}
            } catch (Throwable t) {}
        }
        chooser.removeChooserPanel(chooser.getChooserPanels()[0]);
        chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
        chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
        chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
        chooser.setPreviewPanel(new JPanel());
        colorPicker2.add(chooser);


        ustawienia.add(colorPicker2);

        JPanel dlugosc = new JPanel();
        dlugosc.setBounds(Gra.Szerokosc/2+2, ustawienia.getHeight()/2+2, Gra.Szerokosc/2-1, ustawienia.getHeight()/2-1);
        dlugosc.setBackground(new Color(23,45,64));
        dlugosc.setLayout(null);

        JLabel textLabel = new JLabel("Dlugosc Weza", JLabel.CENTER);
        textLabel.setFont(new Font("Biting My Nails",Font.BOLD,dlugosc.getHeight()/10));
        textLabel.setBounds(0, dlugosc.getHeight()/20, dlugosc.getWidth(), textLabel.getFont().getSize());
        textLabel.setForeground(Color.BLACK);
        dlugosc.add(textLabel);

        JLabel dlugosctext = new JLabel(String.valueOf(Gra.WygranaPunkty),  JLabel.CENTER);
        dlugosctext.setFont(new Font("Biting My Nails",Font.BOLD,dlugosc.getHeight()/10));
        dlugosctext.setBounds(0, dlugosc.getHeight()/5+(textLabel.getFont().getSize()+dlugosc.getHeight()/20), dlugosc.getWidth(), textLabel.getFont().getSize());
        dlugosctext.setForeground(Color.BLACK);
        dlugosc.add(dlugosctext);

        JSlider kolor = new JSlider();
        kolor.setValue(10);
        kolor.setBounds(dlugosc.getWidth()/20, dlugosc.getHeight()/5, dlugosc.getWidth()-dlugosc.getWidth()/10, textLabel.getFont().getSize()+dlugosc.getHeight()/20);
        kolor.setFocusable(false);
        kolor.setBackground(null);
        kolor.setMaximum(100);
        kolor.setSnapToTicks(true);
        kolor.setPaintTicks(true);
        kolor.setPaintTrack(true);
        kolor.setMajorTickSpacing(10);
        kolor.setMinorTickSpacing(10);
        kolor.setValue(Gra.WygranaPunkty);
        kolor.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Gra.WygranaPunkty = kolor.getValue();
                if((Gra.Szerokosc/App.JEDNOSTA_WIELKOSCI_KWADRATU)*(Gra.Wysokosc/App.JEDNOSTA_WIELKOSCI_KWADRATU)>pomocnicza-10){
                    if(Gra.WygranaPunkty>=pomocnicza){
                        pomocnicza+=10;
                        kolor.setMaximum(pomocnicza);
                    }
                }
                if(Gra.WygranaPunkty<pomocnicza-10 && pomocnicza>100){
                    pomocnicza-=10;
                    kolor.setMaximum(pomocnicza);
                }
                kolor.setMajorTickSpacing(pomocnicza/10);
                dlugosctext.setText(String.valueOf(Gra.WygranaPunkty));
                System.out.println(Gra.WygranaPunkty+" "+pomocnicza);
            }
        });
        kolor.setBorder(null);
        kolor.setOpaque(true);
        System.out.println(kolor.getUI());
        dlugosc.add(kolor);

        


        ustawienia.add(dlugosc);


        this.add(ustawienia);
    }
}
