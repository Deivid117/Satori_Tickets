package mx.com.satoritech.web

object APIConstants {

    private const val serverPath = "http://192.168.0.6"
    private const val serverPathProduction = ""
    const val wsPath = "api/satori/"

    private const val production: Boolean = false

    fun getServerPath(): String {
        return if(production)  serverPathProduction else serverPath
    }
}