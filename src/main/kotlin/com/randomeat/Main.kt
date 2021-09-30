package com.randomeat

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi

class Main {
    companion object {
        @JvmStatic fun main(args : Array<String>) {
            ApiContextInitializer.init()
            val bot = Bot(args.first())
            TelegramBotsApi().registerBot(bot)
        }
    }
}