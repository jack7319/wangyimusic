package com.bizideal.mn.controller;

import com.bizideal.mn.biz.SongService;
import com.bizideal.mn.entity.Song;
import com.github.pagehelper.PageInfo;
import org.apache.xpath.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author : liulq
 * @date: 创建时间: 2017/11/8 10:52
 * @version: 1.0
 * @Description:
 */
@Controller
@RequestMapping("/list")
public class SongController {

    @Autowired
    private SongService songService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getSongs() {
        return "redirect:/list/1";
    }

    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public String getSongs(@PathVariable("page") String page, Model model) {
        Integer page1 = null;
        try {
            page1 = Integer.valueOf(page);
        } catch (NumberFormatException e) {
            page1 = 1;
        }
        List<Song> songs = songService.getSongs(page1, 50);
//        Integer count = songService.getCount();
        model.addAttribute("songs", songs);
        model.addAttribute("page", page1);
//        model.addAttribute("count", count);
        model.addAttribute("size", songs.size());
        return "songs";
    }
}
