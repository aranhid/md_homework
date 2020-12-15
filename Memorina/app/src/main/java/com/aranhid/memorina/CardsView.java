package com.aranhid.memorina;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class CardsView extends View {
    public static String TAG = "mytag";

    Context context;

    Integer cardsCount = 4;
    Integer flipSeconds = 2;

    ArrayList<ArrayList<Card>> cards = new ArrayList<>();
    ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(Color.MAGENTA, Color.BLUE, Color.GREEN, Color.RED));
    Integer backColor = Color.YELLOW;

    Integer canvasWidth, canvasHeight;
    Integer tileWidth, tileHeight;

    public LinkedList<Card> openedCards = new LinkedList<>();

    public CardsView(Context context){
        super(context);
        this.context = context;
    }

    public CardsView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        newGame();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        tileWidth = canvasWidth / cardsCount;
        tileHeight = canvasHeight / cardsCount;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);
        paint.setStrokeWidth(4);


        for (int i = 0; i < cards.size(); i++) {
            for (int j = 0; j < cards.get(i).size(); j++) {
                paint.setColor(cards.get(i).get(j).currentColor);
                Rect rect = new Rect(tileWidth * j + 2,tileHeight * i + 2,tileWidth * (j + 1) - 2, tileHeight * (i + 1) - 2);
                canvas.drawRect(rect, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int j = x / tileWidth;
        int i = y / tileHeight;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (openedCards.size() < 2)
            {
                Card card = cards.get(i).get(j);
                if (!card.flipped && !card.isRemoved) {
                    card.flip(flipSeconds);
                }
            }
        }

        checkTwoCards(i ,j);

        if(checkWin()) {
            Toast.makeText(context, R.string.win, Toast.LENGTH_SHORT).show();
            newGame();
        }

        invalidate();
        return true;
    }

    private void newGame() {
        Random random = new Random();
        Integer randomColor = colors.get(0);
        LinkedList<Integer> randomColors = new LinkedList<>();

        for (int i = 0; i < cardsCount * cardsCount; i++) {
            if (i % 2 == 0) {
                Integer pos = random.nextInt(colors.size());
                Log.d(TAG, "newGame: pos = " + pos.toString());
                randomColor = colors.get(pos);
                Log.d(TAG, "newGame: color = " + randomColor.toString());
            }
            randomColors.add(randomColor);
        }
        Collections.shuffle(randomColors);

        cards.clear();
        for (int i = 0; i < cardsCount; i++) {
            cards.add(new ArrayList<>());
            for (int j = 0; j < cardsCount; j++) {
                cards.get(i).add(new Card(this, randomColors.getFirst(), backColor, backColor));
                randomColors.removeFirst();
            }
        }
        invalidate();
    }

    private Boolean checkTwoCards(Integer i, Integer j) {
        if (openedCards.size() == 2) {
            Card card1 = openedCards.get(0);
            Card card2 = openedCards.get(1);
            if (card1.currentColor == card2.currentColor) {
                card1.cancelFlip();
                card2.cancelFlip();
                openedCards.clear();
                card1.currentColor = Color.TRANSPARENT;
                card2.currentColor = Color.TRANSPARENT;
                card1.isRemoved = true;
                card2.isRemoved = true;
                return true;
            }
        }
        return false;
    }

    private boolean checkWin() {
        for (int i = 0; i < cards.size(); i++) {
            for(int j = 0; j < cards.get(i).size(); j++) {
                if (!cards.get(i).get(j).isRemoved) {
                    return false;
                }
            }
        }

        return true;
    }
}
