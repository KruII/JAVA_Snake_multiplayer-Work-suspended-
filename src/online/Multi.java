package online;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.Gra;

public class Multi extends JPanel {
    private JTextField adresHosta;
    private JLabel status;

    public Multi() {
        this.setPreferredSize(new Dimension(Gra.Szerokosc, Gra.Wysokosc));
        this.setBackground(new Color(23, 45, 64));
        this.setLayout(null);
        this.setFocusable(false);

        JLabel tytul = new JLabel("ONLINE", JLabel.CENTER);
        tytul.setBounds(0, Gra.Wysokosc / 12, Gra.Szerokosc, Gra.Wysokosc / 8);
        tytul.setForeground(Color.BLACK);
        tytul.setFont(new Font("Biting My Nails", Font.BOLD, Math.max(40, Gra.Szerokosc / 12)));
        this.add(tytul);

        JLabel opis = new JLabel("Host zostawia adres pusty. Klient wpisuje IP hosta albo localhost.", JLabel.CENTER);
        opis.setBounds(0, Gra.Wysokosc / 4, Gra.Szerokosc, 36);
        opis.setForeground(Color.BLACK);
        opis.setFont(new Font("Bahnschift", Font.BOLD, Math.max(16, Gra.Szerokosc / 70)));
        this.add(opis);

        adresHosta = new JTextField("localhost");
        adresHosta.setHorizontalAlignment(JTextField.CENTER);
        adresHosta.setFocusable(true);
        adresHosta.setBounds(Gra.Szerokosc / 2 - Gra.Szerokosc / 8, Gra.Wysokosc / 4 + 48, Gra.Szerokosc / 4, Gra.Wysokosc / 16);
        adresHosta.setForeground(Color.WHITE);
        adresHosta.setCaretColor(Color.ORANGE);
        adresHosta.setBackground(new Color(10, 10, 10));
        adresHosta.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4));
        adresHosta.setFont(new Font("Bahnschift", Font.BOLD, Math.max(18, Gra.Szerokosc / 60)));
        this.add(adresHosta);

        status = new JLabel("Port: 4999", JLabel.CENTER);
        status.setBounds(0, Gra.Wysokosc / 4 + 48 + Gra.Wysokosc / 14, Gra.Szerokosc, 32);
        status.setForeground(Color.BLACK);
        status.setFont(new Font("Bahnschift", Font.BOLD, Math.max(16, Gra.Szerokosc / 75)));
        this.add(status);

        JButton hostuj = przycisk("HOSTUJ", 0, Gra.Wysokosc / 2, Gra.Szerokosc / 2, Gra.Wysokosc / 2);
        hostuj.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!Gra.host && !Gra.klient) {
                    Gra.host = true;
                    ustawStatus("Czekam na klienta na porcie 4999...");
                    Hostowanie hostowanie = new Hostowanie();
                    hostowanie.start();
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hostuj.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hostuj.setContentAreaFilled(false);
            }
        });
        this.add(hostuj);

        JButton polacz = przycisk("POLACZ", Gra.Szerokosc / 2, Gra.Wysokosc / 2, Gra.Szerokosc / 2, Gra.Wysokosc / 2);
        polacz.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!Gra.klient && !Gra.host) {
                    String host = adresHosta.getText().trim();
                    if (host.length() == 0) {
                        host = "localhost";
                    }

                    Laczenie.serverHost = host;
                    Laczenie.klientonline = true;
                    Gra.klient = true;
                    ustawStatus("Lacze z " + host + ":4999...");
                    Laczenie laczenie = new Laczenie();
                    laczenie.start();
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                polacz.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                polacz.setContentAreaFilled(false);
            }
        });
        this.add(polacz);
    }

    private JButton przycisk(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setBorder(null);
        button.setBounds(x, y, width, height);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Biting My Nails", Font.BOLD, Math.max(38, Gra.Szerokosc / 20)));
        button.setBackground(new Color(10, 10, 10));
        button.setContentAreaFilled(false);
        return button;
    }

    private void ustawStatus(String text) {
        status.setText(text);
    }
}
