package com.example.dig

import android.app.Application
import android.preference.PreferenceManager
import com.example.dig.data.db.DigDatabase
import com.example.dig.data.network.*
import com.example.dig.data.provider.KeysProvider
import com.example.dig.data.provider.KeysProviderImpl
import com.example.dig.data.repository.DigRepository
import com.example.dig.data.repository.DigRepositoryImpl
import com.example.dig.sawtooth.DignitasHelper
import com.example.dig.sawtooth.DignitasHelperImpl
import com.example.dig.ui.report.ReportViewModelFactory
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

        bind<KeysProvider>() with singleton { KeysProviderImpl(instance()) }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<VotesNetworkDataSource>() with singleton { VotesNetworkDataSourceImpl(instance(), instance()) }
        bind<OpinionData>() with singleton { OpinionDataImpl(instance(), instance()) }
        bind<DigRepository>() with singleton { DigRepositoryImpl(instance(), instance(), instance()) }
        bind<DignitasHelper>() with singleton { DignitasHelperImpl(instance()) }

        bind() from singleton { DigDatabase(instance()) }
        bind() from singleton { instance<DigDatabase>().currentVotesDao() }
        bind() from singleton { ApiService(instance()) }
        bind() from provider { ListViewModelFactory(instance()) }
        bind() from provider { ReportViewModelFactory(instance()) }
        bind() from factory { vote_pos: Array<String> -> ListDetailViewModelFactory(instance(), vote_pos) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}