package com.flameshine.crypto.binance.helper.util;

import lombok.experimental.UtilityClass;

// TODO: complete messages
// TODO: consider grouping

@UtilityClass
public class Messages {

    public static final String MAIN_MENU = "Please use the menu below to navigate the bot's functionality.";
    public static final String ACCOUNT_MENU = "In this section you can manage your Binance accounts connected.";
    public static final String ORDER_MENU = "In this section you can configure stop-limit orders for the selected Binance account.";
    public static final String UNRECOGNIZED_MESSAGE = "Sorry, I didn't recognize your command. Type /help for assistance.";
    public static final String API_KEY_SETUP_SUCCESS = "Account was connected successfully!";
    public static final String API_KEY_SETUP_FAILURE = "Sorry, your message format seems incorrect. Please try again: ";
    public static final String ACCOUNT_LIST = "Currently connected accounts: ";
    public static final String ACCOUNT_DISCONNECTION_SUCCESS = "Your account was disconnected successfully.";
    public static final String ORDER_CREATION_FAILURE = "Sorry, your order format seems incorrect. Please try again: ";
    public static final String ORDER_CREATION_SUCCESS = "Stop-limit order was created successfully!";

    public static final String HELP = """
        I can help you configure stop-limit orders without withdrawing your assets from Binance Flexible Earn.
        
        You can interact with me using the following commands:
        
        /menu - Main menu
        /help - Help
        
        Main menu contains the following sections:
        
        1. Accounts. Manage connected Binance accounts.
        2. Orders. Handle stop-limit orders for the selected Binance account. You can view active orders, add new ones, or cancel them.
        3. Support. Find payment details if you'd like to support the developer.
        
        For further assistance, please contact @flameshiner.
        """;

    public static final String EMPTY_ACCOUNT_LIST = """
        You haven't connected any accounts yet.
        To learn how, use the /start command.
        """;

    public static final String UNKNOWN_ACCOUNT = """
        Account not recognized.
        Please try again or connect one if you haven't done so already.
        """;

    public static final String ACCOUNT_DISCONNECTION = """
        Please type the name of the account you'd like to disconnect.
        Your stop-limit orders will be cancelled.
        """;

    private static final String ORDER_CREATION_TRADING_PAIR = """
        Please specify the order details in the following format:
        
        `<account name>: <base/quote> - <price>`
        
        Example:
        
        `account1: BNB/USDT - 465`
        """;

    private static final String GREETING = """
        Before you can to leverage the bot's functionality, you'll need to connect your first Binance account. See instructions below.
        
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

    private static final String ACCOUNT_SETUP = """
        Please share your Binance API key using the following format:
        
        `<name> - <value>`
        
        Example:
        
        `account1 - ECN38H48Su5TdxxV4YO9CeXy`
        """;

    public static String greeting() {
        return escapeMarkdown(GREETING);
    }

    public static String supportDetails() {
        return escapeMarkdown(SUPPORT_DETAILS);
    }

    public static String accountSetup() {
        return escapeMarkdown(ACCOUNT_SETUP);
    }

    public static String orderCreationTradingPair() {
        return escapeMarkdown(ORDER_CREATION_TRADING_PAIR);
    }

    private static String escapeMarkdown(String input) {
        return input.replaceAll("(?=[.<>\\-])", "\\\\");
    }
}