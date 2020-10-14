package tom.game2d.first;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = -3666574735456513282L;
	
	public static final int WIDTH= 640, HEIGHT = WIDTH / 12*9;
	
	private Thread thread;
	private boolean running = false;
	
	public static boolean paused = false;
	public static int diff = 0;
	
	// 0 = normal
	// 1 = hard
	
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Random r;
	private Menu menu;
	private Shop shop;
	
	public enum STATE{
		Menu,
		Select,
		Shop,
		Help,
		Game,
		End
	};
	
	public static STATE gameState = STATE.Menu;
	
	public static BufferedImage sprite_sheet;
	
	public Game() {
		
		handler = new Handler();
		hud = new HUD();
		shop = new Shop(handler,hud);
		menu = new Menu(handler,hud);
		this.addKeyListener(new KeyInput(handler,this));
		this.addMouseListener(menu);
		this.addMouseListener(shop);
		
		new Window(WIDTH,HEIGHT, "Lets Build a Game!", this);
		
		BufferedImageLoader loader = new BufferedImageLoader();
		
		sprite_sheet = loader.loadImage("/Sprite_sheet.png");
		
		spawner = new Spawn(handler, hud);
		r = new Random();
		
		for(int i = 0; i < 10; i++) {
			handler.addObject(new MenuParticle(r.nextInt(WIDTH),r.nextInt(HEIGHT),ID.MenuParticle, handler));
		}
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			//this would be the game loop
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			System.out.println(amountOfTicks);
			System.out.println(timer);
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		
		
		if(gameState == STATE.Game) 
		{
			if(!paused) 
			{
				hud.tick();
				spawner.tick();
				handler.tick();
				
				if(HUD.HEALTH <=0) {
					HUD.HEALTH = 100;
					gameState = STATE.End;
					handler.object.clear();
					for(int i = 0; i < 10; i++) {
						handler.addObject(new MenuParticle(r.nextInt(WIDTH),r.nextInt(HEIGHT),ID.MenuParticle, handler));
					}
				}
			}
				
		}else if(gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Select || gameState == STATE.Help) {
			menu.tick();
			handler.tick();
		}
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(paused) {
			g.setFont(new Font("Arial",1,50));
			g.setColor(Color.white);
			g.drawString("PAUSED", Game.WIDTH/2-100, Game.HEIGHT/2);
			
		}
		
		if(gameState == STATE.Game) {
			hud.render(g);
			handler.render(g);
		}else if(gameState == STATE.Shop) {
			shop.render(g);
		}else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select) {
			menu.render(g);
			handler.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >= max)
			return var = max;
		else if(var <=min)
			return var = min;
		else 
			return var;
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
