package utils

enum class DuckType(private val pngName: String) {
    T_SHIRT("t-shirt"),
    SHERIF("sherif"),
    QUEEN("queen"),
    PIRAT("pirat"),
    MEDAL("medal"),
    GLASSES("glasses"),
    GENTELMAN("gentelman"),
    FLAG("flag"),
    BROW("brow"),
    BOW("bow");
    
    fun resource(): String = "ducks/$pngName.png"
}
