package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.view.HomeView;

import java.io.PrintStream;

public class UserScreenConsole extends AbstractHeaderView implements HomeView {

    public UserScreenConsole(PrintStream output) {
        super(output);
    }

    @Override
    public void displayOptions() {
    }

    @Override
    public int askQuestion() {
        return 0;
    }

}
