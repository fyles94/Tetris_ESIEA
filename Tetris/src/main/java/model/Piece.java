
package model;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Piece
{
    public Piece()
    {
        generate();
    }
    
    private boolean[][] matrix;
    
    private static Point defposition = new Point(0, 0);
    public static Point getDefaultPosition()
    {
        return defposition;
    }
    public static void setDefaultPosition(Point Position)
    {
    	defposition = Position;
    }
    
    private Boolean fixe = false;
    public Boolean isFixed()
    {
        return fixe;
    }
    public void fixe()
    {
        fixe = true;
    }
    
    public Point getSize()
    {
        return new Point(matrix.length, matrix[0].length);
    }
    
    private Point der_position = null;
    private Point position = null;
    public Point getPosition()
    {
        return position;
    }
    private void setPosition(Point Position)
    {
        der_position = position;
        position = Position;
    }
    private void cancelPosition()
    {
        position = der_position;
        der_position = null;
    }
    
    private Color _color;
    public Color getColor()
    {
        return _color;
    }
    
    private void generate()
    {
        switch(new Random().nextInt(7))
        {
            case 0:
                regenerateMatrix(4, 1, true);
                
                _color = Color.RED;
                break;
                
            case 1:
                regenerateMatrix(3, 2, false);
                
                matrix[0][0] = true;
                matrix[1][0] = true;
                matrix[2][0] = true;
                matrix[2][1] = true;
                
                _color = Color.BLUE;
                break;
                
            case 2:
                regenerateMatrix(3, 2, false);
                
                matrix[0][0] = true;
                matrix[1][0] = true;
                matrix[2][0] = true;
                matrix[0][1] = true;
                
                _color = Color.ORANGE;
                break;
                
            case 3:
                regenerateMatrix(2, 2, true);
                
                _color = Color.YELLOW;
                break;
                
            case 4:
                regenerateMatrix(3, 2, false);
                
                matrix[0][1] = true;
                matrix[1][1] = true;
                matrix[1][0] = true;
                matrix[2][0] = true;
                
                _color = Color.MAGENTA;
                break;
                
            case 5:
                regenerateMatrix(3, 2, false);
                
                matrix[0][0] = true;
                matrix[1][0] = true;
                matrix[2][0] = true;
                matrix[1][1] = true;
                
                _color = Color.CYAN;
                break;
                
            case 6:
                regenerateMatrix(3, 2, false);
                
                matrix[0][0] = true;
                matrix[1][0] = true;
                matrix[1][1] = true;
                matrix[2][1] = true;
                
                _color = Color.GREEN;
                break;
        }
        
        setPosition(getDefaultPosition());
    }
    private void regenerateMatrix(int size_x, int size_y, boolean defaultvalue)
    {
        matrix = new boolean[size_x][size_y];
        
        for(int x = 0; x < size_x; x++)
            for(int y = 0; y < size_y; y++)
                matrix[x][y] = defaultvalue;
    }
    
    public boolean getCase(int x, int y)
    {
        return matrix[x][y];
    }
  
    public void moveLeft()
    {
        setPosition(new Point(
                getPosition().x - 1,
                getPosition().y
        ));
    }
    public void moveRight()
    {
        setPosition(new Point(
                getPosition().x + 1,
                getPosition().y
        ));
    }
    public void moveUp()
    {
        setPosition(new Point(
                getPosition().x,
                getPosition().y - 1
        ));
    }
    public void moveDown()
    {
        setPosition(new Point(
                getPosition().x,
                getPosition().y + 1
        ));
    }
    public void cancelLastMove()
    {
        cancelPosition();
    }
    
    private boolean isLastRotateLeft = false;
    public void rotateLeft90()
    {
        isLastRotateLeft = true;
        
        int max_x = getSize().x;
        int max_y = getSize().y;
        
        boolean[][] temp = new boolean[max_y][max_x];
        
        for(int x = 0; x < max_y; x++)
            for(int y = 0; y < max_x; y++)
                temp[x][y] = matrix[max_x - 1 - y][x];
        
        matrix = temp;
    }
    public void rotateRight90()
    {
        isLastRotateLeft = false;
        
        int max_x = getSize().x;
        int max_y = getSize().y;
        
        boolean[][] temp = new boolean[max_y][max_x];
        
        for(int x = 0; x < max_y; x++)
            for(int y = 0; y < max_x; y++)
                temp[x][y] = matrix[y][max_y - 1 - x];
        
        matrix = temp;
    }
    public void cancelLastRotate()
    {
        if(isLastRotateLeft)
            rotateRight90();
        else
            rotateLeft90();
    }
}
