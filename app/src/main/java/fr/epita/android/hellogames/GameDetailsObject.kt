package fr.epita.android.hellogamesimageView1

data class GameDetailsObject(val id : Int,
                             val name : String,
                             val picture : String,
                             val description_en : String,
                             val players : Int,
                             val year : Int,
                             val type : String,
                             val url : String
                             )