package xdest.game.entity.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;


import xdest.game.Game;
import xdest.game.effect.Effect;
import xdest.game.effect.HealthDrain;
import xdest.game.entity.Entity;
import xdest.game.location.Location;
import xdest.game.location.Velocity;
import xdest.game.sound.SoundMaster;
import xdest.game.ui.DamageNumber;
import xdest.game.ui.Screen;
import xdest.game.util.ImageLoader;
import xdest.game.vis.Animation;
import xdest.game.vis.HitAnimation;
import xdest.game.world.Collidable;
import xdest.game.world.World;
import xdest.game.world.WorldObject;

public class Player extends Entity implements Collidable {

	private double hp;
	private int hurtTick;
	// hurt (for health bar)
	private Stats stats;
	private boolean lH, rH;
	private Velocity v;
	private Collection<Effect> effects;
	private Collection<DamageNumber> dns, toAddDns;
	private Collection<Animation> ans, toAddAns;
	private int xoff, yoff;
	private boolean canJump, isFalling;
	private BufferedImage hb;
	private int facing;
	private boolean visible;
	public static final int FACE_RIGHT = 0, FACE_DOWN = 1, FACE_LEFT = 2, FACE_UP = 3;
	/*
	 * 0 is right 1 is down 2 is left 3 is up
	 */
	public static final int WIDTH = 50, HEIGHT = 50;

	/**
	 * Create a new player with a name
	 * 
	 * @param name
	 *            - the playername
	 */
	public Player(String name) {
		super(name, new Location(), ImageLoader.loadImage("/images/defaultplayer.png"),
				new Rectangle(0, 0, Player.WIDTH, Player.HEIGHT));
		hb = ImageLoader.loadImage("/images/healthbar.png");
		stats = new Stats();
		effects = new Stack<Effect>();
		dns = new Stack<DamageNumber>();
		ans = new Stack<Animation>();
		toAddAns = new Stack<Animation>();
		toAddDns = new Stack<DamageNumber>();
		hp = stats.getMaxHp();
		v = new Velocity();
		xoff = 0;
		yoff = 0;
		canJump = true;
		isFalling = false;
		facing = 0;
		visible = true;
		lH = false;
		rH = false;
		hurtTick = 0;
		v.addY(Game.GRAVITY);
	}

	public Player(String name, String path) {
		super(name, new Location(), ImageLoader.loadImage(path), new Rectangle(0, 0, Player.WIDTH, Player.HEIGHT));
		stats = new Stats();
		hb = ImageLoader.loadImage("/images/healthbar.png");
		effects = new Stack<Effect>();
		dns = new Stack<DamageNumber>();
		ans = new Stack<Animation>();
		toAddAns = new Stack<Animation>();
		toAddDns = new Stack<DamageNumber>();
		hp = stats.getMaxHp();
		v = new Velocity();
		xoff = 0;
		yoff = 0;
		canJump = true;
		facing = 0;
		visible = true;
		lH = false;
		hurtTick = 0;
		rH = false;
		v.addY(Game.GRAVITY);
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean b) {
		this.visible = b;
	}

	public int getFacing() {
		return this.facing;
	}

	public void setFacing(int f) {
		if (f < 0 || f > 3)
			this.facing = 0;
		else
			this.facing = f;
	}

	public void setRh(boolean s) {
		this.rH = s;
	}

	public boolean getRh() {
		return this.rH;
	}

	public void setLh(boolean Lh) {
		this.lH = Lh;
	}

	public boolean getLh() {
		return this.lH;
	}

	public Stats getStats() {
		return this.stats;
	}

	public boolean canJump() {
		return canJump;
	}

	public void setCanJump(boolean c) {
		canJump = c;
	}

	public Velocity getVelocity() {
		return this.v;
	}

	public void setVelocity(Velocity v) {
		this.v = v;
	}

	public void setVelocity(int x, int y) {
		v.setX(x);
		v.setY(y);
	}

	/**
	 * Add a status effect
	 * 
	 * @param e
	 *            - the effect
	 */
	public void addEffect(Effect e) {
		effects.add(e);
		Game.log("Effect added to " + getName() + " - " + e.toString());
	}

