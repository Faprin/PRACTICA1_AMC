package App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Random;
import java.io.FileWriter;

public class TrataFicheros {

    /**
     * Se encarga de leer un fichero que se le pasa por parametro y lo devuelve en forma de array de tipo Punto
     * @param path -> Direccion donde se encuentra el fichero correspondiente
     * @return -> Retorna los puntos del fichero en forma de array de Puntos
     */
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
                puntos[j].setId(Integer.parseInt(filtro[0]));
                puntos[j].setX(Double.parseDouble(filtro[1]));
                puntos[j].setY(Double.parseDouble(filtro[2]));

            }

        } catch (Exception e) {
            System.out.println("Error a la hora de leer el archivo con direccion: " + path + "Exception: " + e );
        }

        return puntos;
    }

    /**
     * Crea ficheros tsp con el mismo formato y datos aleatorios
     */
    public static void creaFichero() {
        try {
            // definicion del nombre del archivo
            Random numRandom = new Random(System.nanoTime());
            int dimension = numRandom.nextInt(701);
            String nomFichero = "dataset" + dimension + ".tsp";
            File ficheroAleatorio = new File(nomFichero);


            // creamos el fileWriter y metemos el nombre
            FileWriter fichero = new FileWriter(nomFichero);
            fichero.write("NAME: " + nomFichero + "\n");

            // agrego el resto de cabeceras para que mantenga el mismo formato
            fichero.write("TYPE: TSP \nCOMMENT: " + dimension +"\nDIMENSION: " + dimension + "\nEDGE_WEIGHT_TYPE: NAN\nNODE_COORD_SECTION\n");

            // iremos escribiendo en el fichero los datos que generemos aleatoriamente
            for(int i = 0; i < dimension; i++) {
                double x = numRandom.nextDouble();
                double y = numRandom.nextDouble();
                fichero.write(i + 1 + " " + Math.round(x * 1e10)/1e10 + " " + Math.round(y * 1e10)/1e10 + "\n");
            }

            // indicamos el final del fichero
            fichero.write("EOF");
            fichero.close();
            System.out.println("Fichero creado satisfactoriamente");

        } catch (Exception e){
            System.out.println("No se ha podido crear el nuevo fichero: " + e.getMessage());
        }
    }
}
