package faculdade.timezen.utils

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class InfoManipulator(
    val pomodoroTextViews: PomodoroTextViews,
    private val dataStore: DataStore<Preferences>
) {

    suspend fun setData(key: Preferences.Key<Any>, value: Any) {
        dataStore.edit {
            it[key] = value
        }
    }

    suspend fun getData(key: Preferences.Key<Any>): Any? {
        val prefs = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {
                it[key]
            }

        return prefs.first()
    }

    fun setText(textView: Int, value: Any) {
        val stringValue = value.toString()
        try {
            when (textView) {
                Text.TEXT_VIEW_COUNTER.ordinal -> pomodoroTextViews.counter.text = stringValue
                Text.TEXT_VIEW_PLAN_STAGE.ordinal -> pomodoroTextViews.planStage.text = stringValue
                Text.TEXT_VIEW_PLAN_NAME.ordinal -> pomodoroTextViews.planName.text = stringValue
            }
        } catch (e: Exception) {
            Log.e("Exception", "$e")
        }
    }

    companion object {
        val START_TIME = longPreferencesKey("startKey")
        val PAUSE_TIME = longPreferencesKey("pauseKey")
        val COUNTING = longPreferencesKey("countingKey")
    }
}