package com.jackson_siro.mpango;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.*;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.GestureDetector.OnGestureListener;

import androidx.appcompat.app.AppCompatActivity;

public class CcStart extends AppCompatActivity {
    
    private MpangoView mpangoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mpangoView = new MpangoView(this, null);
        setContentView(mpangoView);
    }

    void goGames(int mode) {
        Intent startGame = new Intent(this, DdTorus.class);
        startGame.putExtra("com.jackson_siro.mpango.Mode", mode);
        this.startActivity(startGame);
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            this.startActivity(new Intent(this, BbWelcome.class));
            this.finish();
        }
        return true;
    }

    public class MpangoView extends SurfaceView implements OnGestureListener,
            SurfaceHolder.Callback {
        private GestureDetector mGestureDetector;
        private SurfaceHolder holder;

        private Bitmap backgroundBmp;
        private Bitmap logoBmp;
        private Bitmap boxBmp;
        private Bitmap traditionaltitleBmp;
        private Bitmap timetitleBmp;
        private Bitmap garbagetitleBmp;
        private Bitmap gotraditionalBmp;
        private Bitmap gotimeBmp;
        private Bitmap gogarbageBmp;
        private Typeface typeface;

        private int WIDTH = 480, HEIGHT = 800;

        public MpangoView(Context context, AttributeSet attrs) {
            super(context, attrs);
            mGestureDetector = new GestureDetector(this);
            setFocusable(true);
            setLongClickable(true);
            holder = getHolder();
            holder.addCallback(this);
            DisplayMetrics dm = new DisplayMetrics();
            dm = getApplicationContext().getResources().getDisplayMetrics();
            WIDTH = dm.widthPixels;
            HEIGHT = dm.heightPixels;

            backgroundBmp = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.garbagebackground);
            logoBmp = Mpango3D.scaleBitmap(context, R.drawable.appicon,
                    0.7f * WIDTH, 0.3f * HEIGHT);
            boxBmp = Mpango3D.scaleBitmap(context, R.drawable.bigbox,
                    0.71f * WIDTH, 0.2f * HEIGHT);
            traditionaltitleBmp = Mpango3D.scaleBitmap(context,
                    R.drawable.traditional, WIDTH * 0.32f, HEIGHT * 0.03f);
            timetitleBmp = Mpango3D.scaleBitmap(context,
                    R.drawable.timeattack, WIDTH * 0.32f, HEIGHT * 0.03f);
            garbagetitleBmp = Mpango3D.scaleBitmap(context,
                    R.drawable.garbage, WIDTH * 0.25f, HEIGHT * 0.03f);
            gotraditionalBmp = Mpango3D.scaleBitmap(context,
                    R.drawable.gotraditional, WIDTH * 0.17f, WIDTH * 0.17f);
            gogarbageBmp = Mpango3D.scaleBitmap(context,
                    R.drawable.gogarbage, WIDTH * 0.17f, WIDTH * 0.17f);
            gotimeBmp = Mpango3D.scaleBitmap(context, R.drawable.gotimer,
                    WIDTH * 0.17f, WIDTH * 0.17f);

            AssetManager am = context.getAssets();
            typeface = Typeface.createFromAsset(am, "222-CAI978.ttf");
        }

        void drawFrame() {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                Paint paint = new Paint();
                canvas.drawBitmap(backgroundBmp, null, new RectF(0, 0, WIDTH,
                        HEIGHT), paint);
                float yPos = 41.0f;
                canvas.drawBitmap(logoBmp, (WIDTH - logoBmp.getWidth()) / 2.0f,
                        yPos, paint);
                float xPos = (WIDTH - boxBmp.getWidth()) / 2.0f;
                yPos += logoBmp.getHeight();
                float h = (HEIGHT - boxBmp.getHeight() * 3 - yPos) * 0.25f;
                float hL = boxBmp.getHeight() / 9.0f;
                yPos += h;
                canvas.drawBitmap(boxBmp, xPos, yPos, paint);
                canvas.drawBitmap(gogarbageBmp, xPos + hL, yPos + hL, paint);
                canvas.drawBitmap(garbagetitleBmp, xPos + hL
                        + gogarbageBmp.getWidth(), yPos + hL, paint);
                String content = Mpango3D.getHelpContent("kawaida.txt",
                        CcStart.this);
                paint.setTextSize(12);
                paint.setStyle(Style.STROKE);
                paint.setTypeface(typeface);
                paint.setColor(0xffffffff);
                FontMetrics fm = paint.getFontMetrics();
                float sFontHeight = (float) Math.ceil(fm.descent - fm.ascent);
                float lineSpace = sFontHeight / 10.0f;
                float lineWidth = WIDTH - 2.0f * xPos - 2.5f * hL
                        - gotraditionalBmp.getWidth();
                float baseHeight = yPos + hL + traditionaltitleBmp.getHeight();
                float startXPos = xPos + hL + gotraditionalBmp.getWidth();
                Mpango3D.drawContent(content, sFontHeight, lineSpace,
                        lineWidth, startXPos, baseHeight, canvas, paint);

                yPos += boxBmp.getHeight() + h;
                canvas.drawBitmap(boxBmp, xPos, yPos, paint);
                canvas.drawBitmap(gotimeBmp, xPos + hL, yPos + hL, paint);
                canvas.drawBitmap(timetitleBmp,
                        xPos + hL + gotraditionalBmp.getWidth(), yPos + hL,
                        paint);
                content = Mpango3D.getHelpContent("wastani.txt", CcStart.this);
                baseHeight = yPos + hL + timetitleBmp.getHeight();
                Mpango3D.drawContent(content, sFontHeight, lineSpace,
                        lineWidth, startXPos, baseHeight, canvas, paint);

                yPos += boxBmp.getHeight() + h;
                canvas.drawBitmap(boxBmp, xPos, yPos, paint);
                canvas.drawBitmap(gotraditionalBmp, xPos + hL, yPos + hL, paint);
                canvas.drawBitmap(traditionaltitleBmp,
                        xPos + hL + gotraditionalBmp.getWidth(), yPos + hL,
                        paint);
                content = Mpango3D.getHelpContent("ngumu.txt", CcStart.this);
                baseHeight = yPos + hL + garbagetitleBmp.getHeight();
                Mpango3D.drawContent(content, sFontHeight, lineSpace,
                        lineWidth, startXPos, baseHeight, canvas, paint);

                holder.unlockCanvasAndPost(canvas);
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            mGestureDetector.onTouchEvent(event);
            return super.onTouchEvent(event);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();
            float yPos = 41.0f + logoBmp.getHeight();
            float h = (HEIGHT - boxBmp.getHeight() * 3 - yPos) * 0.25f;

            if (x >= (WIDTH - boxBmp.getWidth()) / 2.0f
                    && x <= WIDTH + boxBmp.getWidth() / 2.0f) {
                if (y >= yPos + h && y <= yPos + h + boxBmp.getHeight()) {
                    goGames(3);
                } else if (y >= yPos + boxBmp.getHeight() + 2.0f * h
                        && y <= yPos + 2.0f * (boxBmp.getHeight() + h)) {
                    goGames(2);
                    //fullVersion();
                } else if (y >= yPos + 2.0f * boxBmp.getHeight() + 3.0f * h
                        && y <= yPos + 4.0f * boxBmp.getHeight() + 3.0f * h) {
                    goGames(1);
                    //fullVersion();
                }
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawFrame();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    }
}