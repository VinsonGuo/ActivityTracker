package com.guoziwei.timerecorder.component;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guoziwei.timerecorder.R;
import com.guoziwei.timerecorder.bean.Record;
import com.guoziwei.timerecorder.component.adapter.RecordAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {


    private RecordAdapter mAdapter;

    public RecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecordAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Record record = mAdapter.getData().get(position);
            RecordDetailActivity.Companion.launch(getActivity(), record.getPackageName());
        });

        mAdapter.bindToRecyclerView(recyclerView);
        mAdapter.setEmptyView(R.layout.item_empty_view);

        return recyclerView;
    }


    public void setData(List<Record> records) {
        mAdapter.setNewData(records);
    }

}
