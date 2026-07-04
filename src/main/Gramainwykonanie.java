package main;

import javax.swing.JPanel;
import online.Hostowanie;
import online.Laczenie;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Gramainwykonanie extends JPanel implements ActionListener {
    private Random losowa;
    public static final int x[] = new int[Gra.MaxPunkty],
            y[] = new int[Gra.MaxPunkty];
    public static int Zjedzone,
            MARCHW_X,
            MARCHW_Y;
    public static boolean dzialanie = false;
    static Timer CZASOmierz;

    Gramainwykonanie() {
        losowa = new Random();
        this.setPreferredSize(new Dimension(Gra.Szerokosc, Gra.Wysokosc));
        this.setBackground(Gra.tlo);
        this.setFocusable(false);
        PoczatekGry();
    }

    private void PoczatekGry() {
        App.CZESCICIALA_WESZA = 5;
        Zjedzone = 0;
        ustawPozycjeStartowa(SnakeGrid.COLUMNS / 2, SnakeGrid.ROWS / 2);
        RESP_MARCHWI();
        dzialanie = true;
        CZASOmierz = new Timer(App.PREDKOSC_KOSMICZNA_WESZA_KWADRATOWEGO_W_MILISEKUNDACH, this);
        CZASOmierz.start();
    }

    public void restartGry() {
        if (CZASOmierz != null && CZASOmierz.isRunning()) {
            CZASOmierz.stop();
        }

        App.CZESCICIALA_WESZA = 5;
        Zjedzone = 0;
        dzialanie = true;
        Gra.zwyciestwo = false;
        Gra.brakruchu = true;
        Gra.prawo = false;
        Gra.lewo = false;
        Gra.gora = false;
        Gra.dol = false;
        ustawPozycjeStartowa(SnakeGrid.COLUMNS / 2, SnakeGrid.ROWS / 2);
        RESP_MARCHWI();

        if (CZASOmierz != null) {
            CZASOmierz.start();
        }

        repaint();
    }

    public static void ustawPozycjeStartowa(int headX, int headY) {
        int safeHeadX = SnakeGrid.clampX(headX);
        int safeHeadY = SnakeGrid.clampY(headY);

        for (int i = 0; i < App.CZESCICIALA_WESZA; i++) {
            x[i] = SnakeGrid.clampX(safeHeadX - i);
            y[i] = safeHeadY;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        SnakeGrid.recalculate(getWidth(), getHeight());
        Maluj((Graphics2D) g);
    }

    private void Maluj(Graphics2D g) {
        if (dzialanie) {
            g.setColor(Gra.tlo);
            g.fillRect(0, 0, getWidth(), getHeight());

            rysujPlansze(g);
            rysujJablko(g);
            rysujWeza(g, x, y, App.CZESCICIALA_WESZA, Gra.glowa, Gra.tlow);

            if (Gra.host) {
                rysujWeza(g, Hostowanie.xweza, Hostowanie.yweza, Hostowanie.dlugoscweza, Gra.glowahost, Gra.tlowhost);
            }

            if (Gra.klient) {
                rysujWeza(g, Laczenie.xweza, Laczenie.yweza, Laczenie.dlugoscweza, Gra.glowaklient, Gra.tlowklient);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial Black", Font.BOLD, Math.max(18, getWidth() / 42)));
            FontMetrics Metryka = getFontMetrics(g.getFont());
            g.drawString("Wynik: " + Zjedzone, (getWidth() - Metryka.stringWidth("Wynik: " + Zjedzone)) / 2, g.getFont().getSize() + 8);
        } else {
            Gra.hosthostowanie = false;
            Gra.panelPrzegrana.setVisible(true);
            Gra.panelGry.setVisible(false);
        }

        if (Zjedzone >= Gra.WygranaPunkty) {
            Gra.hosthostowanie = false;
            dzialanie = false;
            Gra.panelZwyciestwo.setVisible(true);
            Gra.panelGry.setVisible(false);
        }
    }

    private void rysujPlansze(Graphics2D g) {
        Rectangle board = SnakeGrid.boardRect();

        g.setColor(new Color(13, 17, 23));
        g.fillRect(board.x, board.y, board.width, board.height);

        g.setColor(new Color(40, 50, 65));
        g.drawRect(board.x, board.y, board.width, board.height);

        if (SnakeGrid.getCellSize() >= 12) {
            g.setColor(new Color(31, 41, 55));

            for (int col = 0; col <= SnakeGrid.COLUMNS; col++) {
                int px = board.x + col * SnakeGrid.getCellSize();
                g.drawLine(px, board.y, px, board.y + board.height);
            }

            for (int row = 0; row <= SnakeGrid.ROWS; row++) {
                int py = board.y + row * SnakeGrid.getCellSize();
                g.drawLine(board.x, py, board.x + board.width, py);
            }
        }
    }

    private void rysujJablko(Graphics2D g) {
        Rectangle rect = SnakeGrid.cellRect(MARCHW_X, MARCHW_Y);
        int inset = Math.max(2, SnakeGrid.getCellSize() / 7);

        g.setColor(Gra.marchw);
        g.fillOval(
                rect.x + inset,
                rect.y + inset,
                Math.max(1, rect.width - inset * 2),
                Math.max(1, rect.height - inset * 2)
        );
    }

    private void rysujWeza(Graphics2D g, int[] snakeX, int[] snakeY, int length, Color headColor, Color bodyColor) {
        int safeLength = Math.max(0, Math.min(length, snakeX.length));

        for (int i = 0; i < safeLength; i++) {
            Rectangle rect = SnakeGrid.cellRect(snakeX[i], snakeY[i]);
            int inset = Math.max(1, SnakeGrid.getCellSize() / 10);

            if (i == 0) {
                g.setColor(headColor);
            } else {
                g.setColor(bodyColor);
            }

            g.fillRoundRect(
                    rect.x + inset,
                    rect.y + inset,
                    Math.max(1, rect.width - inset * 2),
                    Math.max(1, rect.height - inset * 2),
                    Math.max(2, SnakeGrid.getCellSize() / 4),
                    Math.max(2, SnakeGrid.getCellSize() / 4)
            );
        }
    }

    private void RESP_MARCHWI() {
        int attempts = 0;

        do {
            MARCHW_X = losowa.nextInt(SnakeGrid.COLUMNS);
            MARCHW_Y = losowa.nextInt(SnakeGrid.ROWS);
            attempts++;
        } while (czyCialoWezaNaPolu(MARCHW_X, MARCHW_Y) && attempts < 1000);
    }

    private boolean czyCialoWezaNaPolu(int gridX, int gridY) {
        for (int i = 0; i < App.CZESCICIALA_WESZA; i++) {
            if (x[i] == gridX && y[i] == gridY) {
                return true;
            }
        }

        return false;
    }

    private void teKocieRuchyTranslacja() {
        if (!Gra.pauzaserwera) {
            if (!Gra.brakruchu && !Gra.pauza) {
                for (int i = App.CZESCICIALA_WESZA; i > 0; i--) {
                    x[i] = x[i - 1];
                    y[i] = y[i - 1];
                }
            }

            if (Gra.gora && !Gra.pauza) {
                y[0] -= 1;
            }
            if (Gra.dol && !Gra.pauza) {
                y[0] += 1;
            }
            if (Gra.lewo && !Gra.pauza) {
                x[0] -= 1;
            }
            if (Gra.prawo && !Gra.pauza) {
                x[0] += 1;
            }
        }
    }

    private void sprawdzenieGluszkiJabluszki() {
        if ((x[0] == MARCHW_X) && (y[0] == MARCHW_Y)) {
            App.CZESCICIALA_WESZA++;
            Zjedzone++;
            RESP_MARCHWI();
        }
    }

    private void szukanieProblemowKolizji() {
        if (!Gra.brakruchu) {
            for (int i = 1; i < App.CZESCICIALA_WESZA; i++) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    dzialanie = false;
                }
            }
        }

        if (x[0] < 0 || x[0] >= SnakeGrid.COLUMNS) {
            dzialanie = false;
        }
        if (y[0] < 0 || y[0] >= SnakeGrid.ROWS) {
            dzialanie = false;
        }
        if (!dzialanie && CZASOmierz != null) {
            CZASOmierz.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (dzialanie) {
            teKocieRuchyTranslacja();
            sprawdzenieGluszkiJabluszki();
            szukanieProblemowKolizji();
        }
        repaint();
    }
}
