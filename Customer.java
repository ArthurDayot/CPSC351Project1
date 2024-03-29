/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

public class Customer implements Runnable {

    public static final int COUNT = 5;    // number of threads

    private int numOfResources;     // N different resources
    private int[] maxDemand;        // maximum this thread will demand
    private int customerNum;        // customer number
    private int[] request;          // request it is making

    private java.util.Random rand;  // random number generator

    private Bank theBank;           // synchronizing object

    public Customer(int customerNum, int[] maxDemand, Bank theBank) {

        this.customerNum = customerNum;
        this.maxDemand = new int[maxDemand.length];
        this.theBank = theBank;

        System.arraycopy(maxDemand, 0, this.maxDemand, 0, maxDemand.length);
        numOfResources = maxDemand.length;
        request = new int[numOfResources];
        rand = new java.util.Random();

    }

    public void run() {

        boolean canRun = true;

        while (canRun) {

            try {

                SleepUtilities.nap();       // take a nap
                // ... then, make a resource request
                for (int i = 0; i < numOfResources; i++) {

                    request[i] = rand.nextInt(maxDemand[i] + 1);

                }

                if (theBank.requestResources(customerNum, request)) {   // if customer can proceed

                    SleepUtilities.nap();   // use and release the resources
                    theBank.releaseResources(customerNum, request);

                } else { //Added else statement
                
                    throw new InterruptedException();
                    
                }

            } catch (InterruptedException ie) {

                canRun = false;

            }

        }

        System.out.println("Thread # " + customerNum + " I'm interrupted.");

    }

}
