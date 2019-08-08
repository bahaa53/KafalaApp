package com.rocca.umrah.kafala.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.holder.MyAdvHolder;
import com.rocca.umrah.kafala.holder.NotificationHolder;
import com.rocca.umrah.kafala.reponse.notifications.InfoDTO;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {

    private List<InfoDTO> notifications;

    public NotificationAdapter(List<InfoDTO> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_layout, parent, false);
        return new NotificationHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        holder.bindNotification(notifications.get(position));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
