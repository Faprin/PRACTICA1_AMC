package App;

import java.util.ArrayList;

public class Algoritmos {

    private static int contador;

    public static int getContador() {
        return Algoritmos.contador;
    }

    public static double distancia(Punto a, Punto b) {
        return (Math.sqrt((b.getX() - a.getX()) * (b.getX() - a.getX()) + (b.getY() - a.getY()) * (b.getY() - a.getY())));
    }

    /**
     * Primera aproximación -> búsqueda exhaustiva
     *
     * @param t -> array de tipo contendor de Puntos
     * @param izq -> Posición inicial del array
     * @param der -> Posicion final del array
     * @return -> Devuelve el punto más cercano
     */
    public static Punto[] exhaustivo(Punto[] t, int izq, int der) {
        Punto[] retorno = new Punto[2];
        // solucion para comparar
        retorno[0] = t[0];
        retorno[1] = t[1];
        
        // calculo la distancia minima a comparar
        double minDistance = distancia(retorno[0], retorno[1]);
        contador = contador + 1;

        double distanciaActual = 0;
        for (int i = izq; i < der; i++) {
            for (int j = i + 1; j < der; j++) {
                // recorro todas las posiciones comparando si la distancia es menorç
                distanciaActual = distancia(t[i], t[j]); // más eficiente
                contador++;
                if (distanciaActual < minDistance) {
                    retorno[0] = t[i];
                    retorno[1] = t[j];
                    minDistance = distanciaActual;
                }
            }
        }
        return retorno;
    }

    /**
     * Segunda aproximacion -> Búsqueda con poda
     *
     * @param t -> array de tipo contendor de Puntos
     * @param izq -> Posición inicial del array
     * @param der -> Posicion final del array
     * @return -> Devuelve el punto más cercano
     */
    public static Punto[] exhaustivoPoda(Punto[] t, int izq, int der) {
        Punto[] retorno = new Punto[2];
        retorno[0] = t[0];
        retorno[1] = t[1];

        // distancia minima referente
        double minDistance = distancia(retorno[0], retorno[1]);
        contador += 1;

        for (int i = izq; i < der; i++) {
            for (int j = i + 1; j < der; j++) {
                // hacemos la poda restando las x de los puntos entre ellas
                if (t[j].getX() - t[i].getX() > minDistance) {
                    break; // en caso de que sean mayores que la distancia mínima no interesa
                } else {
                    if (distancia(t[i], t[j]) < minDistance) {
                        contador++;
                        // si la distancia entre los puntos seleccionados es menor me interesa
                        retorno[0] = t[i];
                        retorno[1] = t[j];
                        minDistance = distancia(t[i], t[j]);
                    }
                }
            }
        }
        return retorno;
    }

    /*
            * TERCERA APROXIMACIÓN -> DIVIDE Y VENCERÁS
     *   Para aplicar una búsqueda de este tipo primero debemos tener el arreglo ordenado
     *   para ello usaremos quick-sort
     *   una vez ordenado usaremos una búsqueda binaria
     * */
    // ---- QUICK-SORT ----
    /**
     * completamente funcional
     *
     * /**
     * metodo implementado para la comodidad del usuario, asi solo tiene que
     * preocuparse por poner el array
     *
     * @param t -> vector de elementos tipo Punto
     */
    public static void quick_sort(Punto[] t) {
        quick_sort(t, 0, t.length - 1);
    }

    /**
     * método recursivo, dividirá el arreglo tantas veces como sea necesario
     * para ordenarlo
     *
     * @param t -> vector de elementos de tipo punto
     * @param izq -> primer elemento del vector
     * @param der -> último elemento del vector
     */
    public static void quick_sort(Punto[] t, int izq, int der) {
        // si el vector tiene más de un elemento, lo seguiremos particionando y ordenando
        if (izq < der) {
            int q = partition(t, izq, der); // dividimos el array
            quick_sort(t, izq, q); // Ordenamos el subarreglo izquierdo
            quick_sort(t, q + 1, der); // Ordenamos el subarreglo derecho
        }
    }

    /**
     * es el encargado de ordenar los subarreglos con la ayuda del pivote, en
     * caso de que i y j sean iguales, es decir, se encuentren
     *
     * @param t
     * @param izq
     * @param der
     * @return
     */
    public static int partition(Punto[] t, int izq, int der) {
        // utilizo de pivote el elemento situado a la izquierda
        Punto pivote = t[izq];
        int i = izq - 1; // puntero izquierdo
        int j = der + 1; // puntero derecho

        // bucle para reordenar los elementos según el pivote
        while (true) {
            // busca un elemento mayor o igual que el pivote desde la izquierda
            do {
                i++;
            } while (t[i].getX() < pivote.getX());

            // buscamos un elemento menor o igual que el pivote desde la derecha
            do {
                j--;
            } while (t[j].getX() > pivote.getX());

            // si j e i se cruzan significa que esta completa la particion
            if (i >= j) {
                return j;
            }

            // intercambiamos los elementos que quedan fuera de lugar
            Punto temp = t[i];
            t[i] = t[j];
            t[j] = temp;
        }

    }

