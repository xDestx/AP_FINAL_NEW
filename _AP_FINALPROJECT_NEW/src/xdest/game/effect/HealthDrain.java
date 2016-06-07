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
	
	
	//Damage the player ( damage / duration )
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
		current++;
		return expired();
	}
	
	protected String toStr()
	{
		return "Heatlh drain for " + this.dmg + " int (seconds)" + (this.interval * 100) + " dur(seconds)" + (this.getDuration());
	}


}
