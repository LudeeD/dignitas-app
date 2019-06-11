package com.example.dig.ui.votes.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.example.dig.R
import com.example.dig.data.db.entity.Opinion
import com.example.dig.internal.VoteIdNotFound
import com.example.dig.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.list_detail_fragment.*
import kotlinx.android.synthetic.main.list_detail_fragment.textView_title
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.factory

class ListDetailFragment : ScopedFragment(), KodeinAware {


    override val kodein by kodein()

    private val viewModelFactoryInstanceFactory
            : (( Int ) -> ListDetailViewModelFactory) by factory()

    private lateinit var viewModel: ListDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var _view = inflater.inflate(R.layout.list_detail_fragment, container, false)
        return  _view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let{
            ListDetailFragmentArgs.fromBundle(it)
        }


        val voteId = safeArgs?.voteId ?: throw VoteIdNotFound()

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(voteId)).get(ListDetailViewModel::class.java)

        button_vote_true.setOnClickListener { onVoteTrueButtonClicked() }
        button_vote_false.setOnClickListener { onVoteFalseButtonClicked() }
        bindUI()
    }

    //private fun onVoteTrueButtonClicked()  = launch(Dispatchers.IO){
    //    viewModel.postOpinion(Opinion(1, -12, 1))
    //}


    private fun onVoteTrueButtonClicked(){
        Toast.makeText(context, "True" + editText_value.text, Toast.LENGTH_LONG).show()
    }

    private fun onVoteFalseButtonClicked(){
        Toast.makeText(context, "FAlSE" + editText_value.text, Toast.LENGTH_LONG).show()
    }


    private fun bindUI() = launch(Dispatchers.Main){
        val vote = viewModel.vote.await()

        vote.observe(this@ListDetailFragment, Observer { voteEntry ->
            if (voteEntry == null) return@Observer

            group_loading.visibility = View.GONE

            textView_title.text = voteEntry.title
            textView_id.text = voteEntry.id.toString()
            textView_info.text = voteEntry.info
            textView_timestamp.text = voteEntry.timestamp.toString()
            textView_lat.text = voteEntry.lat.toString()
            textView_lng.text = voteEntry.lng.toString()
            textView_direction.text = voteEntry.dir.toString()
            textView_status.text = voteEntry.status
            textView_agree.text = voteEntry.true_votes.toString()
            textView_disagree.text = voteEntry.false_votes.toString()
            textView_verdict.text = voteEntry.verdict.toString()

        })


    }

}
