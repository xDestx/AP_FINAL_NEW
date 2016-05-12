package xdest.game;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import xdest.game.effect.HealthDrain;
import xdest.game.entity.Entity;
import xdest.game.entity.player.Player;
import xdest.game.location.Location;
import xdest.game.location.Velocity;
import xdest.game.ui.GameAction;
import xdest.game.ui.Menu;
import xdest.game.ui.Screen;
import xdest.game.ui.Text;
import xdest.game.ui.UIButton;
import xdest.game.util.ImageLoader;
import xdest.game.util.KeyController;
import xdest.game.util.MouseWatcher;
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
	private Player p1,p2;
	private String p1n, p2n;
	public static int GRAVITY = 9;
	//0 = Menu, 1 = Game
	
	public static void main(String[] args)
	{
		System.out.println("Starting...");
		g = new Game();
		g.play();
		
	}
	
	public static Game getCurrentGame()
	{
		return g;
	}
	
	public void setState(int state)
	{
		if (state == 0)
		{
			init();
		}
		this.state = state;
	}
	
	public Game()
	{
		w = WorldHandler.getWorld("Level1");
		UIButton[] buttons = new UIButton[3];
		buttons[0] = new UIButton("PLAY",
				new Rectangle(
						Screen.WIDTH / 4,
						Screen.HEIGHT / 8,
						Screen.WIDTH / 2,
						Screen.HEIGHT / 8),
				Color.RED, ImageLoader.loadImage("/images/dbutton.png"),
				GameAction.START);
		buttons[1] = new UIButton("HELP",
				new Rectangle(
						Screen.WIDTH / 4,
						3 * Screen.HEIGHT / 8,
						Screen.WIDTH / 2,
						Screen.HEIGHT / 8),
				Color.BLUE, ImageLoader.loadImage("/images/dbutton.png"),
				GameAction.HELP);
		buttons[2] = new UIButton("QUIT",
				new Rectangle(
						Screen.WIDTH / 4,
						5 * Screen.HEIGHT / 8,
						Screen.WIDTH / 2,
						Screen.HEIGHT / 8),
				Color.DARK_GRAY, ImageLoader.loadImage("/images/dbutton.png"),
				GameAction.QUIT);
		
		m = new Menu("Super Japan Fighting Adventure XVII", buttons, ImageLoader.loadImage("/images/menubg.png"));
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
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p1n = "P1";
		p2n = "P2";
		state = 1;
	}
	
	private void init()
	{
		screen.requestFocus();
		t = new Ticker();
		p1 = new Player(p1n);
		p1.setLocation(10, 60);
		p1.getStats().addBonusDamage(5);
		p2 = new Player(p2n, "/images/defaultplayer2.png");
		p2.setLocation(400,60);
		p2.getStats().addBonusDamage(2);
		p2.getStats().addPResist(4);
		//p1.addEffect(new HealthDrain(p1, 10000, 100,100));
		//p2.addEffect(new HealthDrain(p2, 10000, 100,100));
		this.createObject(w);
		this.createObject(p1);
		this.createObject(p2);
	}
	
	public void play()
	{
		init();
		playing = true;
		long last = System.nanoTime();
		double ns = 1000000000 / TICK;
		double d = 0;
		int k = 0;
		int f = 0;
		long s = 0;
		while(playing)
		{
			long n = System.nanoTime();
			d += (n - last) / ns;
			s+= (n - last);
			last = n;
			if (d >= 1)
			{
				gameTick();
				k++;
				d--;
			}
			
			renderScreen();
			
			f++;
			if (s >= 1000000000)
			{
				System.out.println("Ticks: " + k + " | Fps: " + f);
				f = 0;
				s = 0;
				k = 0;
			}
			
		}
		System.exit(0);
	}
	
	public Menu getMenu()
	{
		return this.m;
	}
	
	private void gameTick()
	{
		if (state == 0)
		{
			t.tick(this);
			if (p1.isDead())
			{
				this.createObject(new Text("The winner is " + p2.getName() + "!", new Location(0, Screen.HEIGHT / 2), new Color(255,0,0)));
				p1.setVisible(false);
				p1.setVelocity(new Velocity(0,0));
				p1.clearEffects();
				p2.clearEffects();
				p2.setVelocity(new Velocity(0,0));
				p2.setLocation(new Location(((Screen.WIDTH - Player.WIDTH) / 2), ((Screen.HEIGHT - Player.HEIGHT) / 2)));
				state = 3;
				Thread t = new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							Thread.sleep(5000);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
						Game.getCurrentGame().setState(1);
					}
				};
				t.start();
			} else if (p2.isDead())
			{
				p2.setVisible(false);
				this.createObject(new Text("The winner is " + p1.getName() + "!", new Location(0, Screen.HEIGHT / 2), new Color(255,0,0)));
				p1.setVelocity(new Velocity(0,0));
				p1.clearEffects();
				p2.clearEffects();
				p2.setVelocity(new Velocity(0,0));
				p1.setLocation(new Location(((Screen.WIDTH - Player.WIDTH) / 2), ((Screen.HEIGHT - Player.HEIGHT) / 2)));
				state = 3;
				Thread t = new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							Thread.sleep(5000);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
						Game.getCurrentGame().setState(1);
					}
				};
				t.start();
			}
		} else if (state == 1)
		{
			
		} else if (state == 3)
		{
			t.tick(this);
			if (p1.isDead())
			{
				Location x = new Location(((Screen.WIDTH - Player.WIDTH) / 2), ((Screen.HEIGHT - Player.HEIGHT) / 2));
				p2.setLocation(x);
				p2.setVelocity(0, 0);
				p1.setVelocity(0,0);
				p1.setCanJump(false);
				p2.setCanJump(false);
				p1.setLocation(x);
			} else if (p2.isDead())
			{
				Location x = new Location(((Screen.WIDTH - Player.WIDTH) / 2), ((Screen.HEIGHT - Player.HEIGHT) / 2));
				p1.setLocation(x);
				p2.setVelocity(0, 0);
				p1.setVelocity(0,0);
				p1.setCanJump(false);
				p2.setCanJump(false);
				p2.setLocation(x);
			}
		} 
	}
	
	/**
	 * Create a game object
	 * @param g - the game object
	 */
	public void createObject(GameObject g)
	{
		t.addObject(g);
	}
	
	private void renderScreen()
	{
		if (state == 0)
		{
			screen.render(t.getRenderables());
		} else if (state == 1)
		{
			screen.render(m);
		} else if (state == 3)
		{
			screen.render(t.getRenderables());
		}
	}
	
	/**
	 * Quit the game
	 */
	public void quit()
	{
		playing = false;
	}

	public void keyPressed(KeyEvent e)
	{
		if (state == 0)
		{
			if (e.getKeyCode() == KeyEvent.VK_D)
			{
				p1.getVelocity().addX(5);
				p1.setFacing(Player.FACE_RIGHT);
			} else if (e.getKeyCode() == KeyEvent.VK_A)
			{
				p1.getVelocity().addX(-5);
				p1.setFacing(Player.FACE_LEFT);
			} else if (e.getKeyCode() == KeyEvent.VK_S)
			{
				p1.getVelocity().addY(5);
				p1.setFacing(Player.FACE_DOWN);
			} else if (e.getKeyCode() == KeyEvent.VK_W)
			{
				p1.setFacing(Player.FACE_UP);
				if (p1.canJump())
				{
					p1.getVelocity().addY(-12);
					p1.setCanJump(false);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_L)
			{
				p2.getVelocity().addX(5);
				p2.setFacing(Player.FACE_RIGHT);
			} else if (e.getKeyCode() == KeyEvent.VK_J)
			{
				p2.getVelocity().addX(-5);
				p2.setFacing(Player.FACE_LEFT);
				//System.out.println(p2.getVelocity().toString());
			} else if (e.getKeyCode() == KeyEvent.VK_K)
			{
				p2.getVelocity().addY(5);
				p2.setFacing(Player.FACE_DOWN);
			} else if (e.getKeyCode() == KeyEvent.VK_I)
			{
				p2.setFacing(Player.FACE_UP);
				if (p2.canJump())
				{
					p2.getVelocity().addY(-12);
					p2.setCanJump(false);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_C)
			{
				hit(p1);
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				hit(p2);
			}
		} else if (state == 1)
		{
			
		}
	}
	
	private void hit(Player exempt)
	{
		for (Entity e : t.getEntities())
		{
			if (e instanceof Player)
			{
				Player x = (Player)e;
				if (x.colliding(exempt.getHitBounds()))
				{
					x.damage(exempt.getStats().getDamage());
				}
			}
		}
	}
	
	
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			if((Math.abs(p1.getVelocity().getX()) <= 5))
				p1.getVelocity().addX(-5);
		} else if (e.getKeyCode() == KeyEvent.VK_A)
		{
			if((Math.abs(p1.getVelocity().getX()) <= 5))
				p1.getVelocity().addX(5);
		} else if (e.getKeyCode() == KeyEvent.VK_S)
		{
			p1.getVelocity().addY(-5);
		} else if (e.getKeyCode() == KeyEvent.VK_W)
		{
			
		}
		if (e.getKeyCode() == KeyEvent.VK_L)
		{
			if((Math.abs(p2.getVelocity().getX()) <= 5))
				p2.getVelocity().addX(-5);
		} else if (e.getKeyCode() == KeyEvent.VK_J)
		{
			if((Math.abs(p2.getVelocity().getX()) <= 5))
				p2.getVelocity().addX(5);
		} else if (e.getKeyCode() == KeyEvent.VK_K)
		{
			p2.getVelocity().addY(-5);
		} else if (e.getKeyCode() == KeyEvent.VK_I)
		{
			
		}
		
	}
	
	public void setName(int p, String name)
	{
		if (p == 1)
		{
			if (p1 == null)
			{
				p1n = name;
			} else
			{
				p1.setName(name);
				p1n = name;
			}
		} else if (p == 2)
		{
			if (p2 == null)
			{
				p2n = name;
			} else
			{
				p2.setName(name);
				p2n = name;
			}
		}
		
	}
	
	public World getWorld()
	{
		return this.w;
	}
	
	public Rectangle getBounds()
	{
		return screen.getBounds();
	}
	
}
