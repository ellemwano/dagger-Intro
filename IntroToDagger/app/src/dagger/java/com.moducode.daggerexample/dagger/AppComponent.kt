package com.moducode.daggerexample.dagger

import com.moducode.daggerexample.room.DbRepo
import com.moducode.daggerexample.schedulers.SchedulersBase
import com.moducode.daggerexample.service.EpisodeService
import dagger.Component


@Component(modules = [SchedulerModule::class, DatabaseModule::class, EpisodeServiceModule::class])
interface AppComponent {

    fun dbRepo(): DbRepo

    fun schedulers(): SchedulersBase

    fun episodeService(): EpisodeService
}