package it.unimib.justpizza.ui.menu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import it.unimib.justpizza.R;
import it.unimib.justpizza.model.Pizza;

public class PizzaAdapter extends ListAdapter<Pizza, PizzaAdapter.PizzaViewHolder> {

    private OnPizzaClickListener listener;
    public PizzaAdapter() {
        super(new PizzaDiffCallback());
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Pizza pizza = getItem(position);
        holder.bind(pizza);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPizzaClick(pizza);
            }
        });
    }

    static class PizzaViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView priceText;
        private final ImageView imageView;


        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.pizza_name);
            priceText = itemView.findViewById(R.id.pizza_price);
            imageView = itemView.findViewById(R.id.pizza_image);
        }

        public void bind(Pizza pizza) {
            nameText.setText(pizza.getName());
            priceText.setText(String.format("€ %.2f", pizza.getPrice()));

            Glide.with(itemView.getContext())
                    .load(pizza.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);
        }
    }

    static class PizzaDiffCallback extends DiffUtil.ItemCallback<Pizza> {
        @Override
        public boolean areItemsTheSame(@NonNull Pizza oldItem, @NonNull Pizza newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pizza oldItem, @NonNull Pizza newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getPrice() == newItem.getPrice();
        }
    }

    public void setOnPizzaClickListener(OnPizzaClickListener listener) {
        this.listener = listener;
    }

    public interface OnPizzaClickListener {
        void onPizzaClick(Pizza pizza);
    }

}
