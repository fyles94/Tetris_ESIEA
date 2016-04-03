
package view;

import java.awt.Color;
import javax.swing.JPanel;
/***
 * 
 * @author IM&MOOROOGEN
 *
 */

public class HiddenPanel extends JPanel
{
    protected static final Color TRANSPARENT_BG_COLOR = new Color(0,0,0,0);
    
    public HiddenPanel()
    {
        super();
        this.setBackground(TRANSPARENT_BG_COLOR);
    }
}
