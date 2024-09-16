package com.flameshine.crypto.helper.bot.util;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.bot.entity.Order;

// TODO: complete messages
// TODO: consider grouping

@UtilityClass
public class Messages {

    public static final String KEY_SETUP = "Please share your newly created API key: ";
    public static final String MAIN_MENU = "Please use the menu below to navigate the bot's functionality.";
    public static final String ORDER_MENU = "In this section you can configure stop-limit orders for the selected Binance key.";
    public static final String KEY_SETUP_SUCCESS = "Your API key was added successfully!";
    public static final String KEY_SETUP_FAILURE = "Sorry, your API key looks incorrect. Please try again: ";
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
        
        1. Keys. Manage added Binance API keys.
        2. Orders. Handle stop-limit orders for the selected Binance key. You can view active orders, add new ones, or cancel them.
        3. Support. Find payment details if you'd like to support the developer.
        
        For further assistance, please contact @flameshiner.
        """;

    public static final String MISSING_KEY = """
        It looks like you haven't connected your Binance API key yet.
        Please use the /start command for the instructions.
        """;

    public static final String DISCONNECT_SUCCESS = """
        Your API key was disconnected.
        Call /start if you want to set up a new one.
        """;

    private static final String ORDER_CREATION_TRADING_PAIR = """
        Please specify the order details in the following format:

        `<action>: <base/quote> - <price>`

        Action: Use "b" for buy or "s" for sell
        Base/quote: The trading pair details
        Price: The target price for the order
        
        Example:

        `b: BNB/USDT - 465`
        """;

    private static final String GREETING = """
        Before you can to leverage the bot's functionality, you'll need to add your first Binance API key. See instructions below.
        
        [How to create API keys on Binance](https://www.binance.com/en/support/faq/how-to-create-api-keys-on-binance-360002502072)
        
        Rest assured, your funds will remain safe, as the API keys allow to read-only operations exclusively.
        """;

    private static final String SUPPORT_DETAILS = """
        Thank you for using the bot!
        If you'd like to support the developer and contribute to future improvements, you can send a donation via TRC20.
        
        TRC20 (USDT) wallet address:
        
        `TL9V5p9sehJu2L1cSATH6xhedyWNtEeceR`
        
        Thank you for your support!
        """;

    public static String greeting() {
        return escapeMarkdown(GREETING);
    }

    public static String supportDetails() {
        return escapeMarkdown(SUPPORT_DETAILS);
    }

    public static String orderCreationTradingPair() {
        return escapeMarkdown(ORDER_CREATION_TRADING_PAIR);
    }

    public static String orderExecution(Order order) {
        return String.format(
            "Your %s order for %s/%s at a price of %s has been executed successfully!",
            order.getType(),
            order.getBase(),
            order.getQuote(),
            order.getTarget()
        );
    }

    private static String escapeMarkdown(String input) {
        return input.replaceAll("(?=[.<>!\\-])", "\\\\");
    }
}