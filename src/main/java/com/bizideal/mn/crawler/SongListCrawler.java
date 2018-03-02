package com.bizideal.mn.crawler;

import com.bizideal.mn.biz.PlayListService;
import com.bizideal.mn.config.SpringUtil;
import com.bizideal.mn.entity.PlayList;
import com.bizideal.mn.entity.Song;
import com.bizideal.mn.enums.Status;
import com.bizideal.mn.utils.EncryptTools;
import com.bizideal.mn.utils.HttpUtils;
import org.apache.commons.codec.binary.Hex;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 16:44
 * @version: 1.0
 * @Description:
 */
public class SongListCrawler {

    public static ExecutorService executorService = Executors.newFixedThreadPool(6);
    public static ExecutorService songExecutors = Executors.newFixedThreadPool(8);

    private static Logger logger = LoggerFactory.getLogger(SongListCrawler.class);

    private static Pattern pattern = Pattern.compile(".*\\?id=(\\d*)");
    private static Pattern commonsPattern = Pattern.compile(".*total\":(\\d*).*");

    private static String baseUrl = "http://music.163.com";

    // 处理歌曲列表和歌曲详情页面
    public static List<Song> dealSongList(Integer playListId, String url) throws IOException {
//        logger.info("start to request the songList , url :" + url);
        String requestUrl = baseUrl + url;
        List<Song> songs = new ArrayList<>();
        Jsoup.connect(requestUrl)
                .header("Referer", "http://music.163.com/")
                .header("Host", "music.163.com").get().select("ul[class=f-hide] a")
//                .stream().map(w -> w.text() + "-->" + w.attr("href"))
                .forEach((Element a) -> {
                    String url1 = a.attr("href"); // /song?id=509098885
                    String name = a.text();
                    String id = "0";
                    Matcher matcher = pattern.matcher(url1);
                    if (matcher.matches())
                        id = matcher.group(1);
                    Song song = new Song();
                    song.setId(Integer.valueOf(id));
                    song.setPlayListId(playListId);
                    song.setName(name);
                    song.setUrl(url1);
                    song.setAmountOfPlay(null);
                    song.setCreateTime(new Date());
                    song.setStatus(Status.UNCRAWL.name());
                    songs.add(song);
                });
        return songs;
    }

    public static Integer getCommonts(String songId) throws Exception {
        String params = "Cxl3kGBax1oK5/DYmixLo5heAsPMy+olIJ8nEsWF385zVnn3ysJjFl1xiuE8j8eD1ry9EmeMQcGhVCbRGGr/qsHj4Q/dAl0Q+jI0aanoRlIE1v+ubWmU0EkGpuegIzex";
        String encSecKey = "9116443c79675ff04eebe8e3454f4d5202d26aebd88f3ae6af4a6c045f5490ae4be1d1484f1c88b5532e219a4eeab8dc8ce520191800d5e3431da3868ecf8575d60b7dac2a0a1c9a7e8f866b5b2a382badb47edd305617739dec2c2d592698f040fbaa9120b64a824541573707eef7eba64fe4aa861451791574dc3fdef5c0ab";
        //评论获取
//        logger.info("http://music.163.com/weapi/v1/resource/comments/R_SO_4_" + songId + "/");
        Document document = Jsoup.connect("http://music.163.com/weapi/v1/resource/comments/R_SO_4_" + songId + "/").cookie("appver", "1.5.0.75771")
                .header("Referer", "http://music.163.com/").data("params", params).data("encSecKey", encSecKey)
                .ignoreContentType(true).post();
        String commonts = document.text();
        Matcher matcher = commonsPattern.matcher(commonts);
        if (matcher.matches()) {
            return Integer.valueOf(matcher.group(1));
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
//        final String string = HttpUtils.getAsString("http://music.163.com/playlist?id=832109");
//        final String string = HttpUtils.getAsString("http://music.163.com/song?id=22502857");
//        final Document html = Jsoup.parse(string);
//        System.out.println(html);
        System.out.println(getCommonts("191210"));
    }
}
