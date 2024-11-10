import java.io.File

//Codigo colores
const val RESET = "\u001B[0m"
//De fondo

const val BG_BLACK = "\u001B[40m"
//De Letra

const val CYAN = "\u001B[36m"
const val UNDERLINE = "\u001B[4m"
const val GREEN = "\u001B[32m"
const val PURPLE = "\u001B[35m"
//variables globales
val file = File("ultimo_intento.txt")
val numintentosmax=3




fun numaleatorio():Int{ //hace el numero Secreto

 val numeros = (1..6).shuffled().toList()
    var numnuevo = ""
    for (i in 0 until 4){
        numnuevo+=numeros[i]
    }

    return numnuevo.toInt()

}

fun numresultado(numintento:String, numsecreto:String){//Calcula aciertos y coincidencias

    var acierto= 0
    var coincidente = 0

    for (i in 0 until 4){
        if (numintento[i] == numsecreto[i]){//Si concuerdan numero y posicion se suma acierto
            acierto++
        } else if (numsecreto.contains(numintento[i])){// si no concuerda en posicion se suma coincidente
                coincidente++
            }
    }

    println("${GREEN}${UNDERLINE}Aciertos${RESET}${BG_BLACK}${GREEN} $acierto  ${PURPLE}${UNDERLINE}Coincidencias${RESET}${PURPLE}${BG_BLACK} $coincidente${CYAN}")
}


fun validacion(numero: String) {//Numero es la entrada del usuario
    if (numero.length == 4) {
        for (i in numero) {
            if (i !in '1' .. '6') {
                println("Por favor, solo números del 1 al 6. ")
                return  // Termina la función si encuentra un número inválido
            }
        }
        // Si todos los números son válidos, no hace nada
    } else if (numero.isBlank()){//Asi no da error si introducen un enter en blanco
        println("No has introducido ningun numero")
    } else {
        println("El número tiene que tener exactamente 4 CIFRAS.")

        return
    }
}

fun jugar() { //jueg
    var contador = 0
    var intentos = numintentosmax
    val randomnumber=numaleatorio().toString()//esta calculado en otra funcion, es el numero de la solucion
    file.writeText("$randomnumber\n")
    while (contador!=intentos){

        val numintentos= intentos-contador
        println("Escribe un numero de 4 cifras con digitos del 1 al 6 sin repetir. Tienes $numintentos intentos")
        val entradanumero= readln()
        val norepetidos=entradanumero.toSet()// Valida que el usuario no repita numeros
        if (norepetidos.size == 4) {
            validacion(entradanumero) //Valida si la entrada cumple lo solicitado
            contador++
            if (entradanumero.any { it !in '1'..'6' } || entradanumero.length != 4 || entradanumero.isBlank()) {
                intentos++
            } else {//Este if lo hago para que no se resten intentos si el numero que introducimos no cumple lo que se pide
                file.appendText("$entradanumero\n")
                numresultado(entradanumero, randomnumber)
            }
            if (entradanumero == randomnumber) {
                println("Felicidades !!!  Has adivinado el numero secreto")
                break
            }
        } else{
            println("Entrada no valida. Recuerda no usar numeros repetidos")
        }
    }

}

fun leerlineultintento(linea:Int):String {//lee las lineas que vamos a guardar

    val lineas = file.readLines()

    return if (linea in 1 until numintentosmax+2 && linea <= lineas.size) {
        lineas[linea - 1]//Se le resta 1 porque iniciamos en 0
    } else {
        "No realizado"
    }
}
fun ultimointento(){ //Guarda el ultimo intento

    println("-------------------")
    println("Ultimo Intento")
    println("-------------------")
    if (file.exists()){//si encuentra el intento lo imprime
        print("Numero Secreto:")
        println(leerlineultintento(1))
        for (i in 2..numintentosmax+1){//selecciona las lineas de los intentos realizados y las imprime
            println("Numero intento:" + leerlineultintento(i))
    }
    } else {
        println("No hay ningun intento guardado")

    }
    println("-------------------")
}
fun main (){
    var n =0
    while (n !=1){
        println("${CYAN}${BG_BLACK}")
        println("1. Jugar")
        println("2. Ver traza de ultimo intento")
        println("3. Salir\n")
        print("${UNDERLINE}Opción:${RESET}${CYAN}${BG_BLACK}\n")
        val entrada =readln()
        println()
        if (entrada!="1" && entrada!="2" && entrada!="3"){
            println("Porfavor, selecciona una de las opciones disponibles")
        }else {
            val seleccion = entrada.toInt()

            when (seleccion) {//menu del juego
                1 -> jugar()
                2 -> ultimointento()
                3 -> {
                    n = 1
                    println("Nos vemos pronto!!!")
                    file.delete()//borra el ultimo intento al salir
                }

                else -> println("Porfavor, selecciona una de las opciones disponibles")
            }
        }
    }
}