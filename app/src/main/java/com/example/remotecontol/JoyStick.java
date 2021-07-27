package com.example.remotecontol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class JoyStick extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final int BASE_RADIUS_DIVISOR = 2;
    private static final int CON_RADIUS_DIVISOR = 12;
    private static final int CLI_RADIUS_DIVISOR = 10;

    private int centerX;
    private int centerY;
    private int centerXClick;
    private int centerYClick;
    private float lastX;
    private float lastY;

    private int baseRadius;
    private int conRadius;
    private int cliRadius;

    public JoyStick(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    public JoyStick(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    public JoyStick(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    public JoyStick(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        setDimensions();
        drawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float moveX = motionEvent.getX();
        float moveY = motionEvent.getY();
        float displacedFromMove = Utils.getDisplacementFromCentre(moveX, moveY, centerX, centerY);
        float displacedFromClick = Utils.getDisplacementFromCentre(moveX, moveY, centerXClick, centerYClick);

        if (view.equals(this)) {
            if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                if (displacedFromMove < baseRadius) {
                    drawJoystick(moveX, moveY);
                    WebApi.onJoystickMove(getContext(), moveX - lastX, moveY - lastY);
                    lastX = moveX;
                    lastY = moveY;
                }
            } else {
                if (displacedFromClick < cliRadius) WebApi.onClickJoyStick(getContext(), true);
                drawJoystick(centerX, centerY);
                lastX = centerX;
                lastY = centerY;
            }
        }
        return true;
    }

    private void setDimensions() {
        baseRadius = Math.min(getHeight(), getWidth()) / BASE_RADIUS_DIVISOR;
        conRadius = Math.min(getHeight(), getWidth()) / CON_RADIUS_DIVISOR;
        cliRadius = Math.min(getHeight(), getWidth()) / CLI_RADIUS_DIVISOR;
        centerX = getWidth() / 2;
        centerY = baseRadius + getHeight() / 20;
        centerXClick = getWidth() / 2;
        centerYClick = getHeight() - getHeight() / 7;
    }

    private void drawJoystick(float abis, float ordi) {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            colors.setARGB(255, 50, 50, 50);
            canvas.drawCircle(centerX, centerY, baseRadius, colors);

            colors.setARGB(255, 0, 255, 204);
            canvas.drawCircle(abis, ordi, conRadius, colors);

            colors.setARGB(255, 0, 51, 102);
            canvas.drawCircle(centerXClick, centerYClick, cliRadius, colors);

            this.getHolder().unlockCanvasAndPost(canvas);
        }
    }

}
