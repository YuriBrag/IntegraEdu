package yuri.bragine.integraedu.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import yuri.bragine.integraedu.data.dao.CoordenadorDao;
import yuri.bragine.integraedu.data.models.Entity.Coordenador;

@Database(entities = {Coordenador.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CoordenadorDao coordenadorDao();

    private static final String DATABASE_NAME = "integraedu-db";
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
