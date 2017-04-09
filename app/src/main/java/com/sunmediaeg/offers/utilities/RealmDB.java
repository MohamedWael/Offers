package com.sunmediaeg.offers.utilities;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by moham on 3/16/2017.
 */

public class RealmDB {

    private static RealmDB instance;
    private final Realm realm;

    protected RealmDB(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public static Realm reInitRealm() {
        return Realm.getDefaultInstance();
    }

    public static RealmDB getInstance(Context context) {
        if (instance == null) {
            instance = new RealmDB(context);
            Logger.d("RealmDB", "new RealmDB");
        }
        Logger.d("RealmDB", "old RealmDB");
        return instance;
    }

    //under development
    private static RealmDB getAsyncInstance(final Context context) {
        Looper.prepare();
        Looper.loop();
        Handler handler = new Handler(Looper.myLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (instance == null) {
                    instance = new RealmDB(context);
                    Logger.d("RealmDB", "new RealmDB");
                }
                Logger.d("RealmDB", "old RealmDB");
            }
        });
        return instance;
    }


    public RealmConfiguration getConfiguration() {
        return realm.getConfiguration();
    }

    public Realm getRealm() {
        return realm;
    }

    public void save(RealmObject object) {
        realm.beginTransaction();
        realm.copyToRealm(object);
        realm.commitTransaction();
    }

    /**
     * Updates an existing RealmObject that is identified by the same
     * io.realm.annotations.PrimaryKey or creates a new copy if no existing
     * object could be found. This is a deep copy or update
     * i.e., all referenced objects will be either copied or updated.
     *
     * @param object
     */
    public void createOrUpdate(RealmObject object) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }


    public <T extends RealmObject> RealmResults<T> getAll(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    public <T extends RealmObject> RealmResults<T> getAllWithPrimaryKey(Class<T> clazz, String primaryKeyVariableName) {
        return realm.where(clazz).greaterThanOrEqualTo(primaryKeyVariableName, -1L).findAll();
    }

    /**
     * @return true if objects was deleted, false otherwise.
     */
    public <T extends RealmObject> boolean deleteAll(Class<T> clazz) {
        realm.beginTransaction();
        boolean deleted = getAll(clazz).deleteAllFromRealm();
        realm.commitTransaction();
        return deleted;

    }

    /**
     * Deletes all objects of the specified class from the Realm.
     */
    public <X extends RealmObject> void deleteAllX(Class<X> clazz) {
        realm.beginTransaction();
        realm.delete(clazz);
        realm.commitTransaction();
    }

    public <T extends RealmObject> void delete(Class<T> clazz, int atIndex) {
        realm.beginTransaction();
        getAll(clazz).get(atIndex).deleteFromRealm();
        realm.commitTransaction();
    }

    public <T extends RealmObject> RealmQuery<T> getQuery(Class<T> clazz) {
        return realm.where(clazz);
    }

}
