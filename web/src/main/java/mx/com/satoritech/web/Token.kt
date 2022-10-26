package mx.com.satoritech.web

class Token {
    companion object{
        @JvmStatic
        var token:String = ""
            set(value) {
                field = value
                retrofitInstance = null
            }

        @JvmStatic
        var retrofitInstance: ApiService? = null

//        @JvmStatic
//        fun setApiToken(token:String){
//            this.token = token
//            retrofitInstance = null
//        }
    }
}