package io.github.defolters.androiddatamanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private WeakReference<Context> mContextWeakReference;
    private ArrayList<Entry> mEntries;

    public ListAdapter(Context context, ArrayList<Entry> entries) {
        mContextWeakReference = new WeakReference<>(context);
        mEntries = entries;
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.first = convertView.findViewById(R.id.first_text_view);
            viewHolder.second = convertView.findViewById(R.id.second_text_view);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Entry entry = mEntries.get(position);
        viewHolder.first.setText(entry.getFirst());
        viewHolder.second.setText(entry.getSecond());

        return convertView;
    }

    private static class ViewHolder {
        TextView first;
        TextView second;
    }
}
