package com.example.dig.ui.votes.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.dig.R
import com.example.dig.data.db.util.VoteResume

import com.example.dig.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.osmdroid.api.IGeoPoint
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme

import android.graphics.Color
import android.graphics.Paint
import android.preference.PreferenceManager
import android.util.Log
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions


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

            //initRecyclerView(it.toListItems())

            initMap()

            addMarkersOnMap(it)

        })
    }

    private fun initMap(){
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))

        map.setTileSource(TileSourceFactory.MAPNIK)

        val mapController = map.getController();
        mapController.setZoom(17.0);
        val startPoint = GeoPoint( 40.630304, -8.657527);
        mapController.setCenter(startPoint);

    }

    private fun addMarkersOnMap(votes : List<VoteResume>){
        val points = ArrayList<IGeoPoint>()

        votes.forEach{
            points.add(LabelledGeoPoint(it.lat, it.lng, it.status))
        }


        val pt = SimplePointTheme(points, true)


        // create label style
        val textStyle = Paint()
        textStyle.setStyle(Paint.Style.FILL)
        textStyle.setColor(Color.parseColor("#0000ff"))
        textStyle.setTextAlign(Paint.Align.CENTER)
        textStyle.setTextSize(24F)

        val opt = SimpleFastPointOverlayOptions.getDefaultStyle()
            .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MEDIUM_OPTIMIZATION)
            .setRadius(20F).setIsClickable(true).setCellSize(15).setTextStyle(textStyle)

        val sfpo = SimpleFastPointOverlay(pt, opt)

        sfpo.setOnClickListener(SimpleFastPointOverlay.OnClickListener{ pointAdapter: SimpleFastPointOverlay.PointAdapter, i: Int ->
            val click = pointAdapter.get(i) as LabelledGeoPoint
            val pos = arrayOf( click.latitude.toString(), click.longitude.toString())
            val actionDetail = ListFragmentDirections.actionDetail(pos)
            view?.let { Navigation.findNavController(it).navigate(actionDetail) }
        })


        Log.v("DEBUG", "MARKES ADDED")

        map.overlays.add(sfpo)

    }

    private fun List<VoteResume>.toListItems(): List<ListItem>{
        return this.map {
            ListItem(it)
        }
    }

    //private fun initRecyclerView(items: List<ListItem>){
    //    val groupAdapter = GroupAdapter<ViewHolder>().apply {
    //        addAll(items)
    //    }

    //    groupAdapter.setOnItemClickListener{ item, view ->
    //        (item as? ListItem)?.let {
    //            showVoteDetail(it.voteEntry.id, view)
    //        }
    //    }

    //    recyclerView.apply {
    //        layoutManager = LinearLayoutManager(this@ListFragment.context)
    //        adapter = groupAdapter
    //    }


    //}

    //private fun showVoteDetail(voteId: Int, view: View){
    //    val actionDetail = ListFragmentDirections.actionDetail(voteId)
    //    Navigation.findNavController(view).navigate(actionDetail)
    //}

}
