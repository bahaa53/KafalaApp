package com.rocca.umrah.kafala.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.holder.MyAdvHolder;
import com.rocca.umrah.kafala.interfaces.OnMyAdvClicked;
import com.rocca.umrah.kafala.reponse.myadvresponse.InfoDTO;


import java.util.List;

public class MyAdvAdapter extends RecyclerView.Adapter<MyAdvHolder> {

    private List<InfoDTO> advs;
    private OnMyAdvClicked onCitiesClicked;

    public MyAdvAdapter(List<InfoDTO> advs, OnMyAdvClicked onCitiesClicked) {
        this.advs = advs;
        this.onCitiesClicked = onCitiesClicked;
    }

    @Override
    public MyAdvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_adv_layout, parent, false);
        return new MyAdvHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdvHolder holder, int position) {
        holder.bindAdv(advs.get(position), onCitiesClicked);
    }

    @Override
    public int getItemCount() {
        return advs.size();
    }
}
