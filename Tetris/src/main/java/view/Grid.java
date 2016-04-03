package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import model.Piece;

public class Grid extends HiddenPanel implements Observer
{
    public Grid()
    {
        super();
        
        GridLayout layout = new GridLayout(model.Grid.LINE_NUMBERS, model.Grid.COL_NUMBERS);
        super.setLayout(layout);
        
        layout.setHgap(1);
        layout.setVgap(1);
        
        this.cases = new Case[model.Grid.LINE_NUMBERS][model.Grid.COL_NUMBERS];
        for(int y = 0; y < model.Grid.COL_NUMBERS; y++)
            for(int x = 0; x < model.Grid.LINE_NUMBERS; x++)
            {
                Case elem = new Case();
                this.add(elem);
                this.cases[x][y] = elem;
            }
        
        this.setBackground(Color.black);
        this.setBorder(BorderFactory.createMatteBorder(0, 7, 0, 7, Color.magenta));
    }
    private Case[][] cases;
    
    public Case getCase(int x, int y)
    {
        return this.cases[x][y];
    }
    

    public void update(Observable obs, Object obj)
    {
        if(obj instanceof model.Grid.GridChangedEventArg)
        {
            model.Grid.GridChangedEventArg gridArg = (model.Grid.GridChangedEventArg)obj;
            
            
            Color[][] colors = gridArg.getCases();
            
            for(int x = 0; x < model.Grid.COL_NUMBERS; x++)
                for(int y = 0; y < model.Grid.LINE_NUMBERS; y++)
                    cases[x][y].SetColor(colors[x][y]);
            
            this.getParent().repaint();
        }
    }
    
}
