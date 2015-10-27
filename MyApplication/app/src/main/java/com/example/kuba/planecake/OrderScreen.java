package com.example.kuba.planecake;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class OrderScreen extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private OrderScreenFragment fragTable;
    private OrderScreenFragment fragPancake;
    private Button table;
    private Button pancake;
    private Button drink;
    private Button send;
    private String[] order;
    private int checkID;
    private CheckBox[] tabCheckBoxes;
    private static int order_number;




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

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
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
                        System.out.println(checkID + "     AAAAAAAAAAAAAAAAAAAAAAAa");
                        if (((CheckBox) findViewById(checkID)).getCurrentTextColor() != (Color.RED)){
                            ((CheckBox) findViewById(checkID)).setEnabled(true);
                        }
                    }
                    pancake.setEnabled(false);
                }

                break;
        }
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

}
