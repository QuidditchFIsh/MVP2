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



public class grpahics extends event
{
	static JPanel p;
	static JFrame f;
	static JSlider slider;
	static int m =0;
	static int sliderValue;
	static JLabel l;
	
	public grpahics(int[][] grid,BufferedImage bi)
	{
		f = new JFrame();
		f.setIgnoreRepaint(true);
		f.setVisible(true);
		//f.setExtendedState(Frame.MAXIMIZED_BOTH);
		f.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent we) {System.exit(0);}});
		f.setSize(800,800);
		f.setLayout(new GridLayout(1,2,1,1));
		
		p = new JPanel();
		l = new JLabel();
		
		l.setText("Speed");
		
		slider = new JSlider(JSlider.HORIZONTAL,1,grid.length*grid.length,1);
		slider.setValue(grid.length*grid.length);
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
			sliderValue = slider.getValue();
			
		}

	}
	public int getSliderValue()
	{
		//System.out.println(sliderValue);
		return sliderValue;

	}
	
	public static void update(int[][] grid,BufferedImage bi)
	{
		for (int x = 0; x < m; x++) 
			for (int y = 0; y < m; y++)
			{
				if(grid[x][y]==0)
					bi.setRGB(x, y,Color.YELLOW.getRGB());
				else if(grid[x][y]==1)
					bi.setRGB(x, y, Color.RED.getRGB());
				else if(grid[x][y]==2)
					bi.setRGB(x, y, Color.BLUE.getRGB());
			}
	}

	public static BufferedImage initaslise(int [][] grid,final BufferedImage bi)
	{
		int n = grid[0].length;
		m=n;
		//final BufferedImage bi = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
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
		
		//another loop or thread will be needed to update the image so that the new grid is shown on the picture
		return bi;
		
	}
	


		
}

