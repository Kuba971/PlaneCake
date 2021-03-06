package com.example.kuba.planecake;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class StockFragment extends Fragment {

    private PrintWriter writer = new PrintWriter(System.out, true);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private String stringToDisplay ="";
    private TextView display;
    private ScrollView scrollView;


    public final static String START_STR = "Quantité de chacun des plats :";
    public final static String END = "FINLISTE";
    public final static String QUANTITY = "QUANTITE";


    ReadMessages readMessages = new ReadMessages();
    StartNetwork networkActivity = new StartNetwork();
    Socket mySocket;

    public StockFragment() {

    }

    private class StartNetwork extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... v) {
            System.out.println("StartNetwork.doInBackground");
            String name = "10.0.2.2";
            int port = 7777;
            try {
                mySocket = new Socket(name, port);
                writer = new PrintWriter(mySocket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                writer.println(QUANTITY);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean b) {
            if (!b) {
                displayMessage("Could not connect to server\n");
            }
        }
    }

    private void displayMessage(String message) {
        stringToDisplay += message;
        display.setText(stringToDisplay);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    private class ReadMessages extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... v) {
            String message;
            do{
                try {
                    message = reader.readLine();

                    //La premiere et la derniere string renvoyées par le serveur ne nous intéresse pas
                    //On les affiche en console uniquement pour suivre l'avancement,
                    //ainsi seules les données pertinentes pour le user sont publiées
                    if(message.equalsIgnoreCase(START_STR) || message.equalsIgnoreCase(END)) {
                        System.out.println(message);
                    }else{
                        publishProgress(message);
                    }
                } catch (IOException e) {
                    break;
                }
            }while (message != END);
            return null;
        }
        @Override
        protected void onProgressUpdate(String... messages) {
            displayMessage(messages[0] + "\n");
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkActivity.execute();
        readMessages.execute();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stock, container, false);
        display = (TextView) v.findViewById(R.id.display);
        scrollView = (ScrollView) v.findViewById(R.id.scroll);
        return v;
    }

    public void onDestroy(){

        //On vérifie le status de la connexion pour eviter un crash à la destruction du fragment
        if(networkActivity.getStatus() == AsyncTask.Status.FINISHED) {
            readMessages.cancel(true);
            display.setText(" ");
            networkActivity.cancel(true);
            if (!mySocket.isClosed()) {
                try {
                    mySocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onDestroy();
    }
}
