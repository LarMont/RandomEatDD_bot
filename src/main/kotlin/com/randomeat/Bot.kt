package com.randomeat

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import java.io.File
import kotlin.random.Random


class Bot(private val token: String) : TelegramLongPollingBot() {
    override fun getBotToken() = token
    override fun getBotUsername() = BOT_USERNAME

    override fun onUpdateReceived(update: Update) {
        val chatId = update.message.chatId
        with(update.message.text) {
            when {
                contains("/hungry") -> {
                    val deliveryFood = getRandomDeliveryFood()
                    if (deliveryFood.screens.isNotEmpty()) {
                        sendImageAlbum(chatId, deliveryFood)
                    }
                    execute(SendMessage().setChatId(chatId).setText(deliveryFood.toString()))
                }
                contains("/not_random") -> {
                    val keyboard = ReplyKeyboardMarkup()
                    keyboard.keyboard = getKeyboardRows()
                    execute(
                        SendMessage()
                            .setReplyMarkup(keyboard)
                            .setChatId(chatId)
                            .setText("Выбирай ГолоДраник")
                    )

                }
                DELIVERY_FOODS.any { contains(update.message.text) } -> {
                    val index = DELIVERY_FOODS.indexOfFirst { it.name.contains(update.message.text) }
                    val deliveryFood = DELIVERY_FOODS[index]

                    if (deliveryFood.screens.isNotEmpty()) {
                        sendImageAlbum(chatId, deliveryFood)
                    }

                    execute(SendMessage().setChatId(update.message.chatId).setText(deliveryFood.toString()))
                }
                else -> {}
            }
        }
    }

    private fun sendImageAlbum(chatId: Long, deliveryFood: DeliveryFood) {
        with(deliveryFood.screens) {
            when {
                isEmpty() -> {
                }
                size == 1 -> {
                    execute(SendPhoto().setChatId(chatId).setPhoto(first(), javaClass.getResourceAsStream("/drawable/${first()}")))
                }
                else -> {
                    val media = arrayListOf<InputMediaPhoto>()
                    deliveryFood.screens.forEach {
                        media.add(InputMediaPhoto().apply { setMedia(javaClass.getResourceAsStream("/drawable/${it}"), it) })
                    }

                    val mediaGroup = SendMediaGroup()
                    mediaGroup.setChatId(chatId)
                    mediaGroup.media = media.toList()
                    execute(mediaGroup)
                }
            }
        }
    }

    private fun getKeyboardRows(): List<KeyboardRow> {
        val rate = 3
        val points: ArrayList<Int> = arrayListOf(0)
        val buf: Int = DELIVERY_FOODS.size / rate
        if (buf >= 1) {
            1.rangeTo(buf).forEach {
                points.add(it * rate)
            }
        }
        val result = arrayListOf<KeyboardRow>()
        points.forEach { value ->
            val sublist = if (value + rate < DELIVERY_FOODS.size) {
                DELIVERY_FOODS.subList(value, value + rate)
            } else {
                DELIVERY_FOODS.subList(value, DELIVERY_FOODS.size)
            }
            val row = KeyboardRow().apply {
                sublist.map { add(KeyboardButton(it.name)) }
            }
            result.add(row)
        }
        return result.toList()
    }

    private fun getRandomDeliveryFood(): DeliveryFood {
        val max = DELIVERY_FOODS.sumOf { it.weight }
        var number = Random.nextInt(0, max)
        DELIVERY_FOODS.forEach {
            val diff = number - it.weight
            if (diff <= 0) { //Возможно не равно 0
                return it
            } else {
                number = diff
            }
        }
        return DELIVERY_FOODS.first()
    }
}