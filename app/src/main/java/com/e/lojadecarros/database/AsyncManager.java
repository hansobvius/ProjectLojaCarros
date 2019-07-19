package com.e.lojadecarros.database;

import android.content.Context;
import android.os.AsyncTask;

import com.e.lojadecarros.model.VehicleGeneral;

import java.util.List;

public class AsyncManager extends AsyncTask<Void, Void, VehicleDao> {

    private Context mContext;
    private VehicleDao vehicleDao;

    public AsyncManager(Context context){
        this.mContext = context;
    }

    @Override
    protected VehicleDao doInBackground(Void... voids) {
        VehicleDatabase vehicleDatabase = VehicleDatabase.getDatabase(mContext);
        this.vehicleDao = vehicleDatabase.vehicleDao();
        return vehicleDao;
    }

    @Override
    protected void onPostExecute(VehicleDao vehicleDao) {
        super.onPostExecute(vehicleDao);
    }

    public List<VehicleGeneral> getVehicleList(){
        return  vehicleDao.getAll();
    }

    public VehicleGeneral getVehicle(int id){
        return vehicleDao.getVehicle(id);
    }
}
