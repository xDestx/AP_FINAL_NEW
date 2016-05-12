package xdest.game.effect;


import xdest.game.entity.player.Player;

public class HealthDrain extends Effect {

	private int dmg, interval,c;
	
	/**
	 * Create a health drain effect
	 * @param p - The affected Player
	 * @param dur - The duration (in ticks)
	 * @param damage - The TOTAL damage
	 * @param interval - interval (in ticks)
	 */
	public HealthDrain(Player p, int dur, int damage, int interval) {
		super(p,dur);
		this.dmg = damage;
		this.interval = interval;
		this.c = 0;
	}
	
	@Override
	public boolean update()
	{
		c++;
		if (c >= interval)
		{
			double d = dmg / (double)this.getDuration();
			d = d * c;
			p.effectDamage(d);
			c=0;
		}
	//	p.effectDamage(dmg / (double)this.getDuration());
		//System.out.println(dmg / (double)this.getDuration() + " damage! current = " + current + " duration = " + getDuration());
		current++;
		return expired();
	}


}
