package uz.digital.daggerhilt.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract  class PostDatabase : RoomDatabase() {
    abstract val dao: PostDao
}