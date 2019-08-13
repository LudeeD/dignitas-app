package com.example.dig.ui.profile

import org.kodein.di.android.x.kodein
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.dig.R
import com.example.dig.ui.base.ScopedFragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance


class ProfileFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: ProfileViewModelFactory by instance()

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProfileViewModel::class.java)

        setupBalance()
        //setupLineChartData()


    }

    private fun setupBalance() = launch(Dispatchers.Main){
        var balance = viewModel.balance.await()

        balance.observe(this@ProfileFragment, Observer {
            if(it==null) return@Observer
            textView.text  = it.value.toString()
        })

    }

    private fun setupLineChartData() = launch(Dispatchers.Main){
        val balances = viewModel.balance.await()
        val entries = ArrayList<Entry>()

        /*balances.observe(this@ProfileFragment, Observer {
            if(it ==null) return@Observer


            for (balance in it){
                entries.add(Entry(balance.timestamp.time.toFloat(), balance.value.toFloat()))
            }

            val set1: LineDataSet
            set1 = LineDataSet(entries, "Balance")
            set1.color = Color.BLUE

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            val data = LineData(dataSets)
            chart.setData(data)
        })
        */

    }

}
