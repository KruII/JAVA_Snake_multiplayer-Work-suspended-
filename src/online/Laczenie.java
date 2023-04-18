package online;

import java.net.*;

import main.App;
import main.Gra;
import main.Gramainwykonanie;
import main.Zwyciestwo;

import java.io.*;

public class Laczenie extends Thread {

	public static boolean klientonline;
	String dane;
	public static int dlugoscweza;
    public static final int xweza[]=new int[Gra.MaxPunkty],
                            yweza[] = new int[Gra.MaxPunkty];
    
    
    @Override
    public void run() {
        try (Socket s = new Socket("localhost", 4999)) {
			Gra.opcja=false;
            Gra.klient=true;
            InputStreamReader in = new InputStreamReader(s.getInputStream());
			BufferedReader bf = new BufferedReader(in);
			PrintWriter pr = new PrintWriter(s.getOutputStream());
			
            pr.println(String.valueOf(klientonline));
			pr.flush();
			Gra.hosthostowanie= Boolean.valueOf(bf.readLine());
            if(Gra.hosthostowanie){
                Gra.panelMulti.setVisible(false);
                Gra.panelGry.setVisible(true);
                Gra.StartGry=true;
                Gra.Wysokosc= Integer.valueOf(bf.readLine());
                Gra.Szerokosc= Integer.valueOf(bf.readLine());
                Gramainwykonanie.x[0]=Gra.Szerokosc/4+Gra.Szerokosc/2;}

			while(Gra.hosthostowanie){
                pr.println(String.valueOf(Gra.pauza));
				pr.flush();
				Gra.pauzaserwera = Boolean.valueOf(bf.readLine());
				if(Gra.pauzaserwera && !Gra.pauza){
					Gra.panelGry.setVisible(false);
				}else if(Gra.pauza){
					Gra.panelGry.setVisible(false);
				}else if(Gra.zwyciestwo){
					Gra.panelGry.setVisible(false);
				}else{
					Gra.panelGry.setVisible(true);
				}
				pr.println(String.valueOf(App.CZESCICIALA_WESZA));
				pr.flush();
				dlugoscweza = Integer.valueOf(bf.readLine());
				for(int i=0; i<App.CZESCICIALA_WESZA;i++){
					pr.println(String.valueOf(Gramainwykonanie.x[i]));
					pr.flush();
					pr.println(String.valueOf(Gramainwykonanie.y[i]));
					pr.flush();}
                for(int i=0; i<dlugoscweza; i++){
                    xweza[i] =Integer.valueOf(bf.readLine());
                    yweza[i] =Integer.valueOf(bf.readLine());
                    for(int i1 = dlugoscweza;i1>0;i1--){
                        if((Gramainwykonanie.x[0] == xweza[i1])&& (Gramainwykonanie.y[0] == yweza[i1])) {
                            Gramainwykonanie.dzialanie = false;
                        }
                    }
                }
                pr.println(String.valueOf(!Gramainwykonanie.dzialanie));
				pr.flush();
				Gra.zwyciestwo = Boolean.valueOf(bf.readLine());
				if(Gra.zwyciestwo){
					Gra.brakruchu=true;
					Gra.panelGry.setVisible(false);
					Gra.panelZwyciestwo.setVisible(true);
				}
			}
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
