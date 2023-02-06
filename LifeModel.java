import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.*;

import javax.swing.Timer;

public class LifeModel implements ActionListener
{

	/*
	 *  This is the Model component.
	 */

	private static int SIZE = 60;
	private LifeCell[][] grid, initialGrid;
	
	LifeView myView;
	Timer timer;

	/** Construct a new model using a particular file */
	public LifeModel(LifeView view, String fileName) throws IOException
	{       
		int r, c;
		grid = new LifeCell[SIZE][SIZE];
		initialGrid = new LifeCell[SIZE][SIZE];
		for ( r = 0; r < SIZE; r++ )
		{
			for ( c = 0; c < SIZE; c++ ) 
			{
				grid[r][c] = new LifeCell();
				initialGrid[r][c] = new LifeCell();
			}
		}
			

		if ( fileName == null ) //use random population
		{                                           
			for ( r = 0; r < SIZE; r++ )
			{
				for ( c = 0; c < SIZE; c++ )
				{
					if ( Math.random() > 0.85) //15% chance of a cell starting alive
						grid[r][c].setAliveNow(true);
						initialGrid[r][c].setAliveNow(true);
				}
			}
		}
		else
		{                 
			Scanner input = new Scanner(new File(fileName));
			int numInitialCells = input.nextInt();
			for (int count=0; count<numInitialCells; count++)
			{
				r = input.nextInt();
				c = input.nextInt();
				grid[r][c].setAliveNow(true);
				initialGrid[r][c].setAliveNow(true);

			}
			input.close();
		}

		// initialGrid = grid;

			// grid = initialGrid;
			for (r = 0; r < SIZE; r++ )
			{
				for (c = 0; c < SIZE; c++ )
				{
					initialGrid[r][c] =  new LifeCell(grid[r][c].getAliveNowValue(),grid[r][c].getAliveNextValue() );
				}
			}


		myView = view;
		myView.updateView(grid);

	}

	/** Constructor a randomized model */
	public LifeModel(LifeView view) throws IOException
	{
		this(view, "penta.lif");
	}

	


	/** pause the simulation (the pause button in the GUI */
	public void pause()
	{
		timer.stop();
	}
	
	/** resume the simulation (the pause button in the GUI */
	public void resume()
	{
		timer.restart();
	}
	
	/** run the simulation (the pause button in the GUI */
	public void run()
	{
		timer = new Timer(500, this);
		timer.setCoalesce(true);
		timer.start();
	}
	// reset the simulation (the reset button in the GUI)
	public void reset()
	{
		// grid = initialGrid;
		for ( int r = 0; r < SIZE; r++ )
		{
			for ( int c = 0; c < SIZE; c++ )
			{
					// grid[r][c] =  new LifeCell(initialGrid[r][c].getAliveNowValue(),initialGrid[r][c].getAliveNextValue() );
					grid[r][c].setAliveNext(initialGrid[r][c].getAliveNextValue());
					grid[r][c].setAliveNow(initialGrid[r][c].getAliveNowValue());
			}
		}


		// myView = view;
		myView.updateView(grid);
		
	}
	//generate random colors
	public void randomColors()
	{
		Color randomColor =  new Color((int)(Math.random() * 0x1000000));
		for ( int r = 0; r < SIZE; r++ )
			{
				for ( int c = 0; c < SIZE; c++ )
				{
						randomColor =  new Color((int)(Math.random() * 0x1000000));
						grid[r][c].setColor(randomColor);
				}
			}
		
	}

	public void resetMonoColor()
	{
		
		for ( int r = 0; r < SIZE; r++ )
			{
				for ( int c = 0; c < SIZE; c++ )
				{
					Color color =  Color.BLUE;
						grid[r][c].setColor(color);
				}
			}
		
	}

	/** called each time timer fires */
	public void actionPerformed(ActionEvent e)
	{
		oneGeneration();
		myView.updateView(grid);
	}

	/** main logic method for updating the state of the grid / simulation */
	private void oneGeneration()
	{
		int count;


		for (int r = 0; r < SIZE; r++)
		{
			for (int c = 0; c < SIZE; c++)
			{
				count = 0;
			
				if (r > 0 && c > 0)
					if (grid[r-1][c-1].isAliveNow())
						count++;
				if (r > 0)
					if (grid[r-1][c].isAliveNow())
						count++;
				if (r > 0 && c < SIZE-1)
					if (grid[r-1][c+1].isAliveNow())
						count++;
				if (c > 0)
					if (grid[r][c-1].isAliveNow())
						count++;
				if (c < SIZE-1)
					if (grid[r][c+1].isAliveNow())
						count++;
				if (c > 0 && r < SIZE-1)
					if (grid[r+1][c-1].isAliveNow())
						count++;
				if (r < SIZE-1)
					if (grid[r+1][c].isAliveNow())
						count++;
				if (r < SIZE-1 && c < SIZE-1)
					if (grid[r+1][c+1].isAliveNow())
						count++;

							
				//alive
				if (grid[r][c].isAliveNow())
				{
					if (count == 2 || count == 3)
					{	
						grid[r][c].setAliveNext(true); //keep alive
						// grid[r][c].setAliveNow(false);
					}
					else	
					{
						grid[r][c].setAliveNext(false); //turn dead
						// grid[r][c].setAliveNow(true);
					}

				}
				//dead
				if (!grid[r][c].isAliveNow())
					if (count == 3)
					{
						grid[r][c].setAliveNext(true);//turns alive
						// grid[r][c].setAliveNow(false);
					}

			}
		}

		for (int r = 0; r < SIZE; r++)
		{
			for (int c = 0; c < SIZE; c++)
			{
				grid[r][c].setAliveNow(grid[r][c].isAliveNext());

			}
		}

		
				
	}
}

