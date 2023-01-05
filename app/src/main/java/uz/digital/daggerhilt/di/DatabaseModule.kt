package uz.digital.daggerhilt.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.digital.daggerhilt.database.PostDao
import uz.digital.daggerhilt.database.PostDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePostDatabase(
        @ApplicationContext context: Context
    ): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "Post.db"
        ).build()
    }
    @Provides
    @Singleton
    fun providePostDao(database: PostDatabase): PostDao {
        return database.dao
    }
}