package xdest.game.util;

public class CRun extends Thread {

	private boolean finished;
	
	public CRun()
	{
		finished = false;
	}
	
	@Override
	public void run()
	{
		
	}
	
	public void finish()
	{
		finished = true;
	}
	
}
