package app.meetacy.backend.types.annotation

@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "Please use this VERY CAREFUL! You might have lots of bugs and problems while accessing raw username string. " +
        "Ask yourself such questions: " +
        "You expect the username to be with @ or without?" +
        "You are comparing usernames? They should be case-insensetive, remember that!"
)
annotation class UnsafeRawUsername
