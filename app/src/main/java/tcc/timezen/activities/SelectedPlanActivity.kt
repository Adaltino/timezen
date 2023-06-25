package tcc.timezen.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
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
            mBinding.tvPlanStage.text = "Hora do foco!"
            notifyStageChange("Hora do foco!")
        } else {
            mBinding.tvPlanStage.text = "Agora descanse."
            notifyStageChange("Agora descanse.")
        }
    }

    override fun onSessionChange(sessionsLeft: Int, isOnWorkStage: Boolean) {
        mBinding.tvSessionLeft.text = "${sessionsLeft} sessoes restantes"
        if (!isOnWorkStage) {
            if (dbTimezen.hasNameExistsInReport(mPomodoro.plan().name())) {
                val workValue = dbTimezen.getWorkInReport(mPomodoro.plan().name())
                val value = workValue + t.toLimitedMinutes(mPomodoro.plan().getWorkTime()).toInt()
                dbTimezen.updateWorkInReport(mPomodoro.plan().name(), value)
            } else {
                dbTimezen.insertReport(
                    dbTimezen.getPlanId(mPomodoro.plan().name()),
                    mPomodoro.plan().name(),
                    t.toLimitedMinutes(mPomodoro.plan().getWorkTime()).toInt(),
                    t.toLimitedMinutes(mPomodoro.plan().getBreakTime()).toInt(),
                    mPomodoro.plan().getTaskQuantity(),
                    mPomodoro.plan().category(),
                    mPomodoro.plan().importanceLevel()
                )
            }
        }
    }

    private fun notifyStageChange(notificationText: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("TimeZen")
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setTimeoutAfter(30000)

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
                mBinding.buttonStartPomodoro.text = "Pausar"
            } else {
                mPomodoro.pause()
                mBinding.buttonStartPomodoro.text = "Iniciar"
            }
        }

        mBinding.buttonResetPomodoro.setOnClickListener {
            if (mPomodoro.isRunning()) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Tem certeza que quer parar e resetar o temporizador?")
                    .setCancelable(false)
                    .setNegativeButton("Sim") { dialog, _ ->
                        restartPomodoro()
                        dialog.dismiss()
                    }
                    .setPositiveButton("Não") { dialog, _ ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            } else {
                restartPomodoro()
            }
        }
    }

    private fun restartPomodoro() {
        mBinding.buttonStartPomodoro.text = "Iniciar"
        mPomodoro.start(this)
        mPomodoro.stop()
        setTextViewTextsOnActivityCreate()
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