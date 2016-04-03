package model;

import java.util.Observable;

public class Score extends Observable {
	public Score() {
        super();
        score = 0;
    }
    
    private int score;
    private void setScore(int value) {
        score = value;
        super.setChanged();
        super.notifyObservers(new ScoreChangedEventArg(score));
    }
    
    public void addScore(int value)
    {
        if(value == 0)
            setScore(score);
        else
            setScore(score + (int)Math.pow(2, value * 2));
    }
    
    public void reset()
    {
        setScore(0);
    }
    
    //<editor-fold desc="Events">
    public class ScoreChangedEventArg
    {
        public ScoreChangedEventArg(int score)
        {
            value = score;
        }
        
        private final int value;
        public int getScore()
        {
            return value;
        }
    }
}
