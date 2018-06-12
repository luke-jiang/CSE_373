package calculator.gui;

import calculator.errors.EvaluationError;
import calculator.errors.QuitError;
import calculator.interpreter.Calculator;
import calculator.errors.IncompleteInputError;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame {
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_DRAWING_HEIGHT = 600;
    public static final int DEFAULT_TEXT_ROWS = 10;
    public static final int DEFAULT_FONT_SIZE = 16;

    public static void launch() {
        EventQueue.invokeLater(() -> {
            MainWindow window = new MainWindow(
                    "Calculator",
                    DEFAULT_WIDTH,
                    DEFAULT_DRAWING_HEIGHT,
                    DEFAULT_TEXT_ROWS);
            window.construct();
        });
    }

    private String title;
    private int width;
    private int drawingHeight;
    private int textRows;
    private Calculator calculator;

    public MainWindow(String title, int width, int drawingHeight, int textRows) {
        this.title = title;
        this.width = width;
        this.drawingHeight = drawingHeight;
        this.textRows = textRows;
        this.calculator = new Calculator();
    }

    public void construct() {
        this.setupMainWindow();

        // Set up main pane
        JSplitPane mainPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                this.makeDrawingPane(),
                this.makeTextPane());
        mainPane.setDividerLocation(this.drawingHeight);
        this.add(mainPane);

        // Finish setting up geometry
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setupMainWindow() {
        this.setTitle(this.title);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Ignore
        }
    }

    public ImagePanel makeDrawingPane() {
        BufferedImage image = new BufferedImage(
                this.width,
                this.drawingHeight, BufferedImage.TYPE_BYTE_INDEXED);

        // Setup initial background color
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width + 1, this.drawingHeight + 1);

        ImagePanel panel = new ImagePanel(image);
        this.calculator.setImageDrawer(panel.getImageDrawer());
        return panel;
    }

    public JComponent makeTextPane() {
        // Add GUI
        JTextArea textArea = new JTextArea();
        textArea.setRows(this.textRows);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font(
                Font.MONOSPACED,
                Font.PLAIN,
                DEFAULT_FONT_SIZE
        ));
        textArea.setText(">>> ");

        // Add logic
        DisallowEditingPastContentFilter filter = new DisallowEditingPastContentFilter(4);
        ((AbstractDocument) textArea.getDocument()).setDocumentFilter(filter);
        textArea.addKeyListener(new TextAreaResponder(this, textArea, filter, this.calculator));

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollPane;
    }

    private static class TextAreaResponder implements KeyListener {
        private JFrame frame;
        private JTextArea area;
        private DisallowEditingPastContentFilter filter;
        private Calculator calculator;

        private String tempText;

        public TextAreaResponder(JFrame frame,
                                 JTextArea area,
                                 DisallowEditingPastContentFilter filter,
                                 Calculator calculator) {
            this.frame = frame;
            this.area = area;
            this.filter = filter;
            this.calculator = calculator;
            this.tempText = "";
        }

        @Override
        public void keyTyped(KeyEvent e) {
            try {
                if (e.getKeyChar() == '\n') {
                    // Get input
                    int lineno = this.area.getLineCount() - 2;
                    int start = this.area.getLineStartOffset(lineno);
                    int end = this.area.getLineEndOffset(lineno);
                    String enteredText = this.area.getText().substring(start, end);
                    if (enteredText.startsWith(">>> ") || enteredText.startsWith("... ")) {
                        enteredText = enteredText.substring(4);
                    }

                    this.tempText += enteredText;

                    // Run command and handle error processing
                    boolean complete = true;
                    String response = "";
                    try {
                        response = this.calculator.evaluate(this.tempText);
                    } catch (IncompleteInputError ex) {
                        complete = false;
                    } catch (EvaluationError ex) {
                        response = "ERROR: " + ex.getMessage();
                    } catch (QuitError ex) {
                        this.frame.dispose();
                        return;
                    } catch (Exception ex) {
                        this.frame.dispose();
                        throw ex;
                    }

                    // Add response
                    this.filter.allowEditing();
                    if (complete) {
                        this.area.append(response + "\n");
                        this.area.append(">>> ");
                        this.tempText = "";
                    } else {
                        this.area.append("... ");
                    }
                    this.filter.disallowEditing();

                    int newOffset = this.area.getDocument().getLength();
                    this.filter.setPromptPosition(newOffset);
                }
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Do nothing
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Do nothing
        }
    }

    // Code obtained from https://stackoverflow.com/a/10031051/646543
    private static class DisallowEditingPastContentFilter extends DocumentFilter {
        private int promptPosition;
        private boolean allowFreeEditing;

        public DisallowEditingPastContentFilter(int promptPosition) {
            this.promptPosition = promptPosition;
            this.allowFreeEditing = false;
        }

        public void setPromptPosition(int promptPosition) {
            this.promptPosition = promptPosition;
        }

        public void allowEditing() {
            this.allowFreeEditing = true;
        }

        public void disallowEditing() {
            this.allowFreeEditing = false;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (offset >= this.promptPosition || this.allowFreeEditing) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            if (offset >= this.promptPosition || this.allowFreeEditing) {
                super.remove(fb, offset, length);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)
                throws BadLocationException {
            if (offset >= this.promptPosition || this.allowFreeEditing) {
                super.replace(fb, offset, length, string, attr);
            }
        }
    }
}
