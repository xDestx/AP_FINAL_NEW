package xdest.game.effect;


import xdest.game.Game;
import xdest.game.entity.player.Player;

public class HealthDrainRate extends Effect {

	private int dmg;
	
	/**
	 * Create a health drain effect
	 * @param p - The affected Player
	 * @param dur - The duration (in ticks)
	 * @param damage - The damage / second
	 */
	public HealthDrainRate(Player p, int dur, int damage) {
		super(p,dur);
		this.dmg = damage;
	}
	
	/*
	 * Damage / TICK means .01 of the damage per second every 1/100 of a second = damage / second
	 */
	
	@Override
	public boolean update()
	{
		p.effectDamage(dmg / (double)Game.TICK);
		//System.out.println(dmg / (double)this.getDuration() + " damage! current = " + current + " duration = " + getDuration());
		current++;
		return expired();
	}

	@Override
	protected String toStr()
	{
		return "Heatlh drain RATE for " + this.dmg + " dur (seconds)" + (this.getDuration() * 100);
	}
}
