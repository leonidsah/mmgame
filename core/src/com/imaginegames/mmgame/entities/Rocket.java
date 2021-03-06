package com.imaginegames.mmgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.imaginegames.mmgame.GameControl;
import com.imaginegames.mmgame.tools.CollisionRect;

public class Rocket {

	private GameControl game;
	
	public static float SPEED = 900;
	
	private static Animation<?> rocket_ANIMATION;
	private static final int PWIDTH = 74;
	private static final int PHEIGHT = 31;
	private static final float SCALE = 1.2f;
	public static final float WIDTH = 74 * SCALE;
	public static final float HEIGHT = 31 * SCALE;
	private static final float ANIMATION_SPEED = 0.15f;
	public float x, y; // Don't do variable a static if it have a many objects with their own parameters for this variable
	int speed_direction;
	
	Texture x_line;
	Texture y_line;
	private CollisionRect rocket_rect;
	
	public boolean remove = false;
	
	private float stateTime;
	private int roll;
	
	public Rocket (GameControl game, float x, float y, int speed_direction) {
		this.game = game;
		this.x = x;
		this.y = y;
		SPEED = SPEED * GameControl.GAMESPEED;
		x_line = game.assetManager.get("x_line.png", Texture.class);
		y_line = game.assetManager.get("y_line.png", Texture.class);
		this.speed_direction = speed_direction;
		roll = 1; // max = 2
		TextureRegion[][] rocket_animated_sheet = TextureRegion.split(game.assetManager.get("rocket_sheet.png", Texture.class), PWIDTH, PHEIGHT);
		
		if (rocket_ANIMATION == null) {
			rocket_ANIMATION = new Animation<>(ANIMATION_SPEED, rocket_animated_sheet[roll]);
			}
		rocket_rect = new CollisionRect(x, y, WIDTH, HEIGHT);
		
		}
	public void update(float deltaTime) {
		x += speed_direction * SPEED * deltaTime;
		if (y > Gdx.graphics.getHeight() || y < 0 - HEIGHT || x < 0 - WIDTH || x > Gdx.graphics.getWidth()) {
			remove = true;
		}
		rocket_rect.move(x, y);
	}
	public void render(SpriteBatch batch, float delta) {
		if (speed_direction == 1) {
			batch.draw((TextureRegion) rocket_ANIMATION.getKeyFrame(stateTime, true), x, y, WIDTH, HEIGHT);
			if (GameControl.SHOW_STAT) {
			batch.draw(x_line, x + WIDTH, y, 1, HEIGHT);
			batch.draw(x_line, x, y, 1, HEIGHT);
			batch.draw(y_line, x, y + HEIGHT, WIDTH, 1);
			batch.draw(y_line, x, y, WIDTH, 1);
			}
		}
		else if (speed_direction == -1) {
			batch.draw((TextureRegion) rocket_ANIMATION.getKeyFrame(stateTime, true), x + WIDTH, y, -WIDTH, HEIGHT);
			if (GameControl.SHOW_STAT) {
			batch.draw(x_line, x, y, 1, HEIGHT);
			batch.draw(x_line, x + WIDTH, y, 1, HEIGHT);
			batch.draw(y_line, x, y, WIDTH, 1);
			batch.draw(y_line, x, y + HEIGHT, WIDTH, 1);
			}
			
		}
		stateTime += delta;
	}
	
	public CollisionRect getCollisionRect() {
		return rocket_rect;
	}

}