    // ---- DIVIDE Y VENCERÁS ----
    public static Punto[] dyv(Punto[] t, int izq, int der) {
        int nPuntos = der - izq + 1;
        Punto[] retorno = new Punto[2];

        if (nPuntos > 4) {
            int pivote = izq + (der - izq) / 2;

            // llamo recursivamente al método para que opere con los sub-arreglos
            Punto[] solIzq = dyv(t, izq, pivote);
            Punto[] solDer = dyv(t, pivote + 1, der);

            // calgulo la distancia mínima de las soluciones que tienen las divisiones
            double disIzq = distancia(solIzq[0], solIzq[1]);
            contador += 1;
            double disDer = distancia(solDer[0], solDer[1]);
            contador += 1;

            // almaceno la solucion, teniendo asi la solucion entre las dos primeras franjas
            double minDistance;
            if (disIzq <= disDer) {
                minDistance = disIzq;
                retorno = solIzq;
            } else {
                minDistance = disDer;
                retorno = solDer;
            }

            // busco los puntos más cercanos en la frontera
            for (int i = pivote + 1; i <= der - 1; i++) { // derecha
                if ((t[i].getX() - t[pivote].getX()) > minDistance) {
                    break;
                }
            }

            for (int i = pivote; i >= 0; i--) { // izquierda
                if ((t[i].getX() - t[pivote].getX()) > minDistance) {
                    break;
                }
            }

            // comparo los puntos de la frontera
            for (int i = pivote; i >= 0; i--) {
                for (int j = pivote + 1; j <= der - 1; j++) {
                    if ((t[i].getX() - t[j].getX()) > minDistance) {
                        break;
                    } else {
                        if (distancia(t[i], t[j]) < minDistance) {
                            contador++;
                            retorno[0] = t[i];
                            retorno[1] = t[j];
                            minDistance = distancia(t[i], t[j]);
                        }
                    }
                }
            }
        } else {
            retorno = exhaustivo(t, izq, der);
        }

        return retorno;
    }

    // ---- DIVIDE Y VENCERAS MEJORADO ---- 
    public static Punto[] dyvMejorado(Punto[] t, int izq, int der) {
        int nPuntos = der - izq + 1;
        Punto[] retorno = new Punto[2];
        contador = 0;


        if (nPuntos > 2) {
            int pivote = izq + (der - izq) / 2;

            // llamo recursivamente al método para que opere con los sub-arreglos
            Punto[] solIzq = dyvMejorado(t, izq, pivote);
            Punto[] solDer = dyvMejorado(t, pivote + 1, der);

            // calgulo la distancia mínima de las soluciones que tienen las divisiones
            double disIzq = distancia(solIzq[0], solIzq[1]);
            contador++;
            double disDer = distancia(solDer[0], solDer[1]);
            contador++;

            // almaceno la solucion, teniendo asi la solucion entre las dos primeras franjas
            double minDistance;
            if (disIzq <= disDer) {
                minDistance = disIzq;
                retorno = solIzq;
            } else {
                minDistance = disDer;
                retorno = solDer;
            }

            // creo un array para almacenar los puntos que estan detro de la franja
            ArrayList<Punto> aux = new ArrayList<>();
            for (int i = izq; i < der; i++) {
                if (Math.abs(t[i].getX() - t[pivote].getX()) < minDistance) {
                    aux.add(t[i]);
                }
            }
            
            // tengo que ordenar el vector auxiliar 

            // una vez tengo la franja, ahora opero comparo las y´s
            for (int i = 0; i < aux.size(); i++) {
                for (int j = i + 1; j < aux.size() && (aux.get(j).getY() - aux.get(i).getY()) < minDistance; j++) {
                    double dist = distancia(aux.get(i), aux.get(j));
                    contador++;
                    if (dist < minDistance) {
                        minDistance = dist;
                        retorno[0] = aux.get(i);
                        retorno[1] = aux.get(j);
                    }
                }
            }

        } else {
            retorno = exhaustivo(t, izq, der);
        }

        return retorno;
    }
    
    public static void resetContador() {
        contador= 0;
    }

}
