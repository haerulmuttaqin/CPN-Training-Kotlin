package id.haerulmuttaqin.data.di

import androidx.room.Room
import id.haerulmuttaqin.data.local.AppDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module { 
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dummy_api".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), AppDatabase::class.java,"news.db")
//            .openHelperFactory(factory)
            .build()
    }
    single { 
        get<AppDatabase>().postItemDao()
        get<AppDatabase>().remoteKeysDao()
    }
}
