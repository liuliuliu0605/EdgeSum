package com.example.edgesum.util.nearby;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edgesum.R;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder> {
    private final List<Endpoint> endpoints;
    private final LayoutInflater inflater;
    private final DeviceCallback deviceCallback;

    DeviceListAdapter(Context context, List<Endpoint> endpoints, DeviceCallback deviceCallback) {
        this.inflater = LayoutInflater.from(context);
        this.endpoints = endpoints;
        this.deviceCallback = deviceCallback;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.device_list_item, parent, false);
        return new DeviceViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Endpoint endpoint = endpoints.get(position);

        holder.deviceName.setText(endpoint.name);
        holder.disconnectButton.setEnabled(endpoint.connected);
        holder.removeButton.setEnabled(!endpoint.connected);

        if (endpoint.connected) {
            holder.connectionStatus.setImageResource(R.drawable.connected_status);
            holder.disconnectButton.clearColorFilter();
            holder.removeButton.setColorFilter(Color.LTGRAY);
        } else {
            holder.connectionStatus.setImageResource(R.drawable.disconnected_status);
            holder.disconnectButton.setColorFilter(Color.LTGRAY);
            holder.removeButton.clearColorFilter();
        }

        holder.deviceName.setOnClickListener(v -> deviceCallback.connectEndpoint(endpoint));
        holder.disconnectButton.setOnClickListener(v -> deviceCallback.disconnectEndpoint(endpoint));
        holder.removeButton.setOnClickListener(v -> deviceCallback.removeEndpoint(endpoint));
    }

    @Override
    public int getItemCount() {
        return endpoints.size();
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder {
        final DeviceListAdapter adapter;
        final TextView deviceName;
        final ImageView connectionStatus;
        final ImageView disconnectButton;
        final ImageView removeButton;

        DeviceViewHolder(View itemView, DeviceListAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.deviceName = itemView.findViewById(R.id.device_item_text);
            this.connectionStatus = itemView.findViewById(R.id.connection_status);
            this.disconnectButton = itemView.findViewById(R.id.disconnect_button);
            this.removeButton = itemView.findViewById(R.id.remove_device_button);
        }
    }
}