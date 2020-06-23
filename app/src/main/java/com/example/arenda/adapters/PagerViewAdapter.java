package com.example.arenda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.arenda.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PagerViewAdapter extends PagerAdapter {
    ArrayList<String> list;

    public PagerViewAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
           View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager_view, container,false);
        ImageView imageView = view.findViewById(R.id.imageViewAvatar);
        Picasso.get().load(list.get(position)).into(imageView);

         container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
