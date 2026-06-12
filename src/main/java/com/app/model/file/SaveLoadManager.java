package com.app.model.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.app.model.simulation.Simulation;

/**
 * The save load manager class that manages the file system of the simulation
 * @version 3.0
 * @since 3.0
 * @author Rayane
 */
public class SaveLoadManager {

    /**
     * The directory where the simulation saves are stored
     */
    private final static String DIRECTORY = "saves";
    
    /**
     * Saves a simulation in a binary file
     * The file name is the same as the simulation name
     * @param simulation The simulation that will be saved
     */
    public static void saveInFile(Simulation simulation) throws Exception {
        File dir = new File(DIRECTORY);
        
        if(!dir.exists()){
            dir.mkdirs();
        }

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DIRECTORY + "/" + simulation.getName() + ".bin"));
        out.writeObject(simulation);
        out.close();
    }

    /**
     * Restores a simulation from a file
     * @param fileName The name of the file that will be restored
     * @return The simulation that was restored from the file
     */
    public static Simulation restoreFromFile(String fileName) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(DIRECTORY + "/" + fileName));
        Simulation simulation = (Simulation) in.readObject();
        in.close();
        
        return simulation;
    }

    /**
     * Returns a list of the names of all the saves
     * @return a list of the names of all the saves
     */
    public static String[] getFilesName(){
        File dir = new File(DIRECTORY);
        
        if(!dir.exists()){
            return null;
        }

        return dir.list();
    }
}