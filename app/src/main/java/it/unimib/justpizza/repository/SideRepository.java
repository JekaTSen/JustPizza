package it.unimib.justpizza.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.justpizza.database.AppDatabase;
import it.unimib.justpizza.database.SideDAO;
import it.unimib.justpizza.model.Side;

public class SideRepository {

    private final SideDAO sideDao;
    private final ExecutorService executorService;

    public SideRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.sideDao = db.sideDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Side>> getAllSides() {
        return sideDao.getAllSides();
    }

    public void insertAllSides(List<Side> sides) {
        executorService.execute(() -> sideDao.insertAll(sides));
    }

    public void deleteAllSides() {
        executorService.execute(sideDao::deleteAll);
    }
}