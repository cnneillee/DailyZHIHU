package cn.neillee.dailyzhijiu.utils.img;

import cn.neillee.dailyzhijiu.R;

/**
 * 图片加载参数
 */
public class DisplayOption {
    /**
     * 无图模式
     */
    public boolean noPicMode;
    /**
     * 加载中的资源id
     */
    public int loadingResId;
    /**
     * 加载失败的资源id
     */
    public int loadErrorResId;

    private DisplayOption(Builder builder) {
        this.noPicMode = builder.noPicMode;
        this.loadingResId = builder.loadingResId;
        this.loadErrorResId = builder.loadErrorResId;
    }

    public static class Builder {
        private boolean noPicMode = false;
        private int loadingResId = R.drawable.img_loading_default;
        private int loadErrorResId = R.drawable.img_loading_error;

        public Builder setNoPicMode(boolean noPicMode) {
            this.noPicMode = noPicMode;
            return this;
        }

        public Builder setLoadingResId(int loadingResId) {
            this.loadingResId = loadingResId;
            return this;
        }

        public Builder setLoadErrorResId(int loadErrorResId) {
            this.loadErrorResId = loadErrorResId;
            return this;
        }

        public DisplayOption build() {
            return new DisplayOption(this);
        }
    }
}