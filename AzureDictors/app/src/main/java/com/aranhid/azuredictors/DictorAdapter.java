package com.aranhid.azuredictors;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DictorAdapter extends BaseAdapter {

    Context context;
    ArrayList<Dictor> dictors;

    public DictorAdapter(Context context, ArrayList<Dictor> dictors) {
        this.context = context;
        this.dictors = dictors;
    }

    @Override
    public int getCount() {
        return dictors.size();
    }

    @Override
    public Object getItem(int position) {
        return dictors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Dictor dictor = dictors.get(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.dictor_item, parent, false);
        ImageView dictorImage = convertView.findViewById(R.id.dictorImage);
        TextView dictorName = convertView.findViewById(R.id.dictorName);
        TextView countryTextView = convertView.findViewById(R.id.country);
        ImageView dictorCountry = convertView.findViewById(R.id.dictorCountry);

        dictorName.setText(dictor.Name);
        countryTextView.setText(context.getResources().getString(R.string.country, dictor.Locale));
        switch (dictor.Gender) {
            case "Male":
                dictorImage.setImageResource(R.drawable.user_man);
                break;
            case "Female":
                dictorImage.setImageResource(R.drawable.user_woman);
                break;
        }
        String country = dictor.Locale.substring(3,5).toLowerCase();
        try {
            InputStream inputStream = context.getAssets().open("countries/"+country+".png");
            Drawable image = Drawable.createFromStream(inputStream, null);
            dictorCountry.setImageDrawable(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
