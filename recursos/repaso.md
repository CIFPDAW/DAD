MATERIAL DE REPASO: ELEMENTOS DE PROGRAMACIÓN EN JAVA

Nota: descargar OpenJDK (carpeta .zip) y descomprimir la carpeta. Podemos compilar los códigos fuente de los ejemplos con el comando javac abriendo una consola CMD y situándonos en el directorio bin que contiene la carpeta descomprimida.

Java es un lenguaje multiplataforma de programación orientado a objetos y basado en clases que permite y facilita la modularización de código.

En Java creamos y utilizamos objetos definidos por su correspondiente clase. Cada clase cuenta con una serie de atributos o propiedades y métodos específicamente definidos para esa clase. Ejemplo de contenido del archivo fuente Counter.java para crear y definir la clase Counter:

public class Counter { // class Name always starting by a capital letter;

/* public access allows to invoke Counter.class from any other class, in order to create type Counter objects;

This source file must be named as Counter.java (otherwise compilation process won't work);

In order to compile this code, open CMD and move to the OpenJDK bin directory (which contains javac.exe file). Once there execute the following:

javac Counter.java

*/

public int x=0;  //define and initialize the attribute x (type numeric integer) for class Counter;

public void inc_x() {x=x+1;}  // define the method inc_x() for class Counter;

// Compile from CMD by executing javac Counter.java in order to generate the corresponding Counter.class file;

}

Según podemos observar en el código anterior, la clase Counter queda definida de manera que cuenta con:

    Un atributo o propiedad denominado x, de tipo numérico entero.
    Un método denominado inc_x() cuyo efecto es el de incrementar en una unidad el valor de la propiedad x.

Una vez generado el archivo correspondiente Counter.class ya podremos crear objetos tipo Counter desde otras clases:

public class Incrementa {

public static void main(String[] args) {

Counter counter = new Counter(); // create type Counter objec;

System.out.println(counter.x); // print current counter object x property value (initialized to cero);

counter.inc_x(); // invoke the inc_x() Counter class method in order to increment by one the x value;

System.out.println(counter.x); // again print current counter x attribute value (now incremented by one);

} // close main

// Compile and execute by running java Incrementa from CMD;

}

En este último ejemplo hemos definido una nueva clase denominada Incrementa. La clase definida no cuenta con propiedades o atributos propios: únicamente con el método estándar denominado main que, por defecto, ejecutará el código que contiene cada vez que cree un objeto de la clase Incrementa.

Herencia en Java

Java permite, mediante el mecanismo de herencia, utilizar clases ya creadas para "extenderlas", dando lugar a nuevas clases que cuentan por defecto con los atributos y propiedades de una clase ya predefinida y añade nuevos atributos y/o métodos.

Ejemplo de código fuente que crea una nueva clase para ampliar la clase previa Counter:

public class CounterPlus extends Counter {

public int y=0;

public void inc_y() {y=y+1;}

// Compile from CMD by executing javac CounterPlus.java;

}

La clase así definida CounterPlus cuenta con:

    El atributo o propiedad x heredado de la clase Counter además del atributo y específicamente definido para la clase  CounterPlus (ambos de tipo numérico entero).
    El método inc_x() heredado de la clase Counter además del método inc_y() específicamente definido para la clase CounterPlus (ambos incrementan en uno los valores x e y respectivamente).
