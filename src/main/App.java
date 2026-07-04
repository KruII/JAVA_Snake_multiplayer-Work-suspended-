package main;

import launcher.GameEntry;
import launcher.LauncherConfig;
import launcher.MonitorOption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class App implements ActionListener {
    private static final int LAUNCHER_WIDTH = Math.max(900, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 2);
    private static final int LAUNCHER_HEIGHT = Math.max(580, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight() / 2);

    public static final int PLANSZA_KOLUMNY = SnakeGrid.COLUMNS;
    public static final int PLANSZA_WIERSZE = SnakeGrid.ROWS;

    public static int JEDNOSTA_WIELKOSCI_KWADRATU = 20,
            PREDKOSC_KOSMICZNA_WESZA_KWADRATOWEGO_W_MILISEKUNDACH = 100,
            CZESCICIALA_WESZA = 5;

    private JFrame ramka;
    private JPanel belka, panelOpcji, panelPodgladu;
    private Point compCoords;

    private JComboBox<GameEntry> wyborGryComboBox;
    private JComboBox<MonitorOption> monitorComboBox;
    private JComboBox<String> rozdzielczoscComboBox;
    private JCheckBox PelnyEkran, WczytanieZapisu;

    private final List<GameEntry> gry = GameEntry.domyslneGry();
    private List<MonitorOption> monitory = new ArrayList<MonitorOption>();
    private List<Dimension> aktualneRozdzielczosci = new ArrayList<Dimension>();

    private int GrafikaS = 1280;
    private int GrafikaW = 720;

    public App() {
        monitory = MonitorOption.pobierzMonitory();

        ramka = new JFrame("Game Hub");
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.setBounds(
                (Toolkit.getDefaultToolkit().getScreenSize().width - LAUNCHER_WIDTH) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - LAUNCHER_HEIGHT) / 2,
                LAUNCHER_WIDTH,
                LAUNCHER_HEIGHT
        );
        ramka.setUndecorated(true);
        ramka.setResizable(false);
        ramka.setLayout(null);
        ramka.getContentPane().setBackground(new Color(13, 17, 23));
        ramka.setShape(new RoundRectangle2D.Double(1, 1, LAUNCHER_WIDTH, LAUNCHER_HEIGHT, 32, 32));

        zbudujBelke();
        zbudujPanelOpcji();
        zbudujPanelPodgladu();
        wczytajUstawieniaDoLaunchera();

        ramka.add(belka);
        ramka.add(panelOpcji);
        ramka.add(panelPodgladu);
        ramka.setVisible(true);
    }

    private void zbudujBelke() {
        belka = new JPanel();
        belka.setBackground(new Color(8, 11, 17));
        belka.setBounds(0, 0, LAUNCHER_WIDTH, 44);
        belka.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 4));

        belka.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                compCoords = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                compCoords = null;
            }
        });

        belka.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (compCoords == null) {
                    return;
                }

                Point currCoords = e.getLocationOnScreen();
                ramka.setLocation(currCoords.x - compCoords.x, currCoords.y - compCoords.y);
            }
        });

        JButton zamknij = new JButton("×");
        zamknij.setFocusable(false);
        zamknij.setBorder(null);
        zamknij.setForeground(Color.WHITE);
        zamknij.setFont(new Font("Arial", Font.BOLD, 30));
        zamknij.setContentAreaFilled(false);
        zamknij.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        belka.add(zamknij);
    }

    private void zbudujPanelOpcji() {
        panelOpcji = new JPanel();
        panelOpcji.setLayout(null);
        panelOpcji.setBackground(new Color(13, 17, 23));
        panelOpcji.setBounds(32, 72, LAUNCHER_WIDTH / 2 - 48, LAUNCHER_HEIGHT - 104);

        JLabel tytul = etykieta("GAME HUB", 0, 0, panelOpcji.getWidth(), 58, 36, Color.WHITE);
        panelOpcji.add(tytul);

        JLabel opis = etykieta("Wybierz gre, monitor i tryb okna. Reszta niech nie udaje magii.", 4, 58, panelOpcji.getWidth(), 34, 14, new Color(156, 163, 175));
        opis.setHorizontalAlignment(SwingConstants.LEFT);
        panelOpcji.add(opis);

        wyborGryComboBox = new JComboBox<GameEntry>(gry.toArray(new GameEntry[0]));
        ustawCombo(wyborGryComboBox);
        dodajOpisanePole(panelOpcji, "Gra", wyborGryComboBox, 118);

        monitorComboBox = new JComboBox<MonitorOption>(monitory.toArray(new MonitorOption[0]));
        ustawCombo(monitorComboBox);
        monitorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                przeladujRozdzielczosci();
            }
        });
        dodajOpisanePole(panelOpcji, "Monitor", monitorComboBox, 206);

        rozdzielczoscComboBox = new JComboBox<String>();
        ustawCombo(rozdzielczoscComboBox);
        rozdzielczoscComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = rozdzielczoscComboBox.getSelectedIndex();
                if (index >= 0 && index < aktualneRozdzielczosci.size()) {
                    Dimension rozdzielczosc = aktualneRozdzielczosci.get(index);
                    GrafikaS = rozdzielczosc.width;
                    GrafikaW = rozdzielczosc.height;
                }
            }
        });
        dodajOpisanePole(panelOpcji, "Rozdzielczosc okna", rozdzielczoscComboBox, 294);

        PelnyEkran = new JCheckBox("Uruchom w pelnym ekranie");
        PelnyEkran.setFocusable(false);
        PelnyEkran.setForeground(Color.WHITE);
        PelnyEkran.setBackground(new Color(13, 17, 23));
        PelnyEkran.setBounds(4, 386, panelOpcji.getWidth() - 8, 34);
        PelnyEkran.setFont(new Font("Arial", Font.BOLD, 16));
        PelnyEkran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rozdzielczoscComboBox.setEnabled(!PelnyEkran.isSelected());
                przeladujRozdzielczosci();
            }
        });
        panelOpcji.add(PelnyEkran);

        WczytanieZapisu = new JCheckBox("Wczytaj zapisane ustawienia");
        WczytanieZapisu.setFocusable(false);
        WczytanieZapisu.setForeground(Color.WHITE);
        WczytanieZapisu.setBackground(new Color(13, 17, 23));
        WczytanieZapisu.setBounds(4, 424, panelOpcji.getWidth() - 8, 34);
        WczytanieZapisu.setFont(new Font("Arial", Font.BOLD, 16));
        WczytanieZapisu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (WczytanieZapisu.isSelected()) {
                    wczytajUstawieniaDoLaunchera();
                }
            }
        });
        panelOpcji.add(WczytanieZapisu);

        JButton graj = new JButton("START");
        graj.setFocusable(false);
        graj.setBounds(4, panelOpcji.getHeight() - 72, panelOpcji.getWidth() - 8, 54);
        graj.setForeground(Color.BLACK);
        graj.setBackground(new Color(255, 174, 25));
        graj.setFont(new Font("Arial", Font.BOLD, 26));
        graj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uruchomWybranaGre();
            }
        });
        panelOpcji.add(graj);

        przeladujRozdzielczosci();
    }

    private void zbudujPanelPodgladu() {
        panelPodgladu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                rysujPodglad((Graphics2D) g);
            }
        };
        panelPodgladu.setBackground(new Color(23, 45, 64));
        panelPodgladu.setBounds(LAUNCHER_WIDTH / 2 + 20, 72, LAUNCHER_WIDTH / 2 - 52, LAUNCHER_HEIGHT - 104);
    }

    private void rysujPodglad(Graphics2D g) {
        g.setColor(new Color(23, 45, 64));
        g.fillRoundRect(0, 0, panelPodgladu.getWidth(), panelPodgladu.getHeight(), 24, 24);

        g.setColor(new Color(255, 174, 25));
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("Snake", 32, 54);

        g.setColor(new Color(180, 190, 205));
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("Plansza dziala na logicznej siatce " + SnakeGrid.COLUMNS + "x" + SnakeGrid.ROWS + ",", 32, 90);
        g.drawString("a nie na humorze rozdzielczosci.", 32, 112);

        int previewX = 32;
        int previewY = 154;
        int previewCell = Math.max(6, Math.min((panelPodgladu.getWidth() - 64) / SnakeGrid.COLUMNS, 12));
        int previewW = previewCell * SnakeGrid.COLUMNS;
        int previewH = previewCell * SnakeGrid.ROWS;

        g.setColor(new Color(13, 17, 23));
        g.fillRect(previewX, previewY, previewW, previewH);

        g.setColor(new Color(55, 65, 84));
        for (int x = 0; x <= SnakeGrid.COLUMNS; x += 4) {
            int px = previewX + x * previewCell;
            g.drawLine(px, previewY, px, previewY + previewH);
        }
        for (int y = 0; y <= SnakeGrid.ROWS; y += 4) {
            int py = previewY + y * previewCell;
            g.drawLine(previewX, py, previewX + previewW, py);
        }

        g.setColor(Color.PINK);
        g.fillRect(previewX + 20 * previewCell, previewY + 14 * previewCell, previewCell, previewCell);
        g.setColor(Color.BLACK);
        for (int i = 1; i < 6; i++) {
            g.fillRect(previewX + (20 - i) * previewCell, previewY + 14 * previewCell, previewCell, previewCell);
        }
        g.setColor(Color.ORANGE);
        g.fillOval(previewX + 29 * previewCell, previewY + 10 * previewCell, previewCell, previewCell);
    }

    private void dodajOpisanePole(JPanel parent, String tekst, JComponent component, int y) {
        JLabel label = etykieta(tekst, 4, y, parent.getWidth() - 8, 24, 15, new Color(156, 163, 175));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        component.setBounds(4, y + 28, parent.getWidth() - 8, 42);
        parent.add(label);
        parent.add(component);
    }

    private JLabel etykieta(String tekst, int x, int y, int width, int height, int fontSize, Color color) {
        JLabel label = new JLabel(tekst, SwingConstants.CENTER);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setForeground(color);
        return label;
    }

    private void ustawCombo(JComboBox<?> comboBox) {
        comboBox.setFocusable(false);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBackground(new Color(8, 11, 17));
        comboBox.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void przeladujRozdzielczosci() {
        MonitorOption monitor = pobierzWybranyMonitor();
        aktualneRozdzielczosci = monitor.pobierzRozdzielczosci();

        rozdzielczoscComboBox.removeAllItems();

        for (Dimension rozdzielczosc : aktualneRozdzielczosci) {
            rozdzielczoscComboBox.addItem(rozdzielczosc.width + " x " + rozdzielczosc.height);
        }

        if (PelnyEkran != null && PelnyEkran.isSelected()) {
            GrafikaS = monitor.getBounds().width;
            GrafikaW = monitor.getBounds().height;
            rozdzielczoscComboBox.setEnabled(false);
        } else {
            rozdzielczoscComboBox.setEnabled(true);
            wybierzNajlepszaRozdzielczoscOkna(monitor);
        }
    }

    private void wybierzNajlepszaRozdzielczoscOkna(MonitorOption monitor) {
        int maxW = monitor.getBounds().width - 120;
        int maxH = monitor.getBounds().height - 120;

        int bestIndex = 0;
        int bestArea = 0;

        for (int i = 0; i < aktualneRozdzielczosci.size(); i++) {
            Dimension d = aktualneRozdzielczosci.get(i);
            if (d.width <= maxW && d.height <= maxH) {
                int area = d.width * d.height;
                if (area > bestArea) {
                    bestArea = area;
                    bestIndex = i;
                }
            }
        }

        if (!aktualneRozdzielczosci.isEmpty()) {
            rozdzielczoscComboBox.setSelectedIndex(bestIndex);
            Dimension selected = aktualneRozdzielczosci.get(bestIndex);
            GrafikaS = selected.width;
            GrafikaW = selected.height;
        }
    }

    private MonitorOption pobierzWybranyMonitor() {
        Object selected = monitorComboBox == null ? null : monitorComboBox.getSelectedItem();
        if (selected instanceof MonitorOption) {
            return (MonitorOption) selected;
        }

        return monitory.get(0);
    }

    private GameEntry pobierzWybranaGre() {
        Object selected = wyborGryComboBox.getSelectedItem();
        if (selected instanceof GameEntry) {
            return (GameEntry) selected;
        }

        return gry.get(0);
    }

    private void wczytajUstawieniaDoLaunchera() {
        LauncherConfig config = LauncherConfig.load();
        if (config == null) {
            return;
        }

        wybierzGre(config.getGameId());
        wybierzMonitor(config.getMonitorIndex());

        PelnyEkran.setSelected(config.isFullscreen());
        przeladujRozdzielczosci();

        if (!config.isFullscreen()) {
            wybierzRozdzielczosc(config.getWidth(), config.getHeight());
        }
    }

    private void wybierzGre(String gameId) {
        for (int i = 0; i < gry.size(); i++) {
            if (gry.get(i).getId().equals(gameId)) {
                wyborGryComboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    private void wybierzMonitor(int monitorIndex) {
        int safeIndex = Math.max(0, Math.min(monitorIndex, monitory.size() - 1));
        monitorComboBox.setSelectedIndex(safeIndex);
    }

    private void wybierzRozdzielczosc(int width, int height) {
        for (int i = 0; i < aktualneRozdzielczosci.size(); i++) {
            Dimension d = aktualneRozdzielczosci.get(i);
            if (d.width == width && d.height == height) {
                rozdzielczoscComboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    private void uruchomWybranaGre() {
        GameEntry gra = pobierzWybranaGre();
        MonitorOption monitor = pobierzWybranyMonitor();

        LauncherConfig config = new LauncherConfig(
                GrafikaS,
                GrafikaW,
                PelnyEkran.isSelected(),
                monitor.getIndex(),
                gra.getId()
        );
        config.save();

        if ("snake".equals(gra.getId())) {
            new Gra();
            ramka.dispose();
        } else {
            JOptionPane.showMessageDialog(ramka, "Ta gra nie ma jeszcze implementacji. Spokojnie, szkielet juz czeka.");
        }
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
