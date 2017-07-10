package dev.anhnt.kimdung.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dev.anhnt.kimdung.R;
import dev.anhnt.kimdung.models.Chapter;

public class ListChapterAdapter extends BaseAdapter {

    private Activity context;
    private List<Chapter> chapterList;

    public ListChapterAdapter(Activity context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @Override
    public int getCount() {
        if (chapterList != null) {
            return chapterList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (chapterList != null) {
            return chapterList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_chapter, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Chapter chapter = (Chapter) getItem(position);
        holder.name.setText(chapter.getName());
        return convertView;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) v.findViewById(R.id.tvName);
        return holder;
    }

    private static class ViewHolder {
        public TextView name;
    }

    public void swapItems(List<Chapter> list) {
        this.chapterList = list;
        notifyDataSetChanged();
    }

}
