package uz.b7.chessonline.data.model

data class UserData(
    val name:String,
    val id:String,
    var isActive:Boolean,
    var rating:Int
)
