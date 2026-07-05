package online;

import java.net.*;

import main.App;
import main.Gra;
import main.Gramainwykonanie;
import main.SnakeGrid;

import java.io.*;
import javax.swing.*;

public class Hostowanie extends Thread {

    String dane;
    public static int dlugoscweza;
    public static final int xweza[] = new int[Gra.MaxPunkty],
            yweza[] = new int[Gra.MaxPunkty];

    @Override
    public void run() {
        try (ServerSocket ss = new ServerSocket()) {
            ss.setReuseAddress(true);
            ss.bind(new InetSocketAddress(4999));

            Gra.opcja = false;
            Socket s = ss.accept();
            s.setTcpNoDelay(true);

            try (Socket socket = s) {
                InputStreamReader in = new InputStreamReader(socket.getInputStream());
                BufferedReader bf = new BufferedReader(in);
                PrintWriter pr = new PrintWriter(socket.getOutputStream());

                pr.println(String.valueOf(Gra.host));
                pr.flush();
                Gra.hosthostowanie = Boolean.valueOf(bf.readLine());

                if (Gra.hosthostowanie) {
                    Gra.panelMulti.setVisible(false);
                    Gra.panelGry.setVisible(true);
                    Gra.StartGry = true;

                    // Wysyłamy wymiary logicznej planszy, nie rozdzielczość okna.
                    // Dzięki temu host i klient mogą grać na różnych monitorach bez rozjazdu.
                    pr.println(String.valueOf(SnakeGrid.ROWS));
                    pr.flush();
                    pr.println(String.valueOf(SnakeGrid.COLUMNS));
                    pr.flush();

                    Gramainwykonanie.ustawPozycjeStartowa(SnakeGrid.COLUMNS / 4, SnakeGrid.ROWS / 2);
                }

                while (Gra.hosthostowanie) {
                    pr.println(String.valueOf(Gra.pauza));
                    pr.flush();
                    Gra.pauzaserwera = Boolean.valueOf(bf.readLine());

                    if (Gra.pauzaserwera && !Gra.pauza) {
                        Gra.panelGry.setVisible(false);
                    } else if (Gra.pauza) {
                        Gra.panelGry.setVisible(false);
                    } else if (Gra.zwyciestwo) {
                        Gra.panelGry.setVisible(false);
                    } else {
                        Gra.panelGry.setVisible(true);
                    }

                    pr.println(String.valueOf(App.CZESCICIALA_WESZA));
                    pr.flush();
                    dlugoscweza = Integer.valueOf(bf.readLine());

                    for (int i = 0; i < App.CZESCICIALA_WESZA; i++) {
                        pr.println(String.valueOf(Gramainwykonanie.x[i]));
                        pr.flush();
                        pr.println(String.valueOf(Gramainwykonanie.y[i]));
                        pr.flush();
                    }

                    for (int i = 0; i < dlugoscweza; i++) {
                        xweza[i] = Integer.valueOf(bf.readLine());
                        yweza[i] = Integer.valueOf(bf.readLine());
                    }

                    sprawdzKolizjeZDrugimWezem();

                    pr.println(String.valueOf(!Gramainwykonanie.dzialanie));
                    pr.flush();
                    Gra.zwyciestwo = Boolean.valueOf(bf.readLine());

                    if (Gra.zwyciestwo) {
                        Gra.brakruchu = true;
                        Gra.panelGry.setVisible(false);
                        Gra.panelZwyciestwo.setVisible(true);
                    }
                }
            }
        } catch (IOException e) {
            Gra.host = false;
            Gra.hosthostowanie = false;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null, "Nie udalo sie wystartowac hosta na porcie 4999.");
                }
            });
        }
    }

    private void sprawdzKolizjeZDrugimWezem() {
        for (int i = 0; i < dlugoscweza; i++) {
            if ((Gramainwykonanie.x[0] == xweza[i]) && (Gramainwykonanie.y[0] == yweza[i])) {
                Gramainwykonanie.dzialanie = false;
                return;
            }
        }
    }
}
