package tcc.timezen.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tcc.timezen.R
import tcc.timezen.dao.PlanDao
import tcc.timezen.databinding.ActivitySelectedPlanBinding
import tcc.timezen.model.Pomodoro
import tcc.timezen.utils.InfoManipulator
import tcc.timezen.utils.PomodoroTextViews
import tcc.timezen.utils.Translator

class SelectedPlanActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySelectedPlanBinding
    private lateinit var mPomodoro: Pomodoro

    private val dao = PlanDao()
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

    private fun setTextViewTexts() {
        mBinding.tvPlanName.text = mPomodoro.plan().name()
        mBinding.tvCountTime.text = t.timeStringFromLong(mPomodoro.plan().getWorkTime())
    }

    private fun getPlanFromDao() {
        val extras = intent.extras
        mPomodoro = Pomodoro(dao.getPlan(extras!!.getInt("id")), InfoManipulator(
            PomodoroTextViews(
                planName = findViewById(R.id.tv_plan_name),
                planStage = findViewById(R.id.tv_plan_stage),
                counter = findViewById(R.id.tv_count_time)
            )
        ))
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
            if (mPomodoro.isRunning()) {
                mPomodoro.stop()
                setTextViewTexts()
                mBinding.buttonStartPomodoro.text = "iniciar"
            }
        }
    }

    override fun onDestroy() {
        mPomodoro.start()
        mPomodoro.stop()
        super.onDestroy()
    }
}