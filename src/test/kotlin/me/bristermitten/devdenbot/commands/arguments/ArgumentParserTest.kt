package me.bristermitten.devdenbot.commands.arguments

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ArgumentParserTest {

    @Test
    fun parse() {
        val tokens = ArgumentLexer.lex("""
            command name "quoted value"
        """.trimIndent())

        val parsed = ArgumentParser.parse(tokens)

        assertEquals(
            listOf(
                Argument("name"),
                Argument("quoted value"),
            ),
            parsed.args
        )
    }

    @Test
    fun parse2() {
        val tokens = ArgumentLexer.lex("""
            ddfaq set helloworld "Hello World!" "This is a test FAQ, it doesn't supports quoted messages :O"
        """.trimIndent())

        val parsed = ArgumentParser.parse(tokens)

        assertEquals(
            listOf(
                Argument("set"),
                Argument("helloworld"),
                Argument("Hello World!"),
                Argument("This is a test FAQ, it doesn't supports quoted messages :O"),
            ),
            parsed.args
        )
    }
}
