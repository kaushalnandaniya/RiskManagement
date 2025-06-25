
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class arbdb {
    private static final String JDBC_URL_1 = "jdbc:mysql://localhost:3306/stock?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME_1 = "root";
    private static final String PASSWORD_1 = "Kaushal@2004";
    private static final String JDBC_URL_2 = "jdbc:mysql://localhost:3306/stock?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME_2 = "root";
    private static final String PASSWORD_2 = "Kaushal@2004";
    private static int lastFetchedId1 = 0;
    private static int lastFetchedId2 = 0;

    void getprice() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new DatabaseFetchTask(), 0, 1, TimeUnit.SECONDS);
    }

    void setLotSize(double n) {
        arbdb.DatabaseFetchTask.LotMultiplier = n * 50;

    }

    private static class DatabaseFetchTask implements Runnable {
        private double profit = 0.0;
        private ScheduledExecutorService scheduler;
        static double LotMultiplier;

        @Override
        public void run() {
            try (Connection connection1 = DriverManager.getConnection(JDBC_URL_1, USERNAME_1, PASSWORD_1);
                    Statement statement1 = connection1.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery(
                            "SELECT * FROM tatamotorbo WHERE id > " + lastFetchedId1 + " ORDER BY id ASC LIMIT 1")) {

                if (resultSet1.next()) {
                    new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
                    int id1 = resultSet1.getInt("id");
                    double price_open1 = resultSet1.getDouble("open");
                    double price_close1 = resultSet1.getDouble("close");
                    lastFetchedId1 = id1;
                    System.out.println("open: " + price_open1 + ", close: " + price_close1);

                    try (Connection connection2 = DriverManager.getConnection(JDBC_URL_2, USERNAME_2, PASSWORD_2);
                            Statement statement2 = connection2.createStatement();
                            ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM tatamotornse WHERE id > "
                                    + lastFetchedId2 + " ORDER BY id ASC LIMIT 1")) {

                        if (resultSet2.next()) {
                            int id2 = resultSet2.getInt("id");
                            double price_open2 = resultSet2.getDouble("open");
                            double price_close2 = resultSet2.getDouble("close");
                            lastFetchedId2 = id2;
                            System.out.println("open: " + price_open2 + ", close: " + price_close2);

                            double difference = price_open1 - price_open2;
                            if (difference > 0) {
                                profit += difference;
                            } else if (difference < 0) {
                                profit -= difference;
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    scheduler.shutdown();
                    return;
                }
            } catch (SQLException | InterruptedException | IOException e) {
                e.printStackTrace();
            }

            System.out.println("Profit: " + profit * LotMultiplier);
        }

        @SuppressWarnings("unused")
        public void setScheduler(ScheduledExecutorService scheduler) {
            this.scheduler = scheduler;
        }
    }

}