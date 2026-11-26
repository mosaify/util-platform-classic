import dev.mosaify.util.platform.About
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class AboutFreeSpec : FreeSpec({
    "names its component" { About.NAME shouldBe "util-platform-classic" }
    "describe its component" { About.DESCRIPTION shouldBe "A basic platform utils library (Kotlin Multiplatform)" }
})
