package model;

import java.util.Observable;

public class Score extends Observable
{
    public Score()
    {
        super();
    }
    
    private int score;
    private int nb;
    private void setScore(int value)
    {
        score = value;
        
        super.setChanged();
        super.notifyObservers(new ScoreChangedEventArg(score, nb));
    }
    
    public void addScore(int value)
    {
        if(value == 0)
            setScore(score);
        else
        {
            nb++;
            setScore(score + (int)Math.pow(2, value * 2));
        }
    }
    
    public void reset()
    {
        nb = 0;
        setScore(0);
    }
    
    public class ScoreChangedEventArg
    {
        public ScoreChangedEventArg(int score, int nb)
        {
            this.score = score;
            this.nb = nb;
        }
        
        private final int score;
        public int getScore()
        {
            return score;
        }
        
        private final int nb;
        public int getNb()
        {
            return nb;
        }
    }
}
