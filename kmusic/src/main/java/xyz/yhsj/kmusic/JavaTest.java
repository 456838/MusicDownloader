package xyz.yhsj.kmusic;

import java.util.List;

import xyz.yhsj.kmusic.entity.MusicResp;
import xyz.yhsj.kmusic.entity.Song;
import xyz.yhsj.kmusic.impl.QQImpl;
import xyz.yhsj.kmusic.site.MusicSite;

public class JavaTest {

    public static void main(String args[]) {
        /**
         * You need to write INSTANCE
         */
        QQImpl.INSTANCE.getSongTop();
        /**
         *Same as Java's static methods
         */
        MusicResp<List<Song>> tops = KMusic.search("SongName", 1, 10, MusicSite.QQ);
        System.out.println(tops);
    }


}
