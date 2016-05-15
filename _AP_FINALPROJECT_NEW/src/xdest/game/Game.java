package xdest.game;

import java.awt.Color;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import xdest.game.entity.Entity;
import xdest.game.entity.item.ItemMaster;
import xdest.game.entity.player.Player;
import xdest.game.location.Location;
import xdest.game.location.Velocity;
import xdest.game.sound.SoundMaster;
import xdest.game.ui.GameAction;
import xdest.game.ui.Menu;
import xdest.game.ui.Screen;
import xdest.game.ui.Text;
import xdest.game.ui.UIButton;
import xdest.game.util.ImageLoader;
import xdest.game.util.KeyController;
import xdest.game.util.Logger;
import xdest.game.util.MouseWatcher;
import xdest.game.vis.Animation;
import xdest.game.vis.BlackToWhite;
import xdest.game.vis.EndAnimation;
import xdest.game.world.World;
import xdest.game.world.WorldHandler;

public class Game {
	private static Game g;
	private boolean playing;
	public static final double TICK = 100;
	private World w;
	private Ticker t;
	private Menu m;
	private Screen screen;
	private int state;
	private JFrame f;
	private Player p1, p2;
	private String p1n, p2n;
	private static Logger l;
	public static int GRAVITY = 9;
	public static int MENU = 1, FINISHED = 3, FIGHT = 0;
	// 0 = Menu, 1 = Game

	public static void main(String[] args) {
		l = new Logger("AP FINAL PROJECT GAME", 5);
		Game.log("Starting...");
		SoundMaster.init();
		Game.log("Game fix branch test! :)");
		g = new Game();
		g.play();

	}

	public static void log(String message) {
		l.log(message);
	}

	public static Game getCurrentGame() {
		return g;
	}

	public void setState(int state) {
		if (state == 0) {
			init();
		}
		if (state == 1) {
			SoundMaster.playSound("main_screen.wav");
		}
		this.state = state;
	}

	public Game() {
		w = WorldHandler.getWorld("Level1");
		UIButton[] buttons = new UIButton[3];
		buttons[0] = new UIButton("PLAY",
				new Rectangle(Screen.WIDTH / 4, Screen.HEIGHT / 8, Screen.WIDTH / 2, Screen.HEIGHT / 8), Color.RED,
				ImageLoader.loadImage("/images/dbutton.png"), GameAction.START);
		buttons[1] = new UIButton("HELP",
				new Rectangle(Screen.WIDTH / 4, 3 * Screen.HEIGHT / 8, Screen.WIDTH / 2, Screen.HEIGHT / 8), Color.BLUE,
				ImageLoader.loadImage("/images/dbutton.png"), GameAction.HELP);
		buttons[2] = new UIButton("QUIT",
				new Rectangle(Screen.WIDTH / 4, 5 * Screen.HEIGHT / 8, Screen.WIDTH / 2, Screen.HEIGHT / 8),
				Color.DARK_GRAY, ImageLoader.loadImage("/images/dbutton.png"), GameAction.QUIT);
		Game.log("UI Buttons built!");
		m = new Menu("Super Japan Fighting Adventure XVII", buttons, ImageLoader.loadImage("/images/menubg.png"));
		m.addFloatingButton(new UIButton(" ", new Rectangle((Screen.WIDTH / 2) - 25, Screen.HEIGHT - 125, 50, 50),
				Color.cyan, ImageLoader.loadImage("/images/muteButton.png"), GameAction.MUTE));
		screen = new Screen(this);
		screen.addKeyListener(new KeyController(this));
		t = new Ticker();
		f = new JFrame();
		MouseWatcher mw = new MouseWatcher(screen);
		screen.addMouseListener(mw);
		screen.addMouseMotionListener(mw);
		f.add(screen);
		f.setSize(screen.getSize());
		f.setMinimumSize(f.getSize());
		f.setMaximumSize(f.getSize());
		f.setResizable(false);
		f.setVisible(true);
		SoundMaster.playSound("main_screen.wav");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p1n = "P1";
		p2n = "P2";
		state = 1;
		Game.log("Menu and other utilities built!");
	}

	private void init() {
		Game.log("Initializing!");
		screen.requestFocus();
		t = new Ticker();
		p1 = new Player(p1n);
		p1.setLocation(10, 60);
		p1.getStats().addBonusDamage(5);
		p2 = new Player(p2n, "/images/defaultplayer2.png");
		p2.setLocation(400, 60);
		p2.getStats().addBonusDamage(2);
		p2.getStats().addPResist(4);
		// p1.addEffect(new HealthDrain(p1, 10000, 100,100));
		// p2.addEffect(new HealthDrain(p2, 10000, 100,100));
		this.createObject(w);
		this.createObject(p1);
		this.createObject(p2);
		this.createObject(new ItemMaster());
		Game.log("Init complete");
	}

	public void silentSetState(int s) {
		this.state = s;
	}

