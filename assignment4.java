

public class assignment4 {
    public static void main(String[] args) {
/*Results
 * 0 1 1 1 0 0 0 0 0 0 0 0 0 
0 0 0 0 1 1 0 0 0 0 0 0 0
0 0 0 0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 1 0 0 1 0 0 0 0
0 0 0 0 0 0 1 0 0 0 0 0 0
0 0 0 0 0 0 1 1 0 0 0 0 0
0 0 0 0 0 0 0 0 1 1 0 0 0
0 0 0 0 0 0 0 0 1 1 0 0 0
0 0 0 0 0 0 0 0 0 0 1 1 0
0 0 0 0 0 0 0 0 0 0 0 0 1
0 0 0 0 0 0 0 0 0 0 0 0 1
0 0 0 0 0 0 0 0 0 0 0 0 1 
0 0 0 0 0 0 0 0 0 0 0 0 0
Method 1

First pass
Task 0: 0 2 0 0
Task 1: 2 6 0 0
Task 2: 2 7 0 0
Task 3: 2 11 0 0
Task 4: 6 9 0 0
Task 5: 11 13 0 0
Task 6: 13 14 0 0
Task 7: 13 23 0 0
Task 8: 23 34 0 0
Task 9: 23 29 0 0
Task 10: 34 43 0 0
Task 11: 34 42 0 0
Task 12: 43 50 0 0

Second pass
Task 0: 0 2 0 2
Task 1: 2 6 7 11 
Task 2: 2 7 6 11
Task 3: 2 11 2 11
Task 4: 6 9 19 22
Task 5: 11 13 11 13
Task 6: 13 14 22 23
Task 7: 13 23 13 23
Task 8: 23 34 23 34
Task 9: 23 29 37 43
Task 10: 34 43 34 43
Task 11: 34 42 35 43
Task 12: 43 50 43 50
The critical path of nodes is: 0 3 5 7 8 10 12
 */
        //initialize nodes
        int[][] Gmatrix = new int[13][13];
        // Gmatrix[i][j] = 1 iff j is immediate successor of i
        Gmatrix[0][1] = 1;
        Gmatrix[0][2] = 1;
        Gmatrix[0][3] = 1;
        Gmatrix[1][4] = 1;
        Gmatrix[1][5] = 1;
        Gmatrix[2][5] = 1;
        Gmatrix[3][5] = 1;
        Gmatrix[3][8] = 1;
        Gmatrix[4][6] = 1;
        Gmatrix[5][6] = 1;
        Gmatrix[5][7] = 1;
        Gmatrix[6][9] = 1;
        Gmatrix[6][8] = 1;
        Gmatrix[7][8] = 1;
        Gmatrix[7][9] = 1;
        Gmatrix[8][10] = 1;
        Gmatrix[8][11] = 1;
        Gmatrix[9][12] = 1;
        Gmatrix[10][12] = 1;
        Gmatrix[11][12] = 1;
        for(int i = 0; i < Gmatrix.length; i++) {
            for(int j = 0; j < Gmatrix.length; j++) {
                System.out.print(Gmatrix[i][j] + " ");
            }
            System.out.println();
        }
        Node[] Tarray = new Node[13];
        for(int i = 0; i < Tarray.length; i++) {
            Tarray[i] = new Node();
        }
        
        //fill in node information manually
        /**
         * Task Duration Predecessor
         * 1    5        -
         * 2    6        -
         * 3    4        1
         * 4    3        1
         * 5    1        1
         * 6    4        5
         * 7    14       4, 6
         * 8    12       2, 3
         * 9    2        7, 8
         */

        Tarray[0].time = 2;
        Tarray[1].time = 4;
        Tarray[2].time = 5;
        Tarray[3].time = 9;
        Tarray[4].time = 3;
        Tarray[5].time = 2;
        Tarray[6].time = 1;
        Tarray[7].time = 10;
        Tarray[8].time = 11;
        Tarray[9].time = 6;
        Tarray[10].time = 9;
        Tarray[11].time = 8;
        Tarray[12].time = 7;


        method1(Tarray, Gmatrix);

    }

    public static void method1(Node[] T, int[][] G) {
        // first pass
        System.out.println("Method 1");
        System.out.println();
        for(int j = 0; j < G.length; j++) {
            for(int i = 0; i < G.length; i++) {
                if (G[i][j] == 1) {
                    T[j].ES = Math.max(T[j].ES, T[i].EF);
                }
            }
            T[j].EF = T[j].ES + T[j].time;
        }

        System.out.println("First pass");
        for(int k = 0; k < T.length; k++) {
            System.out.print("Task " + k + ": ");
            System.out.print(T[k].ES + " ");
            System.out.print(T[k].EF + " ");
            System.out.print(T[k].LS + " ");
            System.out.print(T[k].LF + " ");
            //System.out.print(T[k].time + " ");
            System.out.println();
        }

        // second pass
        T[T.length - 1].LF = T[T.length - 1].EF;
        T[T.length - 1].LS = T[T.length - 1].ES;

        for(int j = G.length - 1; j >= 0; j--) {
            for(int i = 0; i < G.length; i++) {
                //System.out.print(G[i][j] + " ");
                if (G[i][j] == 1) {
                    if(T[i].LF == 0) {
                        T[i].LF = T[j].LS;
                        T[i].LS = T[i].LF - T[i].time;
                    }
                    else {
                        T[i].LF = Math.min(T[i].LF, T[j].LS);
                        T[i].LS = T[i].LF - T[i].time;
                    }
                }
            }
            //System.out.println();
        }
        System.out.println();
        System.out.println("Second pass");
        String criticalPath = "";
        for(int k = 0; k < T.length; k++) {
            if(T[k].ES == T[k].LS && T[k].EF == T[k].LF) {
                criticalPath = criticalPath + k + " ";
            }
            System.out.print("Task " + k + ": ");
            System.out.print(T[k].ES + " ");
            System.out.print(T[k].EF + " ");
            System.out.print(T[k].LS + " ");
            System.out.print(T[k].LF + " ");
            //System.out.print(T[k].time + " ");
            System.out.println();
        }
        System.out.println("The critical path of nodes is: " + criticalPath);

    }
}

class Node {
    int time = 0; //time to complete task
    int ES = 0; // earliest start time
    int EF = 0; // earliest finish time
    int LS = 0; // latest start time
    int LF = 0; // latest finish time
}
