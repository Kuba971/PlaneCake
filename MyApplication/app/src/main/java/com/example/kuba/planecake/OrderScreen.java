package com.example.kuba.planecake;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class OrderScreen extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private OrderScreenFragment fragTable;
    private PancakeFragment fragPancake;
    private DrinkFragment fragDrink;
    private ConfirmFragment fragConfirm;


    private Button table;
    private Button pancake;
    private Button drink;
    private Button send;
    private int checkID;
    private int pancakeViewID;
    private int pancakeQuantityNumberID;
    private int drinkViewID;
    private int drinkQuantityNumberID;

    private static int order_number = 0;
    public ArrayList<String> order = new ArrayList<String>();
    public ArrayList<String> listPancake = new ArrayList<String>();

    private PrintWriter writer = new PrintWriter(System.out, true);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private ReadMessages readMessages;
    private StartNetwork network = new StartNetwork();
    Socket mySocket;


    public final static String QUANTITY = "QUANTITE";


    private class StartNetwork extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            System.out.println("StartNetwork.onPreExecute");
        }

        @Override
        protected Boolean doInBackground(Void... v) {
            System.out.println("StartNetwork.doInBackground");
            String name = "192.168.0.11";
            int port = 7777;
            try {
                mySocket = new Socket(name, port);
                writer = new PrintWriter(mySocket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if (b) {
                displayMessage("Connected to server\n");
                readMessages = new ReadMessages();
                readMessages.execute();
            } else {
                displayMessage("Could not connect to server\n");
            }
        }
    }


    private class ReadMessages extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... v) {
            while (!isCancelled()) {
                try {
                    String message = reader.readLine();
                        listPancake.add(message);
                    System.out.println("DISPALYYYYYYYYY2 ::::: " + message);
                    publishProgress(message);
                } catch (IOException e) {
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... messages) {
            displayMessage(messages[0] + "\n");
        }
    }

    private String displayMessage(String message) {

        return message;
    }

    protected void onStart() {
        super.onStart();
        network.execute();
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);
        table = (Button) findViewById(R.id.table);
        pancake = (Button) findViewById(R.id.pancake);
        drink = (Button) findViewById(R.id.drink);
        send = (Button) findViewById(R.id.send);
        pancake.setEnabled(false);
        drink.setEnabled(false);
        send.setEnabled(false);
        order.add("-------------- TABLE --------------");
        // Initialisation du gestionnaire de fragments
        fragmentManager = getFragmentManager();

        // On initialise les fragments, soit en récupérant ceux qui existent déjà, soit en les
        // créant sinon
        fragTable = initFragment(R.id.frameLayoutFragment);
    }

    public void ValidPancake(View v) {
        int index = 0;
        if (drink.isEnabled()) {
            for (String object : order) {
                if (object.toString() == "--------------- CREPES -----------------") {
                    index = order.indexOf(object);
                }
            }
                    int indexForRemove = index + 1;
                    while (order.get(indexForRemove).toString() != "--------------- BOISSONS -----------------") {
                        order.remove(indexForRemove);
                    }

            for (int i = 1; i < 6; i++) {
                String PancakeViewID = "PancakeView" + i;
                String PancakeQuantityNumberID = "PancakeQuantityNumber" + i;
                pancakeViewID = getResources().getIdentifier(PancakeViewID, "id", OrderScreen.this.getPackageName());
                pancakeQuantityNumberID = getResources().getIdentifier(PancakeQuantityNumberID, "id", OrderScreen.this.getPackageName());
                if (Integer.parseInt((((TextView) findViewById(pancakeQuantityNumberID)).getText().toString())) != 0) {
                    order.add(indexForRemove,"X " + (((TextView) findViewById(pancakeQuantityNumberID)).getText().toString()));
                    order.add(indexForRemove, ((TextView) findViewById(pancakeViewID)).getText().toString());
                    ((TextView) findViewById(pancakeQuantityNumberID)).setText("0");
                                    }
            }
        }
            else {
            order.add("--------------- CREPES -----------------");
            for (int i = 1; i < 6; i++) {
                String PancakeViewID = "PancakeView" + i;
                String PancakeQuantityNumberID = "PancakeQuantityNumber" + i;
                pancakeViewID = getResources().getIdentifier(PancakeViewID, "id", OrderScreen.this.getPackageName());
                pancakeQuantityNumberID = getResources().getIdentifier(PancakeQuantityNumberID, "id", OrderScreen.this.getPackageName());
                if (Integer.parseInt((((TextView) findViewById(pancakeQuantityNumberID)).getText().toString())) != 0) {
                    order.add(((TextView) findViewById(pancakeViewID)).getText().toString());
                    order.add("X " + (((TextView) findViewById(pancakeQuantityNumberID)).getText().toString()));
                   ((TextView) findViewById(pancakeQuantityNumberID)).setText("0");
                }
            }
        }

        fragDrink = initFragmentDrink(R.id.frameLayoutFragment3);

        FrameLayout frameOrder = (FrameLayout)findViewById(R.id.frameLayoutFragment);
        frameOrder.setVisibility(View.INVISIBLE);
        FrameLayout framePancake = (FrameLayout)findViewById(R.id.frameLayoutFragment2);
        framePancake.setVisibility(View.INVISIBLE);
        FrameLayout frameDrink = (FrameLayout)findViewById(R.id.frameLayoutFragment3);
        frameDrink.setVisibility(View.VISIBLE);
        FrameLayout frameConfirm = (FrameLayout)findViewById(R.id.frameLayoutFragment4);
        frameConfirm.setVisibility(View.INVISIBLE);


        drink.setEnabled(true);
    }

    public void ValidDrink(View v) {

        int index = 0;
        if (send.isEnabled()) {
            for (String object : order) {
                if (object.toString() == "--------------- BOISSONS -----------------") {
                    index = order.indexOf(object);
                }
            }
            int indexForRemove = index + 1;
            while (order.get(indexForRemove).toString() != "--------------- FIN -----------------") {
                order.remove(indexForRemove);
            }

            for (int i = 1; i < 6; i++) {
                String DrinkViewID = "DrinkView" + i;
                String DrinkQuantityNumberID = "DrinkQuantityNumber" + i;
                drinkViewID = getResources().getIdentifier(DrinkViewID, "id", OrderScreen.this.getPackageName());
                drinkQuantityNumberID = getResources().getIdentifier(DrinkQuantityNumberID, "id", OrderScreen.this.getPackageName());
                if (Integer.parseInt((((TextView) findViewById(drinkQuantityNumberID)).getText().toString())) != 0) {
                    order.add("X " + (((TextView) findViewById(drinkQuantityNumberID)).getText().toString()));
                    order.add(((TextView) findViewById(drinkViewID)).getText().toString());
                    ((TextView) findViewById(drinkQuantityNumberID)).setText("0");
                }
            }

        } else {
            order.add("--------------- BOISSONS -----------------");

            for (int i = 1; i < 6; i++) {
                String DrinkViewID = "DrinkView" + i;
                String DrinkQuantityNumberID = "DrinkQuantityNumber" + i;
                drinkViewID = getResources().getIdentifier(DrinkViewID, "id", OrderScreen.this.getPackageName());
                drinkQuantityNumberID = getResources().getIdentifier(DrinkQuantityNumberID, "id", OrderScreen.this.getPackageName());
                if (Integer.parseInt((((TextView) findViewById(drinkQuantityNumberID)).getText().toString())) != 0) {
                    order.add(((TextView) findViewById(drinkViewID)).getText().toString());
                    order.add("X " + (((TextView) findViewById(drinkQuantityNumberID)).getText().toString()));
                    ((TextView) findViewById(drinkQuantityNumberID)).setText("0");

                }
            }

            order.add("--------------- FIN -----------------");
        }

            fragConfirm = initFragmentConfirm(R.id.frameLayoutFragment4);

            FrameLayout frameOrder = (FrameLayout) findViewById(R.id.frameLayoutFragment);
            frameOrder.setVisibility(View.INVISIBLE);
            FrameLayout framePancake = (FrameLayout) findViewById(R.id.frameLayoutFragment2);
            framePancake.setVisibility(View.INVISIBLE);
            FrameLayout frameDrink = (FrameLayout) findViewById(R.id.frameLayoutFragment3);
            frameDrink.setVisibility(View.INVISIBLE);
            FrameLayout frameConfirm = (FrameLayout) findViewById(R.id.frameLayoutFragment4);
            frameConfirm.setVisibility(View.VISIBLE);

            send.setEnabled(true);

    }

    public void SendCommand(View v){
        int startOrder = 3;
            while (order.get(startOrder).toString() != "--------------- BOISSONS -----------------") {
                String[] parts = order.get(startOrder + 1).split(" ");
                int numberOfTime = Integer.parseInt(parts[1]);
                for (int y = 0; y < numberOfTime; y++) {
                    writer.println("COMMANDE " + order.get(startOrder));
                }
                startOrder= startOrder+2;
            }


        File file = new File("order_log.txt");

        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(file);

            // Writes bytes from the specified byte array to this file output stream
            for (String object : order) {
                fos.write(object.getBytes());
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        }
        catch (IOException ioe) {
            System.out.println("Exception while writing file " + ioe);
        }
        finally {
            // close the streams using close method
            try {
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }

        }

        String tableToRed = order.get(1).toString();
        String[] numberOfTable = tableToRed.split(" ");
        String nameOfCheckTable = "checkBox";
        nameOfCheckTable = nameOfCheckTable + numberOfTable[1];
        checkID = getResources().getIdentifier(nameOfCheckTable, "id", OrderScreen.this.getPackageName());
            ((CheckBox) findViewById(checkID)).setEnabled(false);
            ((CheckBox) findViewById(checkID)).setTextColor(Color.RED);
        ((CheckBox) findViewById(checkID)).setChecked(false);
        order.clear();
        order.add("-------------- TABLE --------------");
        assignTable(v);
        fragTable = initFragment(R.id.frameLayoutFragment);
        pancake.setEnabled(false);
        drink.setEnabled(false);
        send.setEnabled(false);
        for (int i=1; i < 10; i++){
            String checkBoxesID = "checkBox" + i ;
            checkID = getResources().getIdentifier(checkBoxesID, "id", OrderScreen.this.getPackageName());
            if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                ((CheckBox) findViewById(checkID)).setEnabled(true);
            }
        }

    }

    public void PancakeButtonMinus(View v) {
        switch(v.getId()) {
            case R.id.PancakeButtonMinus1:
                TextView stockView1 = (TextView) findViewById(R.id.PancakeStock1);
                int stock1 = Integer.parseInt(stockView1.getText().toString());
                TextView quantityView1 = (TextView) findViewById(R.id.PancakeQuantityNumber1);
                int quantity1 = Integer.parseInt(quantityView1.getText().toString());
                if (quantity1 > 0) {
                    quantity1--;
                    quantityView1.setText(quantity1+ "");
                    stock1++;
                    stockView1.setText(stock1 + "");
                    stockView1.setTextColor(Color.GRAY);
                }
                else
                {
                    quantityView1.setTextColor(Color.RED);
                }

                break;
            case R.id.PancakeButtonMinus2:
                TextView stockView2 = (TextView) findViewById(R.id.PancakeStock2);
                int stock2 = Integer.parseInt(stockView2.getText().toString());
                TextView quantityView2 = (TextView) findViewById(R.id.PancakeQuantityNumber2);
                int quantity2 = Integer.parseInt(quantityView2.getText().toString());
                if (quantity2 > 0) {
                    quantity2--;
                    quantityView2.setText(quantity2+ "");
                    stock2++;
                    stockView2.setText(stock2 + "");
                    stockView2.setTextColor(Color.GRAY);
                }
                else
                {
                    quantityView2.setTextColor(Color.RED);
                }

                break;
            case R.id.PancakeButtonMinus3:
                TextView stockView3 = (TextView) findViewById(R.id.PancakeStock3);
                int stock3 = Integer.parseInt(stockView3.getText().toString());
                TextView quantityView3 = (TextView) findViewById(R.id.PancakeQuantityNumber3);
                int quantity3 = Integer.parseInt(quantityView3.getText().toString());
                if (quantity3 > 0) {
                    quantity3--;
                    quantityView3.setText(quantity3+ "");
                    stock3++;
                    stockView3.setText(stock3 + "");
                    stockView3.setTextColor(Color.GRAY);
                }
                else
                {
                    quantityView3.setTextColor(Color.RED);
                }

                break;
            case R.id.PancakeButtonMinus4:
                TextView stockView4 = (TextView) findViewById(R.id.PancakeStock4);
                int stock4 = Integer.parseInt(stockView4.getText().toString());
                TextView quantityView4 = (TextView) findViewById(R.id.PancakeQuantityNumber4);
                int quantity4 = Integer.parseInt(quantityView4.getText().toString());
                if (quantity4 > 0) {
                    quantity4--;
                    quantityView4.setText(quantity4+ "");
                    stock4++;
                    stockView4.setText(stock4 + "");
                    stockView4.setTextColor(Color.GRAY);
                }
                else
                {
                    quantityView4.setTextColor(Color.RED);
                }

                break;
            case R.id.PancakeButtonMinus5:
                TextView stockView5 = (TextView) findViewById(R.id.PancakeStock5);
                int stock5 = Integer.parseInt(stockView5.getText().toString());
                TextView quantityView5 = (TextView) findViewById(R.id.PancakeQuantityNumber5);
                int quantity5 = Integer.parseInt(quantityView5.getText().toString());
                if (quantity5 > 0) {
                    quantity5--;
                    quantityView5.setText(quantity5+ "");
                    stock5++;
                    stockView5.setText(stock5 + "");
                    stockView5.setTextColor(Color.GRAY);
                }
                else
                {
                    quantityView5.setTextColor(Color.RED);
                }

                break;
        }
    }

    public void PancakeButtonPlus(View v) {
        switch(v.getId()) {
            case R.id.PancakeButtonPlus1:
                TextView stockView1 = (TextView) findViewById(R.id.PancakeStock1);
                int stock1 = Integer.parseInt(stockView1.getText().toString());
                TextView quantityView1 = (TextView) findViewById(R.id.PancakeQuantityNumber1);
                int quantity1 = Integer.parseInt(quantityView1.getText().toString());
                if (stock1 >= 1) {
                    quantity1++;
                    quantityView1.setText(quantity1+ "");
                    stock1--;
                    stockView1.setText(stock1 + "");
                    quantityView1.setTextColor(Color.GRAY);

                }
                else
                {
                    stockView1.setTextColor(Color.RED);
                }

                break;
            case R.id.PancakeButtonPlus2:
                TextView stockView2 = (TextView) findViewById(R.id.PancakeStock2);
                int stock2 = Integer.parseInt(stockView2.getText().toString());
                TextView quantityView2 = (TextView) findViewById(R.id.PancakeQuantityNumber2);
                int quantity2 = Integer.parseInt(quantityView2.getText().toString());
                if (stock2 >= 1) {
                    quantity2++;
                    quantityView2.setText(quantity2+ "");
                    stock2--;
                    stockView2.setText(stock2 + "");
                    quantityView2.setTextColor(Color.GRAY);

                }
                else
                {
                    stockView2.setTextColor(Color.RED);

                }

                break;
            case R.id.PancakeButtonPlus3:
                TextView stockView3 = (TextView) findViewById(R.id.PancakeStock3);
                int stock3 = Integer.parseInt(stockView3.getText().toString());
                TextView quantityView3 = (TextView) findViewById(R.id.PancakeQuantityNumber3);
                int quantity3 = Integer.parseInt(quantityView3.getText().toString());
                if (stock3 >= 1) {
                    quantity3++;
                    quantityView3.setText(quantity3+ "");
                    stock3--;
                    stockView3.setText(stock3 + "");
                    quantityView3.setTextColor(Color.GRAY);

                }
                else
                {
                    stockView3.setTextColor(Color.RED);

                }

                break;
            case R.id.PancakeButtonPlus4:
                TextView stockView4 = (TextView) findViewById(R.id.PancakeStock4);
                int stock4 = Integer.parseInt(stockView4.getText().toString());
                TextView quantityView4 = (TextView) findViewById(R.id.PancakeQuantityNumber4);
                int quantity4 = Integer.parseInt(quantityView4.getText().toString());
                if (stock4 >= 1) {
                    quantity4++;
                    quantityView4.setText(quantity4+ "");
                    stock4--;
                    stockView4.setText(stock4 + "");
                    quantityView4.setTextColor(Color.GRAY);

                }
                else
                {
                    stockView4.setTextColor(Color.RED);

                }

                break;
            case R.id.PancakeButtonPlus5:
                TextView stockView5 = (TextView) findViewById(R.id.PancakeStock5);
                int stock5 = Integer.parseInt(stockView5.getText().toString());
                TextView quantityView5 = (TextView) findViewById(R.id.PancakeQuantityNumber5);
                int quantity5 = Integer.parseInt(quantityView5.getText().toString());
                if (stock5>= 1) {
                    quantity5++;
                    quantityView5.setText(quantity5+ "");
                    stock5--;
                    stockView5.setText(stock5 + "");
                    quantityView5.setTextColor(Color.GRAY);

                }
                else
                {
                    stockView5.setTextColor(Color.RED);

                }

                break;
        }
    }

    public void DrinkButtonMinus(View v) {
        switch(v.getId()) {
            case R.id.DrinkButtonMinus1:
                TextView quantityView1 = (TextView) findViewById(R.id.DrinkQuantityNumber1);
                int quantity1 = Integer.parseInt(quantityView1.getText().toString());
                if (quantity1 > 0) {
                    quantity1--;
                    quantityView1.setText(quantity1 + "");
                }
                else
                {
                    quantityView1.setTextColor(Color.RED);
                }

                break;
            case R.id.DrinkButtonMinus2:
                TextView quantityView2 = (TextView) findViewById(R.id.DrinkQuantityNumber2);
                int quantity2 = Integer.parseInt(quantityView2.getText().toString());
                if (quantity2 > 0) {
                    quantity2--;
                    quantityView2.setText(quantity2 + "");
                }
                else
                {
                    quantityView2.setTextColor(Color.RED);
                }

                break;
            case R.id.DrinkButtonMinus3:
                TextView quantityView3 = (TextView) findViewById(R.id.DrinkQuantityNumber3);
                int quantity3 = Integer.parseInt(quantityView3.getText().toString());
                if (quantity3 > 0) {
                    quantity3--;
                    quantityView3.setText(quantity3 + "");
                }
                else
                {
                    quantityView3.setTextColor(Color.RED);
                }

                break;
            case R.id.DrinkButtonMinus4:
                TextView quantityView4 = (TextView) findViewById(R.id.DrinkQuantityNumber4);
                int quantity4 = Integer.parseInt(quantityView4.getText().toString());
                if (quantity4 > 0) {
                    quantity4--;
                    quantityView4.setText(quantity4 + "");
                }
                else
                {
                    quantityView4.setTextColor(Color.RED);
                }

                break;
            case R.id.DrinkButtonMinus5:
                TextView quantityView5 = (TextView) findViewById(R.id.DrinkQuantityNumber5);
                int quantity5 = Integer.parseInt(quantityView5.getText().toString());
                if (quantity5 > 0) {
                    quantity5--;
                    quantityView5.setText(quantity5 + "");
                }
                else
                {
                    quantityView5.setTextColor(Color.RED);
                }

                break;
        }
    }

    public void DrinkButtonPlus(View v) {
        switch(v.getId()) {
            case R.id.DrinkButtonPlus1:
                TextView quantityView1 = (TextView) findViewById(R.id.DrinkQuantityNumber1);
                int quantity1 = Integer.parseInt(quantityView1.getText().toString());
                    quantity1++;
                    quantityView1.setText(quantity1+ "");
                    quantityView1.setTextColor(Color.GRAY);

                break;

            case R.id.DrinkButtonPlus2:
                TextView quantityView2 = (TextView) findViewById(R.id.DrinkQuantityNumber2);
                int quantity2 = Integer.parseInt(quantityView2.getText().toString());
                    quantity2++;
                    quantityView2.setText(quantity2+ "");
                    quantityView2.setTextColor(Color.GRAY);


                break;
            case R.id.DrinkButtonPlus3:
                TextView quantityView3 = (TextView) findViewById(R.id.DrinkQuantityNumber3);
                int quantity3 = Integer.parseInt(quantityView3.getText().toString());
                    quantity3++;
                    quantityView3.setText(quantity3+ "");
                    quantityView3.setTextColor(Color.GRAY);


                break;
            case R.id.DrinkButtonPlus4:
                TextView quantityView4 = (TextView) findViewById(R.id.DrinkQuantityNumber4);
                int quantity4 = Integer.parseInt(quantityView4.getText().toString());
                    quantity4++;
                    quantityView4.setText(quantity4+ "");
                    quantityView4.setTextColor(Color.GRAY);


                break;
            case R.id.DrinkButtonPlus5:
                TextView quantityView5 = (TextView) findViewById(R.id.DrinkQuantityNumber5);
                int quantity5 = Integer.parseInt(quantityView5.getText().toString());
                    quantity5++;
                    quantityView5.setText(quantity5+ "");
                    quantityView5.setTextColor(Color.GRAY);

                break;
        }
    }
