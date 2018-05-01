package uDIY;

import java.awt.EventQueue;

/**
 * Driver class that starts the entire program
 * 
 * @author Matthew
 * @version 11/28/2017
 */
public final class Driver {

    /**
     * Private constructor to prevent outside instantiation
     */
    private Driver() {
        throw new IllegalStateException();
    }

    /**
     * Main method that starts the entire program
     * 
     * @param args - Command Line Arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }

}
