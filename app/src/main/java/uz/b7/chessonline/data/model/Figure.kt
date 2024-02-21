package uz.b7.chessonline.data.model

data class Figure(
    val type: Int, // for white 1-8 black 9-16 0 is void
    val isWhite: Boolean,
    val name:String
)