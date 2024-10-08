package App;



public class App {


    public static void main(String[] args) {
        /*Punto []puntos = TrataFicheros.reader("C:\\Users\\jmdom\\OneDrive - UNIVERSIDAD DE HUELVA\\Universidad\\3 AÑO\\ALGORITMICA Y MODELOS DE COMPUTACIÓN\\Prácticas\\PRACTICA1\\archivosTSP\\berlin52.tsp\\berlin52.tsp");

        // visual del array normal
        Punto.mostrarArray(puntos);

        // comprobamos que exhaustivo funciona bien
        System.out.println("...................D Y V..........................");
        Punto []solu = Algoritmos.dyv(puntos, 0, puntos.length);
        Punto.mostrarArray(solu);

        System.out.println("....................PODA.........................");
        solu = Algoritmos.exhaustivoPoda(puntos, 0, puntos.length);
        Punto.mostrarArray(solu);*/

        TrataFicheros.creaFichero();
    }
}