	public void play() {
		init();
		this.createAnimation(new BlackToWhite(4));
		this.setState(2);
		Thread tr = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(4100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Game.getCurrentGame().silentSetState(1);
			}
		};
		tr.start();
		playing = true;
		long last = System.nanoTime();
		double ns = 1000000000 / TICK;
		double d = 0;
		int k = 0;
		int f = 0;
		long s = 0;
		while (playing) {
			long n = System.nanoTime();
			d += (n - last) / ns;
			s += (n - last);
			last = n;
			if (d >= 1) {
				gameTick();
				k++;
				d--;
			}

			renderScreen();

			f++;
			if (s >= 1000000000) {
				if ((k != 100 && k != 101) || (f < 150)) {
					Game.log("Ticks: " + k + " | Fps: " + f);
					t.log();
				}
				f = 0;
				s = 0;
				k = 0;
			}

		}
		Game.log("Quitting...");
		Game.getLogger().save();
		System.exit(0);
	}

	public static Logger getLogger() {
		return Game.l;
	}

	public int getState() {
		return state;
	}

	public Menu getMenu() {
		return this.m;
	}

	public void removeObject(GameObject o) {
		t.removeObject(o);
	}

	public void createAnimation(Animation a) {
		t.addOverAnimation(a);
	}

	private void gameTick() {
		if (state == 0) {
			t.tick(this);
			if (!SoundMaster.isPlaying("fight_music.wav"))
				SoundMaster.playSound("fight_music.wav");
			if (p1.isDead()) {
				Game.log("Player 2 (" + p2.getName() + ") won. p2 health: " + p2.getHealth() + " p1  health: "
						+ p1.getHealth());
				this.createObject(new Text(p2.getName() + " has beaten " + p1.getName() + "!", new Location(0, Screen.HEIGHT / 2),
						new Color(255, 0, 0)));
				p1.setVisible(false);
				p1.setVelocity(new Velocity(0, 0));
				p1.clearEffects();
				p2.clearEffects();
				p2.setVelocity(new Velocity(0, 0));
				p2.setLocation(
						new Location(((Screen.WIDTH - Player.WIDTH) / 2), ((Screen.HEIGHT - Player.HEIGHT) / 2)));
				state = 3;
				SoundMaster.stopSound("fight_music.wav");
				SoundMaster.playSound("click_sound.wav");
				Game.log("Switching state to 3");
				this.createAnimation(new EndAnimation());
				Thread t = new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(5000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						SoundMaster.playSound("click_sound.wav");
						Game.log("Switching state to 1");
						Game.getCurrentGame().setState(1);
					}
				};
				t.start();
			} else if (p2.isDead()) {
				Game.log("Player 1 (" + p1.getName() + ") won. p2 health: " + p2.getHealth() + " p1  health: "
						+ p1.getHealth());
				p2.setVisible(false);
				this.createObject(new Text(p1.getName() + " has beaten " + p2.getName() + "!", new Location(0, Screen.HEIGHT / 2),
						new Color(255, 0, 0)));
				p1.setVelocity(new Velocity(0, 0));
				p1.clearEffects();
				p2.clearEffects();
				p2.setVelocity(new Velocity(0, 0));
				p1.setLocation(
						new Location(((Screen.WIDTH - Player.WIDTH) / 2), ((Screen.HEIGHT - Player.HEIGHT) / 2)));
				state = 3;
				SoundMaster.stopSound("fight_music.wav");
				Game.log("Switching state to 3");
				SoundMaster.playSound("click_sound.wav");
				this.createAnimation(new EndAnimation());
				Thread t = new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(5000);
						} catch (Exception e) {
							e.printStackTrace();
						}

						SoundMaster.playSound("click_sound.wav");
						Game.log("Switching state to 1");
						Game.getCurrentGame().setState(1);
					}
				};
				t.start();
			}
		} else if (state == 1) {
			if (!SoundMaster.isPlaying("main_screen.wav"))
				SoundMaster.playSound("main_screen.wav");
			m.tick();
		} else if (state == 3) {
			t.tick(this);
			if (p1.isDead()) {
				Location x = new Location(((Screen.WIDTH - Player.WIDTH) / 2), ((Screen.HEIGHT - Player.HEIGHT) / 2));
				p2.setLocation(x);
				p2.setVelocity(0, 0);
				p1.setVelocity(0, 0);
				p1.setCanJump(false);
				p2.setCanJump(false);
				p1.setLocation(x);
			} else if (p2.isDead()) {
				Location x = new Location(((Screen.WIDTH - Player.WIDTH) / 2), ((Screen.HEIGHT - Player.HEIGHT) / 2));
				p1.setLocation(x);
				p2.setVelocity(0, 0);
				p1.setVelocity(0, 0);
				p1.setCanJump(false);
				p2.setCanJump(false);
				p2.setLocation(x);
			}
		} else if (state == 2) {
			t.tickAnimations(this);
		}
	}

	/**
	 * Create a game object
	 * 
	 * @param g
	 *            - the game object
	 */
	public void createObject(GameObject g) {
		t.addObject(g);
	}

	private void renderScreen() {
		if (state == 0) {
			screen.render(t.getRenderables());
		} else if (state == 1) {
			screen.render(m);
		} else if (state == 2) {
			screen.render(t.getAnimationsAsRenderable());
		} else if (state == 3) {
			screen.render(t.getRenderables());
		}
	}

	/**
	 * Quit the game
	 */
	public void quit() {
		playing = false;
	}

	public void keyPressed(KeyEvent e) {
		if (state == 0) {
			if (e.getKeyCode() == KeyEvent.VK_D) {

				// if((Math.abs(p1.getVelocity().getX()) < 5))
				if (!p1.getRh()) {
					p1.setRh(true);
					p1.getVelocity().addX(5);
					p1.setFacing(Player.FACE_RIGHT);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_A) {

				if (!p1.getLh()) {
					p1.getVelocity().addX(-5);
					p1.setFacing(Player.FACE_LEFT);
					p1.setLh(true);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_S) {

				// if((Math.abs(p1.getVelocity().getX()) < 5))
				p1.getVelocity().addY(5);
				p1.setFacing(Player.FACE_DOWN);
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				p1.setFacing(Player.FACE_UP);
				if (p1.canJump()) {
					p1.getVelocity().addY(-12);
					SoundMaster.playSound("jump_sound.wav");
					p1.setCanJump(false);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_L) {
				if (!p2.getRh()) {
					p2.getVelocity().addX(5);
					p2.setRh(true);
					p2.setFacing(Player.FACE_RIGHT);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_J) {
				if (!p2.getLh()) {
					p2.getVelocity().addX(-5);
					p2.setLh(true);
					p2.setFacing(Player.FACE_LEFT);
				}
				// System.out.println(p2.getVelocity().toString());
			} else if (e.getKeyCode() == KeyEvent.VK_K) {
				if ((Math.abs(p2.getVelocity().getX()) < 5))
					p2.getVelocity().addY(5);
				p2.setFacing(Player.FACE_DOWN);
			} else if (e.getKeyCode() == KeyEvent.VK_I) {
				p2.setFacing(Player.FACE_UP);
				if (p2.canJump()) {
					p2.getVelocity().addY(-12);
					SoundMaster.playSound("jump_sound.wav");
					p2.setCanJump(false);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_C) {
				hit(p1);
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				hit(p2);
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				Game.log(p1.getVelocity().toString() + " p1");
				Game.log(p2.getVelocity().toString() + " p2");
				Game.log(p1.getRh() + "RH p1");
				Game.log(p1.getLh() + "LH p1");
				Game.log(p2.getRh() + "RH p2");
				Game.log(p2.getLh() + "LH p2");
				Game.getLogger().save();
				// SoundMaster.close();
				Game.getCurrentGame().quit();
				System.exit(0);
			}
		} else if (state == 1) {
		}
	}

	private void hit(Player exempt) {
		for (Entity e : t.getEntities()) {
			if (e instanceof Player) {
				Player x = (Player) e;
				if (x.colliding(exempt.getHitBounds())) {
					x.damage(exempt.getStats().getDamage());
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			if ((Math.abs(p1.getVelocity().getX()) <= 5) && p1.getRh())
				if (p1.getLh())
					p1.getVelocity().setX(-5);
				else
					p1.getVelocity().setX(0);
			p1.setRh(false);
		} else if (e.getKeyCode() == KeyEvent.VK_A && p1.getLh()) {
			if ((Math.abs(p1.getVelocity().getX()) <= 5))
				if (p1.getRh())
					p1.getVelocity().setX(5);
				else
					p1.getVelocity().setX(0);
			p1.setLh(false);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			p1.getVelocity().addY(-5);
		} else if (e.getKeyCode() == KeyEvent.VK_W) {

		}
		if (e.getKeyCode() == KeyEvent.VK_L) {
			if ((Math.abs(p2.getVelocity().getX()) <= 5) && p2.getRh())
				if (p2.getLh())
					p2.getVelocity().setX(-5);
				else
					p2.getVelocity().setX(0);
			p2.setRh(false);
		} else if (e.getKeyCode() == KeyEvent.VK_J && p2.getLh()) {
			if ((Math.abs(p2.getVelocity().getX()) <= 5))
				if (p2.getRh())
					p2.getVelocity().setX(5);
				else
					p2.getVelocity().setX(0);
			p2.setLh(false);
		} else if (e.getKeyCode() == KeyEvent.VK_K) {
			p2.getVelocity().addY(-5);
		} else if (e.getKeyCode() == KeyEvent.VK_I) {

		}

	}

	public void setName(int p, String name) {
		if (p == 1) {
			if (p1 == null) {
				p1n = name;
			} else {
				p1.setName(name);
				p1n = name;
			}
		} else if (p == 2) {
			if (p2 == null) {
				p2n = name;
			} else {
				p2.setName(name);
				p2n = name;
			}
		}

	}

	public Player getPlayer1() {
		return p1;
	}

	public Player getPlayer2() {
		return p2;
	}

	public static int getHealthPosition(Player p) {
		if (p.equals(Game.getCurrentGame().getPlayer1())) {
			return 10;
		} else {
			return 310;
		}
	}

	public World getWorld() {
		return this.w;
	}

	public Rectangle getBounds() {
		return screen.getBounds();
	}

}
