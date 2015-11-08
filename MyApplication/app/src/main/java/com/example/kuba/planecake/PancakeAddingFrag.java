package com.example.kuba.planecake;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * A simple {@link Fragment} subclass.
 */
public class PancakeAddingFrag extends Fragment {


    private PrintWriter writer = new PrintWriter(System.out, true);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    ReadMessages readMessages = new ReadMessages();
    StartNetwork network = new StartNetwork();
    Socket mySocket;

    private EditText quantite;
    private EditText type;

    public final static String ADD_COMMAND = "AJOUT";

    public PancakeAddingFrag() {
        // Required empty public constructor
    }

    private class StartNetwork extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... v) {
            System.out.println("StartNetwork.doInBackground from AddingFrag");
            String name = "10.0.2.2";
            int port = 7777;
            try {
                mySocket = new Socket(name, port);
                writer = new PrintWriter(mySocket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                writer.print(ADD_COMMAND);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //network.execute();
        //readMessages.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_pancake_adding, container, false);
        quantite = (EditText) v.findViewById(R.id.quantite);
        type = (EditText) v.findViewById(R.id.type);
        Button validationButton = (Button) v.findViewById(R.id.valid);
        validationButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String command = null;
                network.execute();
                if (quantite == null || type == null) {
                    infoToast("Veuillez compléter tout les champs");
                } else {
                    command += " " + quantite.toString() + " " + type.toString();
                }
                writer.println(command);
                readMessages.execute();

            }
        });
        return v;
    }

    private class ReadMessages extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... v) {
            String message = null;
            try {
                message = reader.readLine();
                publishProgress(message);
                System.out.println(message);
            } catch (IOException e) {
                System.out.println(e);
            }
            if (message != null) {
                infoToast(message);
            }
            return null;
        }
    }


    private void infoToast(String str) {
        Toast toast = Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT);
        toast.show();
    }
}
