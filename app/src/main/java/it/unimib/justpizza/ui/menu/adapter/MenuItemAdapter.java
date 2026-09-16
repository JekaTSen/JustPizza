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
import it.unimib.justpizza.model.MenuItem;
import it.unimib.justpizza.model.Pizza;

public class MenuItemAdapter extends ListAdapter<MenuItem, MenuItemAdapter.ItemViewHolder> {

    private OnItemClickListener listener;

    public MenuItemAdapter() {
        super(new ItemDiffCallback());
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pizza, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MenuItem item = getItem(position);
        holder.bind(item);
        holder.addButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView priceText;
        private final TextView ingredientsText;
        private final ImageView imageView;
        final View addButton;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.pizza_name);
            priceText = itemView.findViewById(R.id.pizza_price);
            ingredientsText = itemView.findViewById(R.id.pizza_ingredients);
            imageView = itemView.findViewById(R.id.pizza_image);
            addButton = itemView.findViewById(R.id.btn_add);
        }

        void bind(MenuItem item) {
            nameText.setText(item.getName());
            priceText.setText(String.format("€ %.2f", item.getPrice()));

            if (item instanceof Pizza && ((Pizza) item).getIngredients() != null
                    && !((Pizza) item).getIngredients().isEmpty()) {
                ingredientsText.setText(((Pizza) item).getIngredients());
            } else if (item.getDescription() != null) {
                ingredientsText.setText(item.getDescription());
            } else {
                ingredientsText.setText("");
            }

            Glide.with(itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);
        }
    }

    static class ItemDiffCallback extends DiffUtil.ItemCallback<MenuItem> {
        @Override
        public boolean areItemsTheSame(@NonNull MenuItem a, @NonNull MenuItem b) {
            return a.getId() != null && a.getId().equals(b.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MenuItem a, @NonNull MenuItem b) {
            return a.getName().equals(b.getName()) && a.getPrice() == b.getPrice();
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(MenuItem item);
    }
}