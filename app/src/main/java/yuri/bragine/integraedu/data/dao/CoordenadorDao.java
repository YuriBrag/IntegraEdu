package yuri.bragine.integraedu.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import yuri.bragine.integraedu.data.models.Entity.Coordenador;

@Dao
public interface CoordenadorDao {

    @Query("SELECT * FROM coordenadores WHERE email = :email AND senha = :senha LIMIT 2")
    Coordenador findUserByCredentials(String email, String senha);

    @Insert
    void insert(Coordenador coordenador);

    @Query("SELECT * FROM coordenadores")
    List<Coordenador> getAllCoordenadores();
}
