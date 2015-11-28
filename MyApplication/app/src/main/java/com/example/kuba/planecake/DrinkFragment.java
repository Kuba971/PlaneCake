package com.example.kuba.planecake;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class DrinkFragment extends Fragment {

    private TextView PancakeView;
    private TextView PancakeView2;
    private TextView PancakeView3;
    private TextView PancakeView4;
    private TextView PancakeView5;
    private LinearLayout LayoutDrink1;
    private LinearLayout LayoutDrink2;
    private LinearLayout LayoutDrink3;
    private LinearLayout LayoutDrink4;
    private LinearLayout LayoutDrink5;
    private int TextViewID;
    private int LayoutID;
    public ArrayList<String> listName = new ArrayList<String>();
    public ArrayList<String> listQuantity = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drink, container, false);

        LayoutDrink1 = (LinearLayout) v.findViewById(R.id.LayoutDrink1);
        LayoutDrink1.setVisibility(View.VISIBLE);
        LayoutDrink2 = (LinearLayout) v.findViewById(R.id.LayoutDrink2);
        LayoutDrink2.setVisibility(View.VISIBLE);
        LayoutDrink3 = (LinearLayout) v.findViewById(R.id.LayoutDrink3);
        LayoutDrink3.setVisibility(View.VISIBLE);
        LayoutDrink4 = (LinearLayout) v.findViewById(R.id.LayoutDrink4);
        LayoutDrink4.setVisibility(View.VISIBLE);
        LayoutDrink5 = (LinearLayout) v.findViewById(R.id.LayoutDrink5);
        LayoutDrink5.setVisibility(View.VISIBLE);
        return v;}
}
