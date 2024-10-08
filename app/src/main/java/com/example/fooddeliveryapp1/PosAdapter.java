package com.example.fooddeliveryapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class PosAdapter extends ArrayAdapter<String> {
    private ArrayList<String> posList;
    private Context context;
    private boolean[] checked;

    public PosAdapter(Context context, ArrayList<String> posList) {
        super(context, R.layout.item_pos, posList);
        this.context = context;
        this.posList = posList;
        this.checked = new boolean[posList.size()];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_pos, null);
        }

        TextView textPos = view.findViewById(R.id.text_pos);
        CheckBox checkboxPos = view.findViewById(R.id.checkbox_pos);

        textPos.setText(posList.get(position));
        checkboxPos.setChecked(checked[position]);

        checkboxPos.setOnClickListener(v -> {
            checked[position] = checkboxPos.isChecked();
        });

        return view;
    }

    public boolean[] getChecked() {
        return checked;
    }
}
