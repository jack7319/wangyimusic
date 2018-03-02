package com.bizideal.mn.crawler;

import com.bizideal.mn.entity.PlayList;
import com.bizideal.mn.entity.PlayListThread;
import com.bizideal.mn.enums.Status;
import com.bizideal.mn.mapper.PlayListMapper;
import com.bizideal.mn.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 10:41
 * @version: 1.0
 * @Description:
 */
public class PlayListCrawler {

    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    private static Logger logger = LoggerFactory.getLogger(PlayListCrawler.class);

    static Pattern pattern = Pattern.compile(".*\\?id=(\\d*)");

    public static void start() {
        String string = null;
        try {
            string = HttpUtils.getAsString("http://music.163.com/discover/playlist/?order=hot&cat=%E5%8D%8E%E8%AF%AD&limit=35&offset=140");
            Document html = Jsoup.parse(string);
            Elements select = html.select("a.s-fc1 ");
            for (int i = 1; i < select.size(); i++) {
                PlayListThread playListThread = new PlayListThread(select.get(i).text());
                executorService.execute(playListThread);
            }
        } catch (IOException e) {
            logger.error("初始化所有的种类出现错误", e);
        }
    }

    // limit表示每页35条数据、offset表示从第几条数据开始
    private static String baseUrl = "http://music.163.com/discover/playlist/?order=hot&cat=";
//    private static String baseUrl = "http://music.163.com/discover/playlist/?order=hot&cat=" + category + "&limit=35&offset=35";

    public static Integer getTotalPages(String category) throws IOException {
        String string = HttpUtils.getAsString(baseUrl + category + "&limit=35&offset=0"); // 各个分类的第一页
        final Document html = Jsoup.parse(string);
        Element element = html.select("a.zpgi").last();
        return Integer.valueOf(element.text());
    }

    public static List<PlayList> getPlayList(String category, int pageIndex) throws IOException {
        String url = baseUrl + category + "&limit=35&offset=" + (pageIndex - 1) * 35;
        logger.info("start to collect playlist from category=" + category + " page = " + pageIndex);
        String string = HttpUtils.getAsString(url);
        Document html = Jsoup.parse(string);
        Elements lis = html.select("ul#m-pl-container li");
        List<PlayList> playLists = new ArrayList<>();
        lis.forEach(li -> {
            Element a = li.select("a.tit.f-thide.s-fc0").first(); // 多个样式选择器
            String href = a.attr("href");
            PlayList playList = new PlayList();
            playList.setName(a.text());
            playList.setUrl(href);
            Matcher matcher = pattern.matcher(href);
            if (matcher.matches())
                playList.setId(Integer.valueOf(matcher.group(1)));
            Element span = li.select("span.nb").first();
            String count = span.text();
            if (count.contains("万")) {
                playList.setAmountOfPlay(Integer.valueOf(StringUtils.split(count, "万")[0]) * 10000);
            } else {
                playList.setAmountOfPlay(Integer.valueOf(count));
            }
            playList.setOwner(li.select("a.nm.nm-icn.f-thide.s-fc3").first().text());
            playList.setCategory(category);
            playList.setCreateTime(new Date());
            playList.setStatus(Status.UNCRAWL.name());
            playLists.add(playList);
        });
        return playLists;
    }

}
