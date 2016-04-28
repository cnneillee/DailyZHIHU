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
//public class StoryDBFactory {
//    private static final String LOG_TAG = StoryDBFactory.class.getSimpleName();
//    private static StoryDBdao sInstanceImg;
//
//    public static StoryDBdao getInstance(Context context) {
//        if (sInstanceImg == null) {
//            synchronized (StoryDBdao.class) {
//                if (sInstanceImg == null) {
//                    sInstanceImg = new StoryDBdao(context);
//                }
//            }
//        }
//        return sInstanceImg;
//    }
//
//    public static Story convertStoryContent2DBStory(StoryContent storyContent) {
//        if (storyContent == null) {
//            return null;
//        }
//        String shareUrl = storyContent.getShare_url();
//        String body = storyContent.getBody();
//        String title = storyContent.getTitle();
//        String desc = null;
//        String author = null;
//        if (body != null) {
//            desc = getDescFromBody(body);
//            author = getAuthorFromBody(body);
//        }
//        String gaPrefix = storyContent.getGa_prefix();
//        String storyId = storyContent.getId() + "";
//        String imageUrl = storyContent.getImage();
//        String imageSource = storyContent.getImage_source();
//        String type = storyContent.getType() + "";
//        String currentMillies = System.currentTimeMillis() + "";
//        Story story = new Story(storyId, title, shareUrl, imageUrl, null, imageSource, body, type, gaPrefix, currentMillies);
//        story.setAuthor(author);
//        story.setDesc(desc);
//        return story;
//    }
//
//    public static StoryContent convertDBStory2StoryContent(Story story) {
//        if (story == null) {
//            return null;
//        }
//        String shareUrl = story.getShareUrl();
//        String body = story.getBody();
//        String title = story.getTitle();
//        String gaPrefix = story.getGaPrefix();
//        String storyId = story.getStoryId() + "";
//        String imageUrl = story.getImgUrl();
//        String imageSource = story.getImgSrc();
//        String type = story.getType() + "";
//        String currentMillies = System.currentTimeMillis() + "";
//        StoryContent storyContent = new StoryContent(null, Integer.valueOf(storyId), Integer.valueOf(type), gaPrefix, shareUrl, imageUrl, imageSource, title, body);
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
