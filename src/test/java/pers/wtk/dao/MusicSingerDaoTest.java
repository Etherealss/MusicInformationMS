package pers.wtk.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wtk.pojo.po.Music;
import pers.wtk.pojo.po.Singer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml"})
public class MusicSingerDaoTest {

    private Logger logger = LoggerFactory.getLogger("testLogger");

    @Autowired
    private MusicSingerDao musicSingerDao;
    @Autowired
    private SingerDao singerDao;

    @Test
    public void testInsertSingers4Music() throws Exception {
        Music music = new Music();
        music.setId(3);
        List<Singer> singers = new ArrayList<>(2);
        singers.add(singerDao.getSinger(2L));
        singers.add(singerDao.getSinger(3L));
        music.setSingers(singers);
        musicSingerDao.insertSingers4Music(music.getId(), music.getSingers());
    }
}