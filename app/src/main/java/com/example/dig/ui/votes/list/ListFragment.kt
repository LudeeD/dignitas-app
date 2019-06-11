package com.example.dig.ui.votes.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.dig.R
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.db.util.VoteResume
import com.example.dig.data.network.ApiService
import com.example.dig.data.network.ConnectivityInterceptorImpl
import com.example.dig.data.network.VotesNetworkDataSourceImpl
import com.example.dig.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ListFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: ListViewModelFactory by instance();

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ListViewModel::class.java)

        bindUI()

    }

    private fun bindUI() = launch(Dispatchers.Main){
        val votes = viewModel.votes.await()

        votes.observe(this@ListFragment, Observer {
            if ( it == null) return@Observer
            group_loading.visibility = View.GONE

            initRecyclerView(it.toListItems())
        })
    }

    private fun List<VoteResume>.toListItems(): List<ListItem>{
        return this.map {
            ListItem(it)
        }
    }

    private fun initRecyclerView(items: List<ListItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        groupAdapter.setOnItemClickListener{ item, view ->
            (item as? ListItem)?.let {
                showVoteDetail(it.voteEntry.id, view)
            }
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = groupAdapter
        }


    }

    private fun showVoteDetail(voteId: Int, view: View){
        val actionDetail = ListFragmentDirections.actionDetail(voteId)
        Navigation.findNavController(view).navigate(actionDetail)
    }

}
