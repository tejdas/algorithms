package net.lc.design;

/**
 * 348
 * Design
 */
public class TicTacToe {
    int[] arow, acol;
    int[] brow, bcol;

    int lDiagA, lDiagB;
    int rDiagA, rDiagB;
    int n;

    public TicTacToe(int n) {
        this.n = n;
        arow = new int[n];
        brow = new int[n];
        acol = new int[n];
        bcol = new int[n];
    }



    public int move(int row, int col, int player) {

        if (player == 1) {
            arow[row]++;
            acol[col]++;

            if (arow[row] == n || acol[col] == n) return 1;

            if (row == col) {
                lDiagA++;
            }

            if (row+col == n-1) {
                rDiagA++;
            }

            if (lDiagA == n || rDiagA == n) return 1;
        } else {
            brow[row]++;
            bcol[col]++;

            if (brow[row] == n || bcol[col] == n) return 2;

            if (row == col) {
                lDiagB++;
            }

            if (row+col == n-1) {
                rDiagB++;
            }

            if (lDiagB == n || rDiagB == n) return 2;
        }
        return 0;
    }

    public static void main(String[] args) {
        /*
        {
            TicTacToe ttt = new TicTacToe(3);
            System.out.println(ttt.move(0, 0, 1));
            System.out.println(ttt.move(0, 2, 2));
            System.out.println(ttt.move(2, 2, 1));
            System.out.println(ttt.move(2, 1, 2));
            System.out.println(ttt.move(2, 0, 1));
            System.out.println(ttt.move(1, 0, 2));
            System.out.println(ttt.move(2, 1, 1));
        }
*/
        {
            TicTacToe ttt = new TicTacToe(2);
            System.out.println(ttt.move(0, 1, 1));
            System.out.println(ttt.move(1, 1, 2));
            System.out.println(ttt.move(1, 0, 1));
        }
    }
}
