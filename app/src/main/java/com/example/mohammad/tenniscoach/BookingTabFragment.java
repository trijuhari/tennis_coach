package com.example.mohammad.tenniscoach;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingTabFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    public BookingTabFragment() {
    }

    public static BookingTabFragment newInstance(int position) {
        BookingTabFragment fragment = new BookingTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_booking, container, false);

        // position: 1 = upcoming bookings, 2 = previous bookings
        int position = getArguments().getInt(ARG_POSITION, 0);

        // Dummy data
        List<String> bookings = new ArrayList<>();

        if (position == 0) {
            bookings.addAll(Arrays.asList(
                    "Court 1|Normal Session|02 Feb 2018|12:00PM",
                    "Court 2|Group Session|03 Feb 2018|15:00",
                    "Court 1|Private Session|05 Feb 2018|18:00"
            ));
        } else {
            bookings.addAll(Arrays.asList(
                    "Court 1|Normal Session|15 Jan 2018|18:00",
                    "Court 2|Group Session|10 Jan 2018|15:00",
                    "Court 1|Normal Session|08 Jan 2018|12:00",
                    "Court 4|Private Session|03 Jan 2018|11:00",
                    "Court 2|Private Session|28 Dec 2017|13:00",
                    "Court 3|Group Session|20 Dec 2017|16:00",
                    "Court 4|Private Session|18 Dec 2017|19:00"
            ));
        }
        LVAdapter bookingsAdapter = new LVAdapter(bookings);
        ListView bookingsListView = (ListView) rootView.findViewById(R.id.lv_bookings);
        bookingsListView.setAdapter(bookingsAdapter);
        bookingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(parent.getContext(), "Clicked booking " + ++position, Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }

    public class LVAdapter extends BaseAdapter {

        List<String> bookings;

        LVAdapter(List<String> bookings) {
            this.bookings = bookings;
        }

        @Override
        public int getCount() {
            return bookings.size();
        }

        @Override
        public String getItem(int position) {
            return bookings.get(position);
        }

        @Override
        public long getItemId(int position) {
            return bookings.get(position).hashCode();
        }

        @Override
        public View getView(int position, View listItemView, ViewGroup parent) {
            if (listItemView == null) {
                listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_booking, parent, false);
            }
            final String[] booking = getItem(position).split("\\|");
            ((TextView) listItemView.findViewById(R.id.tv_item_court)).setText(booking[0]);
            ((TextView) listItemView.findViewById(R.id.tv_item_session_type)).setText(booking[1]);
            ((TextView) listItemView.findViewById(R.id.tv_item_date)).setText(booking[2]);
            ((TextView) listItemView.findViewById(R.id.tv_item_time)).setText(booking[3]);
            ImageView sessionImage = listItemView.findViewById(R.id.iv_session_icon);
            switch (booking[1]) {
                case "Normal Session":
                    sessionImage.setImageResource(R.drawable.ic_court);
                    break;
                case "Group Session":
                    sessionImage.setImageResource(R.drawable.ic_balls);
                    break;
                default:
                    sessionImage.setImageResource(R.drawable.ic_racket);
            }
            return listItemView;
        }

    }

}