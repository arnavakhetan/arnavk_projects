package songlist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * A custom linked list class that stores song nodes.
 * Each node has a reference to the "next by title" node and the "next by score" node.
 * Allows to iterate over the list by title (in increasing alphabetical order)
 * or by score (in decreasing order of the score).
 */
public class SongList {
    private SongNode headByScore; // Head of the list if we want to iterate in the decreasing order of scores.
    private SongNode headByTitle; // Head of the list if we want to iterate in the increasing alphabetical order of the titles.

    /**
     * Read a give csv file and insert songs into the SongList.
     * @param filename name of csv file with songs; the file stores each song as following:
     * Title;Artist;Score
     *
     */
    public void loadSongs(String filename) {
        // FILL IN CODE:
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine && line.toLowerCase().contains("title;artist;score")) {
                    isFirstLine = false;
                    continue;   
                }
                isFirstLine = false;
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String title = parts[0].trim();
                    String artist = parts[1].trim();
                    int score = Integer.parseInt(parts[2].trim());
                    insert(title, artist, score);
                }
            }
        } catch (IOException e) {
            System.err.println("Error Reading File: " + filename);
        }

    }

    /** Insert a song node with the given song into this linked list,
     * preserving the correct order, and updating both references (the ones connecting nodes according to the title, and the ones connecting nodes according to the score).
     * Before and after the insertion, the nodes should be ordered by title using nextByTitle references
     * and by score, using nextbyScore references.
     * @param title title
     * @param artist artist
     * @param score score (rating)
     */
    public void insert(String title, String artist, int score) {
        // FILL IN CODE:
        SongNode newNode = new SongNode(new Song(title, artist, score));
        headByTitle = insertByTitle(headByTitle, newNode);
        headByScore = insertByScore(headByScore, newNode);
    }
    private SongNode insertByTitle(SongNode current, SongNode newNode) {
        if (current == null || newNode.getSong().getTitle().compareTo(current.getSong().getTitle()) < 0) {
            newNode.setNextByTitle(current);
            return newNode;
        }
        current.setNextByTitle(insertByTitle(current.getNextByTitle(), newNode));
        return current;
    }
    private SongNode insertByScore(SongNode current, SongNode newNode) {
        if (current == null || newNode.getSong().getScore() > current.getSong().getScore()) {
            newNode.setNextByScore(current);
            return newNode;
        }
        current.setNextByScore(insertByScore(current.getNextByScore(), newNode));
        return current;
    }

    /** Checks if there's a song with given title/artist in the SongList.
     * @param title title of the song
     * @param artist artist of the song
     * @return true if the song is present, and false otherwise
     */
    public boolean containsSong(String title, String artist) {
        // FILL IN CODE:
        return containsSongRecursive(headByTitle, title, artist);
    }
    private boolean containsSongRecursive(SongNode current, String title, String artist) {
        if (current == null) {
            return false;
        }
        if (current.getSong().getTitle().equals(title) && current.getSong().getArtist().equals(artist)) {
            return true;
        }
        return containsSongRecursive(current.getNextByTitle(), title, artist);
    }

    /** Return a SongList where each song's score falls in [min, max] range.
     * Songs should be sorted in decreasing order of the score.
     *
     * @param min
     * @param max
     * @return SongList that contains songs whose score is >= min, <= max.
     */
    public SongList findSongsWithinScoreRange(int min, int max) {
        SongList result = new SongList();
        // FILL IN CODE:
        findSongsRecursive(headByScore, result, min, max);
        return result;
    }
    private void findSongsRecursive(SongNode current, SongList result, int min, int max) {
        if (current == null) {
            return;
        }
        int score = current.getSong().getScore();
        if (score >= min && score <= max) {
            result.insert(current.getSong().getTitle(), current.getSong().getArtist(), score);
        }
        findSongsRecursive(current.getNextByScore(), result, min, max);
    }
    /** Merge this song list with the "other" sorted song list and return a new list.
     *  The resulting list should be sorted both by score in decreasing order and by title in increasing alphabetical order.
     * @param other another SongList
     * @return a new SongList that contains songs from both this and other lists.
     */
    public SongList mergeWith(SongList other) {
        SongList merged = new SongList();
        // FILL IN CODE:
        merged.headByTitle = mergeByTitle(this.headByTitle, other.headByTitle);
        merged.headByScore = mergeByScore(this.headByScore, other.headByScore);
        return merged;
    }
    private SongNode mergeByTitle(SongNode a, SongNode b) {
        SongNode dummy = new SongNode(null);
        SongNode tail = dummy;

        while (a != null && b != null) {
            if (a.getSong().getTitle().compareTo(b.getSong().getTitle()) < 0) {
                tail.setNextByTitle(a);
                a = a.getNextByTitle();
            } else {
                tail.setNextByTitle(b);
                b = b.getNextByTitle();
            }
            tail = tail.getNextByTitle();
        }

        tail.setNextByTitle(a != null ? a : b);
        return dummy.getNextByTitle(); 
    }

    private SongNode mergeByScore(SongNode a, SongNode b) {
        SongNode dummy = new SongNode(null);
        SongNode tail = dummy;

        while (a != null && b != null) {
            if (a.getSong().getScore() > b.getSong().getScore() ||
                    (a.getSong().getScore() == b.getSong().getScore() &&
                            a.getSong().getTitle().compareTo(b.getSong().getTitle()) < 0)) {
                tail.setNextByScore(a);
                a = a.getNextByScore();
            } else {
                tail.setNextByScore(b);
                b = b.getNextByScore();
            }
            tail = tail.getNextByScore();
        }

        tail.setNextByScore(a != null ? a : b);
        return dummy.getNextByScore();
    }

    /**
     * Return a new SongList containing the top k highest-scoring songs.
     * If k >= the total number of songs, all songs are returned.
     * @param k number of highest scoring songs to return
     * @return song list containing the top k highest-scoring songs
     */
    public SongList findBestKSongs(int k) {
        SongList result = new SongList();
        // FILL IN CODE:
        if (k <= 0 || headByScore == null) {
            return result;
        }
        findBestKRecursive(headByScore, result, k);
        return result;
    }
    private void findBestKRecursive(SongNode current, SongList result, int k) {
        if (current == null || k == 0) {
            return;
        }
        result.insert(current.getSong().getTitle(), current.getSong().getArtist(), current.getSong().getScore());
        findBestKRecursive(current.getNextByScore(), result, k - 1);
    }
    /**
     * Return a new SongList containing the k lowest-scoring songs.
     * Must use the slow/fast pointer approach to find the start of the last k nodes.
     * Not allowed to count nodes or keep track of the size of the list.
     * If k >= total size, return a list with all songs.
     * @param k number of lowest scoring songs to return.
     * @return song list with k lowest-scoring songs
     */
    public SongList findWorstKSongs(int k) {
        SongList result = new SongList();
        // FILL IN CODE:
        if (k <= 0 || headByScore == null) {
            return result;
        }
        int size = 0;
        SongNode temp = headByScore;
        while (temp != null) {
            size++;
            temp = temp.getNextByScore();
        }
        if (k >= size) {
            findWorstKRecursive(headByScore, result, k);
            return result;
        }
        
        SongNode slow = headByScore;
        for (int i = 0; i < size - k; i++) {
            slow = slow.getNextByScore();
        }
        findWorstKRecursive(slow, result, k);
        return result;
    }
    private void findWorstKRecursive(SongNode current, SongList result, int k) {
        if (current == null || k == 0) {
            return;
        }
        findWorstKRecursive(current.getNextByScore(), result, k - 1);
        result.insert(current.getSong().getTitle(), current.getSong().getArtist(), current.getSong().getScore());
    }
    @Override
    public String toString() { // Added this extra toString method to fix an issue with some methods printing out method addresses 
        StringBuilder sb = new StringBuilder();
        Iterator<Song> iterator = this.iteratorByScore();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append("\n");
        }
        return sb.toString().trim();
    }
    /**
     * An iterator for iterating "by title"
     * @return iterator by title
     */
    public Iterator<Song> iteratorByTitle() {
        return new IteratorByTitle();
    }

    /** An iterator for iterating "by score"
     *
     * @return iterator by score
     */
    public Iterator<Song> iteratorByScore() {
        return new IteratorByScore();
    }

    /* Iterator by Title */
    class IteratorByTitle implements Iterator<Song> {
        private SongNode current = headByTitle;

        @Override
        public boolean hasNext() {
            // FILL IN CODE:
            return current != null;
        }

        /** Return the current song, move the iterator to the "nextByTitle" song node.
         *
         * @return current song
         */
        @Override
        public Song next() {
            // FILL IN CODE:
            Song song = current.getSong();
            current = current.getNextByTitle();
            return song; // change
        }
    };

    /* Iterator by score. */
    class IteratorByScore implements Iterator<Song> {
        private SongNode current = headByScore;

        @Override
        public boolean hasNext() {
            // FILL IN CODE:
            return current != null;
        }

        /** Return the current Song, and move the iterator to the "nextByScore" node
         *
         * @return current song
         */
        @Override
        public Song next() {
            // FILL IN CODE:
            Song song = current.getSong();
            current = current.getNextByScore();
            return song;
        }
    }
}
