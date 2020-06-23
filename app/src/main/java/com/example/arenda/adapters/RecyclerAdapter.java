package com.example.arenda.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arenda.pogo.Home;
import com.example.arenda.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    public ClickListener clickListener;

   private List<Home> homeList;



    public RecyclerAdapter() {
        homeList = new ArrayList<>();
    }


    public void setHomeList(List<Home> homeList) {
        this.homeList = homeList;
        notifyDataSetChanged();
    }

    public List<Home> getHomeList() {
        return homeList;
    }

    public interface ClickListener{
        void clickListener(int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Home home = homeList.get(position);
        String textType = null;
        if(home.getRoom()!=null && home.getFlor() !=null) {
        textType = home.getRoom() + "-к " +  home.getType()+ ", " + home.getSq() + "м², " + home.getFlor() + " эт.";}
       else if(home.getRoom()==null)
        {
            textType = home.getType()+ ", " + home.getSq() + "м², " + home.getFlor() + " эт.";
        }
       else if(home.getFlor() ==null) {
            textType = home.getRoom() + "-к " +  home.getType()+ ", " + home.getSq() + "м²";
        }
        String textPrice = home.getPrice() + " ₽ в месяц";

        Picasso.get().load(home.getPhotoUrl().get(0)).placeholder(R.drawable.back_image).fit().into(holder.imageViewMailPhoto);
        holder.textViewTypeSpaceFloor.setText(textType);
        holder.textViewPrice.setText(textPrice);
        holder.textViewArea.setText(home.getArea());
        holder.textViewAddress.setText(home.getAddress());
        if(date.equals(home.getDate())) {
        holder.textViewDate.setText("Сегодня");
          
        }
        else { holder.textViewDate.setText(home.getDate()); }

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView imageViewMailPhoto;
        TextView textViewTypeSpaceFloor;
        TextView textViewPrice;
        TextView textViewArea;
        TextView textViewAddress;
        TextView textViewDate;

        public Holder(@NonNull final View itemView) {
            super(itemView);
            imageViewMailPhoto = itemView.findViewById(R.id.imageView4);
            textViewTypeSpaceFloor = itemView.findViewById(R.id.textViewTypeSpaceFloor);
            textViewPrice = itemView.findViewById(R.id.textViewPrice2);
            textViewArea = itemView.findViewById(R.id.textViewArea2);
            textViewAddress = itemView.findViewById(R.id.textViewAddress2);
            textViewDate = itemView.findViewById(R.id.textViewDate2);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener!=null){
                        clickListener.clickListener(getAdapterPosition());
                    }

                }
            });
        }
    }
}
