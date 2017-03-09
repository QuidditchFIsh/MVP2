import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/*
 * Author: Nye Baker
 * Object class to deal with graphics and GUI interface for the simulation
 */


public class grpahics
{
	static JPanel p;
	static JFrame f;
	static JSlider slider;
	static int sliderValue;
	static JLabel l;
	
	public grpahics(int[][] grid,BufferedImage bi,boolean graphics)
	{
		//COnstructor too add the graphics to a frame and have sliders to controll the speed of the simulation.
		f = new JFrame();
		f.setIgnoreRepaint(true);
		f.setVisible(graphics);
		f.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent we) {System.exit(0);}});
		f.setSize(800,800);
		f.setLayout(new GridLayout(1,2,1,1));
		
		p = new JPanel();
		l = new JLabel();
		
		l.setText("Speed");
		
		slider = new JSlider(JSlider.HORIZONTAL,1,grid.length*grid.length,1);
		slider.setValue(50);
		sliderValue = slider.getValue();
		
		event e = new event();
		slider.addChangeListener(e);
		p.add(l);
		p.add(slider);
		f.add(p,BorderLayout.SOUTH);
		
		initaslise(grid,bi);
	}
	
	public class event implements ChangeListener
	{
		public void stateChanged(ChangeEvent e)
		{
			//Class which is always running to tell the slider what to do when it is moved.
			sliderValue = slider.getValue();
			
		}

	}
	public int getSliderValue()
	{
		return sliderValue;
		//Get method to return the value of the slider when the program wants to update itself. 
	}
	
	public static void update(int[][] grid,BufferedImage bi)
	{
		//method to set the color of each cell in the grid when the program updates.
		for (int x = 0; x < grid[0].length; x++) 
			for (int y = 0; y < grid[0].length; y++)
			{
				if(grid[x][y]==0)
					bi.setRGB(x, y,Color.YELLOW.getRGB());
				else if(grid[x][y]==1)
					bi.setRGB(x, y, Color.RED.getRGB());
				else if(grid[x][y]==2)
					bi.setRGB(x, y, Color.BLUE.getRGB());
				else if(grid[x][y]==4)
					bi.setRGB(x, y, Color.GREEN.getRGB());
			}
	}

	public static BufferedImage initaslise(int [][] grid,final BufferedImage bi)
	{
		final Object lock = new Object();
		
		for (int x = 0; x < bi.getWidth(); x++) 
			for (int y = 0; y < bi.getHeight(); y++)
			{
				if(grid[x][y]==0)
					bi.setRGB(x, y,Color.BLACK.getRGB());
				else if(grid[x][y]==1)
					bi.setRGB(x, y, Color.RED.getRGB());
				else if(grid[x][y]==2)
					bi.setRGB(x, y, Color.GREEN.getRGB());
			}
		//A loop is needed to refersh the background everytime
		//The loop is in a new thread so as to run it along side the simulation.
		new Timer().scheduleAtFixedRate(new TimerTask() 
		{
			public void run() 
			{
				synchronized(lock)
				{
					p.getGraphics().drawImage(bi, 0, f.getInsets().top, f.getWidth(), f.getHeight() - f.getInsets().top, null);
				}
			}
		}, 0, 33);
		
		return bi;
		
	}
	


		
}