	/**
	 * Remove a status effect (by reference)
	 * 
	 * @param e
	 *            - the effect
	 */
	public void removeEffect(Effect e) {
		effects.remove(e);
	}

	/**
	 * Remove a status effect (by index)
	 * 
	 * @param i
	 *            - the index
	 */
	public void removeEffect(int i) {
		effects.remove(i);
	}

	public void clearEffects()
	{
		for (Effect e : effects)
		{
			e.end();
		}
		effects.clear();
		Game.log("Cleared effects for " + getName());
	}
	
	/**
	 * Get current status effects
	 * 
	 * @return Collection of effects
	 */
	public Collection<Effect> getEffects() {
		return effects;
	}

	/**
	 * Used to damage by effect
	 * 
	 * @param d
	 *            - damage value
	 */
	public void effectDamage(double d) {
		this.hp -= d;
		addDn(new DamageNumber(d, (int) getLocation().getX() + 40 + (int) (Math.random() * 100),
				(int) getLocation().getY() - 40 - (int) (Math.random() * 30)));
		Game.log(getName() + " effect damage for " + d);
	}

	/**
	 * Used to get the current health of the player
	 * 
	 * @return hp
	 */
	public double getHealth() {
		return this.hp;
	}

	/**
	 * Used to damage by physical
	 * 
	 * @param d
	 *            - damage value
	 */
	public void damage(double d) {
		double td = d - this.getStats().getPResist();
		if (td <= 0) {
			td = 1;
		}
		d = td;
		this.hp -= d;
		addDn(new DamageNumber(d, (int) getLocation().getX() + 40 + (int) (Math.random() * 100),
				(int) getLocation().getY() - 40 - (int) (Math.random() * 30)));
		addAn(new HitAnimation(getLocation()));
		hurtTick = 75;
		// SOUND
		SoundMaster.playSound("hitsound_real.wav");
		Game.log(getName() + " took " + d + " damage");
		// System.out.println("Ow, physical pain!!! -" + d + "hp! total: " +
		// hp);
	}

	/**
	 * Used to set the health of the player
	 * 
	 * @param h
	 *            - new health value
	 */
	public void setHealth(double h) {
		this.hp = h;
		Game.log(getName() + " had health set to " + h);
		// System.out.println("Health set: " + h +"!");
	}

	/**
	 * Used to heal the player by effect
	 * 
	 * @param h
	 *            - health added
	 */
	public void effectHeal(double h) {
		this.hp += h;
		Game.log(getName() + " healed " + h);
		if(getHealth() > getStats().getMaxHp())
		{
			Game.log(getName() + " overhealed for " + (getHealth() - getStats().getMaxHp() + "!"));
			addEffect(new HealthDrain(this, 10*100, (int)(getHealth() - getStats().getMaxHp()), 50));
		}
	}

	private void addAn(Animation a) {
		toAddAns.add(a);
	}

	private void addDn(DamageNumber d) {
		toAddDns.add(d);
	}

	@Override
	public void update(Game g) {
		moveX((int) v.getX());
		moveY((int) v.getY());
		if (v.getX() > 5) {
			System.out.println(v.getX());
		}
		if (isFalling) {
			v.addY(.25);
		}
		if (getLocation().getX() < -5 || getLocation().getX() >= 600 || getLocation().getY() <= -20
				|| getLocation().getY() >= 600) {
			System.out.println(getLocation().toString());
			setLocation(new Location(100, 100));
			setVelocity(0, 0);
		}

		onSurface(g.getWorld());
		// if (co != null)
		// {

		// }
		/*
		 * if (getLocation().getX() <= 0) { getLocation().setX(0); } if
		 * (getLocation().getX() >= 600 - 50) { getLocation().setX(600 - 50); }
		 * if (getLocation().getY() <= 0) { getLocation().setY(0); } if
		 * (getLocation().getY() >= 600 - 72) { getLocation().setY(600 - 72);
		 * v.setY(9); canJump = true; }
		 */
		LinkedList<Effect> toRemove = new LinkedList<Effect>();
		for (Effect e : effects) {
			if (e.update()) {
				toRemove.add(e);
			}
		}

		for (Effect e : toRemove) {
			effects.remove(e);
		}

		Stack<DamageNumber> x = new Stack<DamageNumber>();
		Stack<Animation> y = new Stack<Animation>();
		ans.addAll(toAddAns);
		dns.addAll(toAddDns);
		toAddAns.clear();
		toAddDns.clear();
		for (DamageNumber dn : dns) {
			dn.update(g);
			if (dn.expired())
				x.add(dn);

		}
		for (Animation a : ans) {
			a.update(g);
			if (a.expired())
				y.add(a);
		}
		for (DamageNumber dn : x) {
			dns.remove(dn);
		}
		for (Animation a : y) {
			ans.remove(a);
		}
	}

