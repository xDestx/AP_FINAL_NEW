package xdest.game.util.key;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CKListener extends KeyAdapter {

	//Key listener for key select frame
	private KeySelectFrame ksf;
	
	public CKListener(KeySelectFrame ksf) {
		this.ksf = ksf;
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (ksf.next() && e.getKeyCode() != KeyEvent.VK_ESCAPE)
		{
			String x = (String)ksf.getOptions().getSelectedItem();
			String player = x.substring(0,2);
			String action = x.substring(3);
			if (player.equalsIgnoreCase("P1"))
			{
				if(action.equalsIgnoreCase("HIT"))
				{
					ksf.getKeyConfig().setP1Hit(e.getKeyCode());
					ksf.setLabelText(""+e.getKeyChar());
				}
				if(action.equalsIgnoreCase("LEFT"))
				{
					ksf.getKeyConfig().setP1Left(e.getKeyCode());
					ksf.setLabelText(""+e.getKeyChar());
				}
				if(action.equalsIgnoreCase("RIGHT"))
				{
					ksf.getKeyConfig().setP1Right(e.getKeyCode());
					ksf.setLabelText(""+e.getKeyChar());
				}
				if(action.equalsIgnoreCase("UP"))
				{
					ksf.getKeyConfig().setP1Up(e.getKeyCode());
					ksf.setLabelText(""+e.getKeyChar());
				}
				if(action.equalsIgnoreCase("DOWN"))
				{
					ksf.getKeyConfig().setP1Down(e.getKeyCode());
					ksf.setLabelText(""+e.getKeyChar());
				}
			}
			if (player.equalsIgnoreCase("P2"))
			{
				if(action.equalsIgnoreCase("HIT"))
				{
					ksf.getKeyConfig().setP2Hit(e.getKeyCode());
					ksf.setLabelText(""+e.getKeyChar());
				}
				if(action.equalsIgnoreCase("LEFT"))
				{
					ksf.getKeyConfig().setP2Left(e.getKeyCode());
					ksf.setLabelText(""+e.getKeyChar());
				}
				if(action.equalsIgnoreCase("RIGHT"))
				{
					ksf.getKeyConfig().setP2Right(e.getKeyCode());
					ksf.setLabelText(""+e.getKeyChar());
				}
				if(action.equalsIgnoreCase("UP"))
				{
					ksf.getKeyConfig().setP2Up(e.getKeyCode());
					ksf.setLabelText(""+e.getKeyChar());
				}
				if(action.equalsIgnoreCase("DOWN"))
				{
					ksf.getKeyConfig().setP2Down(e.getKeyCode());
					ksf.setLabelText(""+e.getKeyChar());
				}
			}
			
			ksf.setNext(false);
		}
	}

}
