package com.example.room;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.room.database.AppDatabase;
import com.example.room.database.PersonneEntity;
import com.example.room.database.TestData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//Ici on va communiquer avec la base de données
// c'est un singleton lors de son appel
public class AppRepository {
    private static AppRepository instance;
    public LiveData<List<PersonneEntity>> mPersons;
    private AppDatabase database;
    private Executor executor = Executors.newSingleThreadExecutor();

    private AppRepository(Context context) {
        database = AppDatabase.getInstance(context);
        mPersons = getAllPersons();
    }

    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context);
        }
        return instance;
    }

    private LiveData<List<PersonneEntity>> getAllPersons() {
        return database.personneDao().getAll();
    }

    public void addAllPersons(List<PersonneEntity> persons){
        executor.execute(() -> database.personneDao().insertAll(persons));
    }

    public void deleteAllPersons(){
        executor.execute(() -> database.personneDao().deleteAll());
    }


}
