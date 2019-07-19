package com.e.lojadecarros.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.lojadecarros.R;
import com.e.lojadecarros.database.AsyncManager;
import com.e.lojadecarros.model.VehicleGeneral;
import com.e.lojadecarros.database.httprequest.ApiRequest;
import com.e.lojadecarros.presenter.MainPresenter;
import com.e.lojadecarros.ui.adapter.MainAdapter;
import com.facebook.stetho.Stetho;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private static final int MAX_PAGS = 3;

    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;
    private ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        initMainView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        getApiRequest();
    }

    private void initMainView(){
        this.mRecyclerView = findViewById(R.id.main_recycler_view);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mMainAdapter = new MainAdapter(this, internetConnection());
        mRecyclerView.setAdapter(mMainAdapter);
        apiRequest = new ApiRequest(this, getApplicationContext());
    }

    private void getApiRequest() {
        for(int c = 1; c < MAX_PAGS; c++) apiRequest.getVehiclesList(c);
    }

    private boolean internetConnection(){
        Context context = MainActivity.this;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm != null ? cm.getActiveNetworkInfo() : null;
        boolean isConnected = ni != null && ni.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    public void getVehicleList(List<VehicleGeneral> vehicleGenerals) {
        if(!vehicleGenerals.isEmpty() || vehicleGenerals.size() != 0){
            mMainAdapter.setAdapterData(vehicleGenerals);
        }else if(!internetConnection() && vehicleGenerals.isEmpty() || vehicleGenerals.size() == 0){
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }
}
