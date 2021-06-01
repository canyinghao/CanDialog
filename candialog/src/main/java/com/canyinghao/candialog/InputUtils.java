package com.canyinghao.candialog;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;


/**
 * Copyright 2016 canyinghao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class InputUtils {


    public static void assist(ViewGroup content) {
        new InputUtils(content);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private ViewGroup.LayoutParams frameLayoutParams;
    private int statusBarH;

    private InputUtils(ViewGroup content) {
        statusBarH = getStatusBarHeight(content.getContext());

        mChildOfContent = content;

        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        try{
                            possiblyResizeChildOfContent();
                        }catch (Throwable e){
                            e.printStackTrace();
                        }

                    }
                });
        frameLayoutParams = mChildOfContent
                .getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {

        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView()
                    .getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;

            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard
                        - heightDifference + statusBarH;

            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
                mChildOfContent.setFocusable(true);
                mChildOfContent.setFocusableInTouchMode(true);
                mChildOfContent.requestFocus();

            }
            mChildOfContent.requestLayout();
            if (mChildOfContent instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) mChildOfContent;
                int childCount = vg.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = vg.getChildAt(i);
                    childAt.requestLayout();
                }
            }
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {

        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }


    public static int getStatusBarHeight(Context context) {
        try {
            int result = 0;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        } catch (Exception e) {
            return dp2px(context, 25);

        }
    }


    public static int dp2px(Context context, int dp) {


        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}