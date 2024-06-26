package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    entities = [FavouriteVacancy::class]
)
@TypeConverters(ConvertType::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouritesVacanciesDao(): FavouritesVacanciesDao
}
