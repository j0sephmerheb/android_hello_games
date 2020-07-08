package fr.epita.android.hellogames

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data : List<GameObject>

        val callback : Callback<List<GameObject>> = object : Callback<List<GameObject>> {
            override fun onFailure(call: Call<List<GameObject>>, t: Throwable) {
                Log.d("xxx", "Callback Problem")
            }

            override fun onResponse(call: Call<List<GameObject>>, response: Response<List<GameObject>>) {
                if(response.code() == 200){
                    val res = response.body()
                    val min = 0
                    val max = res!!.size
                    val srcList: MutableList<String> = ArrayList()
                    val idList: MutableList<Int> = ArrayList()

                    val randomGames = res.shuffled().take(4)

                    for (x in 0..3){
                        val gamePicture = randomGames!![x].picture
                        val gameId = randomGames!![x].id
                        srcList.add(gamePicture)
                        idList.add(gameId)
                    }

                    imageView1.setTag(imageView1.id, idList[0])
                    imageView2.setTag(imageView2.id, idList[1])
                    imageView3.setTag(imageView3.id, idList[2])
                    imageView4.setTag(imageView4.id, idList[3])

                    Glide.with(this@MainActivity).load(srcList[0]).into(imageView1)
                    Glide.with(this@MainActivity).load(srcList[1]).into(imageView2)
                    Glide.with(this@MainActivity).load(srcList[2]).into(imageView3)
                    Glide.with(this@MainActivity).load(srcList[3]).into(imageView4)
                }
            }
        }


        imageView1.setOnClickListener {
            val explicitIntent : Intent = Intent(this@MainActivity, GameDetails::class.java)
            explicitIntent.putExtra("gameId", imageView1.getTag(imageView1.id).toString())
            startActivity(explicitIntent)
        }
        imageView2.setOnClickListener {
            val explicitIntent : Intent = Intent(this@MainActivity, GameDetails::class.java)
            explicitIntent.putExtra("gameId", imageView2.getTag(imageView2.id).toString())
            startActivity(explicitIntent)
        }
        imageView3.setOnClickListener {
            val explicitIntent : Intent = Intent(this@MainActivity, GameDetails::class.java)
            explicitIntent.putExtra("gameId", imageView3.getTag(imageView3.id).toString())
            startActivity(explicitIntent)
        }
        imageView4.setOnClickListener {
            val explicitIntent : Intent = Intent(this@MainActivity, GameDetails::class.java)
            explicitIntent.putExtra("gameId", imageView4.getTag(imageView4.id).toString())
            startActivity(explicitIntent)
        }


        /* Web Service */
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()
        val service: WSInterface = retrofit.create(WSInterface::class.java)
        service.getGamesList().enqueue(callback)
    }
}