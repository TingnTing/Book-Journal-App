package com.example.fit3151_lab5trial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fit3151_lab5trial.provider.BookViewModel;


public class Fragment1 extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private BookViewModel mItemViewModel;
    MyRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        recyclerView = view.findViewById(R.id.rv3);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        mItemViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        mItemViewModel.getAllItems().observe(getViewLifecycleOwner(), newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}