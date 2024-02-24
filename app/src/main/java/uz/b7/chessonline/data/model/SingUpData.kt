package uz.b7.chessonline.data.model

data class SingUpData(
    val name:String,
    val phone:String,
    val password:String,
    var rating:Int=0,
    var isActive:Boolean=true
)