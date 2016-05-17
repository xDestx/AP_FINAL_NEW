package xdest.game.util.key;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class KeySelectFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3230676719835545924L;
	private boolean nextKey;
	private JComboBox<String> options;
	private JLabel current;
	private final KeyConfig cfg;
	
	
	public KeySelectFrame(KeyConfig c) {
		nextKey = false;
		this.cfg = c;
		JPanel p = new JPanel();
		String[] ops = new String[10];
		String[] ps = {"P1 ", "P2 "};
		String[] os = {"LEFT","RIGHT","UP","DOWN","HIT"};
		int a = 0;
		for (String str : ps)
		{
			for (String str1 : os)
			{
				ops[a] = str + str1;
				a++;
			}
		}
		options = new JComboBox<String>(ops);
		JButton next = new JButton("Select Key");
		this.setAlwaysOnTop(true);
		next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				nextKey = true;
			}
		});
		current = new JLabel(" ");
		options.addActionListener(new ActionListener()
				{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String x = (String)options.getSelectedItem();
				String player = x.substring(0,2);
				String action = x.substring(3);
				if (player.equalsIgnoreCase("P1"))
				{
					if(action.equalsIgnoreCase("HIT"))
					{
						String c = KeyEvent.getKeyText(getKeyConfig().getP1Hit());
						setLabelText(c);
					}
					if(action.equalsIgnoreCase("LEFT"))
					{
						String c = KeyEvent.getKeyText(getKeyConfig().getP1Left());
						setLabelText(c);
					}
					if(action.equalsIgnoreCase("RIGHT"))
					{
						String c = KeyEvent.getKeyText(getKeyConfig().getP1Right());
						setLabelText(c);
					}
					if(action.equalsIgnoreCase("UP"))
					{
						String c = KeyEvent.getKeyText(getKeyConfig().getP1Up());
						setLabelText(c);
					}
					if(action.equalsIgnoreCase("DOWN"))
					{
						String c = KeyEvent.getKeyText(getKeyConfig().getP1Down());
						setLabelText(c);
					}
				}
				if (player.equalsIgnoreCase("P2"))
				{
					if(action.equalsIgnoreCase("HIT"))
					{
						String c = KeyEvent.getKeyText(getKeyConfig().getP2Hit());
						setLabelText(c);
					}
					if(action.equalsIgnoreCase("LEFT"))
					{
						String c = KeyEvent.getKeyText(getKeyConfig().getP2Left());
						setLabelText(c);
					}
					if(action.equalsIgnoreCase("RIGHT"))
					{
						String c = KeyEvent.getKeyText(getKeyConfig().getP2Right());
						setLabelText(c);
					}
					if(action.equalsIgnoreCase("UP"))
					{
						String c = KeyEvent.getKeyText(getKeyConfig().getP2Up());
						setLabelText(c);
					}
					if(action.equalsIgnoreCase("DOWN"))
					{
						String c = KeyEvent.getKeyText(getKeyConfig().getP2Down());
						setLabelText(c);
					}
				}
			}
				});
		p.add(current);
		p.add(options);
		p.add(next);
		this.add(p);
		next.addKeyListener(new CKListener(this));
		this.addWindowListener(new CWAdapter(this));
		this.setSize(100, 100);
	}
	
	public void setLabelText(String text)
	{
		this.current.setText(text);
	}
	
	public boolean next()
	{
		return nextKey;
	}
	
	public KeyConfig getKeyConfig()
	{
		return this.cfg;
	}
	
	public JComboBox<String> getOptions()
	{
		return this.options;
	}
	
	public void setNext(boolean n)
	{
		this.nextKey = n;
	}

}
