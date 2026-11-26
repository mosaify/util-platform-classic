import dev.mosaify.util.platform.PlatformUtils
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.FreeSpec

class PlatformUtilsFreeSpec : FreeSpec({
    "logs a warning" { shouldNotThrowAny { PlatformUtils.logWarning { "util-platform-classic logs on ${PlatformUtils.getCurrentOs()}" } } }
})
