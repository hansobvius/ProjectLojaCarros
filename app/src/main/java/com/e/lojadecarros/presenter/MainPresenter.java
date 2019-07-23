package com.e.lojadecarros.presenter;

import com.e.lojadecarros.model.VehicleGeneral;

import java.util.List;

import io.reactivex.Flowable;

public interface MainPresenter {

    interface View{
        void getVehicleList(List<VehicleGeneral> vehicleGenerals);
    }

    interface Detail{
        void getVehicle(VehicleGeneral vehicleGeneral);
    }

}
