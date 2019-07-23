package com.e.lojadecarros.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.RxRoom;

import com.e.lojadecarros.model.VehicleGeneral;
import com.e.lojadecarros.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AsyncManager extends RxRoom {

    private Context mContext;
    private VehicleDao vehicleDao;

    private Observable<List<VehicleGeneral>> mObservable;
    private Observer<List<VehicleGeneral>> mObserver;

    private MainPresenter.View mainPresenter;
    private MainPresenter.Detail mainDetail;

    public AsyncManager(MainPresenter.View view, Context context){
        this.mainPresenter = view;
        this.mContext = context;
        if(mContext != null){
           initDatabase(mContext);
        }
    }

    public AsyncManager(MainPresenter.Detail mainDetail, Context context){
        this.mainDetail = mainDetail;
        this.mContext = context;
        if(mContext != null){
            initDatabase(mContext);
        }
    }

    private void initDatabase(Context context){
        VehicleDatabase vehicleDatabase = VehicleDatabase.getDatabase(context);
        this.vehicleDao = vehicleDatabase.vehicleDao();
    }

//    public void insertVehicles(List<VehicleGeneral> vehicleGenerals){
//         Observable.just(vehicleDao)
//                .observeOn(Schedulers.io())
//                .subscribe(dao -> {
//                    dao.insert(vehicleGenerals);
//                });
//    }

//    public void getListVehicle(){
//
//        List<VehicleGeneral> vehicleGenerals = new ArrayList<>();
//
//        Observable.just(vehicleDao)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<VehicleDao>() {
//
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(VehicleDao vehicleDao) {
//                vehicleGenerals.addAll(vehicleDao.getAll());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                mainPresenter.getVehicleList(vehicleGenerals);
//            }
//        });
//    }

    public void getVehiclesList(List<VehicleGeneral> vehicles){

        Observable.just(vehicleDao)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VehicleDao>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.i("RX_JAVA", "onSubscribe " + d);
            }

            @Override
            public void onNext(VehicleDao vehicleDao) {
                Log.i("RX_JAVA", "onNext ");
                vehicleDao.insert(vehicles);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("RX_JAVA", "onError " + e);
            }

            @Override
            public void onComplete() {
                Log.i("RX_JAVA", "onComplete ");
                mainPresenter.getVehicleList(vehicleDao.getAll());
            }
        });


//        Observable.just(vehicleDao)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(dao -> {
//                   mainPresenter.getVehicleList(dao.getAll());
//                });
    }

    public void getVehicle(int id){
        Observable.just(vehicleDao)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VehicleDao>() {

            VehicleGeneral vehicleGeneral = new VehicleGeneral();

            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(VehicleDao vehicleDao) {
                vehicleGeneral = vehicleDao.getVehicle(id);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {
                mainDetail.getVehicle(vehicleGeneral);
            }
        });

//        Observable.just(vehicleDao)
//                .subscribeOn(Schedulers.io())
//                .map(vehicleDao1 -> vehicleDao1.insert())
//                .observeOn(AndroidSchedulers.mainThread()).
    }

    private void initiateObservable(List<VehicleGeneral> vehicleGenerals){
        mObservable = Observable.create(new ObservableOnSubscribe<List<VehicleGeneral>>() {
            @Override
            public void subscribe(ObservableEmitter<List<VehicleGeneral>> emitter) throws Exception {

                emitter.onNext(vehicleGenerals);

                emitter.onComplete();
            }
        });
    }

    private void initiateObserver(){
        mObserver = new Observer<List<VehicleGeneral>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("RX_JAVA", "onSubscribe " + d);
            }

            @Override
            public void onNext(List<VehicleGeneral> vehicleGenerals) {
                Log.i("RX_JAVA", "onNext ");
                vehicleDao.insert(vehicleGenerals);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("RX_JAVA", "onError " + e);
            }

            @Override
            public void onComplete() {
                Log.i("RX_JAVA", "onComplete ");
                mainPresenter.getVehicleList(vehicleDao.getAll());
            }
        };
    }

    public void initiateRequest(List<VehicleGeneral> vehicleGenerals){
        initiateObservable(vehicleGenerals);
        initiateObserver();
        mObservable.subscribe(mObserver);
    }
}
