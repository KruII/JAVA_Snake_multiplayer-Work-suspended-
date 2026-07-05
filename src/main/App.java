package main;

import launcher.GameEntry;
import launcher.LauncherConfig;
import launcher.MonitorOption;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class App implements ActionListener {
    private static final int Wysokosc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight() / 2,
            Szerokosc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 2,
            WysokoscW = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight() / 4,
            SzerokoscS = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 4;

    public static final int PLANSZA_KOLUMNY = SnakeGrid.COLUMNS;
    public static final int PLANSZA_WIERSZE = SnakeGrid.ROWS;

    public static int JEDNOSTA_WIELKOSCI_KWADRATU = 20,
            PREDKOSC_KOSMICZNA_WESZA_KWADRATOWEGO_W_MILISEKUNDACH = 100,
            CZESCICIALA_WESZA = 5;

    private JFrame ramka;
    private JPanel panel, obraz, grafika, sterowanie;
    private Font czcionka2 = new Font("Bahnschift", Font.BOLD, 20);
    private Border krawedzie = BorderFactory.createLineBorder(Color.ORANGE, 5);
    private Border margines = new EmptyBorder(0, 10, 0, 10);
    private CompoundBorder border = new CompoundBorder(krawedzie, margines);
    private static Point compCoords;

    private JButton Wyjscie, Graj;
    private JComboBox<GameEntry> graComboBox;
    private JComboBox<MonitorOption> monitorComboBox;
    private JComboBox<String> grafikaComboBox;
    private JComboBox<String> jakoscComboBox;
    private JCheckBox PelnyEkran, WczytanieZapisu;

    private final List<GameEntry> gry = GameEntry.domyslneGry();
    private List<MonitorOption> monitory = new ArrayList<MonitorOption>();
    private List<Dimension> aktualneRozdzielczosci = new ArrayList<Dimension>();

    private int GrafikaW = Wysokosc * 2,
            GrafikaS = Szerokosc * 2;

    private ImageIcon TrueIcon, FalseIcon;

    public App() {
        monitory = MonitorOption.pobierzMonitory();

        ramka = new JFrame("Gra");
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.setBounds(SzerokoscS, WysokoscW, Szerokosc, Wysokosc);
        ramka.getContentPane().setBackground(new Color(20, 20, 20));
        ramka.setLayout(null);
        ramka.setUndecorated(true);
        ramka.setLocationRelativeTo(null);
        ramka.setShape(new RoundRectangle2D.Double(1, 1, Szerokosc, Wysokosc, Wysokosc / 10, Wysokosc / 10));
        ramka.setResizable(false);

        zbudujGornaBelke();
        zbudujPanelObrazu();
        zbudujPanelGrafiki();
        zbudujPanelSterowania();
        wczytajUstawieniaDoLaunchera();

        ramka.add(grafika);
        ramka.add(sterowanie);
        ramka.add(obraz);
        ramka.add(panel);
        ramka.setVisible(true);
    }

    private void zbudujGornaBelke() {
        panel = new JPanel();
        panel.setBackground(new Color(10, 10, 10));
        panel.setBounds(0, 0, Szerokosc, Wysokosc / 16);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        compCoords = null;

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                compCoords = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                compCoords = null;
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (compCoords == null) {
                    return;
                }

                Point currCoords = e.getLocationOnScreen();
                ramka.setLocation(currCoords.x - compCoords.x, currCoords.y - compCoords.y);
            }
        });

        Wyjscie = new JButton("×");
        Wyjscie.setFocusable(false);
        Wyjscie.setBorder(null);
        Wyjscie.setHorizontalTextPosition(JButton.CENTER);
        Wyjscie.setVerticalTextPosition(JButton.TOP);
        Wyjscie.setForeground(Color.WHITE);
        Wyjscie.setFont(new Font("Bahnschift", Font.BOLD, 30));
        Wyjscie.setBorder(new EmptyBorder(-Wysokosc / 64, 10, 10, 10));
        Wyjscie.setBackground(null);
        Wyjscie.setContentAreaFilled(false);
        Wyjscie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Wyjscie.setForeground(Color.RED);
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Wyjscie.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Wyjscie.setForeground(Color.WHITE);
            }
        });

        panel.add(Wyjscie);
    }

    private void zbudujPanelObrazu() {
        obraz = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                rysujPodglad((Graphics2D) g);
            }
        };
        obraz.setBackground(new Color(10, 10, 10));
        obraz.setBounds(Szerokosc / 40, Wysokosc / 10, Szerokosc / 3 + Szerokosc / 10, (Wysokosc / 5) * 2);
        obraz.setLayout(null);
        obraz.setBorder(border);
    }

    private void zbudujPanelGrafiki() {
        grafika = new JPanel();
        grafika.setBackground(new Color(10, 10, 10));
        grafika.setBounds(Szerokosc / 40, Wysokosc / 2 + Wysokosc / 15, Szerokosc / 3 + Szerokosc / 10, (Wysokosc / 5) * 2);
        grafika.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        grafika.setBorder(border);

        graComboBox = new JComboBox<GameEntry>(gry.toArray(new GameEntry[0]));
        stylComboBox(graComboBox);
        grafika.add(graComboBox);

        monitorComboBox = new JComboBox<MonitorOption>(monitory.toArray(new MonitorOption[0]));
        stylComboBox(monitorComboBox);
        monitorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                przeladujRozdzielczosci();
            }
        });
        grafika.add(monitorComboBox);

        grafikaComboBox = new JComboBox<String>();
        stylComboBox(grafikaComboBox);
        grafikaComboBox.setMaximumRowCount(100);
        grafikaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = grafikaComboBox.getSelectedIndex();
                if (index >= 0 && index < aktualneRozdzielczosci.size()) {
                    Dimension rozdzielczosc = aktualneRozdzielczosci.get(index);
                    GrafikaS = rozdzielczosc.width;
                    GrafikaW = rozdzielczosc.height;
                }
            }
        });
        grafika.add(grafikaComboBox);

        jakoscComboBox = new JComboBox<String>(new String[]{"Najlepsza"});
        stylComboBox(jakoscComboBox);
        grafika.add(jakoscComboBox);

        Graj = new JButton("GRAJ");
        Graj.setFocusable(false);
        Graj.setBorder(null);
        Graj.setForeground(Color.WHITE);
        Graj.setFont(new Font("Bahnschift", Font.BOLD, 30));
        Graj.setBorder(new EmptyBorder(10, 10, 10, 10));
        Graj.setBackground(new Color(10, 10, 10));
        Graj.setContentAreaFilled(false);
        Graj.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                uruchomWybranaGre();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Graj.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Graj.setForeground(Color.WHITE);
            }
        });
        grafika.add(Graj);

        FalseIcon = new ImageIcon(new ImageIcon("lib\\False.png").getImage().getScaledInstance(Math.max(12, Szerokosc / 32), Math.max(12, Szerokosc / 32), Image.SCALE_DEFAULT));
        TrueIcon = new ImageIcon(new ImageIcon("lib\\True.png").getImage().getScaledInstance(Math.max(12, Szerokosc / 32), Math.max(12, Szerokosc / 32), Image.SCALE_DEFAULT));

        PelnyEkran = new JCheckBox("FullScreen");
        stylCheckBox(PelnyEkran);
        PelnyEkran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grafikaComboBox.setEnabled(!PelnyEkran.isSelected());
                przeladujRozdzielczosci();
            }
        });
        grafika.add(PelnyEkran);

        WczytanieZapisu = new JCheckBox("Wczytac Ustawienia");
        stylCheckBox(WczytanieZapisu);
        WczytanieZapisu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (WczytanieZapisu.isSelected()) {
                    wczytajUstawieniaDoLaunchera();
                }
            }
        });
        grafika.add(WczytanieZapisu);

        przeladujRozdzielczosci();
    }

    private void zbudujPanelSterowania() {
        sterowanie = new JPanel();
        sterowanie.setBackground(new Color(10, 10, 10));
        sterowanie.setBounds((Szerokosc / 19) * 10, Wysokosc / 10, (Szerokosc / 83) * 40, Wysokosc / 2 + Wysokosc / 3 + Wysokosc / 30);
        sterowanie.setLayout(null);
        sterowanie.setBorder(border);

        JLabel tytul = new JLabel("SNAKE", JLabel.CENTER);
        tytul.setBounds(0, 20, sterowanie.getWidth(), 70);
        tytul.setForeground(Color.ORANGE);
        tytul.setFont(new Font("Biting My Nails", Font.BOLD, Math.max(34, Szerokosc / 18)));
        sterowanie.add(tytul);

        JLabel info = new JLabel("Stary launcher, nowy kregoslup.", JLabel.CENTER);
        info.setBounds(0, 100, sterowanie.getWidth(), 34);
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Bahnschift", Font.BOLD, 20));
        sterowanie.add(info);

        JTextArea opis = new JTextArea(
                "Gra dalej wybiera monitor i gre z listy, ale wizualnie wraca do starego stylu. " +
                        "Snake jest rysowany na siatce " + SnakeGrid.COLUMNS + "x" + SnakeGrid.ROWS + ", wiec rozdzielczosc okna nie psuje online."
        );
        opis.setBounds(30, 150, sterowanie.getWidth() - 60, 150);
        opis.setForeground(new Color(220, 220, 220));
        opis.setBackground(new Color(10, 10, 10));
        opis.setFont(new Font("Bahnschift", Font.BOLD, 18));
        opis.setLineWrap(true);
        opis.setWrapStyleWord(true);
        opis.setEditable(false);
        opis.setFocusable(false);
        sterowanie.add(opis);
    }

    private void rysujPodglad(Graphics2D g) {
        g.setColor(new Color(10, 10, 10));
        g.fillRect(0, 0, obraz.getWidth(), obraz.getHeight());

        g.setColor(Color.ORANGE);
        g.setFont(new Font("Bahnschift", Font.BOLD, Math.max(22, obraz.getWidth() / 10)));
        g.drawString("SNAKE", obraz.getWidth() / 12, obraz.getHeight() / 5);

        int previewCell = Math.max(4, Math.min((obraz.getWidth() - 60) / SnakeGrid.COLUMNS, (obraz.getHeight() - 110) / SnakeGrid.ROWS));
        int boardW = previewCell * SnakeGrid.COLUMNS;
        int boardH = previewCell * SnakeGrid.ROWS;
        int startX = (obraz.getWidth() - boardW) / 2;
        int startY = obraz.getHeight() / 2 - boardH / 2 + 25;

        g.setColor(new Color(23, 45, 64));
        g.fillRect(startX, startY, boardW, boardH);
        g.setColor(Color.ORANGE);
        g.drawRect(startX, startY, boardW, boardH);

        g.setColor(Color.PINK);
        g.fillRect(startX + 20 * previewCell, startY + 14 * previewCell, previewCell, previewCell);
        g.setColor(Color.WHITE);
        for (int i = 1; i < 6; i++) {
            g.fillRect(startX + (20 - i) * previewCell, startY + 14 * previewCell, previewCell, previewCell);
        }
        g.setColor(Color.ORANGE);
        g.fillOval(startX + 29 * previewCell, startY + 10 * previewCell, previewCell, previewCell);
    }

    private void stylComboBox(JComboBox<?> comboBox) {
        comboBox.setFocusable(false);
        comboBox.setBorder(null);
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("Bahnschift", Font.BOLD, 24));
        comboBox.setBorder(new EmptyBorder(8, 10, 8, 10));
        comboBox.setBackground(new Color(10, 10, 10));
        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▲");
                button.setBorder(null);
                button.setForeground(Color.WHITE);
                button.setFont(czcionka2);
                button.setContentAreaFilled(false);
                return button;
            }
        });
    }

    private void stylCheckBox(JCheckBox checkBox) {
        checkBox.setFocusable(false);
        checkBox.setBorder(null);
        checkBox.setForeground(Color.WHITE);
        checkBox.setFont(new Font("Bahnschift", Font.BOLD, 20));
        checkBox.setBorder(new EmptyBorder(10, 10, 10, 10));
        checkBox.setBackground(new Color(10, 10, 10));
        checkBox.setContentAreaFilled(false);
        if (FalseIcon != null && TrueIcon != null) {
            checkBox.setIcon(FalseIcon);
            checkBox.setSelectedIcon(TrueIcon);
        }
    }

    private void przeladujRozdzielczosci() {
        if (monitorComboBox == null || grafikaComboBox == null) {
            return;
        }

        MonitorOption monitor = pobierzWybranyMonitor();
        aktualneRozdzielczosci = monitor.pobierzRozdzielczosci();

        grafikaComboBox.removeAllItems();

        for (Dimension rozdzielczosc : aktualneRozdzielczosci) {
            grafikaComboBox.addItem(rozdzielczosc.width + " × " + rozdzielczosc.height);
        }

        if (PelnyEkran != null && PelnyEkran.isSelected()) {
            GrafikaS = monitor.getBounds().width;
            GrafikaW = monitor.getBounds().height;
            grafikaComboBox.setEnabled(false);
        } else {
            grafikaComboBox.setEnabled(true);
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
            grafikaComboBox.setSelectedIndex(bestIndex);
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
        Object selected = graComboBox.getSelectedItem();
        if (selected instanceof GameEntry) {
            return (GameEntry) selected;
        }

        return gry.get(0);
    }

    private void wczytajUstawieniaDoLaunchera() {
        LauncherConfig config = LauncherConfig.load();
        if (config == null || graComboBox == null || monitorComboBox == null || PelnyEkran == null) {
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
                graComboBox.setSelectedIndex(i);
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
                grafikaComboBox.setSelectedIndex(i);
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
            JOptionPane.showMessageDialog(ramka, "Ta gra nie ma jeszcze implementacji.");
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
