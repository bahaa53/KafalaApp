package com.rocca.umrah.kafala.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.interfaces.OnCitiesClicked;
import com.rocca.umrah.kafala.interfaces.OnPhoneCallClicked;
import com.rocca.umrah.kafala.reponse.InfoDTO;
import com.rocca.umrah.kafala.reponse.adverismentresponse.SearchResultResponseDTO;

public class SearResultViewHolder extends RecyclerView.ViewHolder {

    private TextView tvadvTitle;
    private TextView tvTime;
    private TextView tvInfoData;
    private TextView tvPhone;
    private TextView tvUserName;
    private TextView tvCity;
    private ImageView ivPhone;

    public SearResultViewHolder(@NonNull View itemView) {
        super(itemView);
        tvadvTitle = (TextView) itemView.findViewById(R.id.tv_adv_title);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvInfoData = (TextView) itemView.findViewById(R.id.tv_additional_info);
        tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
        tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
        tvCity = (TextView) itemView.findViewById(R.id.tv_city);
        ivPhone = (ImageView) itemView.findViewById(R.id.iv_phone);

    }

    public void bindAdv(final SearchResultResponseDTO searchResultResponseDTO, OnPhoneCallClicked onPhoneCallClicked) {
        tvadvTitle.setText(searchResultResponseDTO.getCname());
        tvInfoData.setText(searchResultResponseDTO.getInfo());
        tvPhone.setText(searchResultResponseDTO.getContact());
        tvTime.setText(searchResultResponseDTO.getDate());
        tvUserName.setText(searchResultResponseDTO.getMname());
        tvCity.setText(searchResultResponseDTO.getTname());
        ivPhone.setOnClickListener(v -> onPhoneCallClicked.getPhoneNumber(searchResultResponseDTO.getContact()));
    }
}
