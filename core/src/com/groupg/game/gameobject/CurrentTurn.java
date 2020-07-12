package com.groupg.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class CurrentTurn {
    private static final int POSITION_X = 120, POSITION_Y = 450;
    private static final int FRAME_COLUMN = 9, FRAME_ROW = 1;
    private static final int SPRITE_HEIGHT = 32, SPRITE_WIDTH = 32;
    private Vector3 position;
    private Texture currentTurnTexture;
    private Sprite currentTurnTextSprite;
    private Texture currentTurnFontTexture;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> currentTurnAnimation;
    private float stateTime;
    private boolean whitePlayerTurn;

    public CurrentTurn() {
        this.position = new Vector3(POSITION_X - SPRITE_WIDTH / 2, POSITION_Y - SPRITE_HEIGHT / 2, 0);

        currentTurnFontTexture = new Texture(Gdx.files.internal("Current_Turn_Text.png"));
        currentTurnTexture = new Texture(Gdx.files.internal("Piece_Transform_Sprite_Sheet.png"));

        currentTurnTextSprite = new Sprite(currentTurnFontTexture);
        currentTurnTextSprite.setPosition(POSITION_X - currentTurnFontTexture.getWidth() / 2,
                (POSITION_Y + 32) - currentTurnFontTexture.getHeight() / 2);

        TextureRegion[][] temp = TextureRegion.split(currentTurnTexture,
                currentTurnTexture.getWidth() / FRAME_COLUMN,
                currentTurnTexture.getHeight() / FRAME_ROW);

        TextureRegion[] currentTurnFrameRegion = new TextureRegion[FRAME_COLUMN * FRAME_ROW];
        int index = 0;
        for (int i = 0; i < FRAME_ROW; i++) {
            for (int j = 0; j < FRAME_COLUMN; j++) {
                currentTurnFrameRegion[index++] = temp[i][j];
            }
        }

        currentTurnAnimation = new Animation<>(1f/30f, currentTurnFrameRegion);
        stateTime = 0f;

        whitePlayerTurn = true;
        /*Random random = new Random();
        if (random.nextInt(2) == 1) whitePlayerTurn = true;*/
        if (whitePlayerTurn) currentFrame = currentTurnFrameRegion[0]; else currentFrame = currentTurnFrameRegion[currentTurnFrameRegion.length - 1];
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(float delta, SpriteBatch batch) {
        batch.draw(currentFrame, position.x, position.y);
        currentTurnTextSprite.draw(batch);

        if (whitePlayerTurn) {
            currentTurnAnimation.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        } else {
            currentTurnAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        }
        currentFrame = currentTurnAnimation.getKeyFrame(stateTime, false);
    }

    public void dispose() {
        currentTurnTexture.dispose();
        currentTurnFontTexture.dispose();
    }

    public void changeCurrentTurn() {
        whitePlayerTurn = !whitePlayerTurn;
        stateTime = 0;
    }

    public boolean getCurrentTurn() {
        return whitePlayerTurn;
    }
}
