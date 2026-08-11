package songlist;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        SongList playList = new SongList();
        // FILL IN CODE: call all methods to test them
        // Also use iterators to traverse the list by title and by score
        playList.loadSongs("src/main/resources/songs.csv");
        System.out.println(playList.containsSong("Rolling in the Deep", "Adele"));
        System.out.println(playList.containsSong("Houdini", "Eminem"));
        System.out.println(playList.findSongsWithinScoreRange(4, 5));
        System.out.println(playList.findBestKSongs(5));
        System.out.println(playList.findWorstKSongs(3));
        
        Iterator<Song> titleIterator = playList.iteratorByTitle();
        while (titleIterator.hasNext()) {
            System.out.println(titleIterator.next());
        }
        Iterator<Song> scoreIterator = playList.iteratorByScore();
        while (scoreIterator.hasNext()) {
            System.out.println(scoreIterator.next());
        }
        System.out.println(playList.mergeWith(playList));
    }
}
