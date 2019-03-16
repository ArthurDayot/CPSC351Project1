/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.io.*;
import java.util.*;

public class BankImpl implements Bank {

    private int n;			// the number of threads in the system
    private int m;			// the number of resources

    private int[] available; 	// the amount available of each resource
    private int[][] maximum; 	// the maximum demand of each thread
    private int[][] allocation;	// the amount currently allocated to each thread
    private int[][] need;		// the remaining needs of each thread

    private void showAllMatrices(int[][] alloc, int[][] max, int[][] need, String msg) {

        //Print msg
        System.out.println("\n" + msg + "\nALLOCATED\tMAXIMUM\tNEED");

        //Print matrices
        for (int i = 0; i < alloc.length; i++) {

            System.out.print("[ ");

            for (int j = 0; j < alloc[i].length; j++) {

                System.out.print(alloc[i][j] + " ");

            }

            System.out.print("]\t[ ");

            for (int j = 0; j < max[i].length; j++) {

                System.out.print(max[i][j] + " ");

            }

            System.out.print("]\t[ ");

            for (int j = 0; j < need[i].length; j++) {

                System.out.print(need[i][j] + " ");

            }

            System.out.println("]");

        }

    }

    private void showMatrix(int[][] matrix, String title, String rowTitle) {

        //Print title and rowtitle
        System.out.println("\ntitle\nrowtitle");

        //Print matrix
        for (int i = 0; i < matrix.length; i++) {

            System.out.print("[ ");

            for (int j = 0; j < matrix[i].length; j++) {

                System.out.print(matrix[i][j] + " ");

            }

            System.out.println("]");

        }

    }

    private void showVector(int[] vect, String msg) {

        //Print title and rowtitle
        System.out.println("\ntitle\nrowtitle: [ ");

        //Print matrix
        for (int i = 0; i < vect.length; i++) {

            System.out.print(vect[i] + " ");

        }

        System.out.println("]");

    }

    public BankImpl(int[] resources) {      // create a new bank (with resources)

        available = resources;
        n = Customer.COUNT;
        m = available.length;
        maximum = new int[n][m];
        allocation = new int[n][m];
        need = new int[n][m];

    }
    // invoked by a thread when it enters the system;  also records max demand

    public void addCustomer(int threadNum, int[] allocated, int[] maxDemand) {

        for (int i = 0; i < available.length; i++) {

            allocation[threadNum][i] = allocated[i];
            maximum[threadNum][i] = maxDemand[i];
            need[threadNum][i] = maxDemand[i] - allocated[i];

        }

    }

    public void getState() {        // output state for each thread

        showAllMatrices(allocation, maximum, need, "Getting State\n");

    }

    private boolean isSafeState(int threadNum, int[] request) {

        //Check if resources shall be denied
        for (int i = 0; i < request.length; i++) {

            if (request[i] > need[threadNum][i] || request[i] > available[i]) {

                System.out.println("Resources denied");

                return false;

            }

        }

        //Otherwise, approve request
        System.out.println("Request accepted");

        for (int i = 0; i < request.length; i++) {

            allocation[threadNum][i] += request[i];

            available[i] -= request[i];

            if (need[threadNum][i] - request[i] <= 0) {

                need[threadNum][i] = 0;

            } else {

                need[threadNum][i] = need[threadNum][i] - request[i];

            }

        }
        
        showAllMatrices(allocation, maximum, need, "");

        return true;

    }
    // make request for resources. will block until request is satisfied safely

    @Override
    public synchronized boolean requestResources(int threadNum, int[] request) {

        return isSafeState(threadNum, request);
    }

    public synchronized void releaseResources(int threadNum, int[] release) {

        for (int i = 0; i < release.length; i++) {

            allocation[threadNum][i] -= release[i];
            available[i] += release[i];

        }

    }

}
