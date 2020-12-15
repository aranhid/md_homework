package com.aranhid.memorina;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Card {
    String TAG = "mytag";
    CardsView parent;

    Boolean flipped = false;
    Boolean isRemoved = false;

    Integer currentColor;
    Integer frontColor;
    Integer backColor;
    CardFlip cardFlip;

    public Card(CardsView view, Integer frontColor, Integer backColor, Integer currentColor) {
        parent = view;
        this.frontColor = frontColor;
        this.backColor = backColor;
        this.currentColor = currentColor;
    }

    public void flip(Integer seconds) {
        Log.d(TAG, "Flip: start");
        cardFlip = new CardFlip();
        cardFlip.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, seconds);
        Log.d(TAG, "Flip: end");
    }

    public void cancelFlip() {
        cardFlip.cancel(true);
    }

    public Card getCard() {
        return this;
    }

    public class CardFlip extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                flipped = true;
                parent.openedCards.add(getCard());
                currentColor = frontColor;
                Log.d(TAG, "doInBackground: start" + new Date().toString());

                TimeUnit.SECONDS.sleep(integers[0]);

                Log.d(TAG, "doInBackground: end" + new Date().toString());
                currentColor = backColor;
                flipped = false;
                parent.openedCards.removeFirst();

                parent.invalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
}
