package com.rocca.umrah.kafala.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.holder.CitiesHolder;
import com.rocca.umrah.kafala.holder.SearResultViewHolder;
import com.rocca.umrah.kafala.interfaces.OnCitiesClicked;
import com.rocca.umrah.kafala.interfaces.OnPhoneCallClicked;
import com.rocca.umrah.kafala.reponse.InfoDTO;
import com.rocca.umrah.kafala.reponse.adverismentresponse.SearchResultResponseDTO;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearResultViewHolder> {

    List<SearchResultResponseDTO> results;
    private OnPhoneCallClicked onPhoneCallClicked;

    public SearchResultAdapter(List<SearchResultResponseDTO> results, OnPhoneCallClicked onPhoneCallClicked) {
        this.results = results;
        this.onPhoneCallClicked = onPhoneCallClicked;
    }

    @NonNull
    @Override
    public SearResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result_layout, parent, false);
        return new SearResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearResultViewHolder holder, int position) {
        holder.bindAdv(results.get(position),onPhoneCallClicked);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
