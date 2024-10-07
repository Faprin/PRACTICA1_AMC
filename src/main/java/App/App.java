package App;

import java.io.BufferedReader;
import java.io.FileReader;

public class App {

    public static Punto[] reader(String path) {
        Punto []puntos = null;
        try (FileReader r = new FileReader(path); // vamos a leer desde el sitio que nos manden
             BufferedReader b = new BufferedReader(r)) {

            // me salto las 3 primeras líneas
            for (int i = 0; i < 3; i++)
                b.readLine();

            // leo la dimension para saber cuantos elementos tengo que recorrer
            String stringDimension = b.readLine();

            // tratamos la linea lída para quedarnos con el numero nada mas
            String []parts = stringDimension.split(": ");
            int dimension = Integer.parseInt(parts[1]);

            // saltamos las dos siguientes líneas
            for(int i=0;i<2;i++)
                    b.readLine();

            // creo un array para cargar los puntos
            puntos = new Punto[dimension];
            for (int j = 0; j < dimension; j++) {
                String lineaPura = b.readLine();
                // tratamos la linea
                String []filtro = lineaPura.split(" ");

                // creamos el punto y lo guardamos
                puntos[j] = new Punto();
                puntos[j].setId(j);
                puntos[j].setX(Double.parseDouble(filtro[1]));
                puntos[j].setY(Double.parseDouble(filtro[2]));

            }

        } catch (Exception e) {
            System.out.println("Error a la hora de leer el archivo con direccion: " + path + "Exception: " + e );
        }

        return puntos;
    }

    public static void main(String[] args) {
        Punto []puntos = reader("C:\\Users\\jmdom\\OneDrive - UNIVERSIDAD DE HUELVA\\Universidad\\3 AÑO\\ALGORITMICA Y MODELOS DE COMPUTACIÓN\\Prácticas\\PRACTICA1\\archivosTSP\\berlin52.tsp\\berlin52.tsp");

        // visual del array normal
        Punto.mostrarArray(puntos);

        // comprobamos que exhaustivo funciona bien
        System.out.println("...................D Y V..........................");
        Punto []solu = Algoritmos.dyv(puntos, 0, puntos.length);
        Punto.mostrarArray(solu);

        System.out.println("....................PODA.........................");
        solu = Algoritmos.exhaustivoPoda(puntos, 0, puntos.length);
        Punto.mostrarArray(solu);
    }
}
