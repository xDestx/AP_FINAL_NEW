package xdest.game.effect;


import xdest.game.entity.player.Player;

public class Regeneration extends Effect {

	private int heal, interval,c;
	
	/**
	 * Create a health drain effect
	 * @param p - The affected Player
	 * @param dur - The duration (in ticks)
	 * @param damage - The TOTAL heal
	 * @param interval - interval (in ticks)
	 */
	public Regeneration(Player p, int dur, int heal, int interval) {
		super(p,dur);
		this.heal = heal;
		this.interval = interval;
		this.c = 0;
	}
	
	//Regenerates depending on heal vs duration
	@Override
	public boolean update()
	{
		c++;
		if (c >= interval)
		{
			double d = heal / (double)this.getDuration();
			d = d * c;
			p.effectHeal(d);
			
			c=0;
		}
	//	p.effectDamage(dmg / (double)this.getDuration());
		//System.out.println(dmg / (double)this.getDuration() + " damage! current = " + current + " duration = " + getDuration());
		current++;
		return expired();
	}
	
	
	protected String toStr()
	{
		return "Regeneration for " + this.heal + " int (seconds)" + (this.interval * 100) + " dur(seconds)" + (this.getDuration());
	}


}
