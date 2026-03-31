package portfolio;

import java.util.*;

/**
 * Main entry point for the Stock Portfolio Tracker CLI.
 *
 * Demonstrates Java OOP, Collections (HashMap, ArrayList),
 * Exception Handling, and File I/O.
 *
 * Usage: Run and follow the interactive menu.
 */
public class Main {

    private static final String DATA_FILE = "data/portfolio.csv";
    private static final String DIVIDER = "─".repeat(75);

    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio(DATA_FILE);
        Scanner scanner = new Scanner(System.in);

        printBanner();

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            try {
                switch (input) {
                    case "1" -> addStock(portfolio, scanner);
                    case "2" -> removeStock(portfolio, scanner);
                    case "3" -> updatePrice(portfolio, scanner);
                    case "4" -> viewPortfolio(portfolio);
                    case "5" -> viewSummary(portfolio);
                    case "6" -> {
                        System.out.println("\nGoodbye! Portfolio saved.");
                        running = false;
                    }
                    default -> System.out.println("⚠ Invalid choice. Enter 1–6.");
                }
            } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
                System.out.println("⚠ Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠ Unexpected error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // ---- Menu Handlers ----

    private static void addStock(Portfolio portfolio, Scanner scanner) {
        System.out.println("\n── Add Stock ──");
        System.out.print("Ticker symbol (e.g. RELIANCE): ");
        String ticker = scanner.nextLine().trim();

        System.out.print("Quantity: ");
        int qty = parsePositiveInt(scanner.nextLine().trim(), "Quantity");

        System.out.print("Buy price: ₹");
        double buyPrice = parsePositiveDouble(scanner.nextLine().trim(), "Buy price");

        System.out.print("Current market price (press Enter to use buy price): ₹");
        String currentInput = scanner.nextLine().trim();
        double currentPrice = currentInput.isEmpty() ? buyPrice : parsePositiveDouble(currentInput, "Current price");

        Stock stock = new Stock(ticker, qty, buyPrice, currentPrice);
        portfolio.addStock(stock);
    }

    private static void removeStock(Portfolio portfolio, Scanner scanner) {
        System.out.println("\n── Remove Stock ──");
        if (portfolio.isEmpty()) { System.out.println("Portfolio is empty."); return; }
        System.out.print("Ticker to remove: ");
        String ticker = scanner.nextLine().trim();
        portfolio.removeStock(ticker);
    }

    private static void updatePrice(Portfolio portfolio, Scanner scanner) {
        System.out.println("\n── Update Current Price ──");
        if (portfolio.isEmpty()) { System.out.println("Portfolio is empty."); return; }
        System.out.print("Ticker: ");
        String ticker = scanner.nextLine().trim();
        System.out.print("New current price: ₹");
        double price = parsePositiveDouble(scanner.nextLine().trim(), "Price");
        portfolio.updatePrice(ticker, price);
    }

    private static void viewPortfolio(Portfolio portfolio) {
        System.out.println("\n── Portfolio Holdings ──");
        if (portfolio.isEmpty()) { System.out.println("No stocks in portfolio."); return; }

        System.out.println(DIVIDER);
        System.out.printf("%-8s | %-6s | %-10s | %-10s | %-14s | %s%n",
            "TICKER", "QTY", "BUY PRICE", "CUR PRICE", "P&L (₹)", "P&L (%)");
        System.out.println(DIVIDER);

        for (Stock s : portfolio.getAllStocks()) {
            String plSign = s.getProfitLoss() >= 0 ? "▲" : "▼";
            System.out.printf("%-8s | %6d | %10.2f | %10.2f | %s%+12.2f | %+.2f%%%n",
                s.getTicker(), s.getQuantity(), s.getBuyPrice(), s.getCurrentPrice(),
                plSign, s.getProfitLoss(), s.getProfitLossPercent());
        }
        System.out.println(DIVIDER);
    }

    private static void viewSummary(Portfolio portfolio) {
        System.out.println("\n── Portfolio Summary ──");
        if (portfolio.isEmpty()) { System.out.println("No stocks in portfolio."); return; }

        System.out.println(DIVIDER);
        System.out.printf("  Total Stocks     : %d%n", portfolio.size());
        System.out.printf("  Total Invested   : ₹%.2f%n", portfolio.getTotalInvestment());
        System.out.printf("  Current Value    : ₹%.2f%n", portfolio.getTotalValue());
        System.out.printf("  Overall P&L      : %+.2f (%.2f%%)%n",
            portfolio.getTotalProfitLoss(), portfolio.getTotalProfitLossPercent());

        portfolio.getBestPerformer().ifPresent(s ->
            System.out.printf("  Best Performer   : %s (%+.2f%%)%n", s.getTicker(), s.getProfitLossPercent()));
        portfolio.getWorstPerformer().ifPresent(s ->
            System.out.printf("  Worst Performer  : %s (%+.2f%%)%n", s.getTicker(), s.getProfitLossPercent()));

        System.out.println(DIVIDER);
    }

    // ---- Helpers ----

    private static int parsePositiveInt(String input, String fieldName) {
        try {
            int val = Integer.parseInt(input);
            if (val <= 0) throw new IllegalArgumentException(fieldName + " must be a positive integer.");
            return val;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid integer.");
        }
    }

    private static double parsePositiveDouble(String input, String fieldName) {
        try {
            double val = Double.parseDouble(input);
            if (val <= 0) throw new IllegalArgumentException(fieldName + " must be a positive number.");
            return val;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid number.");
        }
    }

    private static void printBanner() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║     📈 Stock Portfolio Tracker v1.0      ║");
        System.out.println("║     Data persisted to: data/portfolio.csv║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    private static void printMenu() {
        System.out.println("\n  1. Add Stock");
        System.out.println("  2. Remove Stock");
        System.out.println("  3. Update Current Price");
        System.out.println("  4. View All Holdings");
        System.out.println("  5. View Portfolio Summary");
        System.out.println("  6. Exit");
        System.out.println();
    }
}