	private void onSurface(World w) {
		isFalling = true;
		for (WorldObject co : w.getAll()) {
			if (co instanceof Collidable) {
				Collidable obj = (Collidable) co;

				if (getBoundsTop().intersects(obj.getBounds())) {
					v.setY(0);
					move(new Location(getLocation().getX(), obj.getBounds().getMaxY()));
				}
				if (getBoundsBot().intersects(obj.getBounds())) {
					v.setY(0);
					move(new Location(getLocation().getX(), obj.getBounds().getMinY() - getBounds().getHeight()));
					setCanJump(true);
					isFalling = false;
				}
				if (getBoundsLeft().intersects(obj.getBounds())) {
					move(new Location(obj.getBounds().getMaxX(), getLocation().getY()));
				}
				if (getBoundsRight().intersects(obj.getBounds())) {
					move(new Location(obj.getBounds().getMinX() - getBounds().getWidth(), getLocation().getY()));
				}
			}
		}
	}

	public Rectangle getHitBounds() {
		if (facing == Player.FACE_RIGHT) {
			return new Rectangle((int) getBounds().getMaxX(), (int) getBounds().getMinY() - 4,
					(int) (getBounds().getWidth() + getBounds().getWidth() / 8), (int) getBounds().getHeight() + 4);
		}
		if (facing == Player.FACE_LEFT) {
			return new Rectangle((int) (getBounds().getMinX() - (getBounds().getWidth() + getBounds().getWidth() / 8)),
					(int) getBounds().getMinY() - 4, (int) (getBounds().getWidth() + getBounds().getWidth() / 8),
					(int) getBounds().getHeight() + 4);
		}
		if (facing == Player.FACE_UP) {
			return new Rectangle((int) getBounds().getMinX(),
					(int) (getBounds().getMinY() - getBounds().getHeight() - 4), (int) (getBounds().getWidth()),
					(int) getBounds().getHeight() - 4);
		}
		return new Rectangle((int) getBounds().getMinX(), (int) getBounds().getMaxY(), (int) (getBounds().getWidth()),
				(int) getBounds().getHeight() + 4);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle((int) (getBounds().getX() + ((getBounds().getWidth() / 8))), (int) getBounds().getY(),
				(int) (3 * getBounds().getWidth() / 4), (int) getBounds().getHeight() / 2);
	}

	public Rectangle getBoundsBot() {
		return new Rectangle((int) (getBounds().getX() + ((getBounds().getWidth() / 8))),
				(int) (getBounds().getMaxY() - getBounds().getHeight() / 2), (int) (3 * getBounds().getWidth() / 4),
				(int) getBounds().getHeight() / 2);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle((int) (getBounds().getX()), (int) getBounds().getY() + 2,
				(int) (getBounds().getWidth() / 8), (int) getBounds().getHeight() - 4);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle((int) (getBounds().getMaxX() - getBounds().getWidth() / 8), (int) getBounds().getY() + 2,
				(int) (getBounds().getWidth() / 8), (int) getBounds().getHeight() - 4);
	}

	private void move(Location l) {
		setLocation(l);
		getBounds().setLocation(l.toPoint());
	}

	private void moveY(double y) {
		getLocation().addY(y);
		getBounds().setLocation(getLocation().toPoint());
	}

	private void moveX(double x) {
		getLocation().addX(x);
		getBounds().setLocation(getLocation().toPoint());
	}

	public boolean isDead() {
		if (this.hp <= 0) {
			return true;
		}
		return false;
	}

