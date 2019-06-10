package com.moducode.daggerexample.ui.fragment

import android.annotation.SuppressLint
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.moducode.daggerexample.room.DbRepo
import com.moducode.daggerexample.schedulers.SchedulersBase
import com.moducode.daggerexample.service.EpisodeService
import com.moducode.daggerexample.ui.fragment.contract.EpisodeListContract
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class EpisodeListPresenter constructor(private val episodeService: EpisodeService,
                                               private val schedulers: SchedulersBase,
                                               private val dbRepo: DbRepo)
    : MvpBasePresenter<EpisodeListContract.View>(), EpisodeListContract.Actions {

    override fun fetchEpisodes() {
        episodeService.getEpisodes()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe({ result -> ifViewAttached { it.setData(result) } },
                        { error ->
                            ifViewAttached {
                                it.showError(error)
                                Timber.e(error)
                            }
                        }
                )
    }

    override fun fetchFavourites() {
        dbRepo.getFavEpisodes()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                        { next -> ifViewAttached { it.setData(next) } },
                        { e ->
                            Timber.e(e)
                            ifViewAttached { it.showError(e) }
                        },
                        { Timber.d("favourites fetch complete") }
                )
    }
}