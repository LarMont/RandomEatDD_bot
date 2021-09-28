package com.randomeat

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import kotlin.random.Random


class Main {
    companion object {
        @JvmStatic fun main(args : Array<String>) {
            ApiContextInitializer.init()
            val bot = Bot(args.first())
            TelegramBotsApi().registerBot(bot)
        }
    }
}

class Bot(private val token: String) : TelegramLongPollingBot() {
    override fun getBotToken() = token

    override fun onUpdateReceived(update: Update) {
        if (update.message.text.contains("/hungry")) {
            execute(SendMessage().setChatId(update.message.chatId).setText(getRandomDeliveryFood()))
        }
        if (update.message.text.contains("/not_random")) {
            val keyboard = ReplyKeyboardMarkup()
            keyboard.keyboard = getKeyboardRows()
            execute(SendMessage()
                .setReplyMarkup(keyboard)
                .setChatId(update.message.chatId)
                .setText("Выбирай ГолоДраник")
            )
        }
        if (deliveryFoods.any { it.name.contains(update.message.text) }) {
            val index = deliveryFoods.indexOfFirst { it.name.contains(update.message.text) }
            execute(SendMessage().setChatId(update.message.chatId).setText(deliveryFoods[index].toString()))
        }
    }

    private fun getKeyboardRows(): List<KeyboardRow> {
        val rate = 3
        val points: ArrayList<Int> = arrayListOf(0)
        val buf: Int = deliveryFoods.size / rate
        if (buf >= 1) {
            1.rangeTo(buf).forEach {
                points.add(it * rate)
            }
        }
        val result = arrayListOf<KeyboardRow>()
        points.forEach { value ->
            val sublist = if (value + rate < deliveryFoods.size) {
                deliveryFoods.subList(value, value + rate)
            } else {
                deliveryFoods.subList(value, deliveryFoods.size)
            }
            val row = KeyboardRow().apply {
                sublist.map { add(KeyboardButton(it.name)) }
            }
            result.add(row)
        }
        return result.toList()
    }

    private fun getRandomDeliveryFood(): String {
        val number = Random.nextInt(0, deliveryFoods.size-1)
        return deliveryFoods[number].toString()
    }

    override fun getBotUsername() = "RandomEatDD_Bot"

    private val deliveryFoods = arrayListOf(
        DeliveryFood("Стритфуд «Станция»", "https://vk.com/@-178118069-menu", "+7 (84235) 4-00-88", "450"),
        DeliveryFood("Brooklyn BBQ Grill&Bar", "https://vk.com/brooklynbbq_dd","+79991948245", "600"),
        DeliveryFood("Ситора","https://vk.com/sitora73","89279888282", "not"),
        DeliveryFood("Кальяри","https://cagliari-pizza.ru/","8-902-127-7772", "400"),
        DeliveryFood("Додо","https://dodopizza.ru/dimitrovgrad/lenina35a","8-800-333-00-60", "445"),
        DeliveryFood("Курочка Max’s Combo","https://vk.com/market-77494487","+7 (995) 335-55-85", "500"),
        DeliveryFood("Сушибокс","https://vk.com/sushibox_dm","8 (800) 100-84-84", "450"),
        DeliveryFood("Шашлычный рай","https://www.instagram.com/shashlichni_rai_dd/","+7 (84235) 4-00-33", "700"),
        DeliveryFood("ПИТ-СТОП ШАУРМА","https://vk.com/public196646745","89020036272", "600"),
    )
}

data class DeliveryFood(
    val name: String,
    val url: String,
    val number: String,
    val deliveryCost: String
) {
    override fun toString(): String {
        return "Хавальня: $name\n" +
                "Попускать слюни на еду: $url\n" +
                "Позвонить доебаться: $number\n" +
                "Готовь бабос от: $deliveryCost шекелей"
    }
}