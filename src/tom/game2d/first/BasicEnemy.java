package tom.game2d.first;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BasicEnemy extends GameObject{
	
	private Handler handler;
	private BufferedImage basicEnemy_image; 

	public BasicEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		velX = 5;
		velY =5;
		
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		basicEnemy_image = ss.grabImage(1, 2, 16, 16);
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
		
		//handler.addObject(new Trail((int)x,(int)y, ID.Trail,Color.red, 16,16,0.02f,handler));
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(basicEnemy_image, (int) x, (int)y, null);
	}
}
