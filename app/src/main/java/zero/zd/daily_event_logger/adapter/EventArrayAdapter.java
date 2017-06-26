package zero.zd.daily_event_logger.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import zero.zd.daily_event_logger.Event;
import zero.zd.daily_event_logger.R;

public class EventArrayAdapter extends ArrayAdapter<Event> {

    private Context mContext;
    private int mResource;
    private List<Event> mEventList;

    public EventArrayAdapter(Context context, List<Event> eventList) {
        super(context, R.layout.item_event, eventList);
        mContext = context;
        mResource = R.layout.item_event;
        mEventList = eventList;
    }

    @Override
    public int getCount() {
        return mEventList.size();
    }

    @Nullable
    @Override
    public Event getItem(int position) {
        return mEventList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.eventTextView = (TextView) convertView.findViewById(R.id.text_event);
            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.text_date);

            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        Event event = mEventList.get(position);

        viewHolder.eventTextView.setText(event.getEvent());
        viewHolder.dateTextView.setText(getStringDate(event));

        return convertView;
    }

    private String getStringDate(Event event) {
        long timeDifference = (new Date().getTime()) - event.getDate().getTime();
        int s = (int) timeDifference / 1000;
        int m = s / 60;
        int h = m / 60;

        if (h >= 24)
            return event.getStringDate();
        else if (h > 0)
            return h + " " + getProperTimeForm("hr", h);
        else if (m > 0)
            return m + " " + getProperTimeForm("min", m);

        return "a few seconds ago.";
    }

    private String getProperTimeForm(String noun, int value) {
        if (value > 1) noun += "s";
        return noun + " ago.";
    }

    private static class ViewHolder {
        private TextView eventTextView;
        private TextView dateTextView;
    }
}
