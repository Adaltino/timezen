package tcc.timezen.activities

import android.Manifest
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
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
            mBinding.tvPlanStage.text = "Seja produtivo!"
            notifyStageChange("vamos nessa, jovem")
        } else {
            mBinding.tvPlanStage.text = "Agora descanse"
            notifyStageChange("descansa, jovem")
        }
    }

    override fun onSessionChange(sessionsLeft: Int, isOnWorkStage: Boolean) {
        mBinding.tvSessionLeft.text = "${sessionsLeft} sessoes restantes~"
        if (!isOnWorkStage) {
            Log.d(TAG, " ID: ${dbTimezen.getPlanId(mPomodoro.plan().name())} " +
                    "Plano: ${mPomodoro.plan().name()} | " +
                    "Work: ${t.toLimitedMinutes(mPomodoro.plan().getWorkTime())} | " +
                    "Break: ${t.toLimitedMinutes(mPomodoro.plan().getBreakTime())} | " +
                    "Task: $sessionsLeft | " +
                    "Category: ${mPomodoro.plan().category()} | " +
                    "ImportanceLevel: ${mPomodoro.plan().importanceLevel()} | " +
                    "isWorkStage: $isOnWorkStage")

            if (dbTimezen.hasNameExistsInReport(mPomodoro.plan().name())) {
                val workValue = dbTimezen.getWorkInReport(mPomodoro.plan().name())
                val value = workValue + t.toLimitedMinutes(mPomodoro.plan().getWorkTime()).toInt()
                dbTimezen.updateWorkInReport(mPomodoro.plan().name(), value)
                /**
                 * 1 - h 1 - d 1 : insert correr + workTime // 1 // workValue = 1
                 * 2 - h 1 - d 1 : update(valor) valor = workValue + workTime // 1 + 1 // workValue = 2
                 * 3 - h 1 - d 1 : update(valor) valor = workValue + workTime // 2 + 1 // workValue = 3
                 * 4 - h 1 - d 1 : update(valor) valor = workValue + workTime // 3 + 1 // workValue = 4
                 * 5 - h 1 - d 1 : update(valor) valor = workValue + workTime // 4 + 1 // workValue = 5
                 */
                /**
                 * 1 - h 1 - d 1 : update(valor) valor = workValue + workTime // 5 + 1 // workValue = 6
                 * 2 - h 1 - d 1 : update(valor) valor = workValue + workTime // 6 + 1 // workValue = 7
                 * 3 - h 1 - d 1 : update(valor) valor = workValue + workTime // 7 + 1 // workValue = 8
                 * 4 - h 1 - d 1 : update(valor) valor = workValue + workTime // 8 + 1 // workValue = 9
                 * 5 - h 1 - d 1 : update(valor) valor = workValue + workTime // 9 + 1 // workValue = 10
                 */
                /**
                 * 1 - h 45 - d 1 : insert correr + workTime // 45 // workValue = 45
                 * 2 - h 45 - d 1 : update(valor) valor = workValue + workTime // 45 + 45 // workValue = 90
                 * 3 - h 45 - d 1 : update(valor) valor = workValue + workTime // 90 + 45 // workValue = 135
                 * 4 - h 45 - d 1 : update(valor) valor = workValue + workTime // 135 + 45 // workValue = 180
                 * 5 - h 45 - d 1 : update(valor) valor = workValue + workTime // 180 + 45 // workValue = 225
                 */
                Log.d(TAG,"Sim existe, Report Plano: ${mPomodoro.plan().name()} Work: $value")
            } else {
                Log.d(TAG,"Não existe, vou criar")
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