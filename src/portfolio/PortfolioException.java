package portfolio;

/**
 * Custom exception for invalid portfolio operations.
 * Extends RuntimeException for unchecked usage throughout the app.
 */
public class PortfolioException extends RuntimeException {
    public PortfolioException(String message) {
        super(message);
    }

    public PortfolioException(String message, Throwable cause) {
        super(message, cause);
    }
}