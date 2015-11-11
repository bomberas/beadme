package it.polimi.group03.domain;

import java.util.ArrayList;

/**
 * Created by Megi on 11/11/2015.
 */
public class Statistics2 {

    ArrayList<Integer> points=new ArrayList<Integer>();
    ArrayList<Integer> victories=new ArrayList<Integer>();
    ArrayList<Integer> losses=new ArrayList<Integer>();
    ArrayList <Integer> gameCount=new ArrayList<Integer>();


    public void addPoints(int p)
    {
        points.add(p);

    }

    public int sumPoints()
    {
        int sum=0,i;
        for (i=0;i<points.size();i++)
        {
            sum=sum+points.get(i);
        }

        return sum;
    }

    public void addVictories (int v)
    {
        victories.add(v);
    }

    public void addLosses (int l)
    {
        losses.add(l);
    }

    public void addGameCount(int gc)
    {
        gameCount.add(gc);
    }

    public int sumVictories()
    {
        int sum=0,i;
        for (i=0;i<victories.size();i++)
        {
            sum=sum+victories.get(i);
        }

        return sum;
    }

    public int sumLosses()
    {
        int sum=0,i;
        for (i=0;i<losses.size();i++)
        {
            sum=sum+losses.get(i);
        }

        return sum;
    }

    public int sumGameCount()
    {
        int sum=0,i;
        for (i=0;i<gameCount.size();i++)
        {
            sum=sum+gameCount.get(i);
        }

        return sum;
    }
}