package com.kharedji.memosphere.domain.utils

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
