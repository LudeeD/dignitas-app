package com.example.dig

import android.app.Application
import com.example.dig.data.db.DigDatabase
import com.example.dig.data.network.*
import com.example.dig.data.repository.DigRepository
import com.example.dig.data.repository.DigRepositoryImpl
import com.example.dig.ui.votes.detail.ListDetailViewModelFactory
import com.example.dig.ui.votes.list.ListViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class DigApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@DigApplication))

        bind() from singleton { DigDatabase(instance()) }
        bind() from singleton { instance<DigDatabase>().currentVotesDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiService(instance()) }
        bind<VotesNetworkDataSource>() with singleton { VotesNetworkDataSourceImpl(instance()) }
        bind<OpinionData>() with singleton { OpinionDataImpl(instance()) }
        bind<DigRepository>() with singleton { DigRepositoryImpl(instance(), instance(), instance()) }
        bind() from provider { ListViewModelFactory(instance()) }
        bind() from factory { voteId: Int -> ListDetailViewModelFactory(instance(), voteId) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}