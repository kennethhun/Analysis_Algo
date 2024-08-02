//Author: Kenneth Hung
//Assignment: Programming Assignment 2
//Last revision: 03/09/2024
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class assignment2 {
    public static void main(String[] args) {
        Random random = new Random();
        int length = 100;
        int kth = 4;
        int[] array1 = new int[length];
        int[] array2 = new int[length];

        // generate 2 size 20 arrays with a max of 20 and min of -20
        for(int i = 0; i < length; i++) {
            array1[i] = (random.nextInt(40)-20);
        }
        for(int i = 0; i < length; i++) {
            array2[i] = (random.nextInt(40)-20);
        }

        // test 1
        long Start = System. nanoTime();
        int algo1Test1 = Procedure1(kth, array1);
        long End = System. nanoTime();
        System.out.println(algo1Test1 + " " + (End - Start));

        Start = System. nanoTime();
        int algo2Test1 = Procedure2(kth, array1);
        End = System. nanoTime();
        System.out.println(algo2Test1 + " " + (End - Start));

        // System.out.println();
        // Arrays.sort(array1);
        // for (int l = 0; l < length; l++) {
        //     System.out.print(array1[l] + " ");
        // }
        System.out.println();

        // test 2
        Start = System. nanoTime();
        int algo1Test2 = Procedure1(kth, array2);
        End = System. nanoTime();
        System.out.println(algo1Test2 + " " + (End - Start));

        Start = System. nanoTime();
        int algo2Test2 = Procedure2(kth, array2);
        End = System. nanoTime();
        System.out.println(algo2Test2 + " " + (End - Start));

        // System.out.println();
        // Arrays.sort(array2);
        // for (int l = 0; l < length; l++) {
        //     System.out.print(array2[l] + " ");
        // }
        System.out.println();

        
    }

    public static int Procedure1(int k, int[] S) {
        if (S.length == 1) {
            return S[0]; // base case return array length 1
        }
        else {
            //create S1, S2, S3 arrays representing less than, equal to, greater than, randomly generated number from S array
            int m = new Random().nextInt(S.length);
            ArrayList<Integer> SList1 = new ArrayList<Integer>();
            ArrayList<Integer> SList2 = new ArrayList<Integer>();
            ArrayList<Integer> SList3 = new ArrayList<Integer>();

            for (int i = 0; i < S.length; i++) {
                if (S[i] < S[m]) {
                    SList1.add(S[i]); // S1 represents less than m
                }
                else if (S[i] == S[m]) {
                    SList2.add(S[i]); // S2 represents equal to m
                }
                else {
                    SList3.add(S[i]); // S3 represents greater than m
                }
            }

            // convert array lists to primitive int arrays
            int[] S1 = SList1.stream().mapToInt(i -> i).toArray();
            int[] S2 = SList2.stream().mapToInt(i -> i).toArray();
            int[] S3 = SList3.stream().mapToInt(i -> i).toArray();

            if (S1.length >= k) {
                return Procedure1(k,S1);
            }
            else if (S1.length + S2.length >= k) {
                return S[m];
            }
            else {
                return Procedure1((k - S1.length - S2.length), S3);
            }
        }
    }

    public static int Procedure2(int k, int[] S) {
        if (S.length < 50) {
            Arrays.sort(S);
            return S[k-1];
        }
        else {
            int lim = 5; // max sequence of 5
            int count = 0;
            ArrayList<Integer> tempList = new ArrayList<Integer>();
            ArrayList<Integer> MList = new ArrayList<Integer>();
            for (int j = 0; j < S.length; j++) {
                tempList.add(S[j]);
                    count++;
                if (count == lim) { // when sequence max reached find median and start new sequence
                    Collections.sort(tempList);
                    MList.add(tempList.get(2));
                    tempList.clear();
                    count = 0;
                }
                else if (j == S.length-1 && count != 0) { // if there are leftovers find median 
                    if (count == 1 || count == 2) {
                        MList.add(tempList.get(0));
                    }
                    else {
                        MList.add(tempList.get(1));
                    }
                    tempList.clear();
                    count = 0;
                }
            }
            int[] M = MList.stream().mapToInt(i -> i).toArray();

            int m = Procedure2((M.length / 2), M); // find m 
            ArrayList<Integer> SList1 = new ArrayList<Integer>();
            ArrayList<Integer> SList2 = new ArrayList<Integer>();
            ArrayList<Integer> SList3 = new ArrayList<Integer>();

            for (int i = 0; i < S.length; i++) {
                if (S[i] < m) {
                    SList1.add(S[i]);
                }
                else if (S[i] == m) {
                    SList2.add(S[i]);
                }
                else {
                    SList3.add(S[i]);
                }
            }

            // convert array lists to primitive int arrays
            int[] S1 = SList1.stream().mapToInt(i -> i).toArray();
            int[] S2 = SList2.stream().mapToInt(i -> i).toArray();
            int[] S3 = SList3.stream().mapToInt(i -> i).toArray();

            if (S1.length >= k) {
                return Procedure2(k,S1);
            }
            else if (S1.length + S2.length >= k) {
                return S[m];
            }
            else {
                return Procedure2((k - S1.length - S2.length), S3);
            }
        }
    }

}