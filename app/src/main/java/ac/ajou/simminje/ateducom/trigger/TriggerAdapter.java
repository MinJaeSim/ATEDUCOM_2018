package ac.ajou.simminje.ateducom.trigger;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.Query;

import ac.ajou.simminje.ateducom.FireStoreAdapter;
import ac.ajou.simminje.ateducom.R;

public class TriggerAdapter extends FireStoreAdapter<TriggerAdapter.TriggerViewHolder> {

    private OnTriggerClickListener listener;

    public void setOnTriggerClickListener(OnTriggerClickListener onTriggerClickListener) {
        this.listener = onTriggerClickListener;
    }

    public TriggerAdapter(Query query) {
        super(query);
    }

    @Override
    public TriggerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_trigger_holder, parent, false);
        return new TriggerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TriggerViewHolder holder, int position) {
        Trigger trigger = getSnapshot(position).toObject(Trigger.class);
        final String documentKey = getSnapshot(position).getId();

        Glide.with(holder.dietImageView)
                .load(trigger.getProfileImageUrl())
                .apply(new RequestOptions()
                        .override(150, 150)
                        .placeholder(R.drawable.ic_plus)
                )
                .into(holder.dietImageView);

        holder.descriptionTextView.setText(trigger.getDescription());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(trigger, documentKey);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(trigger, documentKey);
            }
        });
    }

    static class TriggerViewHolder extends RecyclerView.ViewHolder {

        private ImageView dietImageView = itemView.findViewById(R.id.image_view_trigger);
        private ImageView deleteButton = itemView.findViewById(R.id.image_view_delete);
        private TextView descriptionTextView = itemView.findViewById(R.id.text_view_description);

        public TriggerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
