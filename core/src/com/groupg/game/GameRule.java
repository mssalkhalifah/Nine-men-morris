package com.groupg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.groupg.game.player.Player;

public class GameRule {
    private static final int FRAME_COLUMN = 11, FRAME_ROW = 1;

    private Texture fontTextureWhite;
    private TextureRegion[] whiteFontTextureRegion;
    private TextureRegion currentWhiteScoreNumber;
    private TextureRegion totalWhiteScoreNumber;

    private Texture fontTextureBlack;
    private TextureRegion[] blackFontTextureRegion;
    private TextureRegion currentBlackScoreNumber;
    private TextureRegion totalBlackScoreNumber;

    private Texture whitePieceTexture;
    private Texture blackPieceTexture;

    private Sprite whitePieceSprite;
    private Sprite blackPieceSprite;

    private Player whitePlayer;
    private Player blackPlayer;

    public GameRule(Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;

        fontTextureWhite = new Texture(Gdx.files.internal("Fonts.png"));
        fontTextureBlack = new Texture(Gdx.files.internal("Fonts.png"));
        whitePieceTexture = new Texture(Gdx.files.internal("White_Piece.png"));
        blackPieceTexture = new Texture(Gdx.files.internal("Black_Piece.png"));

        whitePieceSprite = new Sprite(whitePieceTexture);
        blackPieceSprite = new Sprite(blackPieceTexture);

        whitePieceSprite.setPosition(40 - whitePieceSprite.getWidth() / 2, 350 - whitePieceSprite.getHeight() / 2);
        blackPieceSprite.setPosition(40 - blackPieceSprite.getWidth() / 2,
                (whitePieceSprite.getY() - 40) - blackPieceSprite.getHeight() / 2);

        TextureRegion[][] temp = TextureRegion.split(fontTextureWhite,
                fontTextureWhite.getWidth() / FRAME_COLUMN, fontTextureWhite.getHeight() / FRAME_ROW);

        whiteFontTextureRegion = new TextureRegion[FRAME_COLUMN * FRAME_ROW];
        blackFontTextureRegion = new TextureRegion[FRAME_COLUMN * FRAME_ROW];
        int index = 0;
        for (int i = 0; i < FRAME_ROW; i++) {
            for (int j = 0; j < FRAME_COLUMN; j++) {
                whiteFontTextureRegion[index] = temp[i][j];
                blackFontTextureRegion[index] = temp[i][j];
                index++;
            }
        }
    }

    public void update(float delta) {
        currentWhiteScoreNumber = whiteFontTextureRegion[whitePlayer.getCurrentNumberOfPieces()];
        totalWhiteScoreNumber = whiteFontTextureRegion[whitePlayer.getTotalNumberOfPieces()];

        currentBlackScoreNumber = blackFontTextureRegion[blackPlayer.getCurrentNumberOfPieces()];
        totalBlackScoreNumber = blackFontTextureRegion[blackPlayer.getTotalNumberOfPieces()];
    }

    public void render(float delta, SpriteBatch batch) {
        whitePieceSprite.draw(batch);
        blackPieceSprite.draw(batch);

        // White player score
        batch.draw(currentWhiteScoreNumber, whitePieceSprite.getX() + 32, whitePieceSprite.getY());
        batch.draw(whiteFontTextureRegion[whiteFontTextureRegion.length - 1], whitePieceSprite.getX() + 40, whitePieceSprite.getY());
        batch.draw(totalWhiteScoreNumber, whitePieceSprite.getX() + 48, whitePieceSprite.getY());

        // Black player score
        batch.draw(currentBlackScoreNumber, blackPieceSprite.getX() + 32, blackPieceSprite.getY());
        batch.draw(blackFontTextureRegion[blackFontTextureRegion.length - 1], blackPieceSprite.getX() + 40, blackPieceSprite.getY());
        batch.draw(totalBlackScoreNumber, blackPieceSprite.getX() + 48, blackPieceSprite.getY());
    }

    public void dispose() {
        whitePieceTexture.dispose();
        blackPieceTexture.dispose();
        fontTextureWhite.dispose();
        fontTextureBlack.dispose();
    }
}
