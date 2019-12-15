package micronaut.nonblocking.demo.products

import io.micronaut.core.annotation.Introspected

@Introspected
data class Product(val id: String, val name: String, val price: Double)
