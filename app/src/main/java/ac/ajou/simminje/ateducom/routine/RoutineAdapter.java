package ac.ajou.simminje.ateducom.routine;


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

public class RoutineAdapter extends FireStoreAdapter<RoutineAdapter.RoutineViewHolder> {

    private OnRoutineClickListener listener;

    public void setOnRoutineClickListener(OnRoutineClickListener listener) {
        this.listener = listener;
    }

    public RoutineAdapter(Query query) {
        super(query);
    }

    @Override
    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_routine_holder, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoutineViewHolder holder, int position) {
        final Routine routine = getSnapshot(position).toObject(Routine.class);

        final String documentKey = getSnapshot(position).getId();
        holder.timeTextView.setText(routine.getTime());
        holder.doingTextView.setText(routine.getDoing());

        Glide.with(holder.doingImageView)
                .load(routine.getProfileImageUrl())
                .apply(new RequestOptions()
                        .override(150, 150)
                        .placeholder(R.drawable.ic_plus)
                )
                .into(holder.doingImageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(routine, documentKey);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(documentKey);
            }
        });
    }

    static class RoutineViewHolder extends RecyclerView.ViewHolder {
        private TextView timeTextView = itemView.findViewById(R.id.text_view_time);
        private TextView doingTextView = itemView.findViewById(R.id.text_view_doing);
        private ImageView deleteButton = itemView.findViewById(R.id.image_view_delete);
        private ImageView doingImageView = itemView.findViewById(R.id.image_view_doing);

        public RoutineViewHolder(View itemView) {
            super(itemView);
        }
    }
}
