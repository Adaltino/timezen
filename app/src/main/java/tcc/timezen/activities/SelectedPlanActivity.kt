package tcc.timezen.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import tcc.timezen.R
import tcc.timezen.database.DBTimezen
import tcc.timezen.databinding.ActivitySelectedPlanBinding
import tcc.timezen.listeners.TimerListener
import tcc.timezen.model.Pomodoro
import tcc.timezen.utils.InfoManipulator
import tcc.timezen.utils.PomodoroTextViews
import tcc.timezen.utils.Translator

class SelectedPlanActivity : AppCompatActivity(), TimerListener {

    private lateinit var mBinding: ActivitySelectedPlanBinding
    private lateinit var mPomodoro: Pomodoro
    private lateinit var dbTimezen: DBTimezen

    private val t = Translator()

    /*
    * this activity can cause a crash if pomodoro is not started:
    *
    * java.lang.RuntimeException: Unable to destroy activity
    * {tcc.timezen/tcc.timezen.activities.SelectedPlanActivity}: kotlin.UninitializedPropertyAccessException:
    * lateinit property currentTask has not been initialized
    *
    * mPomodoro.start() inside onDestroy() method actually works to stop this from happening
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySelectedPlanBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        getPlanFromDao()
        setTextViewTexts()
        initializeComponents()
    }

    override fun onSessionChange(s: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("TimeZen")
            .setContentText(s)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        try {
            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
        } catch (e: Exception) {
            Log.e("notification", e.toString())
        }
    }

    private fun setTextViewTexts() {
        mBinding.tvPlanName.text = mPomodoro.plan().name()
        mBinding.tvCountTime.text = t.timeStringFromLong(mPomodoro.plan().getWorkTime())
    }

    private fun getPlanFromDao() {
        dbTimezen = DBTimezen(this)
        val extras = intent.extras
        mPomodoro = Pomodoro(
            dbTimezen.getPlanById(extras!!.getInt("id")), InfoManipulator(
                PomodoroTextViews(
                    planName = findViewById(R.id.tv_plan_name),
                    planStage = findViewById(R.id.tv_plan_stage),
                    counter = findViewById(R.id.tv_count_time)
                ),
                this
            )
        )
    }

    private fun initializeComponents() {
        mBinding.buttonStartPomodoro.setOnClickListener {
            if (!mPomodoro.isRunning()) {
                mPomodoro.start()
                mBinding.buttonStartPomodoro.text = "pausar"
            } else {
                mPomodoro.pause()
                mBinding.buttonStartPomodoro.text = "iniciar"
            }
        }

        mBinding.buttonResetPomodoro.setOnClickListener {
            mPomodoro.stop()
            setTextViewTexts()
            mBinding.buttonStartPomodoro.text = "iniciar"
        }
    }

    override fun onDestroy() {
        mPomodoro.start()
        mPomodoro.stop()
        super.onDestroy()
    }

    private companion object {
        private const val CHANNEL_ID = "TimeZen"
        private const val NOTIFICATION_ID: Int = 1
    }
}