package com.example.kuba.planecake;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class OrderScreenFragment extends Fragment {

    private Button next;
    private CheckBox checkBox7;
    private CheckBox checkBox1;

    public OrderScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        checkBox7 = (CheckBox) v.findViewById(R.id.checkBox7);
        checkBox1 = (CheckBox) v.findViewById(R.id.checkBox1);
        checkBox7.setTextColor(Color.RED);
        checkBox7.setEnabled(false);

       return v;

    }

}
