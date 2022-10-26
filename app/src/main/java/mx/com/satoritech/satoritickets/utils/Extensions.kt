package mx.com.satoritech.satoritickets.utils

object Extensions {

    // Validación para números entre 0 y 9 y de 1 a 64 dígitos
    fun String.isNumber():Boolean{
        return this.matches(Regex("[0-9]{1,64}"))
    }
    // Validación para correos
    fun String.isEmail():Boolean{
        return this.matches(Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"))
    }
}