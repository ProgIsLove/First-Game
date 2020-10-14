package tom.game2d.first;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
	
	Handler handler;
	private BufferedImage player_image;
	
	public Player(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		player_image = ss.grabImage(1,1,32,32);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32,32);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x +=velX;
		y +=velY;
		
		x = Game.clamp(x, 0, Game.WIDTH - 48);
		y = Game.clamp(y, 0, Game.HEIGHT - 70);
		
		collision();
			
	}
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy || tempObject.getId() == ID.HardEnemy || tempObject.getId() == ID.EnemyBoss ) {
				if(getBounds().intersects(tempObject.getBounds())) {
					HUD.HEALTH -= 2;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(player_image, (int) x, (int) y, null);
	}
}
