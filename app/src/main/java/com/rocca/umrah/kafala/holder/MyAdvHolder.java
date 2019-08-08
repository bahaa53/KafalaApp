package com.rocca.umrah.kafala.holder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.interfaces.OnMyAdvClicked;
import com.rocca.umrah.kafala.reponse.myadvresponse.InfoDTO;


public class MyAdvHolder extends RecyclerView.ViewHolder {

    private TextView tvText;

    public MyAdvHolder(@NonNull View itemView) {
        super(itemView);
        tvText = (TextView) itemView.findViewById(R.id.tv_adv_title);
    }

    public void bindAdv(final InfoDTO infoDTO, final OnMyAdvClicked onMyAdvClicked) {
        tvText.setText(infoDTO.getCategoryName() + " من " + infoDTO.getCityName());
        itemView.setOnClickListener(v -> onMyAdvClicked.choosedAdv(infoDTO));
    }

}