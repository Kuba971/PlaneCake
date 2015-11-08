package com.example.kuba.planecake;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ConfirmFragment extends Fragment {

    ListView listView;
    ArrayAdapter<String> listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_confirm, container, false);

        OrderScreen orderScreen = (OrderScreen)getActivity();

        listView  = (ListView) v.findViewById(R.id.listViewPancake);
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, orderScreen.order);
        listView.setAdapter(listAdapter);
        return v;}

}
