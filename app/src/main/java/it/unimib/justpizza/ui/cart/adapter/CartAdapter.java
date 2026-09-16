package it.unimib.justpizza.ui.cart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.justpizza.R;
import it.unimib.justpizza.model.CartItem;

public class CartAdapter extends ListAdapter<CartItem, CartAdapter.CartViewHolder> {

    private OnCartItemClickListener listener;
    public CartAdapter() {
        super(new CartDiffCallback());
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = getItem(position);
        holder.bind(item);

        holder.removeButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemoveClick(item);
            }
        });
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView quantityText;
        private final TextView priceText;
        private final View removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.cart_item_name);
            quantityText = itemView.findViewById(R.id.cart_item_quantity);
            priceText = itemView.findViewById(R.id.cart_item_price);

            removeButton = itemView.findViewById(R.id.btn_remove);

        }

        public void bind(CartItem cartItem) {
            nameText.setText(cartItem.getName());
            quantityText.setText("x" + cartItem.getQuantity());
            priceText.setText(String.format("€ %.2f", cartItem.getTotalPrice()));
        }
    }

    static class CartDiffCallback extends DiffUtil.ItemCallback<CartItem> {
        @Override
        public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getCartId() == newItem.getCartId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getQuantity() == newItem.getQuantity() &&
                    oldItem.getTotalPrice() == newItem.getTotalPrice();
        }
    }

    public void setOnCartItemClickListener(OnCartItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnCartItemClickListener {
        void onRemoveClick(CartItem item);
    }
}
