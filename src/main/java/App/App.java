package App;

import java.util.Random;
import java.util.Scanner;

public class App {

    public static void menu() {
        System.out.println(" ------- Menu ------- ");
        System.out.println("1. Crear un dataset aleatorio");
        System.out.println("2. Cargar un dataset en memoria");
        System.out.println("3. Comprobar estrategias");
        System.out.println("4. Comparar todas las estrategias");

        System.out.println("9. Salir");

        System.out.print(">>  ");

    }

    public static Punto[] generaDatasetsPorTalla(int talla) {
        Random random = new Random(System.nanoTime());

        // tengo que generar un data (array de talla elementos)
        Punto[] data = new Punto[talla];

        for (int i = 0; i < talla; i++) {
            data[i] = new Punto();
            // metemos los puntos de cada posicion
            data[i].setId(i + 1);
            data[i].setX(random.nextDouble(500 - 0 + 1));
            data[i].setY(random.nextDouble(500 - 0 + 1));
        }

        return data;
    }

    public static void main(String[] args) throws InterruptedException {
        // crear un dataset aleatorio -> pregunta por la capacidad  y lo guarda
        // Cargar un dataset en memoria
        // Comprobar estrategias -> Comprueba las estrategias con el dataset cargado en memoria con el formato de la pagina 10

        int eleccion = 0;
        Scanner in = new Scanner(System.in);
        String path = "";
        long startTime = 0, endTime = 0, duration = 0;

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
                        Punto[] memoria = TrataFicheros.reader(path);

                        System.out.println("Estrategia                Punto 1                           Punto 2                           Distancia       Calculadas       Tiempo (mseg)");
                        System.out.println("=============================================================================================================================================");

                        // Exhaustivo
                        Punto[] ordenacion = memoria;
                        startTime = System.currentTimeMillis();
                        Punto[] exhaustivo = Algoritmos.exhaustivo(memoria, 0, memoria.length);
                        endTime = System.currentTimeMillis();
                        duration = (endTime - startTime);

                        System.out.printf("Exhaustivo               %-35s %-35s %-15.8f %-15d %-15d%n",
                                exhaustivo[0], exhaustivo[1],
                                Algoritmos.distancia(exhaustivo[0], exhaustivo[1]),
                                Algoritmos.getContador(), duration);

                        TrataFicheros.generaFicherosPorArray(ordenacion, "Exhaustivo");

                        // Exhaustivo con Poda
                        ordenacion = memoria;
                        Algoritmos.quick_sort(ordenacion);
                        startTime = System.currentTimeMillis();
                        Punto[] exhaustivoPoda = Algoritmos.exhaustivoPoda(memoria, 0, memoria.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("Exhaustivo Poda          %-35s %-35s %-15.8f %-15d %-15d%n",
                                exhaustivoPoda[0], exhaustivoPoda[1],
                                Algoritmos.distancia(exhaustivoPoda[0], exhaustivoPoda[1]),
                                Algoritmos.getContador(), duration);

                        TrataFicheros.generaFicherosPorArray(ordenacion, "Exhaustivo-Poda");

                        // Divide y Vencerás
                        ordenacion = memoria;
                        // ordeno el array porque voy a aplicar dyv 
                        Algoritmos.quick_sort(ordenacion);
                        startTime = System.currentTimeMillis();
                        Punto[] divideYVenceras = Algoritmos.dyv(memoria, 0, memoria.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("DyV                     %-35s %-35s %-15.8f %-15d %-15d%n",
                                divideYVenceras[0], divideYVenceras[1],
                                Algoritmos.distancia(divideYVenceras[0], divideYVenceras[1]),
                                Algoritmos.getContador(), duration);

                        TrataFicheros.generaFicherosPorArray(ordenacion, "Divide-Y-Venceras");

                        // Divide y Vencerás Mejorado
                        ordenacion = memoria;
                        Algoritmos.quick_sort(ordenacion);
                        startTime = System.currentTimeMillis();
                        Punto[] divideYVencerasMejorado = Algoritmos.dyvMejorado(memoria, 0, memoria.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("DyV Mejorado             %-35s %-35s %-15.8f %-15d %-15d%n",
                                divideYVencerasMejorado[0], divideYVencerasMejorado[1],
                                Algoritmos.distancia(divideYVencerasMejorado[0], divideYVencerasMejorado[1]),
                                Algoritmos.getContador(), duration);

                        TrataFicheros.generaFicherosPorArray(ordenacion, "Divide-Y-Venceras-Mejorado");

                    } else {
                        System.out.println("No se ha cargado ningun dataset previamente en memoria");
                    }
                }

                case 4 -> {
                    // debemos generar 10 datasets distintos con un incremento fijo ex de 500 en 500
                    // en este caso la talla base quiero que sea 100 
                    Punto[] dataset;
                    for (int i = 1000; i <= 10000; i += 1000) {
                        dataset = generaDatasetsPorTalla(i);
                        
                        for (int j = 0; j < 2; j++) {
                            System.out.println("");
                            
                        }
                        
                        System.out.println("Talla: " + i);

                        // ahora opero con todos los datasets
                        System.out.println("Estrategia                Punto 1                           Punto 2                           Distancia       Calculadas       Tiempo (mseg)");
                        System.out.println("=============================================================================================================================================");

                        // exhaustivo
                        startTime = System.currentTimeMillis();
                        Punto[] exhaustivo = Algoritmos.exhaustivo(dataset, 0, dataset.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("Exhaustivo               %-35s %-35s %-15.8f %-15d %-15d%n",
                                exhaustivo[0], exhaustivo[1],
                                Algoritmos.distancia(exhaustivo[0], exhaustivo[1]),
                                Algoritmos.getContador(), duration);
                        TrataFicheros.generaFicherosPorArray(dataset, "Exhaustivo");

                        //exaustivo poda
                        Algoritmos.quick_sort(dataset);
                        startTime = System.currentTimeMillis();
                        Punto[] exhaustivoPoda = Algoritmos.exhaustivoPoda(dataset, 0, dataset.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("Exhaustivo Poda          %-35s %-35s %-15.8f %-15d %-15d%n",
                                exhaustivoPoda[0], exhaustivoPoda[1],
                                Algoritmos.distancia(exhaustivoPoda[0], exhaustivoPoda[1]),
                                Algoritmos.getContador(), duration);

                        TrataFicheros.generaFicherosPorArray(dataset, "Exhaustivo-Poda");

                        // Divide y Vencerás
                        // ordeno el array porque voy a aplicar dyv 
                        Algoritmos.quick_sort(dataset);
                        startTime = System.currentTimeMillis();
                        Punto[] divideYVenceras = Algoritmos.dyv(dataset, 0, dataset.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("DyV                     %-35s %-35s %-15.8f %-15d %-15d%n",
                                divideYVenceras[0], divideYVenceras[1],
                                Algoritmos.distancia(divideYVenceras[0], divideYVenceras[1]),
                                Algoritmos.getContador(), duration);
                        TrataFicheros.generaFicherosPorArray(dataset, "Divide-Y-Venceras");
                        
                        // Divide y vencerás Mejorado
                        Algoritmos.quick_sort(dataset);
                        startTime = System.currentTimeMillis();
                        Punto[] divideYVencerasMejorado = Algoritmos.dyvMejorado(dataset, 0, dataset.length);
                        endTime = System.currentTimeMillis();
                        duration = endTime - startTime;

                        System.out.printf("DyV Mejorado             %-35s %-35s %-15.8f %-15d %-15d%n",
                                divideYVencerasMejorado[0], divideYVencerasMejorado[1],
                                Algoritmos.distancia(divideYVencerasMejorado[0], divideYVencerasMejorado[1]),
                                Algoritmos.getContador(), duration);

                        TrataFicheros.generaFicherosPorArray(dataset, "Divide-Y-Venceras-Mejorado");
                        Thread.sleep(1000);
                    }
                }

            }
        } while (eleccion != 9);

    }
}
