package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
/***
 * 
 * @author IM&MOOROOGEN
 *
 */

public class PieceQueue extends HiddenPanel implements Observer
{
    private static final int MAX_W = 5;
    private static final int MAX_H = 12;
    
    public PieceQueue()
    {
        super();
        
        GridLayout layout = new GridLayout(MAX_H, MAX_W);
        super.setLayout(layout);
        
        layout.setHgap(1);
        layout.setVgap(1);
        
        this.cases = new Case[MAX_H][MAX_W];
        for(int x = 0; x < MAX_H; x++)
            for(int y = 0; y < MAX_W; y++)
            {
                Case elem = new Case();
                elem.SetColor(TRANSPARENT_BG_COLOR);
                this.add(elem);
                this.cases[x][y] = elem;
            }
    }
    private Case[][] cases;
    
    
    public void update(Observable obs, Object obj)
    {
        if(obj instanceof model.PieceQueue.PieceQueueChangedEventArg)
        {
            model.PieceQueue.PieceQueueChangedEventArg pqArg = (model.PieceQueue.PieceQueueChangedEventArg)obj;
            
            Iterator<model.Piece> pieces = pqArg.getPieces();
            
            Point position = new Point(1, 1);
                
            for(int x = 0; x < MAX_H; x++)
                for(int y = 0; y < MAX_W; y++)
                    this.cases[x][y].SetColor(TRANSPARENT_BG_COLOR);
            
            int offset_x;
            while(pieces.hasNext())
            {
                model.Piece p = pieces.next();
                Point size = p.getSize();
                
                if(position.y + size.y > MAX_H)
                    break;
                
                offset_x = (4 - size.x) / 2; // X offset
                
                for(int x = 0; x < size.x; x++)
                    for(int y = 0; y < size.y; y++)
                    {
                        Case selectedCase = this.cases[position.y + y][position.x + x + offset_x];
                        if(p.getCase(x, y))
                            selectedCase.SetColor(p.getColor());
                        else
                            selectedCase.SetColor(TRANSPARENT_BG_COLOR);
                    }
                
                position = new Point(position.x, position.y + size.y + 1);
            }
            
            this.getParent().repaint();
        }
    }
}