	@Override
	public void render(Graphics g) {
		if (!visible)
			return;
		drawHealthBar(g);
		drawCharacter(g);
		drawHitBounds(g);
		// drawHitBoxes(g);
		drawDamageNumbers(g);
		drawAnimations(g);
	}

	private void drawHitBounds(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		g2.draw(getHitBounds());
	}

	private void drawHitBoxes(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.draw(getBoundsTop());
		g2.setColor(Color.red);
		g2.draw(getBoundsBot());
		g2.setColor(Color.BLUE);
		g2.draw(getBoundsRight());
		g2.setColor(Color.green);
		g2.draw(getBoundsLeft());
	}

	private void drawAnimations(Graphics g) {
		for (Animation a : ans) {
			a.render(g);
		}
	}

	private void drawDamageNumbers(Graphics g) {
		for (DamageNumber dn : dns) {
			dn.render(g);
		}
	}

	private void drawCharacter(Graphics g) {
		g.drawImage(getImage(), (int) this.getLocation().getX() + xoff, (int) this.getLocation().getY() + yoff, null);
	}
	

	private void drawHealthBar(Graphics g) {
		g.setColor(Color.RED);
		// g.drawRect((int)getLocation().getX(), (int)getLocation().getY() - 14,
		// (int) (100 + 2), 12);
		if (hurtTick <= 0)
			g.setColor(Color.GREEN);
		else {
			g.setColor(Color.RED);
			hurtTick--;
		}
		boolean overHealed = (getHealth() - getStats().getMaxHp() > 1);
		if(overHealed)
		{
			//Overhealed
			g.setColor(Color.yellow);
		}
		/*
		 * Static
		 */

		//Green bar
		if(!overHealed)
		{
			g.fillRect(Game.getHealthPosition(this), 10,
				(int) (((int) hp / stats.getMaxHp()) * 280), 20);
		} else
		{
			g.fillRect(Game.getHealthPosition(this), 10,
					(int) (((int) stats.getMaxHp() / stats.getMaxHp()) * 280), 20);
		}
		if (hurtTick <= 0) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.white);
		}
		g.setFont(new Font("Arial", Font.BOLD, 14));

		g.drawImage(hb, Game.getHealthPosition(this), 10, (int) (280 + 2), 22, null);
		//Health in string form
		g.drawString((int) hp + " / " + (int) stats.getMaxHp(),Game.getHealthPosition(this) + 10, 26);
		//Health overlay
		
		//Name
		g.setFont(new Font("Impact", 0, 20));
		String x = getName();
		int l = x.length();
		if (hurtTick <= 0)
		{
			g.setColor(Color.white);
		} else
		{
			g.setColor(Color.black);
		}
		g.fillRect(Game.getHealthPosition(this), 30, 280, 22);
		g.setColor(Color.cyan);
		g.drawRect(Game.getHealthPosition(this), 30, 280, 22);
		if (hurtTick <= 0)
		{
			g.setColor(Color.black);
		} else
		{
			g.setColor(Color.white);
		}
		for (int i = 0; i < l; i++)
		{
			g.drawString(""+x.charAt(i), Game.getHealthPosition(this) + ((280 / (l + 1)) * (i + 1)), 48);
		}
		//g.drawString(getName(), Game.getHealthPosition(this) + 100, 44);
		
		////////////////////////////
		
		/* Dynamic
		g.fillRect((int) getLocation().getX() + 1, (int) getLocation().getY() - 12,
				(int) (((int) hp / stats.getMaxHp()) * 100), 10);
		if (hurtTick <= 0) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.white);
		}
		g.setFont(new Font("Arial", 0, 10));
		g.drawString((int) hp + " / " + (int) stats.getMaxHp(), (int) getLocation().getX() + 3,
				(int) getLocation().getY() - 4);
		g.drawImage(hb, (int) getLocation().getX(), (int) getLocation().getY() - 14, (int) (100 + 2), 12, null);
		*/
		g.setColor(Color.black);
	}

	@Override
	public boolean colliding(Rectangle r) {
		if (r.intersects(this.getBounds()))
			return true;
		return false;
	}

	@Override
	public boolean expired() {
		return false;
	}

}
