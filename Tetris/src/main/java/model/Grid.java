package model;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;

public class Grid extends Observable
{
    public static final int COL_NUMBERS = 15;
    public static final int LINE_NUMBERS = 15;
    public static final Color DEFAULT_COLOR = new Color(255, 255, 255);
    
    public Grid()
    {
        super();
        
        this.boardPiece = new Color[COL_NUMBERS][LINE_NUMBERS];
        
        for(int x = 0; x < COL_NUMBERS; x++)
            for(int y = 0; y < LINE_NUMBERS; y++)
                this.boardPiece[x][y] = DEFAULT_COLOR;
    }
    
    private final Color[][] boardPiece;
    
    public void setVolatilePiece(Piece p)
    {
        if(!p.isFixed())
        {
            Color[][] cases = (Color[][])DeepArrayClone(this.boardPiece);
            setPiece(p, cases);
        }
    }
    public int setPersistentPiece(Piece p)
    {
        p.fixe();
        return setPiece(p, this.boardPiece);
    }
    private int setPiece(Piece p, Color[][] cases)
    {
        Point pos = p.getPosition();
        Point size = p.getSize();
        
        for(int x = 0; x < size.x; x++)
            for(int y = 0; y < size.y; y++)
                if(p.getCase(x, y))
                    cases[pos.x + x][pos.y + y] = p.getColor();
        
        super.setChanged();
        super.notifyObservers(new GridChangedEventArg(cases, cases == this.boardPiece));
        
        return getScore(cases);
    }
    private boolean isFullLine(Color[][] cases, int offset)
    {
        for(int x = 0; x < COL_NUMBERS; x++)
            if(cases[x][offset] == DEFAULT_COLOR)
                return false;
        return true;
    }
    protected int getScore(Color[][] cases)
    {
        int nb_decal = 0;
        
        for(int y = 0; y < LINE_NUMBERS; y++)
            if(isFullLine(cases, y))
            {
                nb_decal++;
                
                if(y == 0)
                {
                    for(int x = 0; x < COL_NUMBERS; x++)
                        cases[x][0] = DEFAULT_COLOR;
                }
                else
                {
                    for(int y2 = y; y2 > 0; y2--)
                        for(int x = 0; x < COL_NUMBERS; x++)
                            cases[x][y2] = cases[x][y2 - 1];
                }
                
                y--;
            }
        
        return nb_decal;
    }
    
    
    private Color[][] DeepArrayClone(Color[][] array)
    {
        Color[][] cloned = new Color[array.length][];
        
        for(int i = 0; i < array.length; i++)
            cloned[i] = array[i].clone();
        
        return cloned;
    }
    
    public boolean IsPossible(Piece p)
    {
        return !IsOutOfBound(p) &&
               !IsInCollision(p);
    }
    public boolean IsInCollision(Piece p)
    {
        Point pos = p.getPosition();
        Point size = p.getSize();
        
        for(int x = 0; x < size.x; x++)
            for(int y = 0; y < size.y; y++)
                if(p.getCase(x, y))
                    if(this.boardPiece[pos.x + x][pos.y + y] != DEFAULT_COLOR)
                        return true;
        
        return false;
    }
    public boolean IsOutOfBound(Piece p)
    {
        Point pos = p.getPosition();
        Point size = p.getSize();
        
        return (pos.y + size.y > LINE_NUMBERS ||
                pos.x < 0 ||
                pos.x + size.x > COL_NUMBERS);
    }
    
    
    public class GridChangedEventArg
    {
        public GridChangedEventArg(Color[][] cases, boolean isPersistent)
        {
            this.cases = cases;
            this.stillPiece = isPersistent;
        }
        
        private final boolean stillPiece;
        public boolean isPersistent()
        {
            return stillPiece;
        }
        
        private final Color[][] cases;
        public Color[][] getCases()
        {
            return cases;
        }
    }
}