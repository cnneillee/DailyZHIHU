package com.neil.dailyzhihu.ui.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neil.dailyzhihu.R;

/**
 * 作者：Neil on 2017/6/1 01:05.
 * 邮箱：cn.neillee@gmail.com
 */

public class MenuItemBadge {
    public static void update(MenuItem menuItem, String badgeText, @DrawableRes int resId) {
        LinearLayout badgeLayout = (LinearLayout) menuItem.getActionView();
        if (badgeLayout != null) {
            TextView tvBadge = (TextView) badgeLayout.findViewById(R.id.menu_badge);
            ImageView ivBadgeIcon = (ImageView) badgeLayout.findViewById(R.id.menu_badge_icon);
            tvBadge.setText(badgeText);
            ivBadgeIcon.setImageResource(resId);
        }
    }

    public static void update(MenuItem menuItem, String badgeText, Drawable drawable) {
        LinearLayout badgeLayout = (LinearLayout) menuItem.getActionView();
        if (badgeLayout != null) {
            TextView tvBadge = (TextView) badgeLayout.findViewById(R.id.menu_badge);
            ImageView ivBadgeIcon = (ImageView) badgeLayout.findViewById(R.id.menu_badge_icon);
            tvBadge.setText(badgeText);
            ivBadgeIcon.setImageDrawable(drawable);
        }
    }

    public static void update(final MenuItem menuItem, String badgeText) {
        Drawable icon = menuItem.getIcon();
        LinearLayout badgeLayout = (LinearLayout) menuItem.getActionView();
        if (badgeLayout != null) {
            TextView tvBadge = (TextView) badgeLayout.findViewById(R.id.menu_badge);
            ImageView ivBadgeIcon = (ImageView) badgeLayout.findViewById(R.id.menu_badge_icon);
            tvBadge.setText(badgeText);
            if (icon != null) ivBadgeIcon.setImageDrawable(icon);
        }
    }
}
