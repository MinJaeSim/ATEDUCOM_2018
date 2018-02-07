package ac.ajou.simminje.ateducom;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private List<Integer> list;

    public Adapter(List<Integer> list) {
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new Holder(inflater.inflate(R.layout.list_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.textView.setText("list number #" + position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private TextView textView;

        Holder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.holder_text_view);
        }
    }
}
