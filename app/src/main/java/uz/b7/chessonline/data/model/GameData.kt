package uz.b7.chessonline.data.model

class GameData(
    val whiteId:String,
    val blackId:String,
    val data:ArrayList<ArrayList<Int>>,
    val isWhiteTurn: Boolean
)