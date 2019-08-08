package com.rocca.umrah.kafala.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.holder.CitiesHolder;
import com.rocca.umrah.kafala.interfaces.OnCategoryClicked;
import com.rocca.umrah.kafala.interfaces.OnNationalityClicked;
import com.rocca.umrah.kafala.reponse.nationalitiesResponse.InfoDTO;


import java.util.List;

public class NationalityAdapter extends RecyclerView.Adapter<CitiesHolder> {

    private List<InfoDTO> categories;
    private OnNationalityClicked onCategoryClicked;

    public NationalityAdapter(List<InfoDTO> categories, OnNationalityClicked onCategoryClicked) {
        this.categories = categories;
        this.onCategoryClicked = onCategoryClicked;
    }

    @Override
    public CitiesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic_layout, parent, false);
        return new CitiesHolder(v);
    }

    @Override
    public void onBindViewHolder(CitiesHolder holder, int position) {
        holder.bindNationality(categories.get(position), onCategoryClicked);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
