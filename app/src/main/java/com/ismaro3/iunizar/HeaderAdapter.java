package com.ismaro3.iunizar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HeaderAdapter extends ArrayAdapter<Header> {

    public HeaderAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public HeaderAdapter(Context context, int resource, List<Header> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.header_row, null);

        }

        Header h = getItem(position);

        if (h != null) {

            TextView title = (TextView) v.findViewById(R.id.title);
            title.setText(h.getTitle());
            TextView category = (TextView) v.findViewById(R.id.category);
            category.setText(h.getCategory());
            ImageView image = (ImageView) v.findViewById(R.id.image);
            String first = h.getCategory().toUpperCase().substring(0,3);
            String upper = h.getCategory().toUpperCase();
            int sumaColor = (upper.charAt(0)+upper.charAt(1)+upper.charAt(2))/3;
            image.setImageDrawable(new CharacterDrawable(first, 0xFF805781 + sumaColor*9999));

        }

        return v;

    }
}