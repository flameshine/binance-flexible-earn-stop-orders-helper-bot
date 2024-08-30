package com.flameshine.crypto.binance.helper.util;

// TODO: complete messages

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {

    public static final String INITIAL = "I can help you configure stop-limit orders without withdrawing your assets from Binance Flexible Earn.";
    public static final String MAIN_MENU = "Please use the menu below to navigate the bot's functionality.";
    public static final String ACCOUNT_MENU = "In this section you can manage your Binance accounts connected.";
    public static final String ORDER_MENU = "In this section you can configure stop-limit orders for the selected Binance account.";
    public static final String HELP = "Please contact @flameshiner if you have any questions/proposals.";
    public static final String SUPPORT = "Support details.";
    public static final String CONNECT = "Please send your Binance account details below: ";
    public static final String MY_ACCOUNTS = "Currently connected accounts: ";

    public static final String DISCONNECT = """
        Please choose an account you want to disconnect.
        All stop-limits orders will be cancelled and all available assets will be moved to Binance Flexible Earn.
        """;
}