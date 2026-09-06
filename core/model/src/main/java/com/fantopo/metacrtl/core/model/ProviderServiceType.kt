package com.fantopo.metacrtl.core.model

enum class ProviderServiceType(
    val title: String,
    val description: String,
    val packageName: String,
    val iconColorHex: Long
) {
    GRAB(
        title = "Grab",
        description = "Grab Driver & Food partner mock location service",
        packageName = "com.grabtaxi.driver2",
        iconColorHex = 0xFF00B14F
    ),
    GOJEK(
        title = "Gojek",
        description = "Gojek GoPartner driver location integration",
        packageName = "com.gojek.driver.bike",
        iconColorHex = 0xFF00AA13
    ),
    SHOPEEFOOD(
        title = "Shopeefood",
        description = "ShopeeFood Driver partner mock route support",
        packageName = "com.shopee.driver",
        iconColorHex = 0xFFEE4D2D
    )
}
