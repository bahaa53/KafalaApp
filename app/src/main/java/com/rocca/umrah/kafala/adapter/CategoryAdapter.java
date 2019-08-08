package com.rocca.umrah.kafala.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.holder.CitiesHolder;
import com.rocca.umrah.kafala.interfaces.OnCategoryClicked;
import com.rocca.umrah.kafala.interfaces.OnCitiesClicked;
import com.rocca.umrah.kafala.reponse.categoriesresponse.InfoDTO;


import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CitiesHolder> {

    private List<InfoDTO> categories;
    private OnCategoryClicked onCategoryClicked;

    public CategoryAdapter(List<InfoDTO> categories, OnCategoryClicked onCategoryClicked) {
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
        holder.bindCategory(categories.get(position), onCategoryClicked);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
