package com.example.fit3151_lab5trial;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fit3151_lab5trial.provider.BookItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    List<BookItem> data = new ArrayList<>();

    public MyRecyclerViewAdapter()
    {
    }

    public void setData(List<BookItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.textViewID.setText("ID: " + data.get(position).getItemID()+"");
        holder.textViewTitle.setText("Title: "+ data.get(position).getItemTitle());
        holder.textViewISBN.setText("ISBN: " + data.get(position).getItemISBN());
        holder.textViewAuthor.setText("Author: "+data.get(position).getItemAuthor());
        holder.textViewDescription.setText("Description: "+data.get(position).getItemDescription());
        holder.textViewPrice.setText("Price: " + data.get(position).getItemPrice()+"");



        final int fPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() { //set back to itemView for students
            @Override public void onClick(View v) {
                Snackbar.make(v, "Item at position " + fPosition + " was clicked!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView textViewID;
        public TextView textViewTitle;
        public TextView textViewISBN;
        public TextView textViewAuthor;

        public TextView textViewDescription;
        public TextView textViewPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textViewID = itemView.findViewById(R.id.recycle_ID);
            textViewTitle = itemView.findViewById(R.id.recycle_Title);
            textViewISBN = itemView.findViewById(R.id.recycle_ISBN);
            textViewAuthor = itemView.findViewById(R.id.recycle_Author);
            textViewDescription = itemView.findViewById(R.id.recycle_Desc);
            textViewPrice = itemView.findViewById(R.id.recycle_Price);


        }
    }
}