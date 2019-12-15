package micronaut.nonblocking.demo.products

import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap
import io.reactivex.schedulers.Schedulers
import io.reactivex.Maybe
import java.math.BigDecimal
import javax.inject.Singleton


@Singleton
class ProductService {

    val log = LoggerFactory.getLogger(ProductService::class.java)

    val products = ConcurrentHashMap<String, Product>()

    init {
       products.put("PROD-001", Product("PROD-001", "Micronaut in Action", 29.99))
       products.put("PROD-002", Product("PROD-002", "Netty in Action", 31.22))
       products.put("PROD-003", Product("PROD-003", "Effective Java, 3rd edition", 31.22))
       products.put("PROD-004", Product("PROD-004", "Clean Code", 31.22))
   }

    fun findProductById(id: String): Maybe<Product> {
        return Maybe.just(id)
                .subscribeOn(Schedulers.io())
                .map { it ->
                    simulateLatency(600)
                    log.info("Product with id {} ready to return...", it)
                    products.getOrDefault(it, null)
                }
    }

    private fun simulateLatency(millis: Int) {
        try {
            Thread.sleep(millis.toLong())
        } catch (ignored: InterruptedException) {
        }
    }

}

