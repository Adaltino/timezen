package tcc.timezen.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import tcc.timezen.R
import tcc.timezen.database.DBTimezen
import tcc.timezen.databinding.ActivitySelectedPlanBinding
import tcc.timezen.listeners.TimerListener
import tcc.timezen.model.Pomodoro
import tcc.timezen.utils.Translator

class SelectedPlanActivity : AppCompatActivity(), TimerListener {

    private lateinit var mBinding: ActivitySelectedPlanBinding
    private lateinit var mPomodoro: Pomodoro
    private lateinit var dbTimezen: DBTimezen

    private val t = Translator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySelectedPlanBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        dbTimezen = DBTimezen(this)

        getPlanToDisplay()
        setTextViewTextsOnActivityCreate()
        initializeComponents()
    }

    override fun onTick(timeString: String) {
        mBinding.tvCountTime.text = timeString
    }

    override fun onStageChange(isOnWorkStage: Boolean) {
        if (isOnWorkStage) {
            mBinding.tvPlanStage.text = "hora da atividade carai"
            notifyStageChange("vamos nessa, jovem")
        } else {
            mBinding.tvPlanStage.text = "descansa nessa porra"
            notifyStageChange("descança, jovem")
        }
    }

    override fun onSessionChange(sessionsLeft: Int) {
        mBinding.tvSessionLeft.text = "${sessionsLeft} sessoes restantes~"
    }

    private fun notifyStageChange(notificationText: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("TimeZen")
            .setContentText(notificationText)

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

    override fun onFinish() {
        mBinding.tvPlanStage.text = "Pomodoro terminado"
        mBinding.tvCountTime.text = t.timeStringFromLong(0)
    }

    private fun setTextViewTextsOnActivityCreate() {
        mBinding.tvPlanName.text = mPomodoro.plan().name()
        mBinding.tvCountTime.text = t.timeStringFromLong(mPomodoro.plan().getWorkTime())
        mBinding.tvSessionLeft.text = "${mPomodoro.plan().getTaskQuantity()} sessoes restantes~"
    }

    private fun getPlanToDisplay() {
        val extras = intent.extras
        mPomodoro = Pomodoro(
            plan = dbTimezen.getPlanById(extras!!.getInt("id")),
        )
    }

    private fun initializeComponents() {
        mBinding.buttonStartPomodoro.setOnClickListener {
            if (!mPomodoro.isRunning()) {
                mPomodoro.start(this)
                mBinding.buttonStartPomodoro.text = "pausar"
            } else {
                mPomodoro.pause()
                mBinding.buttonStartPomodoro.text = "iniciar"
            }
        }

        mBinding.buttonResetPomodoro.setOnClickListener {
            mPomodoro.stop()
            setTextViewTextsOnActivityCreate()
            mBinding.buttonStartPomodoro.text = "iniciar"
        }
    }

    override fun onDestroy() {
        mPomodoro.start(this)
        mPomodoro.stop()
        super.onDestroy()
    }

    private companion object {
        private const val CHANNEL_ID = "TimeZen"
        private const val NOTIFICATION_ID: Int = 1
    }
}