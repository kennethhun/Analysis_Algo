//Author: Kenneth Hung
//Assignment: Programming Assignment 3, dynamic programming navigating matrix
//Last revision: 3/30/2024
import java.util.ArrayList;
import java.util.Random;

public class assignment3 {
    public static void main(String[] args) {
        Random random = new Random();
        int n = 5; int m = 5; // matrix size
        int row = n-1;
        int column = m-1;
        boolean corner = false;
        ArrayList<String> pathway = new ArrayList<String>();
        int[][] matri1 = new int[n][m];
        int[][] matri2 = new int[n][m];

        // generate and print out a random matrix
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                matri1[i][j] = (random.nextInt(40)-20);
                System.out.print(matri1[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();

        // generate new matrix of greatest sum
        // a | b
        // c | max(a+d, b+d, c+d)
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if (i == 0) {
                    if (j == 0) {
                        matri2[i][j] = matri1[i][j];
                        System.out.print(matri2[i][j] + " ");
                    }
                    else {
                        matri2[i][j] = matri1[i][j] + matri2[i][j-1];
                        System.out.print(matri2[i][j] + " ");
                    }
                }
                else {
                    if (j == 0) {
                        matri2[i][j] = matri1[i][j] + matri2[i-1][j];
                        System.out.print(matri2[i][j] + " ");
                    }
                    else {
                        matri2[i][j] = Math.max((matri1[i][j] + matri2[i-1][j-1]), (matri1[i][j] + matri2[i][j-1])); // check diagonal and left
                        matri2[i][j] = Math.max(matri2[i][j], (matri1[i][j] + matri2[i-1][j])); // check up
                        System.out.print(matri2[i][j] + " ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();

        // search from bottom right for highest sum path
        while(corner == false) {
            if (row == 0) {
                pathway.add(0, "Right ");
                column--;
            }
            else if (column == 0) {
                pathway.add(0, "Down ");
                row--;
            }
            else {
                int check = findMax(matri2[row-1][column-1], matri2[row][column-1], matri2[row-1][column]);
                if (check == 1) {
                    pathway.add(0, "Diagonal ");
                    row--;
                    column--;
                }
                else if (check == 2) {
                    pathway.add(0, "Right ");
                    column--;
                }
                else {
                    pathway.add(0, "Down ");
                    row--;
                }
            }
            if ((row + column) == 0) {
                corner = true;
            }
        }
        System.out.println("Pathway from Dynamic Programming");

        for(int j = 0; j < pathway.size(); j++) {
            System.out.print(pathway.get(j));
        }

    }

    public static int findMax(int diag, int left, int up) {
        if (diag > left && diag > up) {
            return 1;
        }
        else if(left > diag && left > up) {
            return 2;
        }
        else {
            return 3;
        }
    }

// Test 1
// -8 -1 -14 -1 -14 
// -12 12 -13 7 5
// -11 6 -8 12 -2
// 15 -12 15 17 -19
// -8 -12 -2 11 -7

// -8 -9 -23 -24 -38
// -20 4 -9 -2 3
// -31 10 2 14 12 
// -16 -2 25 42 23
// -24 -14 23 53 46
// Time complexity O(n^2)

// Pathway from Dynamic Programming
// Diagonal Down Diagonal Right Down Right

}