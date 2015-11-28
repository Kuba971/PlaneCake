package com.example.kuba.planecake;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class ReplenishmentScreen extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private StockFragment fragStock;
    private PancakeAddingFrag pancakeAddFrag;
    private Context context;

    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replenishment_screen);
        context = getApplicationContext();
        fragmentManager = getFragmentManager();
        fragStock = (StockFragment) fragmentManager.findFragmentById(R.id.frameLayoutFragment);

        createAndAddStockFragment(fragStock);
    }

    private void createAndAddStockFragment(StockFragment frag) {

        if (frag == null) {
            frag = new StockFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.frameLayoutFragment, frag);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_replenishment_screen, menu);
        return true;
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

    public void addPancake(View v){

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragStock != null) {
            fragStock.onDestroy();
        }

        if (pancakeAddFrag == null) {
            pancakeAddFrag = new PancakeAddingFrag();
            transaction.replace(R.id.frameLayoutFragment, pancakeAddFrag, "Frag_Pancake");
            fragStock = null;
        } else {
            transaction.remove(pancakeAddFrag);
            pancakeAddFrag.onDetach();
            pancakeAddFrag = null;
            restartActivity(this);
        }
        transaction.commit();

    }


    public void addDrink(View v){
        soonToast();
    }

    private void soonToast() {
        Toast toast = Toast.makeText(context, "Soon Available", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void restartActivity(ReplenishmentScreen act){

        Intent intent=new Intent();
        intent.setClass(act, act.getClass());
        act.finish();
        act.startActivity(intent);
    }

}
