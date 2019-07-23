package com.e.lojadecarros.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.e.lojadecarros.model.VehicleGeneral;

import java.util.Collection;
import java.util.List;
import java.util.Observable;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<VehicleGeneral> vehicleGeneral);

    @Query("DELETE FROM vehicle_table")
    void deleteAll();

    @Query("SELECT * FROM vehicle_table WHERE id = :id")
    VehicleGeneral getVehicle(int id);

    @Query("SELECT * from vehicle_table")
    List<VehicleGeneral> getAll();
}
