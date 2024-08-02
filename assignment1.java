//Author: Kenneth Hung
//Assignment: Programming Assignment 1
//Last revision: 02/18/2024
import java.util.Random;

public class assignment1 {
    public static void main(String[] args) {
        Random random=new Random();
        int length = 100;
        int[] array1 = new int[length];
        int[] array2 = new int[length];
        

        // generate 2 sets of 100 random numbers for testing
        for(int i = 0; i < length; i++) {
            array1[i] = (random.nextInt(100)-50);
        }
        for(int i = 0; i < length; i++) {
            array2[i] = (random.nextInt(100)-50);
        }

        //array 1 output formatted
        System.out.println("Results of Array 1:");
        long[] container = algor1(array1, length);
        System.out.println("               Sum | Low Index | Upper Index | Task Time");
        System.out.printf("Algorithm 1| %6d|%11d|%13d|%10d", container[0], container[1], container[2], container[3]);
        System.out.println();
        container = new long[4];

        container = algor2(array1, length);
        System.out.printf("Algorithm 2| %6d|%11d|%13d|%10d", container[0], container[1], container[2], container[3]);
        System.out.println();
        container = new long[4];

        container = algor3(array1, 0, length-1);
        System.out.printf("Algorithm 3| %6d|%11d|%13d|%10d", container[0], container[1], container[2], container[3]);
        System.out.println();
        container = new long[4];

        container = algor4(array1, length);
        System.out.printf("Algorithm 4| %6d|%11d|%13d|%10d", container[0], container[1], container[2], container[3]);
        System.out.println();
        container = new long[4];
        System.out.println("-------------------------------------------------------------");
        
        //array output 2 formatted
        System.out.println("Results of Array 2:");
        container = algor1(array2, length);
        System.out.println("               Sum | Low Index | Upper Index | Task Time");
        System.out.printf("Algorithm 1| %6d|%11d|%13d|%10d", container[0], container[1], container[2], container[3]);
        System.out.println();
        container = new long[4];

        container = algor2(array2, length);
        System.out.printf("Algorithm 2| %6d|%11d|%13d|%10d", container[0], container[1], container[2], container[3]);
        System.out.println();
        container = new long[4];

        container = algor3(array2, 0, length-1);
        System.out.printf("Algorithm 3| %6d|%11d|%13d|%10d", container[0], container[1], container[2], container[3]);
        System.out.println();
        container = new long[4];

        container = algor4(array2, length);
        System.out.printf("Algorithm 4| %6d|%11d|%13d|%10d", container[0], container[1], container[2], container[3]);
        System.out.println();
        container = new long[4];
        

    }

    public static long[] algor1(int[] data, int size) {
        long[] result = new long[4]; // 0 is maxSum, 1 is lower, 2 is upper, 3 is time
        int MIN_VALUE = -2147483648;
        result[0] = MIN_VALUE;

        long Start= System. nanoTime();
        for(int lower = 0; lower < size; lower++) {
            for(int upper = lower; upper < size; upper++) {
                int thisSum = 0;
                for (int k = lower; k <= upper; k++) {
                    thisSum += data[k];
                    if(thisSum > result[0]) {
                        result[0] = thisSum; //change maxSum
                        result[1] = lower; // update lower
                        result[2] = upper; // update upper
                    }
                }
            }
        }
        long End= System.nanoTime();
        long CPUTime = End - Start;
        result[3] = CPUTime;

        return result;
    }

    public static long[] algor2(int[] data, int size) {
        long[] result = new long[4]; // 0 is maxSum, 1 is lower, 2 is upper, 3 is time
        int MIN_VALUE = -2147483648;
        result[0] = MIN_VALUE;

        long Start= System.nanoTime();
        for(int lower = 0; lower < size; lower++) {
            int thisSum = 0;
            for(int upper = lower; upper < size; upper++) {
                thisSum += data[upper];
                if(thisSum > result[0]) {
                    result[0] = thisSum; //change maxSum
                    result[1] = lower; // update lower
                    result[2] = upper; // update upper
                }
            }
        }
        long End= System.nanoTime();
        long CPUTime = End - Start;
        result[3] = CPUTime;

        return result;
    }

    public static long[] algor3(int[] data, int left, int right) {
        int INT_MIN = -2147483648;
        long[] result = new long[4];
        
        long Start= System.nanoTime();
        if( left == right) { // Base case 
            long[] temp = {data[left], left, right, 0};
            return temp;
        }

        int center = (left + right) / 2;
        long[] maxLeftSum = algor3(data, left, center); 
        long[] maxRightSum = algor3(data, center + 1, right);

        int maxLeftBorderSum = INT_MIN, leftBorderSum = 0;
        for(int i = center; i >= left; -- i) {
            leftBorderSum += data[i];
            if(leftBorderSum > maxLeftBorderSum) {
                maxLeftBorderSum = leftBorderSum;
                result[1] = i;
            }
        }

        int maxRightBorderSum = INT_MIN, rightBorderSum = 0;
        for(int j = center+1; j <= right; j++) {
            rightBorderSum += data[j];
            if(rightBorderSum > maxRightBorderSum) {
                maxRightBorderSum = rightBorderSum;
                result[2] = j;
            }
        }

        // determine max sum
        if (((maxLeftBorderSum + maxRightBorderSum) > maxLeftSum[0]) && ((maxLeftBorderSum + maxRightBorderSum) > maxRightSum[0])) {
            result[0] = maxLeftBorderSum + maxRightBorderSum;
        }
        if ((maxLeftSum[0] > (maxLeftBorderSum + maxRightBorderSum)) && (maxLeftSum[0] > maxRightSum[0])) {
            result = maxLeftSum;
        }
        if ((maxRightSum[0] > maxLeftSum[0]) && (maxRightSum[0] > (maxLeftBorderSum + maxRightBorderSum))) {
            result = maxRightSum;
        }

        long End= System.nanoTime();
        long CPUTime = End - Start;
        result[3] = CPUTime;

        return result;

    }

    public static long[] algor4(int[] data, int length) {
        int INT_MIN = -2147483648;
        long[] result = new long[4];
        int[] MS = new int[length];
        int maxSubSum = INT_MIN;
        int count = -1;

        long Start= System.nanoTime();
        MS[0] = data[0];

        // generate array of sums based on given data
        for(int i = 1; i <= length-1; i++) {
            MS[i] = MS[i-1] + data[i];
            
            if (MS[i] < 0) {
                if(data[i] < 0) {
                    MS[i] = 0;
                }
                else {
                    MS[i] = data[i];
                }
            }
        }

        // determine largest sum
        for(int j = 0; j < length; j++) {
            if (MS[j] < 1) {
                count = -1;
            }
            else {
                count++;
            }

            if (maxSubSum < MS[j]) {
                maxSubSum = MS[j];
                result[1] = count;
                result[2] = j;
            }
        }
        result[0] = maxSubSum;
        result[1] = result[2] - result[1];

        long End = System.nanoTime();
        long CPUTime = End - Start;
        result[3] = CPUTime;

        return result;
    }
   
}
