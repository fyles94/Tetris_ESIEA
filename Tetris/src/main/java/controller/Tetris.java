package controller;


import model.Runtime;
import view.*;

public class Tetris
{

    public static void main(String[] args)
    {
        Runtime runtime = new Runtime();
        Window fenetre = new Window(runtime);
        
        fenetre.setVisible(true);
        runtime.run();
    }
    
}
