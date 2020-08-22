package com.farhanarnob.singularityattendance.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.farhanarnob.singularityattendance.R;
import com.farhanarnob.singularityattendance.network.repository.StoreListGetApiHandler;
import com.farhanarnob.singularityattendance.network.retrofit.APIClientResponse;
import com.farhanarnob.singularityattendance.network.room.MainViewModel;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        recyclerView = getView().findViewById(R.id.rv_seller_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        StoreListGetApiHandler.getInstance().callAPI(getContext(), new APIClientResponse() {
            @Override
            public void failureOnApiCall(String msg, Object sender) {

            }

            @Override
            public void failureOnNetworkConnection(String msg, Object sender) {

            }

            @Override
            public void successOnApiCall(String msg, Object sender) {
                Toast.makeText(getContext(),"Download Success",Toast.LENGTH_LONG);
            }
        });
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

//        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
//        recyclerView.setAdapter(mAdapter);
        // TODO: Use the ViewModel
    }

}