package it.polimi.group03.domain;
import java.util.ArrayList;

/**
 * Created by Megi on 11/11/2015.
 */

public class Beads {

    String color=" "; //here the bead will take its color according to the color in the arraylist
    ArrayList<String> beads=new ArrayList<String>();

	/*public Beads(String color, ArrayList<String> beads)
	{
		this.color=color;
		this.beads=beads;
	}*/


    //the configuration of beads, each 5 beads get different colors represented by a string
    public void beadsConfiguration()
    {

        for (int i=0;i<5;i++) //beads from 0-4 will have the color RED
        {
            color="RED";
            beads.add(color);
        }

        for (int i=5;i<10;i++) //beads from 5 to 9 will have the GREEN color.
        {
            color="GREEN";
            beads.add(color);
        }
        for (int i=10;i<15;i++)
        {
            color="ORANGE";
            beads.add(color);
        }
        for (int i=15;i<20;i++)
        {
            color="BLUE";
            beads.add(color);

        }


    }

    // for testing the arraylist fo beads
    public String testBeads (int indexBead)
    {
        return beads.get(indexBead);

    }



}
