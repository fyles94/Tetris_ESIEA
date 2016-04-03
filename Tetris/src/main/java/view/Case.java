package view;

import java.awt.Color;
import javax.swing.JPanel;
/***
 * 
 * @author IM&MOOROOGEN
 *
 */
public class Case extends JPanel
{
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    
    public Case()
    {
        super();
        super.setSize(WIDTH, HEIGHT);
        super.setBackground(Color.WHITE);
    }
    
    public void SetColor(Color color)
    {
        super.setBackground(color);
    }
}
