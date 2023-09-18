package app.meetacy.script.featureGen

import kotlin.system.exitProcess

internal fun main(args: Array<String>) = handleFailures {
    checkArgs(args)
    val featureName = parseFeatureName(args[0])
    initFeature(featureName)
}

private inline fun handleFailures(block: () -> Unit) {
    try {
        block()
    } catch (failure: CreateFeatureScriptException) {
        println(failure.message)
        exitProcess(1)
    }
}
