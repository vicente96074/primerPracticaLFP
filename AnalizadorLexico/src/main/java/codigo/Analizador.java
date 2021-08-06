package codigo;

import java.util.ArrayList;

/**
 *
 * @author augusto
 */
public class Analizador {

    ArrayList<Token> lista_token = new ArrayList();

    public Analizador(ArrayList<Token> lista_token) {
        this.lista_token = lista_token;
    }

    public void analizar(String cadena) {
        int estado = 0;
        int decimal = 0;
        int numero_token = 0;
        String lexema = "";
        String tipo = "";
        String[] lineas = separador(cadena, '\n');
        for (int i = 0; i < lineas.length; i++) {
            for (int j = 0; j < lineas[i].length(); j++) {
                int n_actual;
                int n_Siguiente = -1;

                n_actual = lineas[i].codePointAt(j);
                if (estado == 0) {
                    estado = estado_transicion(n_actual);
                }

                try {
                    n_Siguiente = lineas[i].codePointAt(j + 1);
                } catch (Exception e) {

                }

                switch (estado) {
                    case 1:
                        lexema = lexema + lineas[i].charAt(j);
                        if ((n_Siguiente > 96 && n_Siguiente < 123) || (n_Siguiente > 64 && n_Siguiente < 91)) {
                            estado = 1;
                        } else {
                            numero_token = 1;
                            tipo = "Cadena";
                            estado = 0;
                        }
                        break;

                    case 2:
                        lexema = lexema + lineas[i].charAt(j);
                        if (n_Siguiente > 47 && n_Siguiente < 58) {
                            estado = 2;
                        } else {
                            numero_token = 2;
                            tipo = "Entero";
                            estado = 0;
                        }
                        break;
                    case 100:
                        estado = -2;
                        break;
                    case 999:
                        lexema = String.valueOf(lineas[i].charAt(j));
                        numero_token = 999;
                        tipo = "Error lexico";
                        estado = 0;
                        break;
                }

                if (estado == 0) {
                    lista_token.add(new Token(lexema, numero_token, (i + 1), (j + 1), tipo));
                    lexema = "";
                    tipo = "";
                }

                if (estado == -2) {
                    estado = 0;
                }
            }
        }
    }

    public int estado_transicion(int numero) {
        if ((numero > 96 && numero < 123) || (numero > 64 && numero < 91)) {
            return 1;
        } else if (numero > 47 && numero < 58) {
            return 2;

        } else if (numero == 32 || numero == 13 || numero == 9) {
            return 100;
        } else {
            return 999;
        }
    }

    public String[] separador(String texto, char separar) {
        String linea = "";
        int contador = 0;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == separar) {
                contador++;
            }

        }

        String[] cadenas = new String[contador];
        contador = 0;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) != separar) {
                linea = linea + String.valueOf(texto.charAt(i));
            } else {
                cadenas[contador] = linea;
                contador++;
                linea = "";
            }

        }

        return cadenas;
    }
}
