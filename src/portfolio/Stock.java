package portfolio;

/**
 * Represents a single stock holding in the portfolio.
 * Stores ticker symbol, quantity, buy price, and current price.
 */
public class Stock {
    private String ticker;
    private int quantity;
    private double buyPrice;
    private double currentPrice;

    /**
     * Constructs a Stock with a known current price.
     */
    public Stock(String ticker, int quantity, double buyPrice, double currentPrice) {
        if (ticker == null || ticker.isBlank()) throw new IllegalArgumentException("Ticker cannot be empty.");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        if (buyPrice <= 0 || currentPrice <= 0) throw new IllegalArgumentException("Prices must be positive.");

        this.ticker = ticker.toUpperCase();
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;
    }

    /**
     * Constructs a Stock — current price defaults to buy price.
     */
    public Stock(String ticker, int quantity, double buyPrice) {
        this(ticker, quantity, buyPrice, buyPrice);
    }

    // --- Getters ---
    public String getTicker() { return ticker; }
    public int getQuantity() { return quantity; }
    public double getBuyPrice() { return buyPrice; }
    public double getCurrentPrice() { return currentPrice; }

    // --- Setters ---
    public void setCurrentPrice(double currentPrice) {
        if (currentPrice <= 0) throw new IllegalArgumentException("Current price must be positive.");
        this.currentPrice = currentPrice;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        this.quantity = quantity;
    }

    // --- Derived Calculations ---

    /** Total investment = buyPrice * quantity */
    public double getTotalInvestment() {
        return buyPrice * quantity;
    }

    /** Current value = currentPrice * quantity */
    public double getCurrentValue() {
        return currentPrice * quantity;
    }

    /** Profit or loss = currentValue - totalInvestment */
    public double getProfitLoss() {
        return getCurrentValue() - getTotalInvestment();
    }

    /** P&L as a percentage of investment */
    public double getProfitLossPercent() {
        return (getProfitLoss() / getTotalInvestment()) * 100;
    }

    /**
     * Serializes stock to a CSV line for file persistence.
     * Format: TICKER,QUANTITY,BUY_PRICE,CURRENT_PRICE
     */
    public String toCSV() {
        return String.format("%s,%d,%.2f,%.2f", ticker, quantity, buyPrice, currentPrice);
    }

    /**
     * Deserializes a Stock from a CSV line.
     */
    public static Stock fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) throw new IllegalArgumentException("Invalid CSV format: " + line);
        return new Stock(
            parts[0].trim(),
            Integer.parseInt(parts[1].trim()),
            Double.parseDouble(parts[2].trim()),
            Double.parseDouble(parts[3].trim())
        );
    }

    @Override
    public String toString() {
        return String.format("%-8s | Qty: %4d | Buy: %8.2f | Current: %8.2f | P&L: %+9.2f (%+.2f%%)",
            ticker, quantity, buyPrice, currentPrice, getProfitLoss(), getProfitLossPercent());
    }
}