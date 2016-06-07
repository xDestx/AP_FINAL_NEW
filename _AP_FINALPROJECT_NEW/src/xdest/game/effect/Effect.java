package xdest.game.effect;

import xdest.game.entity.player.Player;


public abstract class Effect {
	
	//duration in ticks
	final private int duration;
	protected final Player p;
	protected int current;
	
	/**
	 * 
	 * @param p The player affected 
	 * @param duration duration in ticks
	 */
	public Effect(Player p, int duration) {
		this.p = p;
		this.duration = duration;
		this.current = 0;
	}
	
	/**
	 * 
	 * @return the duration in ticks
	 */
	
	public final int getDuration()
	{
		return this.duration;
	}
	
	/*
	 * Ends the effect
	 */
	public final void end()
	{
		this.current = this.duration;
	}

	
	/**
	 * Update the effect
	 * @return
	 */
	public abstract boolean update();

	/**
	 * Check if the effect has expired
	 * @return true if expired
	 */
	public final boolean expired()
	{
		if (current >= getDuration())
		{
			return true;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return toStr();
	}
	
	/*
	 * Doing this requires each effect have its own toString method 
	 */
	protected abstract String toStr();

}
