package com.rocca.umrah.kafala.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.holder.CitiesHolder;
import com.rocca.umrah.kafala.interfaces.OnCitiesClicked;
import com.rocca.umrah.kafala.reponse.InfoDTO;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesHolder> {

    private List<InfoDTO> cities;
    private OnCitiesClicked onCitiesClicked;

    public CitiesAdapter(List<InfoDTO> cities, OnCitiesClicked onCitiesClicked) {
        this.cities = cities;
        this.onCitiesClicked = onCitiesClicked;
    }

    @Override
    public CitiesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic_layout, parent, false);
        return new CitiesHolder(v);
    }

    @Override
    public void onBindViewHolder(CitiesHolder holder, int position) {
        holder.bindCities(cities.get(position), onCitiesClicked);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
