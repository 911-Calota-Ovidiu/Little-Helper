package org.example.a.RegexTool

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class RegexToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val regexPanel = RegexToolWindowPanel()
        val content = ContentFactory.getInstance().createContent(regexPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}
