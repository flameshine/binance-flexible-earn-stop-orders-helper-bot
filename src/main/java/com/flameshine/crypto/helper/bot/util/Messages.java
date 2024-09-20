package com.flameshine.crypto.helper.bot.util;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.api.entity.Order;

// TODO: complete messages
// TODO: consider grouping

@UtilityClass
public class Messages {

    public static final String MAIN_MENU = "Please use the menu below to navigate the bot's functionality.";
    public static final String ORDER_MENU = "In this section you can configure stop-limit orders for the connected Binance account.";
    public static final String ACCOUNT_SETUP_SUCCESS = "Your account was connected successfully!";
    public static final String ACCOUNT_SETUP_FAILURE = "Sorry, your message format looks incorrect. Please try again: ";
    public static final String ORDER_CREATION_FAILURE = "Sorry, your order format seems incorrect. Please try again: ";
    public static final String ORDER_CREATION_SUCCESS = "Stop-limit order was created successfully!";
    public static final String EMPTY_ORDER_LIST = "It looks like you haven't added any orders yet.";
    public static final String ORDER_LIST = "Your orders: ";
    public static final String ORDER_CANCELLATION = "Please select an order you'd like to cancel: ";
    public static final String ORDER_CANCELLATION_SUCCESS = "Your order was cancelled successfully.";

    public static final String UNRECOGNIZED_MESSAGE = """
        Sorry, I didn't recognize your command.
        Type /help for assistance.
        """;

    public static final String HELP = """
        I can help you configure stop-limit orders without withdrawing your assets from Binance Flexible Earn.
        
        You can interact with me using the following commands:
        
        /menu - Main menu
        /help - Help
        
        Main menu contains the following sections:
        
        1. Orders. Manage stop-limit orders for the selected Binance key. You can view active orders, add new ones, or cancel them.
        2. Support. Find payment details if you'd like to support the developer.
        3. Disconnect. Disconnect your account and cancel all pending orders.
        
        For further assistance, please contact @flameshiner.
        """;

    public static final String ACCOUNT_ALREADY_EXISTS = """
        You already have a Binance account connected.
        Please disconnect it if you want to set up a new one.
        """;

    public static final String MISSING_ACCOUNT = """
        It looks like you haven't connected your Binance account yet.
        Please use the /start command for the instructions.
        """;

    public static final String DISCONNECT_SUCCESS = """
        Your account was disconnected.
        Call /start if you want to connect a new one.
        """;

    public static final String ORDER_EXECUTION_FAILURE_INSUFFICIENT_FUNDS = """
        Error executing your order.
        
        Please check if you have sufficient funds available in your Flexible Earn account.
        """;

    private static final String ACCOUNT_SETUP = """
        Once you have your API and secret keys, send the details in the following format:
        
        `<api-key>:<secret-key>`
        
        Example:
        
        `xDPg6l09eUAicJZ4UvN:WxgbkSbzIOuO2IwLpRt`
        """;

    private static final String GREETING = """
        Before you can leverage the bot's functionality, you'll need to add your Binance API key.
        Please follow the instructions below to create one:
        
        [How to create API keys on Binance](https://www.binance.com/en/support/faq/how-to-create-api-keys-on-binance-360002502072)
        
        While creating your key, ensure that you:
        
            1. Whitelist the bot's IP address for your API key
            2. Enable the "Spot & Margin trading" option
        
        Rest assured, your funds will remain safe.
        """;

    private static final String SUPPORT_DETAILS = """
        Thank you for using the bot!
        If you'd like to support the developer and contribute to future improvements, you can send a donation via TRC20.
        
        TRC20 (USDT) wallet address:
        
        `TL9V5p9sehJu2L1cSATH6xhedyWNtEeceR`
        
        Thank you for your support!
        """;

    private static final String ORDER_CREATION_TRADING_PAIR = """
        Please specify the order details in the following format:

        `<action>: <amount> <base/quote> - <price>`

        Action: Use "b" for buy or "s" for sell
        Amount: Number of coins to buy
        Base/quote: The trading pair details
        Price: The target price for the order
        
        Example:

        `b: 5 BNB/USDT - 465`
        """;

    public static String greeting() {
        return escapeMarkdown(GREETING);
    }

    public static String accountSetup() {
        return escapeMarkdown(ACCOUNT_SETUP);
    }

    public static String supportDetails() {
        return escapeMarkdown(SUPPORT_DETAILS);
    }

    public static String orderCreationTradingPair() {
        return escapeMarkdown(ORDER_CREATION_TRADING_PAIR);
    }

    public static String orderExecutionSuccess(Order order) {
        return String.format(
            "Your %s order for %s %s at a price of %s has been executed successfully!",
            order.getType(),
            order.getAmount(),
            order.getBase(),
            order.getPrice()
        );
    }

    public static String orderExecutionFailure(Order order) {
        return String.format(
            "Unable to execute your %s order for %s %s at a price of %s. Please check if you have sufficient assets available.",
            order.getType(),
            order.getAmount(),
            order.getBase(),
            order.getPrice()
        );
    }

    private static String escapeMarkdown(String input) {
        return input.replaceAll("(?=[.<>!\\-])", "\\\\");
    }
}