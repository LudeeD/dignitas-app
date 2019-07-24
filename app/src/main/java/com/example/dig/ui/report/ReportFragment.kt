package com.example.dig.ui.report

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.example.dig.R
import com.example.dig.data.db.entity.Location
import com.example.dig.data.db.entity.Status
import com.example.dig.data.db.entity.Vote
import com.example.dig.ui.base.ScopedFragment
import com.squareup.okhttp.Dispatcher
import kotlinx.android.synthetic.main.report_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ReportFragment : ScopedFragment(),  KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: ReportViewModelFactory by instance()

    private lateinit var viewModel: ReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.report_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ReportViewModel::class.java)

        button_submit_vote.setOnClickListener { onSubmitCicked() }

        viewModel.status.observe(this@ReportFragment, Observer {
            if (it==null) return@Observer

            Toast.makeText(context, it, Toast.LENGTH_LONG).show()

        })
    }

    fun onSubmitCicked() = launch(Dispatchers.IO){
        val v = Vote("", submit_title.text.toString(), submit_info.text.toString(),
            Location(submit_lat.text.toString().toDouble(),submit_lng.text.toString().toDouble(), submit_dir.text.toString().toDouble())
            , Status("",0,0,""),
            0)
        viewModel.postVote(v)

    }
}
