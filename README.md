# Trading Risk Management System

A comprehensive Java-based trading and investment management system that provides real-time stock price monitoring, investment analysis, and arbitrage opportunities detection.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Technical Details](#technical-details)
- [Contributing](#contributing)

## 🎯 Overview

This Trading Risk Management System is designed to help investors and traders make informed decisions by providing:

- **Real-time stock price monitoring** with live updates every second
- **Long-term investment analysis** for index funds, mutual funds, and individual stocks
- **Short-term trading opportunities** including arbitrage detection
- **Technical analysis** using moving averages (30-day and 120-day)
- **Risk management tools** with profit/loss tracking

## ✨ Features

### Long-term Investment Options
1. **Index Funds**
   - Current price monitoring for major indices (NIFTY50, NIFTY BANK, NIFTY AUTO, etc.)
   - Investment simulation with monthly SIP (Systematic Investment Plan)
   - Real-time portfolio valuation

2. **Mutual Funds**
   - Investment amount tracking
   - Percentage change calculation
   - Portfolio value updates

3. **Individual Stocks**
   - 120-day and 30-day moving average analysis
   - Buy/Sell/Hold recommendations based on technical indicators
   - Real-time price monitoring

### Short-term Trading
1. **Current Price Monitoring**
   - Live stock price updates
   - Open and close price tracking

2. **Arbitrage Opportunities**
   - Price difference detection between BSE and NSE
   - Lot size-based profit calculation
   - Real-time arbitrage monitoring

## 🔧 System Requirements

- **Java**: JDK 8 or higher
- **MySQL**: 5.7 or higher
- **MySQL Connector/J**: For database connectivity
- **Operating System**: Windows, macOS, or Linux

## 📦 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd MC-122
   ```

2. **Install MySQL Connector/J**
   - Download MySQL Connector/J from the official MySQL website
   - Add the JAR file to your project's classpath

3. **Compile the Java files**
   ```bash
   javac -cp ".:mysql-connector-java.jar" *.java
   ```

## 🗄️ Database Setup

1. **Create MySQL database**
   ```sql
   CREATE DATABASE stock;
   USE stock;
   ```

2. **Import the provided SQL files**
   ```bash
   mysql -u root -p stock < NIFTY50.sql
   mysql -u root -p stock < TATAMOTORS.NS.sql
   mysql -u root -p stock < tableConvert.com_lyp40u.sql
   ```

3. **Update database credentials**
   - Open each Java file and update the database connection details:
     - `JDBC_URL`: Your MySQL connection string
     - `USERNAME`: Your MySQL username
     - `PASSWORD`: Your MySQL password

## 🚀 Usage

### Running the Application

```bash
java -cp ".:mysql-connector-java.jar" App
```

### Main Menu Options

1. **Long-term Investment**
   - Index Funds: Monitor and invest in major market indices
   - Mutual Funds: Track mutual fund investments
   - Stocks: Technical analysis with moving averages

2. **Short-term Investment**
   - Current Price: Real-time stock price monitoring
   - Arbitrage Trading: Detect price differences between exchanges

### Example Usage

```
ENTER:
1 : For the long term investment
2 : For the short term investment

Choose 1 for long-term investment:
categories for Long term Investment
Enter
1 : For Index funds
2 : For Mutual funds
3 : For Stocks
```

## 📁 Project Structure

```
MC-122/
├── App.java                    # Main application entry point
├── db.java                     # Database connectivity and price fetching
├── MutualFund.java            # Mutual fund investment tracking
├── stocks120.java             # Stock analysis with moving averages
├── arbdb.java                 # Arbitrage opportunity detection
├── NIFTY50.sql               # NIFTY50 index data
├── TATAMOTORS.NS.sql         # TATA Motors stock data
├── tableConvert.com_lyp40u.sql # Additional market data
└── Trading Risk Management System(Insights).pdf # Project documentation
```

## 🔍 Technical Details

### Key Components

- **Real-time Data Processing**: Uses `ScheduledExecutorService` for continuous data fetching
- **Database Integration**: MySQL with JDBC for data storage and retrieval
- **Technical Analysis**: Implements 30-day and 120-day moving averages
- **Arbitrage Detection**: Monitors price differences between BSE and NSE
- **Investment Tracking**: Tracks portfolio value changes and SIP investments

### Database Schema

The system uses multiple tables:
- `NIFTY50`: Index fund data
- `tatamotorbo`: TATA Motors BSE data
- `tatamotornse`: TATA Motors NSE data
- Additional market data tables

### Performance Features

- **Efficient Data Fetching**: Incremental data retrieval using ID-based queries
- **Memory Management**: Proper resource cleanup and connection pooling
- **Real-time Updates**: 1-second intervals for live data monitoring

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Kaushal Nandania**
- Created the Trading Risk Management System
- Specialized in financial technology and risk management

## 📞 Support

For support and questions, please contact the development team or create an issue in the repository.

---

**Note**: This system is for educational and research purposes. Always consult with financial advisors before making investment decisions. The system does not guarantee profits and trading involves risk. 