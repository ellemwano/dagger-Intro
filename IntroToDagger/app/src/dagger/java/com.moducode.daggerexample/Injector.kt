package com.moducode.daggerexample

import com.moducode.daggerexample.dagger.ContextModule
import com.moducode.daggerexample.dagger.DaggerAppComponent
import com.moducode.daggerexample.ui.fragment.EpisodeDetailFragment
import com.moducode.daggerexample.ui.fragment.EpisodeDetailPresenter
import com.moducode.daggerexample.ui.fragment.EpisodeListFragment
import com.moducode.daggerexample.ui.fragment.EpisodeListPresenter


fun EpisodeDetailFragment.buildPresenter(): EpisodeDetailPresenter {
    val component = DaggerAppComponent.builder()
            .contextModule(ContextModule(requireContext()))
            .build()

    return EpisodeDetailPresenter(component.dbRepo(), component.schedulers())
}


fun EpisodeListFragment.buildPresenter(): EpisodeListPresenter {
    val component = DaggerAppComponent.builder()
            .contextModule(ContextModule(requireContext()))
            .build()

    return EpisodeListPresenter(component.episodeService(), component.schedulers(), component.dbRepo())
}




