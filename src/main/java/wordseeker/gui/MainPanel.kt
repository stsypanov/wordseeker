package wordseeker.gui

import wordseeker.Parser
import wordseeker.gui.table.Table
import wordseeker.gui.table.TableModel

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.io.File
import java.io.FileNotFoundException

/**
 * User: rad1kal
 * Date: 14.03.13
 */
class MainPanel : JPanel(GridBagLayout()) {
    private val inputField: InputField
    private val searchButton = Button("Недостаточно данных для поиска")
    private var rootFolder: File? = null
    private val fileTypePanel = FileTypePanel()
    private val controlPanel: ControlPanel
    private val table: JTable

    init {
        border = null
        background = Color.LIGHT_GRAY
        val label = Label("Введите искомую строку:")
        val c = GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                Insets(10, 10, 5, 10),
                0, 0)
        add(label, c)
        /**





         */

        c.insets = Insets(5, 10, 5, 10)
        c.gridy = 1
        inputField = InputField()
        inputField.addKeyListener(InputFieldListener())
        add(inputField, c)
        /**


         */

        c.gridy = 2
        c.gridwidth = 2
        c.weightx = 1.0
        val browsePanel = JPanel(FlowLayout(FlowLayout.LEFT))
        browsePanel.background = Color.LIGHT_GRAY
        browsePanel.add(Label("Выберите каталог: "))
        val browseButton = Button("Обзор")
        browsePanel.add(browseButton)
        browseButton.addActionListener(BrowseButtonListener())
        add(browsePanel, c)

        c.fill = GridBagConstraints.BOTH
        c.gridx = 0
        c.gridy = 3
        c.gridwidth = 2
        controlPanel = ControlPanel()
        add(controlPanel, c)

        c.gridy = 4
        add(fileTypePanel, c)

        c.gridy = 5
        c.fill = GridBagConstraints.NONE
        c.anchor = GridBagConstraints.CENTER

        searchButton.isEnabled = false
        searchButton.addActionListener(SearchButtonListener())
        searchButton.isEnabled = false
        add(searchButton, c)

        table = Table()
        jsp = JScrollPane(table)
        jsp.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED

        c.fill = GridBagConstraints.BOTH
        c.gridx = 0
        c.gridy = 6
        c.weighty = 1.0
        c.gridwidth = 2
        c.gridheight = 1
        c.weightx = 1.0
        add(jsp, c)
    }

    /**
     * Private class for panel labels.
     */
    private class Label internal constructor(text: String) : JLabel(text) {
        init {
            font = Font("Verdana", Font.PLAIN, 12)
            foreground = Color.DARK_GRAY
            isFocusable = false
        }
    }

    private class Button internal constructor(text: String) : JButton(text) {
        init {
            font = Font("Verdana", Font.BOLD, 12)
            isFocusable = false
        }
    }

    private inner class InputFieldListener : KeyAdapter() {
        override fun keyReleased(e: KeyEvent?) {
            if (!inputField.text.isEmpty() and (rootFolder != null)) {
                searchButton.text = "Поиск"
                searchButton.isEnabled = true
            } else if (inputField.text.isEmpty() and (rootFolder != null)) {
                searchButton.text = "Введите строку для поиска"
                searchButton.isEnabled = false
            } else if (!inputField.text.isEmpty() and (rootFolder == null)) {
                searchButton.text = "Укажите корневой каталог"
                searchButton.isEnabled = false
            } else {
                searchButton.text = "Недостаточно данных для поиска"
                searchButton.isEnabled = false
            }
        }
    }

    private inner class BrowseButtonListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            val folderBrowser = JFileChooser("D:\\")
            folderBrowser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            val result = folderBrowser.showOpenDialog(null)
            if (result == JFileChooser.APPROVE_OPTION) {
                rootFolder = folderBrowser.selectedFile
                if (!inputField.text.isEmpty()) {
                    searchButton.text = "Поиск"
                    searchButton.isEnabled = true
                }
            }
        }
    }

    private inner class SearchButtonListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            val docxSelected = fileTypePanel.isDocxSelected
            val docSelected = fileTypePanel.isDocSelected
            val rtfSelected = fileTypePanel.isRtfSelected
            val txtSelected = fileTypePanel.isTxtSelected
            val regExpUsed = controlPanel.isRegExpUsed

            //clear the data in table
            (table.model as TableModel).listOfEntries!!.clear()
            (table.model as TableModel).fireTableDataChanged()

            try {
                val parser: Parser
                if (regExpUsed) {
                    val regExp = inputField.text
                    parser = Parser(rootFolder!!, regExp,
                            docxSelected, docSelected,
                            rtfSelected, txtSelected)
                } else {
                    val toBeFound = inputField.text
                    val isCaseSensitive = controlPanel.isCaseSensitive()
                    parser = Parser(rootFolder!!, toBeFound, isCaseSensitive,
                            docxSelected, docSelected,
                            rtfSelected, txtSelected)
                }
                Thread(parser).start()
            } catch (ex: FileNotFoundException) {
                ex.printStackTrace()
            }

        }

    }

    companion object {
        /**
         * @return the jsp
         */
        var jsp: JScrollPane = JScrollPane()
    }
}


