/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.io.*;
import java.util.*;

public class Factory {

    public static void main(String[] args) {

//        String[] args2 = {"10", "5", "7"};
        String filename = "infile.txt";
        int nResources = args.length;
        int nResources = args2.length;
        int[] resources = new int[nResources];

        for (int i = 0; i < nResources; i++) {

            resources[i] = Integer.parseInt(args[i].trim());
//            resources[i] = Integer.parseInt(args2[i].trim());

        }

        Bank theBank = new BankImpl(resources);
        int[] maxDemand = new int[nResources];
        int[] allocated = new int[nResources];
        Thread[] workers = new Thread[Customer.COUNT];      // the customers

        BufferedReader myReader;
        String[] inputArray;

        int threadNum = 0;

        try {
            // read in values and initialize the matrices
            // to do
            // ...
            myReader = new BufferedReader(new FileReader(filename));

            for (int i = 0; i < Customer.COUNT; i++) {

                inputArray = myReader.readLine().split(",");
                
                for(int j = 0; j < nResources; j++){
                
                    allocated[j] = Integer.parseInt(inputArray[j]);
                    maxDemand[j] = Integer.parseInt(inputArray[j+3]);
                
                }

                workers[threadNum] = new Thread(new Customer(threadNum, maxDemand, theBank));
                theBank.addCustomer(threadNum, allocated, maxDemand);

                ++threadNum;        //theBank.getCustomer(threadNum);

            }
//            resourceNum = 0;

        } catch (FileNotFoundException fnfe) {

            throw new Error("Unable to find file \"" + filename + "\"");

        } catch (IOException ioe) {

            throw new Error("Error processing \"" + filename + "\"");

        }

        System.out.println("FACTORY: created threads");     // start the customers

        for (int i = 0; i < Customer.COUNT; i++) {

            workers[i].start();

        }

        System.out.println("FACTORY: started threads");

    }

}
