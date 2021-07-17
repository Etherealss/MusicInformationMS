package pers.wtk.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wtk.pojo.po.Music;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml"})
public class MusicDaoTest {

    private Logger logger = LoggerFactory.getLogger("testLogger");

    @Qualifier("musicDao")
    @Autowired
    private MusicDao dao;

    @Test
    public void testGetMusicSize() throws Exception {
        int musicSize = dao.getMusicSize();
        logger.debug(String.valueOf(musicSize));
    }

    @Test
    public void testGetRangeMusic() throws Exception {
        List<Music> rangeMusic = dao.getRangeMusic(1, 3);
        for (Music music : rangeMusic) {
            logger.debug(music.getName());

        }
    }

    @Test
    public void testGetMusic() throws Exception {
        Music music = dao.getMusic(1L);
        logger.debug(music.toString());
    }

    @Test
    public void testUpdateMusic() throws Exception {
        Music music = dao.getMusic(1L);
        music.setPrice(123F);
        dao.updateMusic(music);
    }

    @Test
    public void testInsertMusic() throws Exception {
        Music music = dao.getMusic(1L);
        music.setName("test");
        dao.insertMusic(music);
    }

    @Test
    public void testDeleteMusic() throws Exception {
        dao.deleteMusic(3L);
    }

    @Test
    public void testSearchMusicBySingerName() throws Exception {
        List<Music> singers = dao.getMusicBySingerName("胡歌");
        for (Music singer : singers) {
            logger.debug(singers.toString());
        }
    }

    @Test
    public void testGetMusicSizeBySingerName() throws Exception {
        int count = dao.getMusicSizeBySingerName("周杰伦");
        logger.debug("{}",count);
    }

    @Test
    public void testGetMusicSizeByMusicName() throws Exception {
        int count = dao.getMusicSizeByMusicName("美丽");
        logger.debug("{}",count);
    }

    @Test
    public void testGetRangeMusicByMusicName() throws Exception {
        List<Music> musicL = dao.getRangeMusicByMusicName(0, 10, "美丽");
        for (Music music : musicL) {
            logger.debug(music.toString());
        }
    }

    @Test
    public void testGetRangeMusicBySingerName() throws Exception {
        List<Music> musicL = dao.getRangeMusicBySingerName(0, 10, "周杰伦");
        for (Music music : musicL) {
            logger.debug(music.toString());
        }
    }

    @Test
    public void testDeleteMultiMusic() throws Exception {
        boolean b = dao.deleteMultiMusic(new Long[]{23L, 24L});
        logger.debug("{}", b);
    }
}