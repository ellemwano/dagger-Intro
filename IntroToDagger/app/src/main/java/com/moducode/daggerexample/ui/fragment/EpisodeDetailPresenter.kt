package com.moducode.daggerexample.ui.fragment

import android.annotation.SuppressLint
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.moducode.daggerexample.data.EpisodeData
import com.moducode.daggerexample.room.DbRepo
import com.moducode.daggerexample.schedulers.SchedulersBase
import com.moducode.daggerexample.ui.fragment.contract.EpisodeDetailContract
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class EpisodeDetailPresenter constructor(private val dbRepo: DbRepo,
                                         private val schedulersBase: SchedulersBase)
    : MvpBasePresenter<EpisodeDetailContract.View>(), EpisodeDetailContract.Actions {

    override fun saveEpisode(episodeData: EpisodeData) {
        dbRepo.checkEpisodeExists(episodeData)
                .subscribeOn(schedulersBase.io())
                .observeOn(schedulersBase.ui())
                .flatMap { list ->
                    if (list.isEmpty()) {
                        Timber.d("Episode ${episodeData.id} did not exist. Inserting...")
                        return@flatMap dbRepo.insertEpisodes(episodeData).toFlowable<Unit>().subscribeOn(schedulersBase.io()).observeOn(schedulersBase.ui())
                    } else {
                        Timber.d("Episode ${episodeData.id} existed. Deleting...")
                        return@flatMap dbRepo.deleteEpisode(episodeData).toFlowable<Unit>().subscribeOn(schedulersBase.io()).observeOn(schedulersBase.ui())
                    }
                }.subscribe(
                        { Timber.d("Insert/delete onNext") },
                        { e -> Timber.e(e) },
                        { Timber.d("Insert/delete complete") }
                )


    }

}