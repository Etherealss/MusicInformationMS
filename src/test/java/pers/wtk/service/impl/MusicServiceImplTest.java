package pers.wtk.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wtk.dao.MusicSingerDao;
import pers.wtk.pojo.po.Music;
import pers.wtk.pojo.vo.Page;
import pers.wtk.service.MusicService;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml"})
public class MusicServiceImplTest {

    private Logger logger = LoggerFactory.getLogger("testLogger");

    @Autowired
    MusicService musicService;

    @Test
    public void testGetPageMusic() throws Exception {
//        Page<Music> page = musicService.getPageMusic(1, 10, null, "周杰伦", null);
//        Page<Music> page = musicService.getPageMusic(1,10, 1L, null, null);
//        Page<Music> page = musicService.getPageMusic(1,10, null, null, "美丽的");
        Page<Music> page = musicService.getPageMusic(1,10, null, null, null);
        List<Music> dataList = page.getDataList();
        for (Music music : dataList) {
            logger.debug(music.toString());
        }
    }
}