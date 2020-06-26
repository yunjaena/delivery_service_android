package com.yunjaena.seller.ui.main.adapater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yunjaena.seller.R;
import com.yunjaena.seller.data.entity.Order;

import java.util.List;
import java.util.Locale;


public class MainOrderAdapter extends RecyclerView.Adapter<MainOrderAdapter.ViewHolder> {
    private List<Order> orderList;
    private OrderRecyclerViewClickListener orderRecyclerViewClickListener;

    public MainOrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void setOrderRecyclerViewClickListener(OrderRecyclerViewClickListener orderRecyclerViewClickListener) {
        this.orderRecyclerViewClickListener = orderRecyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.roomNameTextView.setText(String.format(Locale.KOREA, "room%d", order.getRoomNumber()));
        holder.receiptButton.setOnClickListener(v -> {
            if (orderRecyclerViewClickListener != null)
                orderRecyclerViewClickListener.onClickOrderButton(position, true);
        });
        holder.deliveryButton.setOnClickListener(v -> {
            if (orderRecyclerViewClickListener != null)
                orderRecyclerViewClickListener.onClickOrderButton(position, false);
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public interface OrderRecyclerViewClickListener {
        void onClickOrderButton(int position, boolean isReceipt);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView roomNameTextView;
        public Button deliveryButton;
        public Button receiptButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNameTextView = itemView.findViewById(R.id.room_name_text_view);
            deliveryButton = itemView.findViewById(R.id.delivery_button);
            receiptButton = itemView.findViewById(R.id.receipt_button);
        }
    }
}
