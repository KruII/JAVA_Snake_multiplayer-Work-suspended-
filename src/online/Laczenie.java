package online;

import java.net.*;

import main.App;
import main.Gra;
import main.Gramainwykonanie;
import main.SnakeGrid;

import java.io.*;

public class Laczenie extends Thread {

    public static boolean klientonline;
    String dane;
    public static int dlugoscweza;
    public static final int xweza[] = new int[Gra.MaxPunkty],
            yweza[] = new int[Gra.MaxPunkty];

    @Override
    public void run() {
        try (Socket s = new Socket("localhost", 4999)) {
            Gra.opcja = false;
            Gra.klient = true;
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            PrintWriter pr = new PrintWriter(s.getOutputStream());

            pr.println(String.valueOf(klientonline));
            pr.flush();
            Gra.hosthostowanie = Boolean.valueOf(bf.readLine());

            if (Gra.hosthostowanie) {
                Gra.panelMulti.setVisible(false);
                Gra.panelGry.setVisible(true);
                Gra.StartGry = true;

                int remoteRows = Integer.valueOf(bf.readLine());
                int remoteColumns = Integer.valueOf(bf.readLine());

                if (remoteRows != SnakeGrid.ROWS || remoteColumns != SnakeGrid.COLUMNS) {
                    System.out.println("Host ma inny rozmiar logicznej planszy: " + remoteColumns + "x" + remoteRows);
                }

                // Klient nie przejmuje rozdzielczości hosta. Gra działa w komórkach siatki.
                Gramainwykonanie.ustawPozycjeStartowa((SnakeGrid.COLUMNS / 4) * 3, SnakeGrid.ROWS / 2);
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
        } catch (IOException e) {
            e.printStackTrace();
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
