package com.app.model.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.app.model.simulation.Simulation;

public class SaveLoadManager {

    private final static String DIRECTORY = "saves";
    
    public static void saveInFile(Simulation simulation) throws Exception {
        File dir = new File(DIRECTORY);
        
        if(!dir.exists()){
            dir.mkdirs();
        }

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DIRECTORY + "/" + simulation.getName() + ".bin"));
        out.writeObject(simulation);
        out.close();
    }

    public static Simulation restoreFromFile(String fileName) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(DIRECTORY + "/" + fileName));
        Simulation simulation = (Simulation) in.readObject();
        in.close();
        return simulation;
    }

    public static String[] getFilesName(){
        File dir = new File(DIRECTORY);
        
        if(!dir.exists()){
            return null;
        }

        return dir.list();
    }
}
