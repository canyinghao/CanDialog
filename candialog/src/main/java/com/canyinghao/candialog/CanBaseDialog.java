package com.canyinghao.candialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.canyinghao.cananimation.CanAnimation;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

/**
 * Copyright 2016 canyinghao
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public abstract class CanBaseDialog extends CanManagerDialog  {

    //  dialog类型
    public static final int DIALOG_MSG = 0;
    public static final int DIALOG_LIST_ITEM = 1;
    public static final int DIALOG_SINGLE_CHOICE = 2;
    public static final int DIALOG_MULTI_CHOICE = 3;
    public static final int DIALOG_EDIT = 4;
    public static final int DIALOG_PROGRESS = 5;
    public static final int DIALOG_CUSTOM = 6;

    //  svg图标类型
    public static final int ICON_SUCCESS = 11;
    public static final int ICON_DANGER = 12;
    public static final int ICON_INFO = 13;
    public static final int ICON_WARNING = 14;


    //  svg图标动画类型
    public static final int ANIM_INFO_SUCCESS = 21;
    public static final int ANIM_INFO_DANGER = 22;
    public static final int ANIM_INFO_WARNING = 23;


    //  dialog所在的activity
    protected Activity mContext;


    //  添加到getWindow().getDecorView()的布局，用以显示黑色半透明背景，添加此dialog等
    protected FrameLayout animLayout;

    protected Button mButtonPositive;

    protected Button mButtonNegative;

    protected Button mButtonNeutral;


    protected LinearLayout mTopPanel;
    protected FrameLayout mContentPanel;
    protected FrameLayout mCustomPanel;
    protected LinearLayout mButtonPanel;
    protected TextView mAlertTitle;
    protected ImageView mIcon;
    protected ScrollView mScrollView;
    protected TextView mMessage;
    protected View textSpacerNoButtons;

    protected FrameLayout mCustom;


    protected boolean isInput;
    //  用来监听view是否测量完毕
    protected ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;


    //  dialog当前类型
    protected int mType;

    //  是否可取消，为false时，点击back键也不能取消
    protected boolean mCancelable = true;

    protected boolean mFocus = true;

    //  animLayout的颜色
    protected int mFullBackgroundColor;
    //    是否设置过animLayout的背景
    protected boolean mHaveFullBackgroundColor;
    //  animLayout的背景
    protected int mFullBackgroundResid;


    //  dialog自己
    protected CanBaseDialog mDialog;


    //  svg图标当前类型
    protected int mIconType;
    //  多选时存储选择的状态
    protected int checkedItem;
    //  单选时存储选中的状态
    protected boolean[] checkedItems;

    //  是否处于显示状态
    protected boolean isShowing;

    //    按键监听
    protected CanDialogInterface.OnKeyListener mOnKeyListener;


    //   消失的点击事件
    protected OnClickListener dismissListener = new OnClickListener() {
        @Override
        public void onClick(final View view) {

            dismiss();

        }
    };


    //   左右间距
    protected  int leftRightMargin = 0;
    //   高度
    protected  int dialogHeight = LayoutParams.WRAP_CONTENT;
    //   宽度
    protected  int dialogWidth = LayoutParams.MATCH_PARENT;
    //   显示动画
    protected Animator mAnimatorStart;
    //    消失动画
    protected Animator mAnimatorEnd;

    protected boolean isSystemDialog;

    protected AppCompatDialog compatDialog;
    //   消失时监听
    protected CanDialogInterface.OnDismissListener mOnDismissListener;



    //    显示时监听
    protected CanDialogInterface.OnShowListener mOnShowListener;

    //    显示或消失动画是否正在播放中
    protected boolean isAnimatorPlaying;


    //   显示或消失动画的监听事件
    protected AnimatorListenerAdapter animatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationCancel(Animator animation) {
            super.onAnimationCancel(animation);
            isAnimatorPlaying = false;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            isAnimatorPlaying = false;
        }
    };


    public CanBaseDialog(@NonNull Activity context) {
        this(context,null);
    }

    public CanBaseDialog(@NonNull Activity context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanBaseDialog(@NonNull Activity context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setActivity(context);
        this.mContext = context;
        this.leftRightMargin = InputUtils.dp2px(mContext, 20);
        mDialog =this;
        onCrate();
    }

    protected abstract void onCrate();

    protected void setContentView(@LayoutRes int layoutResID) {

        View view = LayoutInflater.from(mContext).inflate(layoutResID, null);
        setContentView(view);
    }


    protected void setContentView(View view) {

        setContentView(view,true);
    }

    protected void setContentView(View view,boolean mFocus) {

        addView(view);
        this.mFocus = mFocus;
        if(mFocus){
            setOnClickListener(null);
        }

    }

    public CanBaseDialog setLeftRightMargin(int leftRightMargin) {
        this.leftRightMargin = leftRightMargin;
        return this;
    }

    public CanBaseDialog setDialogHeight(int dialogHeight) {
        this.dialogHeight = dialogHeight;
        return this;
    }

    public CanBaseDialog setDialogWidth(int dialogWidth) {
        this.dialogWidth = dialogWidth;
        return this;
    }

    public AppCompatDialog getCompatDialog() {
        return compatDialog;
    }

    public void show() {

        isShowing = true;
        if (mButtonPanel != null && textSpacerNoButtons != null && mButtonPanel.getVisibility() == View.GONE) {

            textSpacerNoButtons.setVisibility(View.VISIBLE);

        }
        LayoutParams params = new LayoutParams(dialogWidth, dialogHeight);
        params.gravity = Gravity.CENTER;
        params.leftMargin = leftRightMargin;
        params.rightMargin = leftRightMargin;
        if(isSystemDialog){
            setVisibility(View.VISIBLE);
            compatDialog=  new AppCompatDialog(mContext);
            compatDialog.setContentView(this,params);
            compatDialog.setCancelable(mCancelable);
            compatDialog.setCanceledOnTouchOutside(mCancelable);
            compatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if(mOnDismissListener!=null){
                        mOnDismissListener.onDismiss(CanBaseDialog.this);
                    }
                    if(mOnDismissListeners!=null&&!mOnDismissListeners.isEmpty()){
                        for(CanDialogInterface.OnDismissListener onDismissListener:mOnDismissListeners){
                            onDismissListener.onDismiss(CanBaseDialog.this);
                        }
                    }
                }
            });
            compatDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if(mOnKeyListener!=null){
                        return  mOnKeyListener.onKey(CanBaseDialog.this,i,keyEvent);
                    }
                    return false;
                }
            });
            Window dialogWindow = compatDialog.getWindow();
            if(dialogWindow!=null){
                dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
                WindowManager.LayoutParams layoutParams= dialogWindow.getAttributes();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialogWindow.setAttributes(layoutParams);
            }
            compatDialog.show();
        }else{

            FrameLayout layout = createAnimLayout();

            layout.addView(this, params);


            if (mAnimatorStart != null) {

                try {
                    mAnimatorStart.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    isAnimatorPlaying = false;
                    setVisibility(View.VISIBLE);
                }

            }

        }


        if (mOnShowListener != null) {

            mOnShowListener.onShow(mDialog);
        }
    }


    /**
     * 取消
     */
    public void dismiss() {

        if(isSystemDialog){

            if(compatDialog!=null&&compatDialog.isShowing()){
                compatDialog.dismiss();
                compatDialog=null;
            }

        }else{
            if (mAnimatorEnd != null) {
                try {

                    mAnimatorEnd.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    isAnimatorPlaying = false;
                    dismissAll();

                }

            } else {
                dismissAll();

            }
        }




    }


    /**
     * 无动画的取消
     */
    protected void dismissAll() {
        isShowing = false;

        removeAnimLayout();
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(mDialog);
        }
        if(mOnDismissListeners!=null&&!mOnDismissListeners.isEmpty()){
            for(CanDialogInterface.OnDismissListener onDismissListener:mOnDismissListeners){
                onDismissListener.onDismiss(CanBaseDialog.this);
            }
            mOnDismissListeners.clear();
        }
        mOnDismissListener = null;



    }


    /**
     * 移出animLayout层
     */
    protected void removeAnimLayout() {

        ViewGroup rootView = (ViewGroup) mContext.getWindow().getDecorView();

        if (animLayout != null) {
            animLayout.removeView(this);
            rootView.removeView(animLayout);
            animLayout = null;
        }

    }


    /**
     * 创建animLayout层
     *
     * @return FrameLayout
     */
    protected FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) mContext.getWindow().getDecorView();


        if (animLayout != null) {


            if (mType != DIALOG_PROGRESS) {

                if (mCancelable) {
                    animLayout.setOnClickListener(dismissListener);
                } else {
                    animLayout.setOnClickListener(null);
                }
            }


            return animLayout;

        }
        animLayout = new FrameLayout(mContext);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        animLayout.setLayoutParams(params);
        if (mHaveFullBackgroundColor) {
            animLayout.setBackgroundColor(mFullBackgroundColor);
        } else if (mFullBackgroundResid != 0) {
            animLayout.setBackgroundResource(mFullBackgroundResid);
        } else {
            animLayout.setBackgroundColor(Color.parseColor("#77000000"));
        }


        if (mType != DIALOG_PROGRESS) {

            if(mFocus){
                if (mCancelable) {
                    animLayout.setOnClickListener(dismissListener);
                } else {
                    animLayout.setOnClickListener(null);
                }
            }

        }


        rootView.addView(animLayout);


        animLayout.setFocusable(mFocus);
        animLayout.setFocusableInTouchMode(mFocus);
        animLayout.requestFocus();
        animLayout.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (mCancelable) {
                        dismiss();
                    }
                }

                if (mOnKeyListener != null) {
                    return mOnKeyListener.onKey(mDialog, i, keyEvent);
                }
                return true;
            }
        });

        return animLayout;
    }


    /**
     * 是否显示中
     *
     * @return boolean
     */
    public boolean isShow() {

        return isShowing;
    }


    protected void setType(int type) {

        this.mType = type;

    }


    public void setAnimatorStart(Animator mAnimatorStart) {
        this.mAnimatorStart = mAnimatorStart;
    }

    public void setAnimatorStart(Runnable runnable) {
        this.mAnimatorStart = CanAnimation.run(runnable);
    }

    public void setAnimatorEnd(Animator mAnimatorEnd) {
        this.mAnimatorEnd = mAnimatorEnd;
    }

    public void setAnimatorEnd(Runnable runnable) {
        this.mAnimatorEnd = CanAnimation.run(runnable);
    }


    public void setIsInput() {

        if (!isInput && animLayout != null) {
            isInput = true;
            InputUtils.assist(animLayout);
        }
    }



    /**
     * 设置是否可取消
     *
     * @param cancelable boolean
     */
    public void setCancelable(boolean cancelable) {

        this.mCancelable = cancelable;
    }

    public void setSystemDialog(boolean systemDialog) {
        isSystemDialog = systemDialog;
    }

    public void setOnDismissListener(CanDialogInterface.OnDismissListener onDismissListener) {

        this.mOnDismissListener = onDismissListener;
    }

    public void setOnShowListener(CanDialogInterface.OnShowListener onShowListener) {

        this.mOnShowListener = onShowListener;
    }

    public void setOnKeyListener(CanDialogInterface.OnKeyListener onKeyListener) {

        this.mOnKeyListener = onKeyListener;
    }

    public void setFullBackgroundColor(int color) {
        mHaveFullBackgroundColor = true;
        this.mFullBackgroundColor = color;

    }

    public void setFullBackgroundResource(int rid) {
        this.mFullBackgroundResid = rid;

    }

    public void setIcon(int resId) {}

    public void setIcon(Drawable icon) {}

    public void setTitle(int rid) {}

    public void setTitle(CharSequence title) {}


    public void setMessage(int rid) {}

    public void setMessage(CharSequence title) {}

    public void setPositiveButton(int textId, boolean dismiss, CanDialogInterface.OnClickListener listener) {}


    public void setPositiveButton(CharSequence text, boolean dismiss, CanDialogInterface.OnClickListener listener) {}

    public void setNegativeButton(int textId, boolean dismiss, CanDialogInterface.OnClickListener listener) {}

    public void setNegativeButton(CharSequence text, boolean dismiss, CanDialogInterface.OnClickListener listener) {}

    public void setNeutralButton(int textId, boolean dismiss, CanDialogInterface.OnClickListener listener) {}

    public void setNeutralButton(CharSequence text, boolean dismiss, CanDialogInterface.OnClickListener listener) {}


    public void setCustomTitle(View customTitleView) {}


    public void setView(int layoutResId) {}


    public void setView(View customView) {}

    public void setView(View customView, int viewSpacingLeft, int viewSpacingTop,
                        int viewSpacingRight, int viewSpacingBottom) {}


    public void setItems(int itemsId, CanDialogInterface.OnClickListener listener) {}


    public void setItems(final CharSequence[] mItems, final CanDialogInterface.OnClickListener listener) {}

    public void setSingleChoiceItems(int itemsId, int checkedItem,
                                     CanDialogInterface.OnClickListener listener) {}


    public void setSingleChoiceItems(final CharSequence[] mItems, int checkedItem,
                                     final CanDialogInterface.OnClickListener listener) {}


    public void setMultiChoiceItems(int itemsId, final boolean[] checkedItems,
                                    final CanDialogInterface.OnMultiChoiceClickListener listener) {}

    public void setMultiChoiceItems(CharSequence[] items, final boolean[] checkedItems,
                                    final CanDialogInterface.OnMultiChoiceClickListener listener) {}



    public void setEditDialog(String hintText, boolean isPwd, final int minLength, final int eyeColor) {}



    public void setProgress(String loadText) {

    }


    public void setProgressCustomView(View customView) {}


    public void setBackgroundColor(int color) {}


    public void hideTitle() {}


    public void hideButtons() {}



    public void setAnimationMessage(@IntRange(from = ANIM_INFO_SUCCESS, to = ANIM_INFO_WARNING) int animType, String message) {}



    public void setIconType(int type) {}


    public void setIconType(int type, int color) {}



}
