package advisor;

import java.io.IOException;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;
    private boolean isBrowsingMode = false;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() throws IOException, InterruptedException {
        mainLoop:
        while (true) {
            String[] cmd = scanner.nextLine().split(" ");

            if (!SpotifyAPI.isAuthorized() && !(cmd[0].equals("exit") || cmd[0].equals("auth"))) {
                System.out.println(Prompt.PLEASE_AUTHORIZE);
                continue;
            }

            switch (cmd[0]) {
                case "exit":
                    if (isBrowsingMode) {
                        isBrowsingMode = false;
                        break;
                    }
                    break mainLoop;

                case "new":
                    Controller.setItems(SpotifyAPI.getNewReleases());
                    System.out.println(Controller.getItems(""));
                    break;

                case "featured":
                    Controller.setItems(SpotifyAPI.getFeaturedPlaylists());
                    System.out.println(Controller.getItems(""));
                    break;

                case "categories":
                    Controller.setItems(SpotifyAPI.getCategories());
                    System.out.println(Controller.getItems(""));
                    break;

                case "playlists":
                    StringBuilder name = new StringBuilder();
                    for (int i = 1; i < cmd.length; i++) {
                        name.append(cmd[i]).append(" ");
                    }
                    Controller.setItems(SpotifyAPI.getPlaylists(name.toString().trim()));
                    System.out.println(Controller.getItems(""));
                    break;

                case "next":
                    System.out.println(Controller.getItems("next"));
                    break;

                case "prev":
                    System.out.println(Controller.getItems("prev"));
                    break;

                case "auth":
                    SpotifyAPI.runServer();
                    System.out.println(SpotifyAPI.getAccessToken() + Prompt.SUCCESS);
                    break;

                default:
                    System.out.println(Prompt.INVALID_INPUT);
            }

            if (!cmd[0].equals("exit")) {
                isBrowsingMode = true;
            }
        }
        System.out.println("Goodbye!");
    }
}