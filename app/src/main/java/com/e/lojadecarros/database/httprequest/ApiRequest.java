package com.e.lojadecarros.database.httprequest;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.e.lojadecarros.database.AsyncManager;
import com.e.lojadecarros.database.VehicleDao;
import com.e.lojadecarros.database.VehicleDatabase;
import com.e.lojadecarros.model.VehicleGeneral;
import com.e.lojadecarros.presenter.MainPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRequest {

    private ServiceApi serviceApi;
    private MainPresenter.View view;
    private Context mContext;
    private VehicleDao vehicleDao;
    private AsyncManager asyncManager;

    public ApiRequest(MainPresenter.View view, Context context){
        this.view = view;
        this.mContext = context;
        if(serviceApi == null){
            serviceApi = new ServiceApi();
        }
        VehicleDatabase vehicleDatabase = VehicleDatabase.getDatabase(mContext);
        this.vehicleDao = vehicleDatabase.vehicleDao();
        initManager(this.mContext);
    }

    private void initManager(Context context){
        this.asyncManager = new AsyncManager(view, context);
    }

    public void getVehiclesList(int page){

        serviceApi
                .getAPI()
                .getVehicles(page)
                .enqueue(new Callback<List<VehicleGeneral>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<VehicleGeneral>> call, @NonNull Response<List<VehicleGeneral>> response) {
                        Log.i("RX_JAVA", "SUCCESS " + response.body().toString());
//                        asyncManager.getVehiclesList(response.body());
//                        asyncManager.insertVehicles(response.body());
//                        setMainView();
                        asyncManager.initiateRequest(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<VehicleGeneral>> call, Throwable t) {
                        Log.i("RESPONSE", "ERROR " + t.toString());
//                        setMainView();
                    }
                });
    }
//
//    private void setMainView(){
//        asyncManager.getVehiclesList();
//    }
}
