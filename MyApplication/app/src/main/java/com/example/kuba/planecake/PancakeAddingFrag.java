package com.example.kuba.planecake;



import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
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
    private StockFragment stockFrag;
    ReadMessage readMessage = new ReadMessage();
    StartNetwork network = new StartNetwork();
    Socket mySocket;
    Context c;

    private EditText quantite;
    private EditText type;

    public static String ADD_COMMAND = "AJOUT";

    public PancakeAddingFrag() {
        // Required empty public constructor
    }

    private class StartNetwork extends AsyncTask<String, Void, Boolean> {

            @Override
            protected Boolean doInBackground (String... str){
                System.out.println("StartNetwork.doInBackground from AddingFrag, str = " + str[0]);
                String name = "10.0.2.2";
                int port = 7777;
                try {
                    mySocket = new Socket(name, port);
                    writer = new PrintWriter(mySocket.getOutputStream(), true);
                    reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                    writer.println(str[0]);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                String qte = quantite.getText().toString();
                String tp = type.getText().toString();
                if (qte.matches("") || tp.matches("")) {
                    infoToast("Veuillez compléter tout les champs");
                } else {
                    command = ADD_COMMAND + " " + qte + " " + tp;
                    network.execute(command);
                    readMessage.execute();
                    quantite.setText("");
                    type.setText("");
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    stockFrag = new StockFragment();
                    transaction.replace(R.id.frameLayoutFragment, stockFrag);
                    transaction.commit();
                }


            }
        });
        return v;
    }

    private class ReadMessage extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... v) {
            String message;
            try {
                message = reader.readLine();
                publishProgress(message);
                System.out.println(message);
            } catch (IOException e) {
                System.out.println(e);
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String... messages) {
            infoToast(messages[0]);
        }
    }

    public void onPause(){
        readMessage.cancel(true);
        network.cancel(true);
        if(!mySocket.isClosed()){
            try {
                mySocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        c = activity.getApplicationContext();
    }


    private void infoToast(String str) {
        Toast toast = Toast.makeText(c, str, Toast.LENGTH_SHORT);
        toast.show();
    }

}
