package com.example.dig.ui.votes.list

import com.example.dig.R
import com.example.dig.data.db.util.VoteResume
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_votes.*

//https://www.youtube.com/watch?v=yRHYx8dkUd4
class ListItem(
    val voteEntry : VoteResume
):  Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_title.text = voteEntry.title
            textView_lat.text = voteEntry.lat.toString()
            textView_lng.text = voteEntry.lng.toString()
            textView_status.text = voteEntry.status
        }
    }

    override fun getLayout() = R.layout.item_votes

}