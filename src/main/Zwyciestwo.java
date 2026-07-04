package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Zwyciestwo extends JPanel {
    public Zwyciestwo() {
        this.setPreferredSize(new Dimension(Gra.Szerokosc, Gra.Wysokosc));
        this.setBackground(new Color(23, 45, 64));
        this.setLayout(null);
        this.setFocusable(false);

        JLabel text = new JLabel("WYGRALES", JLabel.CENTER);
        text.setFocusable(false);
        text.setFont(new Font("Biting My Nails", Font.BOLD, Gra.Szerokosc / 10));
        text.setBounds(0, Math.max(40, Gra.Wysokosc / 8), Gra.Szerokosc, text.getFont().getSize());
        text.setForeground(Color.BLACK);
        this.add(text);

        JButton sprobojPonownie = przycisk("Zagraj Ponownie", Gra.Wysokosc / 2);
        sprobojPonownie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                resetStanuGry();
                Gra.panelPrzegrana.setVisible(false);
                Gra.panelZwyciestwo.setVisible(false);
                Gra.panelGry.restartGry();
                Gra.panelGry.setVisible(true);
                Gra.StartGry = true;
            }
        });
        this.add(sprobojPonownie);

        JButton wyjdz2 = przycisk("Wyjdz Do Lobby", Gra.Wysokosc / 2 + Math.max(70, Gra.Wysokosc / 8));
        wyjdz2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                resetStanuGry();
                Gra.panelPrzegrana.setVisible(false);
                Gra.panelZwyciestwo.setVisible(false);
                Gra.panelGry.restartGry();
                Gra.panelStart.setVisible(true);
            }
        });
        this.add(wyjdz2);

        JButton wyjdz = przycisk("Wyjdz Z Gry", Gra.Wysokosc / 2 + Math.max(140, Gra.Wysokosc / 4));
        wyjdz.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.exit(0);
            }
        });
        this.add(wyjdz);
    }

    private JButton przycisk(String text, int y) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        button.setFont(new Font("Biting My Nails", Font.BOLD, Math.max(18, Gra.Szerokosc / 40)));
        FontMetrics metryka = getFontMetrics(button.getFont());
        int width = metryka.stringWidth(text) + Math.max(40, Gra.Szerokosc / 40);
        int height = button.getFont().getSize() + Math.max(28, Gra.Wysokosc / 40);
        button.setBounds(Gra.Szerokosc / 2 - width / 2, y, width, height);
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(100, 17, 100));
        button.setContentAreaFilled(true);
        return button;
    }

    private void resetStanuGry() {
        Gra.hosthostowanie = false;
        Gra.prawo = false;
        Gra.lewo = false;
        Gra.gora = false;
        Gra.dol = false;
        Gra.brakruchu = true;
        Gra.pauza = false;
        Gra.pauzaserwera = false;
        Gra.StartGry = false;
        Gra.Multi = false;
        Gra.host = false;
        Gra.klient = false;
        Gra.zwyciestwo = false;
        Gra.opcja = false;
        Gramainwykonanie.dzialanie = true;
    }
}
