package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.view.HeaderView;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class AbstractHeaderView implements HeaderView, OutputAware {

    private static final String PATH_DELIMITER = "/";
    private static final String HEADER_PATH = PATH_DELIMITER.concat("view/header.txt");
    private String headerContent;
    protected PrintStream output;

    public AbstractHeaderView(PrintStream output) {
        this();
        this.output = output;
    }

    public AbstractHeaderView() {
        this.loadHeaderTemplate();
    }

    @Override
    public void displayHeader() {
        output.println(headerContent);
    }

    private void loadHeaderTemplate() {
        InputStream inputStream = this.getClass().getResourceAsStream(HEADER_PATH);
        try {
            this.headerContent = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException ignored) {
            this.headerContent = "";
        }
    }

    @Override
    public void setOutput(PrintStream output) {
        this.output = output;
    }
}
