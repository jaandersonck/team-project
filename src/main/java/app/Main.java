package app;

import javax.swing.*;

public class Main {
    /**
     * Builds and launches the application.
     * @param args unused command line arguments
     */
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                // add views and use cases here
                .addLoginView()
                .addLoginUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
