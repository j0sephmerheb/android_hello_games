package fr.epita.android.hellogames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import fr.epita.android.hellogamesimageView1.GameDetailsObject
import kotlinx.android.synthetic.main.activity_game_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GameDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        val gameId = getIntent().getStringExtra("gameId").toInt()

        val callback : Callback<GameDetailsObject> = object : Callback<GameDetailsObject> {
            override fun onFailure(call: Call<GameDetailsObject>, t: Throwable) {
                Log.d("xxx", "Callback Problem")
            }

            override fun onResponse(call: Call<GameDetailsObject>, response: Response<GameDetailsObject>) {
                if(response.code() == 200){
                    val res = response.body()
                    val name = res!!.name
                    val type = res!!.type
                    val players = res!!.players
                    val year = res!!.year
                    val description = res!!.description_en
                    val picture = res!!.picture
                    val url = res!!.url

                    gameName.text = name
                    gameType.text = type
                    gamePlayers.text = players.toString()
                    gameYear.text = year.toString()
                    gameDescription.text = description
                    Glide.with(this@GameDetails).load(picture).into(gameImage)

                    btnKnowMore.setOnClickListener {
                        val implicitIntent = Intent(Intent.ACTION_VIEW)
                        implicitIntent.data = Uri.parse(url)
                        startActivity(implicitIntent)
                    }

                }
            }
        }


        /* Web Service */
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val service: WSInterface = retrofit.create(WSInterface::class.java)
        service.getGamesById(gameId).enqueue(callback)
    }
}