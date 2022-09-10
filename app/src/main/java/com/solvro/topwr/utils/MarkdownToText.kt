package com.solvro.topwr.utils

import android.content.Context
import android.widget.TextView
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.linkify.LinkifyPlugin

object MarkdownToText {

    fun createTextFromMarkdown(context: Context, description: String, textView: TextView,
                                codeTextColor: Int, linkColor: Int, isLinkUnderlined:Boolean = true) {
        val markwon = Markwon.builder(context)
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureTheme(builder: MarkwonTheme.Builder) {
                    builder
                        .codeTextColor(codeTextColor)
                        .linkColor(linkColor)
                        .isLinkUnderlined(isLinkUnderlined)
                }
            })
            .usePlugin(LinkifyPlugin.create())
            .build()
        markwon.setMarkdown(textView, description)
    }
}