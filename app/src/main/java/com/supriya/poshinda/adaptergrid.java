package com.supriya.poshinda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class adaptergrid extends ArrayAdapter<modelgrid> {
    public adaptergrid(@NonNull Context context, ArrayList<modelgrid> modelgridadminArrayList) {
        super(context, 0, modelgridadminArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_layout, parent, false);
        }
        modelgrid modelgridadmin = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.txtGrid);
        ImageView courseIV = listitemView.findViewById(R.id.imgGrid);
        courseTV.setText(modelgridadmin.getTextimg());
        courseIV.setImageResource(modelgridadmin.getImgid());
        return listitemView;
    }

}
