package ru.vsu.cs.uvarov_d_p.oop.music_player;

import ru.vsu.cs.uvarov_d_p.oop.SimpleLinkedListException;

import java.util.Scanner;

public class MusicPlayerApp {
    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("МУЗЫКАЛЬНЫЙ ПЛЕЕР");
        System.out.println("=================");

        System.out.print("Введите имя файла с плейлистом: ");
        String filename = scanner.nextLine().trim();

        player.loadPlaylistFromFile(filename);

        if (player.getPlaylistSize() == 0) {
            System.out.println("Плейлист пуст. Завершение работы.");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\nГЛАВНОЕ МЕНЮ");
            System.out.println("1. ▶  Воспроизвести");
            System.out.println("2. ⏭️  Следующий трек");
            System.out.println("3. ⏮️  Предыдущий трек");
            System.out.println("4. 📋 Показать плейлист");
            System.out.println("5. 🔀 Перемешать плейлист");
            System.out.println("6. 💾 Сохранить плейлист");
            System.out.println("7. 🚪 Выйти");
            System.out.print("Выберите действие: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        player.play();
                        break;
                    case 2:
                        player.next();
                        break;
                    case 3:
                        player.previous();
                        break;
                    case 4:
                        player.showPlaylist();
                        break;
                    case 5:
                        player.shuffle();
                        break;
                    case 6:
                        System.out.print("Введите имя файла для сохранения: ");
                        String saveFile = scanner.nextLine();
                        player.savePlaylistToFile(saveFile);
                        break;
                    case 7:
                        running = false;
                        System.out.println("Выход...");
                        break;
                    default:
                        System.out.println("Неверный выбор!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число от 1 до 7!");
            } catch (SimpleLinkedListException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }
}