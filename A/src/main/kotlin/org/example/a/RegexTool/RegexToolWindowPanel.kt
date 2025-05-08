package org.example.a.RegexTool

import java.awt.*
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.*

class RegexToolWindowPanel : JPanel() {
    private val patternField = JTextField()
    private val inputArea = JTextArea(10, 40)
    private val outputPane = JTextPane()

    init {
        layout = BorderLayout()

        val topPanel = JPanel(BorderLayout())
        topPanel.add(JLabel("Regex:"), BorderLayout.WEST)
        topPanel.add(patternField, BorderLayout.CENTER)

        val centerPanel = JSplitPane(JSplitPane.VERTICAL_SPLIT, JScrollPane(inputArea), JScrollPane(outputPane))
        centerPanel.resizeWeight = 0.5

        add(topPanel, BorderLayout.NORTH)
        add(centerPanel, BorderLayout.CENTER)

        outputPane.isEditable = false

        val listener = object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent?) = updatePreview()
            override fun removeUpdate(e: DocumentEvent?) = updatePreview()
            override fun changedUpdate(e: DocumentEvent?) = updatePreview()
        }

        patternField.document.addDocumentListener(listener)
        inputArea.document.addDocumentListener(listener)
    }

    private fun updatePreview() {
        val regexText = patternField.text
        val inputText = inputArea.text

        val doc = outputPane.styledDocument
        doc.remove(0, doc.length)
        doc.insertString(0, inputText, null)

        if (regexText.isBlank()) return

        try {
            val regex = Regex(regexText)
            val matches = regex.findAll(inputText)
            for (match in matches) {
                val attr = SimpleAttributeSet()
                StyleConstants.setBackground(attr, Color.YELLOW)
                doc.setCharacterAttributes(match.range.first, match.value.length, attr, false)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
