package tom.game2d.first;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class HardEnemy extends GameObject{
	
	private Handler handler;
	private Random r = new Random();

	public HardEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		velX = (r.nextInt(8)+5)*-1;
		velY =(r.nextInt(8)+5)*-1;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16,16);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x+=velX;
		y+=velY;
		
		if(y <=0 || y >= Game.HEIGHT-48) velY *= -1;
		if(x <=0 || x >= Game.WIDTH-32) velX *= -1;
		handler.addObject(new Trail((int)x,(int)y, ID.Trail,Color.yellow, 16,16,0.02f,handler));
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.yellow);
		g.fillRect((int)x,(int)y,16,16);
	}
}
