package pers.wtk.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wtk.pojo.po.Singer;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml"})
public class SingerDaoTest {

    private Logger logger = LoggerFactory.getLogger("testLogger");

    @Autowired
    private SingerDao singerDao;

    @Test
    public void testSearchSingerByName() throws Exception {
        List<Singer> singers = singerDao.searchSingerByName("之");
        for (Singer singer : singers) {
            logger.debug(singer.toString());
        }

    }

    @Test
    public void testGetSingersByMusicId() throws Exception {
        List<Singer> singersByMusicId = singerDao.getSingersByMusicId(1L);
        for (Singer singer : singersByMusicId) {
            logger.debug(singer.toString());
        }
    }

    @Test
    public void testGetSinger() throws Exception {
        Singer singer = singerDao.getSinger(1L);
        logger.debug(singer.toString());
    }

    @Test
    public void testGetSingerByName() throws Exception {
        Singer singer = singerDao.getSingerByName("胡歌");
        logger.debug(singer.toString());
    }

    @Test
    public void testInsertSinger() throws Exception {
        Singer singer = singerDao.getSingerByName("胡歌");
        singer.setSingerName("周杰伦");
        singerDao.insertSinger(singer);
    }

    @Test
    public void testDeleteSinger() throws Exception {
        singerDao.deleteSinger(6L);
    }

    @Test
    public void testUpdateSinger() throws Exception {
        Singer singer = singerDao.getSingerByName("");
        singer.setSingerName("周杰伦");
        singerDao.updateSinger(singer);
    }
}