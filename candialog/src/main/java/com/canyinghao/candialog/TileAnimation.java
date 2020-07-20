package com.canyinghao.candialog;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.canyinghao.cananimation.CanAnimation;
import com.canyinghao.cananimation.CanObjectAnimator;

import static com.canyinghao.cananimation.CanAnimation.animationSequence;
import static com.canyinghao.cananimation.CanAnimation.animationTogether;


/**
 *
 Copyright 2016 canyinghao

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 *
 */
public class TileAnimation {

    public static DisplayMetrics getScreenDisplayMetrics(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = manager.getDefaultDisplay();
        display.getMetrics(displayMetrics);

        return displayMetrics;
    }


    public static Point getStartPoint(Context context, View view) {


        int h = getScreenDisplayMetrics(context).heightPixels;
        int w = getScreenDisplayMetrics(context).widthPixels;


        int viewH = view.getHeight();
        int viewW = view.getWidth();


        int y = (h - viewH) / 2;
        int x = (w - viewW) / 2;


        return new Point(x, y);


    }


    private static AbsoluteLayout createAnimLayout(FrameLayout rootLayout, Activity act) {


        AbsoluteLayout animLayout = new AbsoluteLayout(act);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);


        animLayout.setLayoutParams(params);

        animLayout.setBackgroundResource(android.R.color.transparent);


        rootLayout.addView(animLayout);
        return animLayout;
    }

    public static Bitmap loadBitmapFromView(View view, int width, int height) {
        if (view == null) {
            return null;
        }

        // 生成bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_4444);
        // 利用bitmap生成画布
        Canvas canvas = new Canvas(bitmap);
        // 把view中的内容绘制在画布上
        view.draw(canvas);

        return bitmap;
    }

    public static Animator[] show(Activity act, final View v, int width, int height, final boolean isIn) {


        Bitmap bitmap = loadBitmapFromView(v, width, height);


        v.setVisibility(View.GONE);


        Point[][] points = new Point[5][2];


        points[0][0] = new Point(0, 0);
        points[0][1] = new Point(width / 3, height / 3 * 2);


        points[1][0] = new Point(width / 3, 0);
        points[1][1] = new Point(width / 3 * 2, height / 3);

        points[2][0] = new Point(width / 3, height / 3);
        points[2][1] = new Point(width / 3, height / 3);


        points[3][0] = new Point(width / 3 * 2, height / 3);
        points[3][1] = new Point(width / 3, height / 3 * 2);


        points[4][0] = new Point(0, height / 3 * 2);
        points[4][1] = new Point(width / 3 * 2, height / 3);


        Point[] moves = new Point[5];

        moves[0] = new Point(0, -height / 3 * 2);
        moves[1] = new Point(width / 3 * 2, 0);
        moves[2] = new Point(0, -height / 3 * 2);
        moves[3] = new Point(0, height / 3 * 2);
        moves[4] = new Point(-width / 3 * 2, 0);

//        Point  xy = new Point((int)v.getX(),(int)v.getY());
        Point xy = getStartPoint(act, v);


        final AbsoluteLayout ll = createAnimLayout((FrameLayout) v.getParent(), act);

        Animator[] animators = new Animator[5];
        for (int i = 0; i < 5; i++) {

            Bitmap bitmapP = Bitmap.createBitmap(bitmap, points[i][0].x, points[i][0].y,
                    points[i][1].x, points[i][1].y);


            final ImageView iv = new ImageView(v.getContext());
            iv.setImageBitmap(bitmapP);
            iv.setBackgroundColor(Color.WHITE);
            AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(points[i][1].x, points[i][1].y, points[i][0].x + xy.x, points[i][0].y + xy.y);

            ll.addView(iv, params);


            CanObjectAnimator obj = CanObjectAnimator.on(iv);


            Animator together = null;
            if (i != 2) {


                int currentX = isIn ? xy.x + points[i][0].x + moves[i].x : xy.x + points[i][0].x;
                int targetX = !isIn ? xy.x + points[i][0].x + moves[i].x : xy.x + points[i][0].x;

                int currentY = isIn ? xy.y + points[i][0].y + moves[i].y : xy.y + points[i][0].y;
                int targetY = !isIn ? xy.y + points[i][0].y + moves[i].y : xy.y + points[i][0].y;

                Animator move = obj.moveTo(currentX, targetX, currentY, targetY, 1, new LinearInterpolator());
                Animator alpha = obj.alpha(isIn ? 0 : 1, isIn ? 1 : 0, 1, null, false);

                together = animationTogether(move, alpha);


            } else {

                together = obj.alpha(isIn ? 0 : 1, isIn ? 1 : 0, 1, null, false);


                together = animationSequence(together, CanAnimation.run(new Runnable() {
                    @Override
                    public void run() {
                        ((FrameLayout) v.getParent()).removeView(ll);
                        if (isIn) {
                            v.setVisibility(View.VISIBLE);
                        } else {
                            v.setVisibility(View.GONE);
                        }

                    }
                }));

            }


            animators[i] = together;


        }


        return animators;

    }

    public static Animator[] show(Activity act, final View v, boolean isIn) {




        return show(act, v, v.getWidth(), v.getHeight(), isIn);


    }
}
