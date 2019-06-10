package com.moducode.daggerexample.service

import com.moducode.daggerexample.data.EpisodeData
import io.reactivex.Single
import retrofit2.http.GET

interface EpisodeService {

    @GET("shows/618/episodes")
    fun getEpisodes(): Single<List<EpisodeData>>

}