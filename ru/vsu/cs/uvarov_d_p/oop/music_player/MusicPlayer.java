package ru.vsu.cs.uvarov_d_p.oop.music_player;

import ru.vsu.cs.uvarov_d_p.oop.CircularLinkedList;
import ru.vsu.cs.uvarov_d_p.oop.DoublyLinkedList;
import ru.vsu.cs.uvarov_d_p.oop.SimpleLinkedListException;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    private CircularLinkedList<Song> playlist;
    private DoublyLinkedList<Song> history;
    private Song currentSong;

    public MusicPlayer() {
        playlist = new CircularLinkedList<>();
        history = new DoublyLinkedList<>();
    }

    public void addSong(String title, String artist) {
        Song song = new Song(title, artist);
        playlist.addLast(song);
        System.out.println("🎵 Добавлен трек: " + song);
    }

    public void play() throws SimpleLinkedListException {
        if (playlist.isEmpty()) {
            System.out.println("❌ Плейлист пуст!");
            return;
        }

        if (currentSong == null) {
            currentSong = playlist.getFirst();
        }

        System.out.println("\n🎶 Сейчас играет: " + currentSong);
    }

    public void loadPlaylistFromFile(String filename) {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(filename);
            List<String> lines = java.nio.file.Files.readAllLines(path);

            int loadedSongs = 0;
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    try {
                        String artist = parts[0].trim();
                        String title = parts[1].trim();

                        Song song = new Song(title, artist);
                        playlist.addLast(song);
                        loadedSongs++;

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.err.println("Неверный формат строки: " + line);
                }
            }

            System.out.println("Загружено " + loadedSongs + " треков из файла: " + filename);

        } catch (java.io.IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    public void savePlaylistToFile(String filename) {
        try {
            List<String> lines = new ArrayList<>();

            for (Song song : playlist) {
                String line = String.format("%s - %s",
                        song.getArtist(), song.getTitle());
                lines.add(line);
            }

            java.nio.file.Files.write(java.nio.file.Paths.get(filename), lines);
            System.out.println("Плейлист сохранен в файл: " + filename);

        } catch (java.io.IOException e) {
            System.err.println("Ошибка сохранения файла: " + e.getMessage());
        }
    }

    public void next() throws SimpleLinkedListException {
        if (currentSong != null) {
            history.addLast(currentSong);
        }
        boolean found = false;
        for (Song song : playlist.circularIterator()) {
            if (found) {
                currentSong = song;
                break;
            }
            if (song.equals(currentSong)) {
                found = true;
            }
        }

        play();
    }

    public void previous() throws SimpleLinkedListException {
        if (!history.isEmpty()) {
            currentSong = history.removeLast();
            play();
        } else {
            System.out.println("❌ История прослушивания пуста");
        }
    }

    public void showPlaylist() {
        System.out.println("\n📀 Плейлист (" + playlist.size() + " треков):");
        int i = 1;
        for (Song song : playlist) {
            String marker = song.equals(currentSong) ? "▶ " : "  ";
            System.out.println(marker + i++ + ". " + song);
        }
    }

    public void shuffle() throws SimpleLinkedListException {
        if (playlist.size() <= 1) {
            return;
        }

        System.out.println("🔀 Перемешиваем плейлист ...");

        int size = playlist.size();

        for (int i = 0; i < size * 2; i++) {
            int fromIndex = (int) (Math.random() * size);
            int toIndex = (int) (Math.random() * size);

            if (fromIndex != toIndex) {
                Song songToMove = playlist.get(fromIndex);
                playlist.remove(fromIndex);

                int newIndex = toIndex > fromIndex ? toIndex - 1 : toIndex;
                playlist.insert(newIndex, songToMove);
            }
        }

        System.out.println("✅ Плейлист перемешан!");
    }

    public int getPlaylistSize() {
        return playlist.size();
    }
}