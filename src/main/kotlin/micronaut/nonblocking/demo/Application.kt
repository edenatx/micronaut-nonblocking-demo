package micronaut.nonblocking.demo

import io.micronaut.context.annotation.Configuration
import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("micronaut.nonblocking.demo")
                .mainClass(Application.javaClass)
                .start()
    }
}