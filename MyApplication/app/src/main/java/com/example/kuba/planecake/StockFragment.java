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

    private String toDisplay ="";
    private TextView display;
    private ScrollView scroll;


    public final static String END = "FINLISTE";
    public final static String QUANTITY = "QUANTITE";


    ReadMessages readMessages = new ReadMessages();


    public StockFragment() {
    }

    private class StartNetwork extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... v) {
            System.out.println("StartNetwork.doInBackground");
            String name = "10.0.2.2";
            int port = 7777;
            try {
                Socket mySocket = new Socket(name, port);
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
        toDisplay += message;
        display.setText(toDisplay);
        scroll.fullScroll(View.FOCUS_DOWN);
    }

    private class ReadMessages extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... v) {
            String message;
            do{
                try {
                    message = reader.readLine();
                    publishProgress(message);
                    System.out.println(message);
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
        new StartNetwork().execute();
        readMessages.execute();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stock, container, false);
        display = (TextView) v.findViewById(R.id.display);
        scroll = (ScrollView) v.findViewById(R.id.scroll);
        return v;
    }


}
