import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DrawingClass extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JButton Clicks[] = new JButton[100];
	private JLabel JL = new JLabel();
	private int Checks[] = new int[100],clk=0,add[] = {-11,-10,-9,-1,1,9,10,11},total=0;
	private Boolean Checked[] = new Boolean[100];
	public DrawingClass()
	{
		super("MineR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(11,7));
	    setBackground(Color.WHITE);
		setVisible(true);
		setResizable(false);
	}
	
	private void paint()
	{
		for (int i=0;i<100;i++)
		{
			if (Checks[i]==9 && Checked[i].booleanValue()==true)
			{
				Clicks[i].setText("*");
			}
			else if((Checks[i]>=0 && Checks[i]<=8) && Checked[i].booleanValue()==true)
			{
				Clicks[i].setText(String.valueOf(Checks[i]));
			}
			else
			{
				//Do Nothing
			}
		}
			int Count=0;
			for(int i=0;i<100;i++)
			{
				
				if((Checks[i]>=0 && Checks[i]<=8) && Checked[i].booleanValue()==true)
				{
					Count++;
				}
			}
			
			if(Count==total)
			{
				JOptionPane.showMessageDialog(null,String.format("You Won With %d Clicks",clk));
				Restart();
			}
		
	}
	
	public void setAll()
	{
		Random rand = new Random(System.currentTimeMillis());
		for(int i=0;i<100;i++)
		{
			if (rand.nextInt(10)==7 || rand.nextInt(10)==7 )
			{
				Checks[i]=9;
			}
			else
			{
				Checks[i]=10;
				total++;
			}
			Clicks[i] = new JButton();
			Clicks[i].setSize(new Dimension(10,10));
			Clicks[i].setName(String.format("%d",i));
			Clicks[i].addActionListener(new HandlerClass());
			Checked[i] = false;
			super.add(Clicks[i]);
	    }
		add(JL);
		super.setSize(600,600);
	}
	
	private int Calculate(int Position)
	{
		int count=0;
		for(int i=0;i<8;i++)
		{
			try
			{
			if(Checks[Position+add[i]]==9)
			{
				count++;
			}
			}
			catch(Exception ex)
			{
				//Do Nothing
			}
		}
		return count;
}
	
	private void Restart()
	{
	  clk=0;
	  total=0;
	  JL.setText("");
	  for(int i=0;i<100;i++)
	  {
		  remove(Clicks[i]);
	  }
      setAll();
	  paint();
	}
	
	public class HandlerClass implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0)
		{
			JButton j = (JButton) arg0.getSource();
			Clicks[Integer.parseInt(j.getName())].setEnabled(false);
			Checked[Integer.parseInt(j.getName())] = true;
			clk++;
			JL.setText(String.format("%d",clk));
			if(Checks[Integer.parseInt(j.getName())]==9)
			 {
				 findBomb();
				 paint();
				 JOptionPane.showMessageDialog(null,"You Lost This Game");
				 Restart();
			 }
			else
			{
				 int ans = Calculate(Integer.parseInt(j.getName()));
				 Checks[Integer.parseInt(j.getName())]=ans;
				 if(ans==0)
				 {
					 zeroBonus(Integer.parseInt(j.getName()));
				 }
			     paint();
			}
		} 
		private void zeroBonus(int pos)
		{
			for(int i=0;i<8;i++)
			{
				try{
				Checks[pos+add[i]]=Calculate(pos+add[i]);
				if(Checks[pos+add[i]]==0 && Checked[pos+add[i]]==false)
				{
					Clicks[pos+add[i]].setEnabled(false);
					Checked[pos+add[i]]=true;
					paint();
					zeroBonus(pos+add[i]);
				}
				else
				{
				Clicks[pos+add[i]].setEnabled(false);
				Checked[pos+add[i]]=true;
				}
				}
				catch(Exception ex)
				{
				//Do Nothing	
				}
			}
		}
		private void findBomb()
		{
			for(int i=0;i<100;i++)
			{
				if(Checks[i]==9)
				{
					Clicks[i].setEnabled(false);
					Checked[i]=true;
				}
			}
		}
	}
}
