package fr.epita.android.hellogames

import fr.epita.android.hellogamesimageView1.GameDetailsObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WSInterface {
    @GET("game/list")
    fun getGamesList() : Call<List<GameObject>>


    @GET("game/details")
    fun getGamesById(@Query("game_id") game_id : Int) : Call<GameDetailsObject>

}