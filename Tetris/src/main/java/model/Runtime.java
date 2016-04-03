package model;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Runtime implements Runnable
{
    private static final int START_TIME = 1000;
    private static final int DECREMENT_TIME = 100;
    
    public Runtime()
    {
        Piece.setDefaultPosition(new Point(Grid.COL_NUMBERS / 2, 0));
        
        grid = new Grid();
        
        queue = new PieceQueue();
        currentPiece = queue.getNext();
        grid.setVolatilePiece(currentPiece);
        
        score = new Score();
    }
    
    private final PieceQueue queue;
    private final Grid grid;
    private final Score score;
    
    private Piece currentPiece;
    private boolean finished;
    

    public void run()
    {
        try
        {
            finished = false;
            score.reset();
            
            int time = START_TIME;
            
            while(!finished)
            {
                currentPiece.moveDown();
                if(grid.IsPossible(currentPiece))
                    grid.setVolatilePiece(currentPiece);
                else
                {
                    currentPiece.cancelLastMove();
                    nextPiece();
                    
                    // Accelerer le temps
                    time -= DECREMENT_TIME;
                }
                
                Thread.sleep(time);
                ManagePause();
            }
        }
        catch (InterruptedException ex)
        { }
    }
    
    //<editor-fold desc="Pause / Resume">
    private boolean pause = false;
    private void ManagePause()
    {
        if(pause)
        {
            try
            {
                synchronized(this)
                {
                    this.wait();
                }
            }
            catch (InterruptedException ex)
            { }
            finally
            {
                setPause(false);
            }
        }
    }
    public void Pause()
    {
        setPause(true);
    }
    synchronized public void setPause(boolean value)
    {
        pause = value;
    }
    synchronized public void Resume()
    {
        this.notifyAll();
    }
    
    private void nextPiece()
    {
        int score = grid.setPersistentPiece(currentPiece);
        this.score.addScore(score);
        
        currentPiece = queue.getNext();
        finished = grid.IsInCollision(currentPiece);
        
        if(!finished)
            grid.setVolatilePiece(currentPiece);
    }
    
    public void setObservers(view.Grid grid, view.Score score, view.PieceQueue queue)
    {
        this.grid.addObserver(grid);
        this.score.addObserver(score);
        this.queue.addObserver(queue);
    }
    
    private boolean isBlocked()
    {
        return finished || pause;
    }
    
    public void MoveLeft()
    {
        if(isBlocked())
            return;
        
        currentPiece.moveLeft();
        if(grid.IsPossible(currentPiece))
            grid.setVolatilePiece(currentPiece);
        else
            currentPiece.cancelLastMove();
    }
    public void MoveRight()
    {
        if(isBlocked())
            return;
        
        currentPiece.moveRight();
        if(grid.IsPossible(currentPiece))
            grid.setVolatilePiece(currentPiece);
        else
            currentPiece.cancelLastMove();
    }
    public void Rotate()
    {
        if(isBlocked())
            return;
        
        currentPiece.rotateRight90();
        if(grid.IsPossible(currentPiece))
            grid.setVolatilePiece(currentPiece);
        else
            currentPiece.cancelLastRotate();
    }
    public void PushBottom()
    {
        if(isBlocked())
            return;
        
        if(grid.IsPossible(currentPiece))
        {
            do
            {
                currentPiece.moveDown();
            } while(grid.IsPossible(currentPiece));

            currentPiece.cancelLastMove();
            nextPiece();
        }
    }

    public class TerminatedEventArg
    {
        public TerminatedEventArg(int score)
        {
            value = score;
        }
        
        private final int value;
        public int getFinalScore()
        {
            return value;
        }
    }
}
