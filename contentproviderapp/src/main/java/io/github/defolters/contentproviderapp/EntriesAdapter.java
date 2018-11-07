package io.github.defolters.contentproviderapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


class EntryViewHolder extends RecyclerView.ViewHolder {

    private TextView firstTextView;
    private TextView secondTextView;

    public EntryViewHolder(View itemView) {
        super(itemView);

        firstTextView = itemView.findViewById(R.id.first_text_view);
        secondTextView = itemView.findViewById(R.id.second_text_view);
    }

    public TextView getFirstTextView() {
        return firstTextView;
    }

    public TextView getSecondTextView() {
        return secondTextView;
    }
}

public class EntriesAdapter extends RecyclerView.Adapter<EntryViewHolder> {
    private ArrayList<Entry> entries;
    private Context context;


    public EntriesAdapter(Context context, ArrayList<Entry> entries) {
        this.context = context;
        this.entries = entries;
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.entry_item, parent, false);

        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {

        Entry entry = entries.get(position);
        holder.getFirstTextView().setText(entry.getFirst());
        holder.getSecondTextView().setText(entry.getSecond());
        Log.d("Entry", entry.getFirst() + entry.getSecond());
    }

}
