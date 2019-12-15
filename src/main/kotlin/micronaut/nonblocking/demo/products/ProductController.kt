package micronaut.nonblocking.demo.products

import io.micronaut.http.annotation.Controller
import org.slf4j.LoggerFactory
import io.reactivex.Maybe
import io.micronaut.http.annotation.Get


@Controller("/product")
class ProductController(val productService: ProductService) {

    private val log = LoggerFactory.getLogger(ProductController::class.java)

    @Get("/{id}")
    fun getProduct(id: String): Maybe<Product> {
        log.info("ProductController.getProduct({}) executed...", id)

        return productService.findProductById(id).onErrorComplete()
    }
}