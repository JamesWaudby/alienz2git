package com.max.Alienz2;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

public class Wave {
	
	Array<Enemy> enemies;
	
	public Wave(int enemyNum, int type, Ship ship) {
		
		this.enemies = new Array<Enemy>(enemyNum);
		
		for(int i = 0; i < enemyNum; ++i) {
			
			Vector3 endPos = new Vector3(ship.x, ship.y, 0);
			Vector3 startPos = new Vector3();
			float rand;
			int randType = MathUtils.random(0, 2);
			
			switch(type) {
			
				// From top
				case 0:				
					rand = MathUtils.random(0f, 800f);	
					startPos.set(rand, -24, 0);
					break;
					
				// From left
				case 1:	
					rand = MathUtils.random(0f, 480f);
					startPos.set(-24, rand, 0);
					break;
					
				// From bottom
				case 2:	
					rand = MathUtils.random(0f, 480f);
					startPos.set(rand, 500, 0);
					break;
					
				// From right
				case 3:
					rand = MathUtils.random(0f, 480f);
					startPos.set(840, rand, 0);
					break;
			}
			
			enemies.add(new Enemy(randType, startPos, endPos));
		}

	}
	
	public void render(SpriteBatch batch) {
		for(Enemy enemy: enemies) {
			enemy.render(batch);
		}
			
	}
	
	public void update(ShapeRenderer shapes, OrthographicCamera camera) {
		for(Enemy enemy:enemies) {
			enemy.update(shapes, camera);
		}
	}
	
	/**
	 * @return the enemies
	 */
	public int getEnemies() {
		return enemies.size;
	}	
	
}
