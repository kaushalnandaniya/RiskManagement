
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

abstract class term {

    class long_term {
    };

    class short_term {
    };

}

class long_term {

    void lonterm() {
        System.out.println("ENTER for current price:");
        Scanner stable = new Scanner(System.in);
        String st = stable.nextLine();
        String UpperCase = st.toUpperCase();
        db mydb = new db();
        mydb.setTable(UpperCase);
        mydb.getprice();
        stable.close();
    }

}

class IndexFund extends long_term {
    IndexFund() {
        System.out.println("List of Index fund:");
        System.out.println("NIFTY50");
        System.out.println("NIFTY BANK");
        System.out.println("NIFTY AUTO");
        System.out.println("NIFTY MIDCAP");
        System.out.println("NIFTY SMLCAP");
        System.out.println("");
    }

    void MutualIndex() {
        System.out.println("Enter any of the Index:");
        Scanner stable = new Scanner(System.in);
        String st = stable.nextLine();
        String UpperCase = st.toUpperCase();

        MutualFund mutind = new MutualFund();
        mutind.setTable(UpperCase);

        System.out.println("Enter the amount to invest");
        Scanner sinvestIndex = new Scanner(System.in);
        double investIndex = sinvestIndex.nextDouble();
        mutind.SetMutualInvest(investIndex);
        mutind.getprice();
        sinvestIndex.close();
        stable.close();

    }
}

class MutualFuc extends long_term {

    void MutualFu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the amount to invest:");

        double n = scanner.nextDouble();
        MutualFund mutualFun = new MutualFund();
        mutualFun.SetMutualInvest(n);

        scanner.close();
        mutualFun.getprice();
    }
}

class short_term {
    short_term() {
        System.out.println("ENTER:");
        System.out.println("1 : For the current value");
        System.out.println("2 : For the Arbitrage Opportunities Trading");
    }

    void ArbitrageIndex() {
        arbdb arbdb = new arbdb();
        System.out.println("Enter the Lot Size :");
        Scanner slot = new Scanner(System.in);
        int n = slot.nextInt();
        arbdb.setLotSize((double) n);
        slot.close();
        arbdb.getprice();
    }

}

public class App {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/stock?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Kaushal@2004";
    private static int lastFetchedId = 0;

    void getprice() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(new DatabaseFetchTask(), 0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL JDBC driver");
            e.printStackTrace();
            return;
        }

        Scanner s = new Scanner(System.in);
        int n;

        do {
            System.out.println("ENTER:");
            System.out.println("1 : For the long term investment");
            System.out.println("2 : For the short term investment");

            n = s.nextInt();

            switch (n) {
                case 1:
                    System.out.println("categories for Long term Investment");
                    System.out.println("Enter");
                    System.out.println("1 : For Index funds");
                    System.out.println("2 : For Mutual funds");
                    System.out.println("3 : For Stocks");
                    Scanner scat = new Scanner(System.in);
                    int selecter;

                    do {

                        selecter = scat.nextInt();

                        switch (selecter) {
                            case 1:
                                System.out.println("Enter:");
                                System.out.println("1 : For current price");
                                System.out.println("2 : to invest");
                                Scanner sindex = new Scanner(System.in);
                                int nindex;
                                do {
                                    nindex = sindex.nextInt();

                                    switch (nindex) {
                                        case 1:
                                            long_term ind = new IndexFund();
                                            ind.lonterm();
                                            break;
                                        case 2:
                                            IndexFund index = new IndexFund();
                                            index.MutualIndex();
                                            break;
                                        default:
                                            System.out.println("Invalid choice.");
                                            System.out.println(" ");
                                            System.out.println("Enter correct number:");
                                    }
                                } while (nindex != 1 && nindex != 2);

                                break;
                            case 2:
                                MutualFuc mut = new MutualFuc();
                                mut.MutualFu();

                                break;
                            case 3:
                                stocks120 stocks120 = new stocks120();
                                stocks120.getAverage();
                                break;
                            default:
                                System.out.println("Invalid choice.");
                                System.out.println("Enter correct number:");
                        }
                    } while (selecter != 1 && selecter != 2);

                    break;
                case 2:
                    short_term shor = new short_term();

                    do {
                        n = s.nextInt();

                        switch (n) {
                            case 1:
                                App currtemp = new App();
                                currtemp.getprice();
                                break;
                            case 2:
                                shor.ArbitrageIndex();
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    } while (n != 1 && n != 2);

                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }

        } while (n != 1 && n != 2);

        s.close();

    }

    private static class DatabaseFetchTask implements Runnable {

        @Override
        public void run() {
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                    Statement statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery(
                            "SELECT * FROM NIFTY50 WHERE id > " + lastFetchedId + " ORDER BY id ASC LIMIT 1")) {

                if (resultSet.next()) {
                    new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
                    int id = resultSet.getInt("id");
                    double price_open = resultSet.getDouble("open");
                    double price_close = resultSet.getDouble("close");

                    lastFetchedId = id;
                    System.out.println("open: " + price_open + ", close: " + price_close);

                }

            } catch (SQLException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}