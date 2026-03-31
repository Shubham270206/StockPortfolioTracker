package portfolio;

import java.io.*;
import java.util.*;

/**
 * Manages a collection of Stock objects.
 * Supports add, remove, update, list, and file persistence.
 */
public class Portfolio {
    private Map<String, Stock> stocks;
    private String dataFile;

    public Portfolio(String dataFile) {
        this.stocks = new HashMap<>();
        this.dataFile = dataFile;
        loadFromFile();
    }

    /**
     * Adds a new stock to the portfolio.
     * Throws exception if ticker already exists.
     */
    public void addStock(Stock stock) {
        if (stocks.containsKey(stock.getTicker())) {
            throw new IllegalStateException("Stock " + stock.getTicker() + " already exists. Use 'update' to modify it.");
        }
        stocks.put(stock.getTicker(), stock);
        saveToFile();
        System.out.println("✔ Added: " + stock.getTicker());
    }

    /**
     * Removes a stock by ticker.
     * Throws exception if not found.
     */
    public void removeStock(String ticker) {
        ticker = ticker.toUpperCase();
        if (!stocks.containsKey(ticker)) {
            throw new NoSuchElementException("Stock not found: " + ticker);
        }
        stocks.remove(ticker);
        saveToFile();
        System.out.println("✔ Removed: " + ticker);
    }

    /**
     * Updates the current market price of a stock.
     */
    public void updatePrice(String ticker, double newPrice) {
        ticker = ticker.toUpperCase();
        Stock stock = stocks.get(ticker);
        if (stock == null) throw new NoSuchElementException("Stock not found: " + ticker);
        stock.setCurrentPrice(newPrice);
        saveToFile();
        System.out.printf("✔ Updated %s current price to %.2f%n", ticker, newPrice);
    }

    /**
     * Returns all stocks sorted alphabetically by ticker.
     */
    public List<Stock> getAllStocks() {
        List<Stock> list = new ArrayList<>(stocks.values());
        list.sort(Comparator.comparing(Stock::getTicker));
        return list;
    }

    /**
     * Returns total invested amount across all stocks.
     */
    public double getTotalInvestment() {
        return stocks.values().stream().mapToDouble(Stock::getTotalInvestment).sum();
    }

    /**
     * Returns total current portfolio value.
     */
    public double getTotalValue() {
        return stocks.values().stream().mapToDouble(Stock::getCurrentValue).sum();
    }

    /**
     * Returns overall P&L.
     */
    public double getTotalProfitLoss() {
        return getTotalValue() - getTotalInvestment();
    }

    /**
     * Returns overall P&L percentage.
     */
    public double getTotalProfitLossPercent() {
        if (getTotalInvestment() == 0) return 0;
        return (getTotalProfitLoss() / getTotalInvestment()) * 100;
    }

    /**
     * Returns the best performing stock by P&L%.
     */
    public Optional<Stock> getBestPerformer() {
        return stocks.values().stream().max(Comparator.comparingDouble(Stock::getProfitLossPercent));
    }

    /**
     * Returns the worst performing stock by P&L%.
     */
    public Optional<Stock> getWorstPerformer() {
        return stocks.values().stream().min(Comparator.comparingDouble(Stock::getProfitLossPercent));
    }

    public boolean isEmpty() {
        return stocks.isEmpty();
    }

    public int size() {
        return stocks.size();
    }

    // ---- File Persistence ----

    /**
     * Saves all stocks to a CSV file.
     * Creates the file if it doesn't exist.
     */
    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(dataFile))) {
            for (Stock s : stocks.values()) {
                pw.println(s.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not save portfolio — " + e.getMessage());
        }
    }

    /**
     * Loads stocks from the CSV file on startup.
     * Silently skips if file doesn't exist yet.
     */
    private void loadFromFile() {
        File f = new File(dataFile);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    Stock s = Stock.fromCSV(line);
                    stocks.put(s.getTicker(), s);
                } catch (Exception e) {
                    System.err.println("Warning: Skipping malformed line " + lineNum + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not load portfolio — " + e.getMessage());
        }
    }
}