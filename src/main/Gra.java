package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import launcher.LauncherConfig;
import opcje_w_grze.*;
import online.*;

public class Gra implements KeyListener {
    private int UPS_SET = 10;
    public static boolean hosthostowanie;
    public static int Wysokosc,
            Szerokosc,
            grawitacja = 0,
            wysokoscmapy,
            wysokocgracza,
            szerokoscgracza,
            XRatio,
            YRatio,
            PunktyMapy,
            WysokoscU,
            SzerokoscU,
            WygranaPunkty = 100,
            MonitorIndex = 0;
    public static final int MaxPunkty = 20000000;
    public static boolean FullScreen;
    private JFrame ramka;
    private GraphicsDevice device;
    public static Point compCoords;
    public static String komputer = System.getProperty("user.home");
    public static String GameId = "snake";
    public static Gramainwykonanie panelGry;
    public static Start panelStart;
    public static Ucieczka panelUcieczka;
    public static Multi panelMulti;
    public static Zwyciestwo panelZwyciestwo;
    public static Przegrana panelPrzegrana;
    public static Opcje panelOpcje;
    public static Autor panelAutor;
    public static boolean prawo = false,
            lewo = false,
            gora = false,
            dol = false,
            brakruchu = true,
            pauza = false,
            pauzaserwera = false;
    public static boolean StartGry = false,
            Multi = false,
            host = false,
            klient = false,
            zwyciestwo = false,
            opcja = false;
    public static Color glowa = Color.PINK,
            tlow = Color.BLACK,
            marchw = Color.ORANGE,
            glowahost = Color.BLUE,
            tlowhost = Color.WHITE,
            glowaklient = Color.RED,
            tlowklient = Color.WHITE,
            tlo = Color.GREEN;

    public Gra() {
        LauncherConfig config = LauncherConfig.load();

        if (config == null) {
            System.out.println("Fatal Error: brak pliku ustawien launchera.");
            System.exit(0);
            return;
        }

        Szerokosc = config.getWidth();
        Wysokosc = config.getHeight();
        FullScreen = config.isFullscreen();
        MonitorIndex = config.getMonitorIndex();
        GameId = config.getGameId();

        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = environment.getScreenDevices();

        if (devices.length == 0) {
            throw new IllegalStateException("Nie znaleziono monitora.");
        }

        MonitorIndex = Math.max(0, Math.min(MonitorIndex, devices.length - 1));
        device = devices[MonitorIndex];
        Rectangle monitorBounds = device.getDefaultConfiguration().getBounds();

        if (FullScreen) {
            DisplayMode displayMode = device.getDisplayMode();
            Szerokosc = displayMode.getWidth();
            Wysokosc = displayMode.getHeight();
        } else {
            Szerokosc = Math.min(Szerokosc, Math.max(640, monitorBounds.width - 80));
            Wysokosc = Math.min(Wysokosc, Math.max(480, monitorBounds.height - 80));
        }

        wysokoscmapy = Wysokosc / 2;
        PunktyMapy = SnakeGrid.getCellSize();
        XRatio = SnakeGrid.COLUMNS;
        YRatio = SnakeGrid.ROWS;
        WysokoscU = Wysokosc;
        SzerokoscU = Szerokosc;
        SnakeGrid.recalculate(Szerokosc, Wysokosc);

        ramka = new JFrame("Gra - " + GameId);
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.getContentPane().setLayout(null);
        ramka.setUndecorated(true);
        ramka.setResizable(!FullScreen);

        if (FullScreen) {
            ramka.setBounds(monitorBounds);
            device.setFullScreenWindow(ramka);
        } else {
            int x = monitorBounds.x + (monitorBounds.width - Szerokosc) / 2;
            int y = monitorBounds.y + (monitorBounds.height - Wysokosc) / 2;
            ramka.setBounds(x, y, Szerokosc, Wysokosc);
            ramka.setMinimumSize(new Dimension(640, 480));
        }

        panelGry = new Gramainwykonanie();
        panelGry.setVisible(false);
        ramka.add(panelGry);

        panelStart = new Start();
        panelStart.setVisible(true);
        ramka.add(panelStart);

        panelUcieczka = new Ucieczka();
        panelUcieczka.setVisible(false);
        ramka.add(panelUcieczka);

        panelMulti = new Multi();
        panelMulti.setVisible(false);
        ramka.add(panelMulti);

        panelZwyciestwo = new Zwyciestwo();
        panelZwyciestwo.setVisible(false);
        ramka.add(panelZwyciestwo);

        panelPrzegrana = new Przegrana();
        panelPrzegrana.setVisible(false);
        ramka.add(panelPrzegrana);

        panelOpcje = new Opcje();
        panelOpcje.setVisible(false);
        ramka.add(panelOpcje);

        panelAutor = new Autor();
        panelAutor.setVisible(false);
        ramka.add(panelAutor);

        ustawRozmiaryPaneli();
        ramka.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ustawRozmiaryPaneli();
            }
        });

        ramka.addKeyListener(this);
        ramka.setVisible(true);
    }

    private void ustawRozmiaryPaneli() {
        int width = Math.max(1, ramka.getContentPane().getWidth());
        int height = Math.max(1, ramka.getContentPane().getHeight());

        if (width <= 1 || height <= 1) {
            width = Szerokosc;
            height = Wysokosc;
        }

        Szerokosc = width;
        Wysokosc = height;
        WysokoscU = Wysokosc;
        SzerokoscU = Szerokosc;
        SnakeGrid.recalculate(width, height);

        ustawBounds(panelGry, width, height);
        ustawBounds(panelStart, width, height);
        ustawBounds(panelUcieczka, width, height);
        ustawBounds(panelMulti, width, height);
        ustawBounds(panelZwyciestwo, width, height);
        ustawBounds(panelPrzegrana, width, height);
        ustawBounds(panelOpcje, width, height);
        ustawBounds(panelAutor, width, height);
    }

    private void ustawBounds(JPanel panel, int width, int height) {
        if (panel != null) {
            panel.setBounds(0, 0, width, height);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (StartGry && !pauzaserwera) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    if (!dol && !pauza && !pauzaserwera) {
                        brakruchu = false;
                        gora = true;
                        lewo = false;
                        prawo = false;
                    }
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if (!gora && !pauza && !pauzaserwera) {
                        brakruchu = false;
                        dol = true;
                        lewo = false;
                        prawo = false;
                    }
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if (!prawo && !pauza && !pauzaserwera) {
                        brakruchu = false;
                        lewo = true;
                        gora = false;
                        dol = false;
                    }
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    if (!lewo && !pauza && !pauzaserwera) {
                        brakruchu = false;
                        prawo = true;
                        gora = false;
                        dol = false;
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    if (!pauza) {
                        pauza = true;
                        panelGry.setVisible(false);
                        panelUcieczka.setVisible(true);
                    } else {
                        panelUcieczka.setVisible(false);
                        panelGry.setVisible(true);
                        pauza = false;
                    }
                    break;
            }
        } else if (opcja) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    opcja = false;
                    Multi = false;
                    host = false;
                    klient = false;
                    Laczenie.klientonline = false;

                    panelMulti.setVisible(false);
                    panelOpcje.setVisible(false);
                    panelAutor.setVisible(false);
                    panelStart.setVisible(true);
                    break;
            }
        } else {
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
    }
}
