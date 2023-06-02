package tcc.timezen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tcc.timezen.databinding.FragmentListPlanBinding

class ListPlanFragment : Fragment() {
    private lateinit var mBinding: FragmentListPlanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentListPlanBinding.inflate(inflater, container, false)
        mBinding.fabAddNewPlan.setOnClickListener {
            val intent = Intent(activity, FormPlanActivity::class.java)
            startActivity(intent)
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListPlanFragment()
    }
}