package App;

import java.util.Random;
import java.util.Scanner;

public class App {

    public static void menu() {
        System.out.println(" ------- Menu ------- ");
        System.out.println("1. Crear un dataset aleatorio");
        System.out.println("2. Cargar un dataset en memoria");
        System.out.println("3. Comprobar estrategias");

        System.out.println("9. Salir");

        System.out.print(">>  ");

    }

    public static Punto[] generarVector(int dimension) {
        Random random = new Random(System.nanoTime());
        Punto[] retorno = new Punto[dimension];

        for (int i = 0; i < dimension; i++) {
            double x = random.nextDouble(5000 - 0 + 1);
            double y = random.nextDouble(5000 - 0 + 1);
            retorno[i].setX(x);
            retorno[i].setY(y);
            retorno[i].setId(i);

        }

        return retorno;
    }

    public static void main(String[] args) {
        // crear un dataset aleatorio -> pregunta por la capacidad  y lo guarda
        // Cargar un dataset en memoria
        // Comprobar estrategias -> Comprueba las estrategias con el dataset cargado en memoria con el formato de la pagina 10

        int eleccion = 0;
        Scanner in = new Scanner(System.in);
        String path = "";

        // limpiamos consola
        for (int i = 0; i < 50; i++) {
            System.out.println("");
        }

        do {
            menu();
            eleccion = in.nextInt();

            switch (eleccion) {
                case 1 -> {
                    // crear un dataset aleatorio
                    System.out.print("Indica la capacidad del dataset aleatorio: ");
                    int capacidad = in.nextInt();
                    TrataFicheros.creaFichero(capacidad);
                }

                case 2 -> {
                    // carga un dataset en memoria
                    System.out.print("Indica la ruta del fichero: ");
                    in.nextLine();
                    path = in.nextLine();
                    System.out.println(path);
                }

                case 3 -> {
                    // comrpbar estrategias con el dataset cargado en memoria
                    if (!path.equals("")) {
                        long startTime = 0, endTime = 0, duration = 0;
                        Punto[] memoria = TrataFicheros.reader(path);

                        System.out.println("Estrategia                Punto 1                           Punto 2                           Distancia       Calculadas       Tiempo (mseg)");
                        System.out.println("===============================================================================================================");

                        // Exhaustivo
                        startTime = System.currentTimeMillis();
                        Punto[] exhaustivo = Algoritmos.exhaustivo(memoria, 0, memoria.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("Exhaustivo               %-35s %-35s %-15f %-15d %-15d%n",
                                exhaustivo[0], exhaustivo[1],
                                Algoritmos.distancia(exhaustivo[0], exhaustivo[1]),
                                Algoritmos.getContador(), duration);

                        // Exhaustivo con Poda
                        startTime = System.currentTimeMillis();
                        Punto[] exhaustivoPoda = Algoritmos.exhaustivoPoda(memoria, 0, memoria.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("Exhaustivo Poda          %-35s %-35s %-15f %-15d %-15d%n",
                                exhaustivoPoda[0], exhaustivoPoda[1],
                                Algoritmos.distancia(exhaustivoPoda[0], exhaustivoPoda[1]),
                                Algoritmos.getContador(), duration);

                        // Divide y Vencerás
                        startTime = System.currentTimeMillis();
                        Punto[] divideYVenceras = Algoritmos.dyv(memoria, 0, memoria.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("DyV                     %-35s %-35s %-15f %-15d %-15d%n",
                                divideYVenceras[0], divideYVenceras[1],
                                Algoritmos.distancia(divideYVenceras[0], divideYVenceras[1]),
                                Algoritmos.getContador(), duration);

                        // Divide y Vencerás Mejorado
                        startTime = System.currentTimeMillis();
                        Punto[] divideYVencerasMejorado = Algoritmos.dyvMejorado(memoria, 0, memoria.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("DyV Mejorado             %-35s %-35s %-15f %-15d %-15d%n",
                                divideYVencerasMejorado[0], divideYVencerasMejorado[1],
                                Algoritmos.distancia(divideYVencerasMejorado[0], divideYVencerasMejorado[1]),
                                Algoritmos.getContador(), duration);

                    } else {
                        System.out.println("No se ha cargado ningun dataset previamente en memoria");
                    }
                }

            }
        } while (eleccion != 9);

    }
}
