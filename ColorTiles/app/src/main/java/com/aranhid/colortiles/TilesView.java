package com.aranhid.colortiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TilesView extends View {

    Context context;
    int[][] tiles = new int[4][4];
    int darkColor = Color.GRAY;
    int brightColor = Color.YELLOW;
    List<Integer> colors = new ArrayList<>(Arrays.asList(darkColor, brightColor));
    int width, height;
    int tileWidth, tileHeight;

    public TilesView(Context context) {
        super(context);
        this.context = context;
    }

    public TilesView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        newGame();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();

        tileWidth = width / tiles[0].length;
        tileHeight = height / tiles.length;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4);

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == darkColor) {
                    paint.setColor(darkColor);
                }
                else {
                    paint.setColor(brightColor);
                }
                Rect rect = new Rect(tileWidth * j + 2,tileHeight * i + 2,tileWidth * (j + 1) - 2, tileHeight * (i + 1) - 2);
                canvas.drawRect(rect, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int j = x / tileWidth;
            int i = y / tileHeight;
            if (tiles[i][j] == darkColor) {
                tiles[i][j] = brightColor;
            }
            else  {
                tiles[i][j] = darkColor;
            }
        }

        if(checkGameWin()) {
            Toast.makeText(context, R.string.win, Toast.LENGTH_SHORT).show();
            newGame();
        }

        invalidate();
        return true;
    }

    private void newGame() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Collections.shuffle(colors);
                tiles[i][j] = colors.get(0);
            }
        }
    }

    private Boolean checkGameWin() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != tiles[0][0]) {
                    return  false;
                }
            }
        }

        return true;
    }
}
