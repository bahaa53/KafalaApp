package com.rocca.umrah.kafala.holder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.interfaces.OnCategoryClicked;
import com.rocca.umrah.kafala.interfaces.OnCitiesClicked;
import com.rocca.umrah.kafala.interfaces.OnNationalityClicked;
import com.rocca.umrah.kafala.reponse.InfoDTO;

public class CitiesHolder extends RecyclerView.ViewHolder {

    private TextView tvText;

    public CitiesHolder(@NonNull View itemView) {
        super(itemView);
        tvText = (TextView) itemView.findViewById(R.id.tv_city);
    }

    public void bindCities(final InfoDTO infoDTO, final OnCitiesClicked onCitiesClicked) {
        tvText.setText(infoDTO.getName());
        itemView.setOnClickListener(v -> onCitiesClicked.chooseCity(infoDTO));
    }

    public void bindCategory(final com.rocca.umrah.kafala.reponse.categoriesresponse.InfoDTO infoDTO, final OnCategoryClicked onCategoryClicked) {
        tvText.setText(infoDTO.getName());
        itemView.setOnClickListener(v -> onCategoryClicked.chooseCategory(infoDTO));
    }


    public void bindNationality(final com.rocca.umrah.kafala.reponse.nationalitiesResponse.InfoDTO infoDTO, final OnNationalityClicked onNationalityClicked) {
        tvText.setText(infoDTO.getName());
        itemView.setOnClickListener(v -> onNationalityClicked.chooseNationality(infoDTO));
    }
}
