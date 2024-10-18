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

    public static Punto[] copiaArray(Punto[] original) {
        Punto[] copia = new Punto[original.length];

        for (int i = 0; i < original.length; i++) {
            copia[i] = new Punto(original[i].getX(), original[i].getY(), original[i].getId());
        }

        return copia;
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
                        System.out.println("=============================================================================================================================================");

                        // Exhaustivo
                        Algoritmos.resetContador();
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
                        Algoritmos.resetContador();
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
                        Algoritmos.resetContador();
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
                        Algoritmos.resetContador();
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
                    double tiempoExhaustivo[] = new double[5],
                            tiempoExhaustivoPoda[] = new double[5],
                            tiempoDyV[] = new double[5],
                            tiempoDyVMejorado[] = new double[5];

                    int iter = 0;

                    System.out.println("");
                    System.out.println(String.format("%-8s%-15s%-20s%-20s%-30s", "TALLA", "EXHAUSTIVO", "EXHAUSTIVO-PODA", "DIVIDE Y VENCERAS", "DIVIDE Y VENCERAS MEJORADO"));
                    for (int i = 1000; i <= 5000; i += 1000) {
                        // para cada tamaño son 10 datasets distintos
                        for (int j = 0; j < 10; j++) {
                            // opero con algoritmos y para saber el timpo medio de cada talla divido entre 10
                            Punto[] dataset = generaDatasetsPorTalla(i);
                            Punto[] cambia = copiaArray(dataset);
                            double startTime, endTime, duration;
                            // exhaustivo
                            Algoritmos.resetContador();
                            startTime = System.currentTimeMillis();
                            Algoritmos.exhaustivo(cambia, 0, cambia.length);
                            endTime = System.currentTimeMillis();
                            tiempoExhaustivo[iter] += (endTime - startTime);

                            // exhaustivoPoda
                            Algoritmos.resetContador();
                            cambia = copiaArray(dataset);
                            startTime = System.currentTimeMillis();
                            Algoritmos.exhaustivoPoda(cambia, 0, cambia.length);
                            endTime = System.currentTimeMillis();
                            tiempoExhaustivoPoda[iter] += (endTime - startTime);

                            // Divide y Vencerás
                            Algoritmos.resetContador();
                            cambia = copiaArray(dataset);
                            startTime = System.currentTimeMillis();
                            Algoritmos.dyv(cambia, 0, cambia.length);
                            endTime = System.currentTimeMillis();
                            tiempoDyV[iter] += (endTime - startTime);

                            // Divide y Vencerás Mejorado
                            Algoritmos.resetContador();
                            cambia = copiaArray(dataset);
                            startTime = System.currentTimeMillis();
                            Algoritmos.dyvMejorado(cambia, 0, cambia.length);
                            endTime = System.currentTimeMillis();
                            tiempoDyVMejorado[iter] += (endTime - startTime);

                        }

                        System.out.println(String.format("%-8d%-15.4f%-20.4f%-20.4f%-30.4f",
                                i,
                                tiempoExhaustivo[iter] / 10,
                                tiempoExhaustivoPoda[iter] / 10,
                                tiempoDyV[iter] / 10,
                                tiempoDyVMejorado[iter] / 10));
                        iter++;
                    }

                    // creamos los ficheros
                    System.out.println("");
                    TrataFicheros.generaFicheroDeTiempos(tiempoExhaustivo, "Exhaustivo");
                    TrataFicheros.generaFicheroDeTiempos(tiempoExhaustivoPoda, "Exhaustivo-Poda");
                    TrataFicheros.generaFicheroDeTiempos(tiempoDyV, "Divide-Y-Venceras");
                    TrataFicheros.generaFicheroDeTiempos(tiempoDyVMejorado, "Divide-Y-Venceras-Mejorado");
                    System.out.println("");
                }

            }
        } while (eleccion != 9);

    }
}
