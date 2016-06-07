package xdest.game.effect;

import xdest.game.Game;
import xdest.game.entity.player.Player;

public class OverhealDrain extends Effect {

	public OverhealDrain(Player p, int duration) {
		super(p, duration);
	}

	//Default effect wich drains 2hp / s if over 100hp
	@Override
	public boolean update() {
		if(p.getHealth() > p.getStats().getMaxHp())
		{
			p.setHealth(p.getHealth()-(2 / Game.TICK));
			if(p.getHealth() < p.getStats().getMaxHp())
				p.setHealth(p.getStats().getMaxHp());
		}
		return false;
	}

	@Override
	protected String toStr() {

		return "Overheal drain for " + p.getName();
	}

}
