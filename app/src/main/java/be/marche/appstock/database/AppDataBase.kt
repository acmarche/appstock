package be.marche.appstock.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.marche.appstock.entity.Categorie
import be.marche.appstock.entity.Produit

const val DATABASE_NAME = "appstock"

@Database(
    entities = [Produit::class, Categorie::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bottinDao(): StockDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
