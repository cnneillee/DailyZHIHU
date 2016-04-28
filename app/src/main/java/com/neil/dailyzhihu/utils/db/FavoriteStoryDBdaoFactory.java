//package com.neil.dailyzhihu.utils.db;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.neil.dailyzhihu.bean.orignallayer.StoryContent;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
///**
// * Created by Neil on 2016/4/23.
// */
//public class FavoriteStoryDBdaoFactory {
//    private static final String LOG_TAG = FavoriteStoryDBdaoFactory.class.getSimpleName();
//    private static FavoriteStoryDBdao sInstanceImg;
//
//    public static FavoriteStoryDBdao getInstance(Context context) {
//        if (sInstanceImg == null) {
//            synchronized (FavoriteStoryDBdao.class) {
//                if (sInstanceImg == null) {
//                    sInstanceImg = new FavoriteStoryDBdao(context);
//                }
//            }
//        }
//        return sInstanceImg;
//    }
//
//    public static FavoriteStory convertStoryContent2DBStory(StoryContent story) {
//        if (story == null) {
//            return null;
//        }
//        String shareUrl = story.getShare_url();
//        String body = story.getBody();
//        String title = story.getTitle();
//        String desc = getDescFromBody(body);
//        String author = getAuthorFromBody(body);
//        String gaPrefix = story.getGa_prefix();
//        String storyId = story.getId() + "";
//        String imageUrl = story.getImage();
//        String imageSource = story.getImage_source();
//        String type = story.getType() + "";
//        String currentMillies = System.currentTimeMillis() + "";
//        FavoriteStory favoriteStory = new FavoriteStory(storyId, null, title, desc, author, shareUrl, imageUrl, imageSource, body, null, currentMillies, null, type, gaPrefix, null);
//        return favoriteStory;
//    }
//
//    public static StoryContent convertStoryContent2DBStory(FavoriteStory story) {
//        if (story == null) {
//            return null;
//        }
//        String shareUrl = story.getShareUrl();
//        String body = story.getBody();
//        String title = story.getTitle();
//        String desc = getDescFromBody(body);
//        String author = getAuthorFromBody(body);
//        String gaPrefix = story.getGaPrefix();
//        String storyId = story.getStoryId() + "";
//        String imageUrl = story.getImgUrl();
//        String imagePath = story.getImgPath();
//        String imageSource = story.getImgsrc();
//        String type = story.getType() + "";
//        String currentMillies = System.currentTimeMillis() + "";
//
//        StoryContent storyContent = new StoryContent(imagePath, Integer.valueOf(storyId), Integer.valueOf(type), gaPrefix, shareUrl, imageUrl, imageSource, title, body);
//        return storyContent;
//    }
//
//    public static String getDescFromBody(String body) {
//        Document bodyDoc = Jsoup.parse(body);
//        Elements contentEles = bodyDoc.getElementsByClass("content");
//        if (contentEles != null && contentEles.size() > 0) {
//            Elements pElements = contentEles.get(0).getElementsByTag("p");
//            if (pElements != null && pElements.size() > 0) {
//                String desc = pElements.get(0).text();
//                Log.e(LOG_TAG, "desc: " + desc);
//                return desc;
//            }
//        }
//        return "";
//    }
//
//    public static String getAuthorFromBody(String body) {
//        Document bodyDoc = Jsoup.parse(body);
//        Elements authorEles = bodyDoc.getElementsByClass("author");
//        if (authorEles != null && authorEles.size() > 0) {
//            String author = authorEles.get(0).text();
//            Log.e(LOG_TAG, "author: " + authorEles);
//            return author;
//        }
//        return "";
//    }
//}
