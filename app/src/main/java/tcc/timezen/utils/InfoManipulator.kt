package tcc.timezen.utils

//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.emptyPreferences
//import androidx.datastore.preferences.core.longPreferencesKey
import android.util.Log
import tcc.timezen.listeners.TimerListener

class InfoManipulator(
    val listener: TimerListener
    //private val dataStore: DataStore<Preferences>
) {

//    suspend fun setData(key: Preferences.Key<Any>, value: Any) {
//        dataStore.edit {
//            it[key] = value
//        }
//    }

//    suspend fun getData(key: Preferences.Key<Any>): Any? {
//        val prefs = dataStore.data
//            .catch { exception ->
//                if (exception is IOException) {
//                    emit(emptyPreferences())
//                } else {
//                    throw exception
//                }
//            }
//            .map {
//                it[key]
//            }
//
//        return prefs.first()
//    }

//    companion object {
//        val START_TIME = longPreferencesKey("startKey")
//        val PAUSE_TIME = longPreferencesKey("pauseKey")
//        val COUTING = longPreferencesKey("coutingKey")
//    }
}