package com.flameshine.crypto.binance.helper.util;

// TODO: complete messages

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {

    public static final String API_KEY_SETUP_SUCCESS = "You have successfully configured your first API key!";
    public static final String API_KEY_SETUP_FAILURE = "Sorry, your message format seems incorrect. Please try again: ";
    public static final String MAIN_MENU = "Please use the menu below to navigate the bot's functionality.";
    public static final String ACCOUNT_MENU = "In this section you can manage your Binance accounts connected.";
    public static final String ORDER_MENU = "In this section you can configure stop-limit orders for the selected Binance account.";
    public static final String SUPPORT = "Support details.";
    public static final String CONNECT = "Please send your Binance account details below: ";
    public static final String MY_ACCOUNTS = "Currently connected accounts: ";

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

    public static final String DISCONNECT = """
        Please choose an account you want to disconnect. Your stop-limits orders will be cancelled.
        """;

    private static final String GREETING = """
        Before you can to leverage the bot's functionality, you'll need to configure your first Binance API key:
        
        [How to create API keys on Binance](https://www.binance.com/en/support/faq/how-to-create-api-keys-on-binance-360002502072)
        
        Rest assured, your funds will remain safe, as the API keys allow to read-only operations exclusively.
        """;

    private static final String API_KEY_SETUP = """
        Please send me the API key you just created using the following format:
        
        <name> - <value>
        
        Example:
        
        My key - ECN38H48Su5TdxxV4YO9CeXy
        """;

    public static String greeting() {
        return escapeMarkdown(GREETING);
    }

    public static String apiKeySetup() {
        return escapeMarkdown(API_KEY_SETUP);
    }

    // TODO: consider replacing with regex

    private static String escapeMarkdown(String input) {
        return input.replaceAll("\\.", "\\\\.")
            .replaceAll("-", "\\\\-")
            .replaceAll("<", "\\\\<")
            .replaceAll(">", "\\\\>");
    }
}