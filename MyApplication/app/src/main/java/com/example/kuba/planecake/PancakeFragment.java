package com.example.kuba.planecake;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PancakeFragment extends Fragment {

    private TextView PancakeView;
    private TextView PancakeView2;
    private TextView PancakeView3;
    private TextView PancakeView4;
    private TextView PancakeView5;
    private LinearLayout LayoutPancake1;
    private LinearLayout LayoutPancake2;
    private LinearLayout LayoutPancake3;
    private LinearLayout LayoutPancake4;
    private LinearLayout LayoutPancake5;
    private TextView PancakeStock1;
    private TextView PancakeStock2;
    private TextView PancakeStock3;
    private TextView PancakeStock4;
    private TextView PancakeStock5;
    private int TextViewID;
    private int TextStockID;
    private int LayoutID;
    public ArrayList<String> listName = new ArrayList<String>();
    public ArrayList<String> listQuantity = new ArrayList<String>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pancake, container, false);
        LayoutPancake1 = (LinearLayout) v.findViewById(R.id.LayoutPancake1);
        LayoutPancake1.setVisibility(View.INVISIBLE);
        LayoutPancake2 = (LinearLayout) v.findViewById(R.id.LayoutPancake2);
        LayoutPancake2.setVisibility(View.INVISIBLE);
        LayoutPancake3 = (LinearLayout) v.findViewById(R.id.LayoutPancake3);
        LayoutPancake3.setVisibility(View.INVISIBLE);
        LayoutPancake4 = (LinearLayout) v.findViewById(R.id.LayoutPancake4);
        LayoutPancake4.setVisibility(View.INVISIBLE);
        LayoutPancake5 = (LinearLayout) v.findViewById(R.id.LayoutPancake5);
        LayoutPancake5.setVisibility(View.INVISIBLE);
        OrderScreen order = (OrderScreen)getActivity();
        int numberLine = order.listPancake.size();
        numberLine = numberLine - 2;
        for (int i = 0; i < numberLine/2; i++) {
           listName.add(order.listPancake.get(i+(1+i)));}
            for (int i = 0; i < numberLine/2; i++) {
            listQuantity.add(order.listPancake.get(i+(2+i)));
            String PancakeName = "PancakeView" + (i+1);
            String PancakeStock = "PancakeStock" + (i+1);
            String LayoutPancake = "LayoutPancake" + (i+1);
            TextViewID = getResources().getIdentifier(PancakeName, "id", getActivity().getPackageName());
            TextStockID = getResources().getIdentifier(PancakeStock, "id", getActivity().getPackageName());
            LayoutID = getResources().getIdentifier(LayoutPancake, "id", getActivity().getPackageName());
            TextView tempView = (TextView) v.findViewById(TextViewID);
            tempView.setText(listName.get(i));
            TextView tempStock = (TextView) v.findViewById(TextStockID);
            tempStock.setText(listQuantity.get(i));
            LinearLayout tempLayout = (LinearLayout) v.findViewById(LayoutID);
            tempLayout.setVisibility(View.VISIBLE);
        }
    return v;}

}
