import org.gradle.api.initialization.Settings

//@DslMarker
//annotation class IncludeDsl
//
//@IncludeDsl
//class IncludeContext(
//    @PublishedApi internal val settings: Settings,
//    @PublishedApi internal val prefix: String = ""
//) {
//    operator fun String.invoke(subBuilder: IncludeContext.() -> Unit = {}) {
//        settings.include(this)
//        val subContext = IncludeContext(settings, prefix = "$prefix$this:")
//        subContext.subBuilder()
//    }
//}
//
//inline fun <T> Settings.include(block: IncludeContext.() -> T): T {
//    val context = IncludeContext(settings)
//    return block(context)
//}
