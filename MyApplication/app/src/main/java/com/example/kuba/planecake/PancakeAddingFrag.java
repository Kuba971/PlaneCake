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
    private ReadMessage readMessage = new ReadMessage();
    private StartNetwork network = new StartNetwork();
    private Socket mySocket;
    private Context c;

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

            //Action effectuée lorsque le user clique sur le bouton de validation du réappro
            public void onClick(View v) {
                String command = null;
                String qte = quantite.getText().toString();
                String tp = type.getText().toString();

                //Pour informer le user qu'il y a un ou plusieurs champs de saisie vides
                if (qte.matches("") || tp.matches("")) {
                    infoToast("Veuillez compléter tout les champs");

                } else {
                    //On vérifie maintenant que la quantité saisie est bien un entier
                    //sinon on informe le user avec un toast
                    if (isInteger(qte)) {

                        //on construit la commande de sorte qu'elle soit reconnue par le serveur
                        command = ADD_COMMAND + " " + qte + " " + tp;

                        network.execute(command);
                        readMessage.execute();
                        quantite.setText("");
                        type.setText("");
                        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                        stockFrag = new StockFragment();
                        transaction.replace(R.id.frameLayoutFragment, stockFrag);
                        transaction.commit();
                    }else{
                        infoToast("La quantité doit être un entier (ex: 4)");
                    }
                }
            }
        });
        return v;
    }


    //Renvoie true si str est un entier, sinon false
    private boolean isInteger(String qte) {
        try {
            int i = Integer.parseInt(qte);
        }
        catch (NumberFormatException nfe){
            return false;
        }
        return true;
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


    // La méthode replace de FragmentTransaction appelle la méthode onPause du fragment
    // On veut que la connexion soit interrompue à ce moment là donc on surchage onPause
    @Override
    public void onPause(){
        if (network.getStatus() == AsyncTask.Status.FINISHED) {
            readMessage.cancel(true);
            network.cancel(true);
            if (!mySocket.isClosed()) {
                try {
                    mySocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onPause();
    }


    //Surcharge de la methode onAttach pour récupérer le context de l'activité parente
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        c = activity.getApplicationContext();
    }

    //Permet d'afficher le résultat de l'ajout effectué par le user sous forme de toast
    private void infoToast(String str) {
        Toast toast = Toast.makeText(c, str, Toast.LENGTH_SHORT);
        toast.show();
    }

}
