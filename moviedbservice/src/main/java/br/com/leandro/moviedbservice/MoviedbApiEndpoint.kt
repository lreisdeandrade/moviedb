package br.com.leandro.moviedbservice



enum class MoviedbApiEndpoint(private val endPointName: String, host: String, endpoint: String) {
    PROD("Production", "https://hoadwcs006fst.fastshop.com.br/", "wcs/"),
    DEVELOP("Production_Develop", "http://www.fastshop.com.br/", "wcs/");
    val url: String = host.plus(endpoint)

    override fun toString(): String {
        return endPointName
    }
}
