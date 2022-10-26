package mx.com.satoritech.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mx.com.satoritech.database.converters.DBTypeConverters
import mx.com.satoritech.database.dao.UserDao
import mx.com.satoritech.domain.models.User

@Database(entities = [User::class], version = 5)
@TypeConverters(DBTypeConverters::class)
abstract class DBSatoritickets: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        @JvmStatic
        fun newInstance(context:Context): DBSatoritickets {
            return Room.databaseBuilder(context, DBSatoritickets::class.java,"DBSatoritickets")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}