package pers.wtk.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wtk.common.enums.LyricLanguage;
import pers.wtk.pojo.po.Lyric;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-config.xml"})
public class LyricDaoTest {

    private Logger logger = LoggerFactory.getLogger("testLogger");
    @Autowired
    private LyricDao lyricDao;

    @Test
    public void testGetLyricByMusic() throws Exception {
        List<Lyric> lyricByMusic = lyricDao.getLyricByMusic(1L);
        logger.debug(String.valueOf(lyricByMusic.size()));
    }

    @Test
    public void testGetLyricByMusicAndLang() throws Exception {
        Lyric lyric = lyricDao.getLyricByMusicAndLang(1L, LyricLanguage.CHINESE);
        logger.debug(lyric.toString());
    }

    @Test
    public void testInsertLyric() throws Exception {
        Lyric lyric = new Lyric();
        lyric.setMusicId(1L);
        lyric.setLanguage(LyricLanguage.CHINESE);
        lyric.setLyricText("孙：梦中人熟悉的脸孔\n" +
                "你是我守候的温柔\n" +
                "就算泪水淹没天地我不会放手\n" +
                "每一刻孤独的承受\n" +
                "只因我曾许下承诺\n" +
                "合：你我之间熟悉的感动\n" +
                "爱就要苏醒");
        lyricDao.insertLyric(lyric);
    }

    @Test
    public void testDeleteLyric() throws Exception {
    }

}