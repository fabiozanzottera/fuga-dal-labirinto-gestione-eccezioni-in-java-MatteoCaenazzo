package org.example;

import java.util.Scanner;

// Eccezione personalizzata per movimenti fuori dai limiti
class OutOfBoundsException extends Exception {
    public OutOfBoundsException(String message) {
        super(message);
    }
}

// Eccezione personalizzata per collisione con muri
class WallCollisionException extends Exception {
    public WallCollisionException(String message) {
        super(message);
    }
}

public class MazeEscape {
    // Dichiarazione della matrice del labirinto
    private static final char[][] LABIRINTO = {
        { 'P', '.', '#', '.', '.' },
        { '#', '.', '#', '.', '#' },
        { '.', '.', '.', '#', '.' },
        { '#', '#', '.', '.', '.' },
        { '#', '.', '#', '#', 'E' }
    };

    // Coordinate iniziali del giocatore
    private static int playerX = 0;
    private static int playerY = 0;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean escaped = false;

        System.out.println("Benvenuto in Maze Escape! Trova l'uscita ('E').");

        while (!escaped) {
            printMaze();
            System.out.print("Muoviti (W = su, A = sinistra, S = giù, D = destra): ");
            char move = scanner.next().toUpperCase().charAt(0);

            try {
                movePlayer(move);
                escaped = finito();
            } catch (OutOfBoundsException | WallCollisionException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }


    static boolean finito(){
        for (int i = 0; i < LABIRINTO.length; i++) {
            for (int j = 0; j < LABIRINTO[i].length; j++) {
                if(LABIRINTO[i][j] == 'E'){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Metodo per spostare il giocatore all'interno del labirinto
     * Deve controllare:
     * - Se il movimento è fuori dai limiti → lancia OutOfBoundsException
     * - Se il movimento porta su un muro ('#') → lancia WallCollisionException
     * - Se il movimento è valido, aggiornare la posizione
     */
    private static void movePlayer(char direction) throws OutOfBoundsException, WallCollisionException {
        switch (direction){
            case 'W':
                if(playerY-1 < 0){
                    throw new OutOfBoundsException("uscita dal campo di gioco");
                }
                else if(LABIRINTO[playerY-1][playerX] == '#'){
                    throw new WallCollisionException("colpito un muro");
                }
                else{
                    LABIRINTO[playerY][playerX] = '.';
                    playerY--;
                    LABIRINTO[playerY][playerX] = 'P';
                }
                break;
            case 'S':
                if(playerY+1 > 4){
                    throw new OutOfBoundsException("uscita dal campo di gioco");
                }
                else if(LABIRINTO[playerY+1][playerX] == '#'){
                    throw new WallCollisionException("colpito un muro");
                }
                else{
                    LABIRINTO[playerY][playerX] = '.';
                    playerY++;
                    LABIRINTO[playerY][playerX] = 'P';
                }
                break;
            case 'A':
                if(playerX-1 < 0){
                    throw new OutOfBoundsException("uscita dal campo di gioco");
                }
                else if(LABIRINTO[playerY][playerX-1] == '#'){
                    throw new WallCollisionException("colpito un muro");
                }
                else{
                    LABIRINTO[playerY][playerX] = '.';
                    playerX--;
                    LABIRINTO[playerY][playerX] = 'P';
                }
                break;
            case 'D':
                if(playerX+1 > 4){
                    throw new OutOfBoundsException("uscita dal campo di gioco");
                }
                else if(LABIRINTO[playerY][playerX+1] == '#'){
                    throw new WallCollisionException("colpito un muro");
                }
                else{
                    LABIRINTO[playerY][playerX] = '.';
                    playerX++;
                    LABIRINTO[playerY][playerX] = 'P';
                }
                break;
            default:
                System.out.println("input non accettato");
        }
    }

    /**
     * Metodo per stampare il labirinto attuale
     */
    private static void printMaze() {
        for (int i = 0; i < LABIRINTO.length; i++) {
            for (int j = 0; j < LABIRINTO[i].length; j++) {
                System.out.print(LABIRINTO[i][j]);
            }
            System.out.println();
        }
    }
}
