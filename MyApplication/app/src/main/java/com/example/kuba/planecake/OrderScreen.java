package com.example.kuba.planecake;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class OrderScreen extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private OrderScreenFragment fragTable;
    private PancakeFragment fragPancake;
    private DrinkFragment fragDrink;


    private Button table;
    private Button pancake;
    private Button drink;
    private Button send;
    private int checkID;
    private static int order_number = 0;
    public ArrayList<String> order = new ArrayList<String>();
    public ArrayList<String> listPancake = new ArrayList<String>();

    private PrintWriter writer = new PrintWriter(System.out, true);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private ReadMessages readMessages;
    public final static String QUANTITY = "QUANTITE";



    private class StartNetwork extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            System.out.println("StartNetwork.onPreExecute");
        }

        @Override
        protected Boolean doInBackground(Void... v) {
            System.out.println("StartNetwork.doInBackground");
            String name = "10.0.2.2";
            int port = 7777;
            try {
                Socket mySocket = new Socket(name, port);
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
        new StartNetwork().execute();
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

        // Initialisation du gestionnaire de fragments
        fragmentManager = getFragmentManager();

        // On initialise les fragments, soit en récupérant ceux qui existent déjà, soit en les
        // créant sinon
        fragTable = initFragment(R.id.frameLayoutFragment);
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

    Handler h = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            RefreshQuantity();
        }
    };

        public void RefreshQuantity()
        {
            writer.println("QUANTITE");
            h.postDelayed(runnable, 5000);
        }

    public void assignTable(View v){
        FrameLayout framePancake = (FrameLayout)findViewById(R.id.frameLayoutFragment2);
        framePancake.setVisibility(View.INVISIBLE);
        FrameLayout frameOrder = (FrameLayout)findViewById(R.id.frameLayoutFragment);
        frameOrder.setVisibility(View.VISIBLE);
        FrameLayout frameDrink = (FrameLayout)findViewById(R.id.frameLayoutFragment3);
        frameDrink.setVisibility(View.INVISIBLE);
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

    }

    public void drinkOrder(View v){
        fragDrink = initFragmentDrink(R.id.frameLayoutFragment3);
        FrameLayout frameOrder = (FrameLayout)findViewById(R.id.frameLayoutFragment);
        frameOrder.setVisibility(View.INVISIBLE);
        FrameLayout framePancake = (FrameLayout)findViewById(R.id.frameLayoutFragment2);
        framePancake.setVisibility(View.INVISIBLE);
        FrameLayout frameDrink = (FrameLayout)findViewById(R.id.frameLayoutFragment3);
        frameDrink.setVisibility(View.VISIBLE);

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
    protected void onDestroy() {
        System.out.println("ChatActivity.onDestroy");
        super.onDestroy();
    }



    public void onCheckboxClicked(View view) {
        order_number++;

        // Is the view now checked?
        boolean checked = ((CheckBox) view) .isChecked();
        order.add(((CheckBox) view).getText().toString());
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.checkBox1:
                if (checked) {
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
