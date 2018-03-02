package org.springframework.boot;

import com.bizideal.mn.Application;
import com.bizideal.mn.biz.PlayListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MapperScan("com.bizideal.mn.mapper")
@SpringBootTest(classes = Application.class)
public class AppTest {

    @Autowired
    private PlayListService playListService;

    @Test
    public void save(){
//        PlayList playList = new PlayList();
//        playList.setId(1);
//        playListService.save(playList);
    }

}
