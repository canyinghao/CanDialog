package com.canyinghao.candialog;

import android.view.KeyEvent;

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
public interface CanDialogInterface {


    public interface OnKeyListener {
        boolean onKey(CanBaseDialog dialog, int code, KeyEvent event);
    }

    public interface OnMultiChoiceClickListener {
        void onClick(CanBaseDialog dialog, int position, boolean flag);
    }

    public interface OnClickListener {
        void onClick(CanBaseDialog dialog, int checkItem,CharSequence text,boolean[] checkItems);
    }

    public interface OnShowListener {
        void onShow(CanBaseDialog dialog);
    }

    public interface OnDismissListener {
        void onDismiss(CanManagerDialog dialog);
    }




}
