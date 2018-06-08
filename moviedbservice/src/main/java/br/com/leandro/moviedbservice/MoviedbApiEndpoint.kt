package br.com.leandro.moviedbservice


enum class MoviedbApiEndpoint(private val endPointName: String, host: String) {
    PROD("Production", "https://api.themoviedb.org/3/");
//    DEVELOP("Develop", "http://www.fastshop.com.br/", "wcs/");

    val url: String = host

    override fun toString(): String {
        return endPointName
    }
}
