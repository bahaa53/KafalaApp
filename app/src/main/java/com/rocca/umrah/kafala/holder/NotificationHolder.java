package com.rocca.umrah.kafala.holder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.reponse.notifications.InfoDTO;

public class NotificationHolder extends RecyclerView.ViewHolder {


    private TextView tvNotificationTitle, tvNotificationDate;

    public NotificationHolder(@NonNull View itemView) {
        super(itemView);
        tvNotificationTitle = (TextView) itemView.findViewById(R.id.tv_adv_title);
        tvNotificationDate = (TextView) itemView.findViewById(R.id.tv_notification_date);
    }

    public void bindNotification(InfoDTO infoDTO) {
        tvNotificationTitle.setText(infoDTO.getText());
        tvNotificationDate.setText(infoDTO.getDate());
    }
}
