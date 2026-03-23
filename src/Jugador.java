import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN_SUPERIOR = 10;
    private final int MARGEN_IZQUIERDO = 10;
    private final int DISTANCIA = 40;
    private final int XDISTANCIA = 10;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicion = MARGEN_IZQUIERDO + DISTANCIA * (TOTAL_CARTAS - 1);
        for (Carta carta : cartas) { //objeto | variable temporal | en | arreglo a recorrer(Carta[])
            carta.mostrar(pnl, posicion, MARGEN_SUPERIOR); //De derecha a izquierda
            posicion -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() { //string me devuelve un string
        int[] contadores = new int[NombreCarta.values().length]; //values = arreglo de los elementos del enumerario
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        String resultado = "";
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) { 
                resultado += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
            }
        }
        return resultado;
    }
    public String getEscaleras() {
    // Matriz de 4 pintas (filas) y 13 nombres (columnas)
    boolean[][] mapa = new boolean[Pinta.values().length][NombreCarta.values().length];

    // Marcamos las cartas que tenemos
    for (Carta c : cartas) {
        int fila = c.getPinta().ordinal();
        int columna = c.getNombre().ordinal();
        mapa[fila][columna] = true;
    }

    String resultado = "";

    // Recorremos cada pinta (fila)
    for (int p = 0; p < Pinta.values().length; p++) {
        int consecutivas = 0;
        int inicio = -1;

        for (int n = 0; n < NombreCarta.values().length; n++) {
            if (mapa[p][n]) {
                if (consecutivas == 0) inicio = n;
                consecutivas++;
            } else {
                // Si se rompe la secuencia, verificamos si hubo escalera
                if (consecutivas >= 3) {
                    resultado += "Escalera de " + Pinta.values()[p] + ": " + 
                                 NombreCarta.values()[inicio] + " a " + 
                                 NombreCarta.values()[n - 1] + "\n";
                }
                consecutivas = 0;
            }
        }
        // Chequeo final por si la escalera termina en el REY (K)
        if (consecutivas >= 3) {
            resultado += "Escalera de " + Pinta.values()[p] + ": " + 
                         NombreCarta.values()[inicio] + " a " + 
                         NombreCarta.values()[NombreCarta.values().length - 1] + "\n";
        }
    }
    return resultado;
}



    //funcion para mostrar los grupos de cartas
    public int mostrarGrupos(JPanel pnl, int x, int yInicial) {
        int yActual = yInicial;
        int[] obtenerGrupitos = new int[NombreCarta.values().length]; 
        
        for (Carta carta : cartas) {
            obtenerGrupitos[carta.getNombre().ordinal()]++;
        }

        JLabel GrupitoTitulo = new JLabel("Grupos encontrados:");
        GrupitoTitulo.setBounds(XDISTANCIA, yActual, 300, 20);
        pnl.add(GrupitoTitulo);
        yActual += 20;


        for (int i = 0; i < obtenerGrupitos.length; i++) {
            if (obtenerGrupitos[i] >= 2) { 
                String textoGrupo = Grupo.values()[obtenerGrupitos[i]] + " de " + NombreCarta.values()[i];
                
                JLabel grupo = new JLabel(textoGrupo);
                grupo.setBounds(x, yActual, 400, 20);
                pnl.add(grupo);
                
                yActual += 20; 
            }
        }

        return yActual; 
    }

    //funcion para mostrar las cartas sobrantes
    public void mostrarSobrantes(JPanel pnl, int x, int yInicial) {
        int[] conteoNombres = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            conteoNombres[c.getNombre().ordinal()]++;
        }

        int yActual = yInicial;

        JLabel TituloSobrantes = new JLabel("Cartas que sobran:");
        TituloSobrantes.setBounds(XDISTANCIA, yActual, 300, 20);
        pnl.add(TituloSobrantes);
        yActual += 20;

        for (int i = 0; i < cartas.length; i++) {
            int ubicasionDelNombre = cartas[i].getNombre().ordinal();
            if (conteoNombres[ubicasionDelNombre] == 1) {
                String texto = cartas[i].getNombre() + " de " + cartas[i].getPinta();
                JLabel renglones = new JLabel(texto);
                renglones.setBounds(x, yActual, 300, 20);
                pnl.add(renglones);
                yActual += 20; 
            }
        }
    }

    private int[] obtenerConteo() {
        int[] conteo = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            conteo[c.getNombre().ordinal()]++;
        }
        return conteo;
    }

    //funcion para calcular el puntaje de las cartas sobrantes
    public void calcularPuntaje(JPanel pnl, int x, int yInicial) {
        int[] contadores = obtenerConteo(); 
        int puntajeSobrantes = 0;

        for (int i = 0; i < cartas.length; i++) {
            int indiceNombre = cartas[i].getNombre().ordinal(); //posicion del enumerarioo

            
            if (contadores[indiceNombre] == 1) {//carta sola
                
                int valorCarta = indiceNombre + 1;
                
                if (valorCarta > 10) {
                    puntajeSobrantes += 10;
                } else {
                    puntajeSobrantes += valorCarta;
                }
            }
        }

        JLabel Puntaje = new JLabel("Puntos de las cartas sobrantes: " + puntajeSobrantes);
        Puntaje.setBounds(XDISTANCIA, yInicial, 300, 20);
        
        pnl.add(Puntaje);
    }


    //Funcion y funciones auxiliares para escontrar las escaleras

    private int tieneCarta(NombreCarta nombre, Pinta pinta) {
        for (Carta c : cartas) {
            if (c.getNombre() == nombre && c.getPinta() == pinta) {
                return 1;
            }
        }
        return 0;
    }

    public int mostrarEscaleras(JPanel pnl, int x, int yInicial) {
        int yActual = yInicial;
        
        JLabel tituloEscaleras = new JLabel("Escaleras encontradas:");
        tituloEscaleras.setBounds(XDISTANCIA, yActual, 300, 20);
        pnl.add(tituloEscaleras);
        yActual += 20;

        int hayAlgunaEscalera = 0;

        for (Pinta p : Pinta.values()) {
            int consecutivas = 0;
            NombreCarta inicio = null; 
            NombreCarta fin = null;

            for (NombreCarta n : NombreCarta.values()) {
                if (tieneCarta(n, p) == 1) {
                    if (consecutivas == 0) {
                        inicio = n;
                    }
                    fin = n; 
                    consecutivas++;
                } else {
                    if (consecutivas >= 3) {
                        dibujarEscalera(pnl, x, yActual, p, inicio, fin);
                        yActual += 20;
                        hayAlgunaEscalera = 1;
                    }
                    consecutivas = 0;
                }
            }
            
            if (consecutivas >= 3) {
                dibujarEscalera(pnl, x, yActual, p, inicio, fin);
                yActual += 20;
                hayAlgunaEscalera = 1;
            }
        }

        if (hayAlgunaEscalera == 0) {
            JLabel ninguna = new JLabel("Ninguna");
            ninguna.setBounds(x, yActual, 300, 20);
            pnl.add(ninguna);
            yActual += 20;
        }

        return yActual;
    }

    private void dibujarEscalera(JPanel pnl, int x, int y, Pinta p, NombreCarta inicio, NombreCarta fin) {
        String mensaje = "Escalera de " + p + ": " + inicio + " a " + fin;
        
        JLabel mensajeEscalera = new JLabel(mensaje);
        mensajeEscalera.setBounds(x, y, 400, 20);
        pnl.add(mensajeEscalera);
    }
}
