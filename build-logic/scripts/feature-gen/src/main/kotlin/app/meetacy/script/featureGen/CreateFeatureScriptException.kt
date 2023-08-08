package app.meetacy.script.featureGen

class CreateFeatureScriptException(message: String) : RuntimeException(message)

internal fun failScript(message: String): Nothing = throw CreateFeatureScriptException(message)