//
//    Handler h = new Handler();
//    Runnable runnable = new Runnable() {
//        public void run() {
//            RefreshQuantity();
//        }
//    };
//
//        public void RefreshQuantity()
//        {
//            writer.println("QUANTITE");
//            h.postDelayed(runnable, 5000);
//        }

    public void assignTable(View v){
        FrameLayout framePancake = (FrameLayout)findViewById(R.id.frameLayoutFragment2);
        framePancake.setVisibility(View.INVISIBLE);
        FrameLayout frameOrder = (FrameLayout)findViewById(R.id.frameLayoutFragment);
        frameOrder.setVisibility(View.VISIBLE);
        FrameLayout frameDrink = (FrameLayout)findViewById(R.id.frameLayoutFragment3);
        frameDrink.setVisibility(View.INVISIBLE);
        FrameLayout frameConfirm = (FrameLayout)findViewById(R.id.frameLayoutFragment4);
        frameConfirm.setVisibility(View.INVISIBLE);
    }

    public void pancakeOrder(View v){
        writer.println("QUANTITE");
        fragPancake = initFragmentPancake(R.id.frameLayoutFragment2);
        FrameLayout frameOrder = (FrameLayout)findViewById(R.id.frameLayoutFragment);
        frameOrder.setVisibility(View.INVISIBLE);
        FrameLayout framePancake = (FrameLayout)findViewById(R.id.frameLayoutFragment2);
        framePancake.setVisibility(View.VISIBLE);
        FrameLayout frameDrink = (FrameLayout)findViewById(R.id.frameLayoutFragment3);
        frameDrink.setVisibility(View.INVISIBLE);
        FrameLayout frameConfirm = (FrameLayout)findViewById(R.id.frameLayoutFragment4);
        frameConfirm.setVisibility(View.INVISIBLE);


    }

    public void drinkOrder(View v){
        fragDrink = initFragmentDrink(R.id.frameLayoutFragment3);
        FrameLayout frameOrder = (FrameLayout)findViewById(R.id.frameLayoutFragment);
        frameOrder.setVisibility(View.INVISIBLE);
        FrameLayout framePancake = (FrameLayout)findViewById(R.id.frameLayoutFragment2);
        framePancake.setVisibility(View.INVISIBLE);
        FrameLayout frameDrink = (FrameLayout)findViewById(R.id.frameLayoutFragment3);
        frameDrink.setVisibility(View.VISIBLE);
        FrameLayout frameConfirm = (FrameLayout)findViewById(R.id.frameLayoutFragment4);
        frameConfirm.setVisibility(View.INVISIBLE);

    }

    public void sendToKitchen(View v){
        fragConfirm = initFragmentConfirm(R.id.frameLayoutFragment4);
        FrameLayout frameOrder = (FrameLayout)findViewById(R.id.frameLayoutFragment);
        frameOrder.setVisibility(View.INVISIBLE);
        FrameLayout framePancake = (FrameLayout)findViewById(R.id.frameLayoutFragment2);
        framePancake.setVisibility(View.INVISIBLE);
        FrameLayout frameDrink = (FrameLayout)findViewById(R.id.frameLayoutFragment3);
        frameDrink.setVisibility(View.INVISIBLE);
        FrameLayout frameConfirm = (FrameLayout)findViewById(R.id.frameLayoutFragment4);
        frameConfirm.setVisibility(View.VISIBLE);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_screen, menu);
        return true;
    }

    @Override
    protected void onPause() {
        // On attache tous les fragments pour conserver les données
        attacher(fragTable, R.id.frameLayoutFragment);
       // attacher(fragPancake, R.id.frameLayoutFragment2);
        // On appelle la méthode de la super-classe
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private OrderScreenFragment initFragment(int id) {
        // On récupère le fragment, ou null s'il n'existe pas encore
        OrderScreenFragment fragment = (OrderScreenFragment) fragmentManager.findFragmentById(id);

        // S'il n'existe pas, on le crée et on l'attache
        if (fragment == null) {
            fragment = new OrderScreenFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(id, fragment);
            transaction.commit();
        }

        return fragment;
    }

    private PancakeFragment initFragmentPancake(int id) {
        // On récupère le fragment, ou null s'il n'existe pas encore
        PancakeFragment fragment = (PancakeFragment) fragmentManager.findFragmentById(id);

        // S'il n'existe pas, on le crée et on l'attache
        if (fragment == null) {
            fragment = new PancakeFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(id, fragment);
            transaction.commit();
        }

        return fragment;
    }

    private DrinkFragment initFragmentDrink(int id) {
        // On récupère le fragment, ou null s'il n'existe pas encore
        DrinkFragment fragment = (DrinkFragment) fragmentManager.findFragmentById(id);

        // S'il n'existe pas, on le crée et on l'attache
        if (fragment == null) {
            fragment = new DrinkFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(id, fragment);
            transaction.commit();
        }

        return fragment;
    }

    private ConfirmFragment initFragmentConfirm(int id) {
        // On récupère le fragment, ou null s'il n'existe pas encore
        ConfirmFragment fragment = (ConfirmFragment) fragmentManager.findFragmentById(id);

        // S'il n'existe pas, on le crée et on l'attache
        if (fragment == null) {
            fragment = new ConfirmFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(id, fragment);
            transaction.commit();
        }

        return fragment;
    }

    // Cette méthode sert à attacher un fragment :
    // - s'il n'est pas attaché, on l'attache et on renvoie true
    // - sinon, on n'a rien à faire et on renvoie false
    private boolean attacher(Fragment f, int id) {
        if (f.isAdded()) {
            return false;
        } else {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(id, f);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
            return true;
        }
    }

    // Cette méthode détache un fragment s'il est attaché, et l'attache sinon
    public void attacherDetacher(Fragment f, int id) {
        if (!attacher(f, id)) {
            // Si on n'a pas eu à l'attacher, c'est qu'il faut le détacher
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(f);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("ChatActivity.onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("ChatActivity.onRestart");
    }

    @Override
    protected void onStop() {
        System.out.println("ChatActivity.onStop");
        super.onStop();
    }

    @Override
    public void onDestroy(){
        readMessages.cancel(true);
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

    public void onCheckboxClicked(View view) {
        order_number++;

        // Is the view now checked?
        boolean checked = ((CheckBox) view) .isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.checkBox1:
                if (checked) {
                    order.add(((CheckBox) findViewById(R.id.checkBox1)).getText().toString());
                    ((CheckBox) findViewById(R.id.checkBox2)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox3)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox4)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox5)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox6)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox7)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox8)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox9)).setEnabled(false);
                    pancake.setEnabled(true);
                }
                else{
                    int last = order.size();
                    order.remove(last-1);
                    for (int i=1; i < 10; i++){
                        String checkBoxesID = "checkBox" + i ;
                        checkID = getResources().getIdentifier(checkBoxesID, "id", OrderScreen.this.getPackageName());
                        if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                            ((CheckBox) findViewById(checkID)).setEnabled(true);
                        }
                    }
                    pancake.setEnabled(false);
                }
                break;
            case R.id.checkBox2:
                if (checked) {
                    order.add(((CheckBox) findViewById(R.id.checkBox2)).getText().toString());
                    ((CheckBox) findViewById(R.id.checkBox1)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox3)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox4)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox5)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox6)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox7)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox8)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox9)).setEnabled(false);
                    pancake.setEnabled(true);
                }
                else{
                    int last = order.size();
                    order.remove(last-1);
                    for (int i=1; i < 10; i++){
                        String checkBoxesID = "checkBox" + i ;
                        checkID = getResources().getIdentifier(checkBoxesID, "id", OrderScreen.this.getPackageName());
                        if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                            ((CheckBox) findViewById(checkID)).setEnabled(true);
                        }
                    }
                    pancake.setEnabled(false);
                }
                break;
            case R.id.checkBox3:
                if (checked) {
                    order.add(((CheckBox) findViewById(R.id.checkBox3)).getText().toString());
                    ((CheckBox) findViewById(R.id.checkBox2)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox1)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox4)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox5)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox6)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox7)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox8)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox9)).setEnabled(false);
                    pancake.setEnabled(true);
                }
                else{
                    int last = order.size();
                    order.remove(last-1);
                    for (int i=1; i < 10; i++){
                        String checkBoxesID = "checkBox" + i ;
                        checkID = getResources().getIdentifier(checkBoxesID, "id", OrderScreen.this.getPackageName());
                        if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                            ((CheckBox) findViewById(checkID)).setEnabled(true);
                        }
                    }
                    pancake.setEnabled(false);
                }

                break;
            case R.id.checkBox4:
                if (checked) {
                    order.add(((CheckBox) findViewById(R.id.checkBox4)).getText().toString());
                    ((CheckBox) findViewById(R.id.checkBox2)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox3)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox1)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox5)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox6)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox7)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox8)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox9)).setEnabled(false);
                    pancake.setEnabled(true);
                }
                else{
                    int last = order.size();
                    order.remove(last-1);
                    for (int i=1; i < 10; i++){
                        String checkBoxesID = "checkBox" + i ;
                        checkID = getResources().getIdentifier(checkBoxesID, "id", OrderScreen.this.getPackageName());
                        if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                            ((CheckBox) findViewById(checkID)).setEnabled(true);
                        }
                    }
                    pancake.setEnabled(false);
                }

            case R.id.checkBox5:
                if (checked) {
                    order.add(((CheckBox) findViewById(R.id.checkBox5)).getText().toString());
                    ((CheckBox) findViewById(R.id.checkBox2)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox3)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox4)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox1)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox6)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox7)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox8)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox9)).setEnabled(false);
                    pancake.setEnabled(true);
                }
                else{
                    int last = order.size();
                    order.remove(last-1);
                    for (int i=1; i < 10; i++){
                        String checkBoxesID = "checkBox" + i ;
                        checkID = getResources().getIdentifier(checkBoxesID, "id", OrderScreen.this.getPackageName());
                        if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                            ((CheckBox) findViewById(checkID)).setEnabled(true);
                        }
                    }
                    pancake.setEnabled(false);
                }

                break;
            case R.id.checkBox6:
                if (checked) {
                    order.add(((CheckBox) findViewById(R.id.checkBox6)).getText().toString());
                    ((CheckBox) findViewById(R.id.checkBox2)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox3)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox4)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox5)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox1)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox7)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox8)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox9)).setEnabled(false);
                    pancake.setEnabled(true);
                }
                else{
                    for (int i=1; i < 10; i++){
                        String checkBoxesID = "checkBox" + i ;
                        checkID = getResources().getIdentifier(checkBoxesID, "id", OrderScreen.this.getPackageName());
                        if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                            ((CheckBox) findViewById(checkID)).setEnabled(true);
                        }
                    }
                    pancake.setEnabled(false);
                }

                break;
            case R.id.checkBox7:
                if (checked) {
                    order.add(((CheckBox) findViewById(R.id.checkBox7)).getText().toString());
                    ((CheckBox) findViewById(R.id.checkBox2)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox3)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox4)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox5)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox6)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox1)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox8)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox9)).setEnabled(false);
                    pancake.setEnabled(true);
                }
                else{
                    for (int i=1; i < 10; i++){
                        String checkBoxesID = "checkBox" + i ;
                        checkID = getResources().getIdentifier(checkBoxesID, "id", OrderScreen.this.getPackageName());
                        if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                            ((CheckBox) findViewById(checkID)).setEnabled(true);
                        }
                    }
                    pancake.setEnabled(false);
                }

                break;
            case R.id.checkBox8:
                if (checked) {
                    order.add(((CheckBox) findViewById(R.id.checkBox8)).getText().toString());
                    ((CheckBox) findViewById(R.id.checkBox2)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox3)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox4)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox5)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox6)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox7)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox1)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox9)).setEnabled(false);
                    pancake.setEnabled(true);
                }
                else{
                    for (int i=1; i < 10; i++){
                        String checkBoxesID = "checkBox" + i ;
                        checkID = getResources().getIdentifier(checkBoxesID, "id", OrderScreen.this.getPackageName());
                        if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                            ((CheckBox) findViewById(checkID)).setEnabled(true);
                        }
                    }
                    pancake.setEnabled(false);
                }

                break;
            case R.id.checkBox9:
                if (checked) {
                    order.add(((CheckBox) findViewById(R.id.checkBox9)).getText().toString());
                    ((CheckBox) findViewById(R.id.checkBox2)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox3)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox4)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox5)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox6)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox7)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox8)).setEnabled(false);
                    ((CheckBox) findViewById(R.id.checkBox1)).setEnabled(false);
                    pancake.setEnabled(true);
                }
                else{
                    for (int i=1; i < 10; i++){
                        String checkBoxesID = "checkBox" + i ;
                        checkID = getResources().getIdentifier(checkBoxesID, "id", OrderScreen.this.getPackageName());
                        if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                            ((CheckBox) findViewById(checkID)).setEnabled(true);
                        }
                    }
                    pancake.setEnabled(false);
                }

                break;
        }
    }

}
