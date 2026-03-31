# 📈 Stock Portfolio Tracker

A command-line Java application to manage and track your personal stock portfolio. Add stocks, update market prices, and instantly view your profit/loss across all holdings — with data persisted between sessions via CSV file storage.

---

## 🚀 Features

- **Add stocks** — Record ticker, quantity, buy price, and current market price
- **Remove stocks** — Delete any holding from your portfolio
- **Update prices** — Refresh the current market price of any stock
- **View holdings** — Table view with P&L (₹ and %) for each stock
- **Portfolio summary** — Total invested, current value, overall P&L, best and worst performers
- **Persistent storage** — Portfolio is saved to `data/portfolio.csv` automatically

---

## 🛠️ Tech Stack

- **Language:** Java 11+
- **Storage:** CSV flat file (no database required)
- **Concepts used:** OOP, Collections (HashMap, ArrayList, Streams), Exception Handling, File I/O

---

## 📁 Project Structure

```
StockPortfolioTracker/
├── src/
│   └── portfolio/
│       ├── Main.java            # CLI entry point & menu
│       ├── Portfolio.java       # Portfolio manager (business logic)
│       ├── Stock.java           # Stock model with P&L calculations
│       └── PortfolioException.java  # Custom exception class
├── data/
│   └── portfolio.csv            # Auto-generated on first run
└── README.md
```

---

## ⚙️ Setup & Run

### Prerequisites
- Java 11 or higher installed
- Terminal / Command Prompt

### 1. Clone the repository

```bash
git clone https://github.com/Shubham270206/StockPortfolioTracker.git
cd StockPortfolioTracker
```

### 2. Compile

```bash
mkdir -p out data
javac -d out src/portfolio/*.java
```

### 3. Run

```bash
java -cp out portfolio.Main
```

---

## 🖥️ Usage

On launch, you'll see an interactive menu:

```
╔══════════════════════════════════════════╗
║     📈 Stock Portfolio Tracker v1.0      ║
╚══════════════════════════════════════════╝

  1. Add Stock
  2. Remove Stock
  3. Update Current Price
  4. View All Holdings
  5. View Portfolio Summary
  6. Exit
```

### Example: Adding a stock

```
Enter choice: 1

── Add Stock ──
Ticker symbol: RELIANCE
Quantity: 10
Buy price: ₹2800.00
Current market price: ₹2950.00
✔ Added: RELIANCE
```

### Example: Viewing holdings

```
───────────────────────────────────────────────────────────────────────────
TICKER   | QTY    | BUY PRICE | CUR PRICE | P&L (₹)       | P&L (%)
───────────────────────────────────────────────────────────────────────────
INFY     |     50 |   1500.00 |   1620.00 | ▲    +6000.00 | +8.00%
RELIANCE |     10 |   2800.00 |   2950.00 | ▲    +1500.00 | +5.36%
TCS      |      5 |   3500.00 |   3380.00 | ▼    -600.00  | -3.43%
───────────────────────────────────────────────────────────────────────────
```

### Example: Portfolio summary

```
── Portfolio Summary ──
  Total Stocks     : 3
  Total Invested   : ₹109500.00
  Current Value    : ₹116400.00
  Overall P&L      : +6900.00 (6.30%)
  Best Performer   : INFY (+8.00%)
  Worst Performer  : TCS (-3.43%)
```

---

## 💾 Data Storage

All portfolio data is stored in `data/portfolio.csv`:

```
RELIANCE,10,2800.00,2950.00
INFY,50,1500.00,1620.00
TCS,5,3500.00,3380.00
```

The file is automatically created and updated on every add/remove/update operation.

---

## 🧪 Exception Handling

The app handles:
- Duplicate ticker symbols
- Invalid numeric inputs (negative prices, zero quantity)
- Non-existent ticker lookups
- Malformed CSV lines on file load

---

## 👤 Author

**Shubham Sinha**  
B.Tech CSE (AI/ML) — VIT Bhopal University  
GitHub: [@Shubham270206](https://github.com/Shubham270206)