package com.e.lojadecarros.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.lojadecarros.R;
import com.e.lojadecarros.model.VehicleGeneral;
import com.e.lojadecarros.presenter.MainPresenter;
import com.e.lojadecarros.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    public static final String INTENT_EXTRA = "INTENT_EXTRA";

    private List<VehicleGeneral> vehicleGeneralList = new ArrayList<>();
    private Context mContext;
    private boolean connection;

    public MainAdapter(Context context, boolean b){
        this.mContext = context;
    }

    public void setAdapterData(List<VehicleGeneral> vehicleGenerals){
        this.vehicleGeneralList = vehicleGenerals;
        if(!connection && this.vehicleGeneralList.isEmpty()){
        }else{
            notifyDataSetChanged();
        }

    }
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_content_view, viewGroup, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
        VehicleGeneral vehicleGeneral = vehicleGeneralList.get(i);
        Picasso.with(mContext).load(vehicleGeneral.getImage()).into(mainViewHolder.mImageView);
        mainViewHolder.mTitle.setText(vehicleGeneral.getMake() + " " + vehicleGeneral.getModel());
        mainViewHolder.mDescription.setText(vehicleGeneral.getVersion());
        mainViewHolder.mYear.setText(
                + vehicleGeneral.getYearFab()
                + " / "
                + vehicleGeneral.getYearModel());

        mainViewHolder.mPrice.setText(mContext.getResources().getString(R.string.preco_icone) + ": " +vehicleGeneral.getPrice());

        mainViewHolder.mKm.setText(mContext.getResources().getString(R.string.km) + ": " + vehicleGeneral.getKm());

        mainViewHolder.mColor.setText(vehicleGeneral.color);

        mainViewHolder.getVehiclePosition(vehicleGeneral.movieId);
    }

    @Override
    public int getItemCount() {
        return vehicleGeneralList.isEmpty() ? 0 : vehicleGeneralList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mTitle;
        TextView mDescription;
        TextView mYear;
        TextView mPrice;
        TextView mColor;
        TextView mKm;
        int mId;

        void getVehiclePosition(int id){
            this.mId = id;
        }

        MainViewHolder(final View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTitle = itemView.findViewById(R.id.vehicle_title);
            mDescription = itemView.findViewById(R.id.vehicle_descr);
            mYear = itemView.findViewById(R.id.vehicle_year);
            mPrice = itemView.findViewById(R.id.text_price);
            mKm = itemView.findViewById(R.id.text_km);
            mColor = itemView.findViewById(R.id.text_color);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(INTENT_EXTRA, mId);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
