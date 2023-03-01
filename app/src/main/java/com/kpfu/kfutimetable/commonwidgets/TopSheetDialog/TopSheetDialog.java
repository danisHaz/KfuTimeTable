package com.kpfu.kfutimetable.commonwidgets.TopSheetDialog;

/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.google.android.material.R;
import com.kpfu.kfutimetable.utils.User;
import com.kpfu.kfutimetable.utils.UserSession;

import io.getstream.avatarview.AvatarView;
import kotlin.Unit;

/**
 * Base class for {@link android.app.Dialog}s styled as a bottom sheet.
 */
public class TopSheetDialog extends AppCompatDialog {

    private TopSheetBehavior<FrameLayout> behavior;

    private ConstraintLayout container;

    private DialogLifecycleOwner lifecycleOwner;

    boolean dismissWithAnimation = true;

    boolean cancelable = true;
    private boolean canceledOnTouchOutside = true;
    private boolean canceledOnTouchOutsideSet;

    public TopSheetDialog(@NonNull Context context) {
        this(context, 0);
    }

    public TopSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, getThemeResId(context, theme));
        // We hide the title bar for any style configuration. Otherwise, there will be a gap
        // above the bottom sheet when it is expanded.
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        lifecycleOwner = new DialogLifecycleOwner();
    }

    protected TopSheetDialog(
            @NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.cancelable = cancelable;
        lifecycleOwner = new DialogLifecycleOwner();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(wrapInBottomSheet(layoutResId, null, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(wrapInBottomSheet(0, view, params));
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        if (this.cancelable != cancelable) {
            this.cancelable = cancelable;
            if (behavior != null) {
                behavior.setHideable(cancelable);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifecycleOwner.onStop();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        lifecycleOwner.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleOwner.onStart();
        if (behavior != null) {
            behavior.setState(TopSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void cancel() {
        TopSheetBehavior<FrameLayout> behavior = getBehavior();

        if (!dismissWithAnimation || behavior.getState() == TopSheetBehavior.STATE_HIDDEN) {
            super.cancel();
        } else {
            behavior.setState(TopSheetBehavior.STATE_HIDDEN);
        }
    }

    public void cancelWithAction(IAction action) {
        setOnCancelListener((dialogInterface) -> {
            action.performAction();
            setOnCancelListener((dialogInterface1) -> {});
        });
        cancel();
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !cancelable) {
            cancelable = true;
        }
        canceledOnTouchOutside = cancel;
        canceledOnTouchOutsideSet = true;
    }

    @NonNull
    public TopSheetBehavior<FrameLayout> getBehavior() {
        if (behavior == null) {
            // The content hasn't been set, so the behavior doesn't exist yet. Let's create it.
            ensureContainerAndBehavior();
        }
        return behavior;
    }

    /**
     * Set to perform the swipe down animation when dismissing instead of the window animation for the
     * dialog.
     *
     * @param dismissWithAnimation True if swipe down animation should be used when dismissing.
     */
    public void setDismissWithAnimation(boolean dismissWithAnimation) {
        this.dismissWithAnimation = dismissWithAnimation;
    }

    /**
     * Returns if dismissing will perform the swipe down animation on the bottom sheet, rather than
     * the window animation for the dialog.
     */
    public boolean getDismissWithAnimation() {
        return dismissWithAnimation;
    }

    /**
     * Creates the container layout which must exist to find the behavior
     */
    private ConstraintLayout ensureContainerAndBehavior() {
        if (container == null) {
            container =
                    (ConstraintLayout) View.inflate(getContext(), com.kpfu.kfutimetable.R.layout.layout_top_sheet_dialog, null);

            FrameLayout bottomSheet = (FrameLayout) container.findViewById(R.id.design_bottom_sheet);
            behavior = TopSheetBehavior.from(bottomSheet);
            behavior.addTopSheetCallback(topSheetCallback);
            behavior.setHideable(cancelable);
        }
        return container;
    }

    public FrameLayout getLayout() {
        FrameLayout bottomSheet = (FrameLayout) container.findViewById(R.id.design_bottom_sheet);
        return bottomSheet;
    }

    private View wrapInBottomSheet(
            int layoutResId, @Nullable View view, @Nullable ViewGroup.LayoutParams params) {
        ensureContainerAndBehavior();
        CoordinatorLayout coordinator = (CoordinatorLayout) container.findViewById(R.id.coordinator);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }

        FrameLayout bottomSheet = (FrameLayout) container.findViewById(R.id.design_bottom_sheet);
        bottomSheet.removeAllViews();
        if (params == null) {
            bottomSheet.addView(view);
        } else {
            bottomSheet.addView(view, params);
        }
        // We treat the CoordinatorLayout as outside the dialog though it is technically inside
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelable && isShowing() && shouldWindowCloseOnTouchOutside()) {
                    cancel();
                }
            }
        };
        container
                .findViewById(com.kpfu.kfutimetable.R.id.menu)
                .setOnClickListener(listener);
        coordinator
                .findViewById(R.id.touch_outside)
                .setOnClickListener(listener);

        ConstraintLayout toolbarSkeleton = (ConstraintLayout) container.findViewById(
                com.kpfu.kfutimetable.R.id.toolbar_skeleton
        );
        TextView userNameField = (TextView) toolbarSkeleton.findViewById(
                com.kpfu.kfutimetable.R.id.name
        );
        TextView userSurnameField = (TextView) toolbarSkeleton.findViewById(
                com.kpfu.kfutimetable.R.id.surname
        );
        AvatarView avatarView = (AvatarView) toolbarSkeleton.findViewById(
                com.kpfu.kfutimetable.R.id.avatar
        );
        UserSession.INSTANCE.subscribeToUserUpdates(lifecycleOwner, (User user) -> {
            if (user != null) {
                userNameField.setText(user.getName());
                userSurnameField.setText(user.getSurname());
                Log.e("kek", user.getUserProfilePhotoUri().toString());
                avatarView.setImageURI(user.getUserProfilePhotoUri());
            }
            return Unit.INSTANCE;
        });
        // Handle accessibility events
        ViewCompat.setAccessibilityDelegate(
                bottomSheet,
                new AccessibilityDelegateCompat() {
                    @Override
                    public void onInitializeAccessibilityNodeInfo(
                            @NonNull View host, @NonNull AccessibilityNodeInfoCompat info) {
                        super.onInitializeAccessibilityNodeInfo(host, info);
                        if (cancelable) {
                            info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS);
                            info.setDismissable(true);
                        } else {
                            info.setDismissable(false);
                        }
                    }

                    @Override
                    public boolean performAccessibilityAction(View host, int action, Bundle args) {
                        if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS && cancelable) {
                            cancel();
                            return true;
                        }
                        return super.performAccessibilityAction(host, action, args);
                    }
                });
        bottomSheet.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        // Consume the event and prevent it from falling through
                        return true;
                    }
                });
        return container;
    }

    boolean shouldWindowCloseOnTouchOutside() {
        if (!canceledOnTouchOutsideSet) {
            TypedArray a =
                    getContext()
                            .obtainStyledAttributes(new int[]{android.R.attr.windowCloseOnTouchOutside});
            canceledOnTouchOutside = a.getBoolean(0, true);
            a.recycle();
            canceledOnTouchOutsideSet = true;
        }
        return canceledOnTouchOutside;
    }

    private static int getThemeResId(@NonNull Context context, int themeId) {
        if (themeId == 0) {
            // If the provided theme is 0, then retrieve the dialogTheme from our theme
            TypedValue outValue = new TypedValue();
            if (context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, outValue, true)) {
                themeId = outValue.resourceId;
            } else {
                // bottomSheetDialogTheme is not provided; we default to our light theme
                themeId = R.style.Theme_Design_Light_BottomSheetDialog;
            }
        }
        return themeId;
    }

    void removeDefaultCallback() {
        behavior.removeTopSheetCallback(topSheetCallback);
    }

    @NonNull
    private final TopSheetBehavior.SheetCallback topSheetCallback =
            new TopSheetBehavior.SheetCallback() {
                @Override
                public void onStateChanged(
                        @NonNull View topSheet, @TopSheetBehavior.State int newState) {
                    if (newState == TopSheetBehavior.STATE_HIDDEN) {
                        cancel();
                    }
                }

                @Override
                public void onSlide(@NonNull View topSheet, float slideOffset) {
                }
            };

    // this is made for cancelWithAction
    public interface IAction {
        void performAction();
    }

    private static final class DialogLifecycleOwner implements LifecycleOwner {

        private LifecycleRegistry registry = new LifecycleRegistry(this);

        public DialogLifecycleOwner() {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() { return registry; }

        public void onStart() { registry.handleLifecycleEvent(Lifecycle.Event.ON_START); }
        public void onStop() { registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP); }
        public void onDestroy() { registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY); }

    }
}