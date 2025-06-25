
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class stocks120 {
    private static final String JDBC_URL_1 = "jdbc:mysql://localhost:3306/stock?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME_1 = "root";
    private static final String PASSWORD_1 = "Kaushal@2004";
    private static int lastFetchedId1 = 0;
    private static int lastFetchedId2 = 0;
    private static int lastFetchedId130 = 120;
    private static int lastFetchedId230 = 120;

    void getAverage() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new DatabaseFetchTask(), 0, 1, TimeUnit.SECONDS);
    }

    private static class DatabaseFetchTask implements Runnable {
        static double total120 = 0;
        static int n120 = 120;
        static double total30 = 0;
        static int n30 = 30;
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";

        @Override
        public void run() {

            try {
                Connection connection1 = DriverManager.getConnection(JDBC_URL_1, USERNAME_1, PASSWORD_1);
                Statement statement1 = connection1.createStatement();
                while (n120 > 0) {
                    ResultSet resultSet = statement1.executeQuery(
                            "SELECT * FROM tatamotorbo WHERE id > " + lastFetchedId1 + " ORDER BY id ASC");

                    if (resultSet.next()) {
                        double openprice = resultSet.getDouble("open");

                        lastFetchedId1++;
                        total120 += openprice;
                    }
                    n120--;
                }
                ResultSet resultSet = statement1.executeQuery(
                        "SELECT * FROM tatamotorbo WHERE id > " + lastFetchedId1 + " ORDER BY id ASC");

                if (resultSet.next()) {
                    new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
                    double openprice = resultSet.getDouble("open");

                    lastFetchedId1++;
                    total120 += openprice;
                }

            } catch (SQLException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
            try {
                Connection connection2 = DriverManager.getConnection(JDBC_URL_1, USERNAME_1, PASSWORD_1);
                Statement statement2 = connection2.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(
                        "SELECT * FROM tatamotorbo WHERE id > " + lastFetchedId2 + " ORDER BY id ASC");

                if (resultSet2.next()) {
                    double preopenprice = resultSet2.getDouble("open");
                    lastFetchedId2++;
                    total120 -= preopenprice;
                    System.out.println("Average120: " + total120 / 120);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Connection connection3 = DriverManager.getConnection(JDBC_URL_1, USERNAME_1, PASSWORD_1);
                Statement statement3 = connection3.createStatement();
                while (n30 > 0) {
                    ResultSet resultSet3 = statement3.executeQuery(
                            "SELECT * FROM tatamotorbo WHERE id > " + lastFetchedId130 + " ORDER BY id ASC");

                    if (resultSet3.next()) {
                        double openprice30 = resultSet3.getDouble("open");

                        lastFetchedId130++;
                        total30 += openprice30;
                    }
                    n30--;
                }
                ResultSet resultSet3 = statement3.executeQuery(
                        "SELECT * FROM tatamotorbo WHERE id > " + lastFetchedId130 + " ORDER BY id ASC");

                if (resultSet3.next()) {
                    double openprice30 = resultSet3.getDouble("open");

                    lastFetchedId130++;
                    total30 += openprice30;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                Connection connection4 = DriverManager.getConnection(JDBC_URL_1, USERNAME_1, PASSWORD_1);
                Statement statement4 = connection4.createStatement();
                ResultSet resultSet4 = statement4.executeQuery(
                        "SELECT * FROM tatamotorbo WHERE id > " + lastFetchedId230 + " ORDER BY id ASC");

                if (resultSet4.next()) {
                    double preopenprice30 = resultSet4.getDouble("open");
                    lastFetchedId230++;
                    total30 -= preopenprice30;
                    System.out.println("Average30: " + total30 / 30);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (total30 / 30 > total120 / 120) {
                System.out.println(GREEN + "BUY" + RESET);
            } else if (total30 / 30 < total120 / 120) {
                System.out.println(RED + "SELL" + RESET);
            } else {
                System.out.println("HOLD");
            }
        }
    }

}