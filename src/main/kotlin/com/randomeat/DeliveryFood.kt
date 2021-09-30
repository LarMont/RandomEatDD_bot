package com.randomeat

data class DeliveryFood(
    val name: String,
    val url: String,
    val number: String,
    val deliveryCost: String,
    val weight: Int = 5,
    val screens: List<String> = arrayListOf(),
    val dateUpdate: String = ""
) {
    override fun toString(): String {
        return "Хавальня: $name\n" +
                "Попускать слюни на еду: $url\n" +
                "Позвонить доебаться: $number\n" +
                "Готовь бабос от: $deliveryCost шекелей"
    }
}

val DELIVERY_FOODS = arrayListOf(
    DeliveryFood(
        name = "Стритфуд «Станция»",
        url = "https://vk.com/@-178118069-menu",
        number = "+7 (84235) 4-00-88",
        deliveryCost = "450",
        screens = listOf(
            "station_1.png",
            "station_2.png",
            "station_3.png",
        )
    ),
    DeliveryFood(
        name = "Brooklyn BBQ Grill&Bar",
        url = "https://vk.com/brooklynbbq_dd",
        number = "+79991948245",
        deliveryCost = "600",
        screens = listOf(
            "brooklyn_promotions.png"
        )
    ),
    DeliveryFood(
        name = "Ситора",
        url = "https://vk.com/sitora73",
        number = "89279888282",
        deliveryCost = "not"
    ),
    DeliveryFood(
        name = "Кальяри",
        url = "https://cagliari-pizza.ru/",
        number = "8-902-127-7772",
        deliveryCost = "400"
    ),
    DeliveryFood(
        name = "Додо",
        url = "https://dodopizza.ru/dimitrovgrad/lenina35a",
        number = "8-800-333-00-60",
        deliveryCost = "445"
    ),
    DeliveryFood(
        name = "Курочка Max’s Combo",
        url = "https://vk.com/market-77494487",
        number = "+7 (995) 335-55-85",
        deliveryCost = "500"
    ),
    DeliveryFood(
        name = "Сушибокс",
        url = "https://vk.com/sushibox_dm",
        number = "8 (800) 100-84-84",
        deliveryCost = "450"
    ),
    DeliveryFood(
        name = "Шашлычный рай",
        url = "https://www.instagram.com/shashlichni_rai_dd/",
        number = "+7 (84235) 4-00-33",
        deliveryCost = "700",
        screens = arrayListOf(
            "paradise_1.png",
            "paradise_2.png"
        )
    ),
    DeliveryFood(
        name = "ПИТ-СТОП ШАУРМА",
        url = "https://vk.com/public196646745",
        number = "89020036272",
        deliveryCost = "600",
        screens = arrayListOf(
            "pit_stop_1.png",
            "pit_stop_2.png"
        )
    ),
    DeliveryFood(
        name = "Ной люля",
        url = "https://eda.yandex.ru/restaurant/noj_yung_severnogo_flota_1a",
        number = "+7 (84235) 9-59-19",
        deliveryCost = "500",
        weight = 1
    ),
)