package it.unimib.justpizza.ui.orders.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import it.unimib.justpizza.R;
import it.unimib.justpizza.model.Order;

public class OrderAdapter extends ListAdapter<Order, OrderAdapter.OrderViewHolder> {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd/MM/yyyy  HH:mm", Locale.ITALY);

    public OrderAdapter() {
        super(new OrderDiffCallback());
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateText;
        private final TextView summaryText;
        private final TextView totalText;
        private final TextView statusText;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.order_date);
            summaryText = itemView.findViewById(R.id.order_summary);
            totalText = itemView.findViewById(R.id.order_total);
            statusText = itemView.findViewById(R.id.order_status);
        }

        public void bind(Order order) {
            if (order.getOrderDate() != null) {
                dateText.setText(DATE_FORMAT.format(order.getOrderDate()));
            } else {
                dateText.setText("");
            }
            summaryText.setText(order.getItemsSummary());
            totalText.setText(String.format(Locale.ITALY, "€ %.2f", order.getTotalPrice()));
            statusText.setText("Confermato");
        }
    }

    static class OrderDiffCallback extends DiffUtil.ItemCallback<Order> {
        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.getTotalPrice() == newItem.getTotalPrice()
                    && java.util.Objects.equals(oldItem.getItemsSummary(), newItem.getItemsSummary());
        }
    }
}