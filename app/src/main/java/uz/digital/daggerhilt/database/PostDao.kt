package uz.digital.daggerhilt.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.digital.daggerhilt.model.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePostList(list: List<PostEntity>)

    @Query("SELECT * FROM PostEntity")
    fun getLocalPostList(): Flow<List<PostEntity>>

    @Query("DELETE FROM PostEntity")
    suspend fun clearData()
}