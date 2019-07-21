package com.e.lojadecarros.ui;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.e.lojadecarros.R;
import com.e.lojadecarros.database.AsyncManager;
import com.e.lojadecarros.model.VehicleGeneral;

import static com.e.lojadecarros.ui.adapter.MainAdapter.INTENT_EXTRA;

public class DetailActivity extends AppCompatActivity {

    private AsyncManager asyncManager;
    private int vehicleGeneralId;
    private ImageView mImageVehicle;
    private VehicleGeneral mVehicleGeneral;
    private TextView mVehicleTitle;
    private TextView mVehicleDescr;
    private TextView price;
    private TextView marca;
    private TextView modelo;
    private TextView km;
    private TextView anoModelo;
    private TextView anoFabr;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        intent.hasExtra(INTENT_EXTRA);
        vehicleGeneralId = intent.getIntExtra(INTENT_EXTRA, 1);

        mImageVehicle = findViewById(R.id.image_vehicle_view);
        mVehicleTitle = findViewById(R.id.vehicle_title);
        mVehicleDescr = findViewById(R.id.vehicle_descr);
        price = findViewById(R.id.price);
        marca = findViewById(R.id.fabricante);
        modelo = findViewById(R.id.modelo);
        km = findViewById(R.id.km);
        anoModelo = findViewById(R.id.anoModelo);
        anoFabr = findViewById(R.id.anoFabr);
        asyncManager = new AsyncManager(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        asyncManager.execute();
    }

    @Override
    protected void onResume(){
        super.onResume();
        populateView();
    }


    private void populateView(){
        mVehicleGeneral = asyncManager.getVehicle(vehicleGeneralId);
        Glide.with(this).load(mVehicleGeneral.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageVehicle);
        mVehicleTitle.setText(mVehicleGeneral.model);
        mVehicleDescr.setText(mVehicleGeneral.version);
        price.setText(getResources().getString(R.string.km) + ": " + mVehicleGeneral.price);
        marca.setText(getResources().getString(R.string.marca) + ": " +mVehicleGeneral.make);
        modelo.setText(getResources().getString(R.string.modelo) + ": " + mVehicleGeneral.model);
        km.setText(getResources().getString(R.string.km) + ": " + mVehicleGeneral.km);
        anoModelo.setText(getResources().getString(R.string.ano_modelo) + ": " + mVehicleGeneral.yearModel);
        anoFabr.setText(getResources().getString(R.string.ano_fabr) + ": " + mVehicleGeneral.yearFab);
    }
}
