
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MutualFund {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/stock?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Kaushal@2004";
    private static String IndexTable = "NIFTY50";
    private static int lastFetchedId = 0;
    private double currentPrice = 0;
    private double previousPrice = 0;
    private double investment = 0;
    private int updateCount = 0;
    private double invest = 0;

    public void SetMutualInvest(double investment) {
        this.investment = investment;
        this.invest = investment;
    }

    @SuppressWarnings("static-access")
    public void setTable(String table) {
        this.IndexTable = table;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(double previousPrice) {
        this.previousPrice = previousPrice;
    }

    double PerChange() {
        double previousPrice = getPreviousPrice();
        if (previousPrice == 0) {
            return 0;
        }
        double change = getCurrentPrice() - previousPrice;
        return (change / previousPrice) * 100;
    }

    void getprice() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(new DatabaseFetchTask(this), 0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        MutualFund mutualFund = new MutualFund();
        mutualFund.getprice();
    }

    private static class DatabaseFetchTask implements Runnable {
        private final MutualFund mutualFund;

        public DatabaseFetchTask(MutualFund mutualFund) {
            this.mutualFund = mutualFund;
        }

        @Override
        public void run() {
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM " + IndexTable + " WHERE id > "
                            + lastFetchedId + " ORDER BY id ASC LIMIT 1")) {

                if (resultSet.next()) {
                    mutualFund.setPreviousPrice(mutualFund.getCurrentPrice());
                    int id = resultSet.getInt("id");
                    double priceOpen = resultSet.getDouble("open");
                    mutualFund.setCurrentPrice(priceOpen);
                    lastFetchedId = id;

                    double changePercentage = mutualFund.PerChange();
                    mutualFund.investment += (Math.round(mutualFund.investment) * changePercentage / 100);
                    System.out.printf("Updated Investment: %.3f%n", mutualFund.investment);

                    mutualFund.updateCount++;
                    if (mutualFund.updateCount % 30 == 0) {
                        System.out.println("valuation after " + mutualFund.updateCount / 30 + " months: "
                                + (mutualFund.investment + mutualFund.invest));
                        mutualFund.investment += mutualFund.invest;
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}