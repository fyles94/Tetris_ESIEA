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

public class Grid extends HiddenPanel implements Observer{
	public Grid()
    {
        super();
        
        GridLayout layout = new GridLayout(model.Grid.MAX_H, model.Grid.MAX_W);
        super.setLayout(layout);
        
        layout.setHgap(1);
        layout.setVgap(1);
        
        this.cases = new Case[model.Grid.MAX_H][model.Grid.MAX_W];
        for(int y = 0; y < model.Grid.MAX_W; y++)
            for(int x = 0; x < model.Grid.MAX_H; x++)
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
    
    @Override
    public void update(Observable obs, Object obj)
    {
        if(obj instanceof model.Grid.GridChangedEventArg)
        {
            model.Grid.GridChangedEventArg gridArg = (model.Grid.GridChangedEventArg)obj;
            
            if(gridArg.isPersistent())
            {
                // Son
            }
            
            Color[][] colors = gridArg.getCases();
            
            for(int x = 0; x < model.Grid.MAX_W; x++)
                for(int y = 0; y < model.Grid.MAX_H; y++)
                    cases[x][y].SetColor(colors[x][y]);
            
            return;
        }
    }
}
