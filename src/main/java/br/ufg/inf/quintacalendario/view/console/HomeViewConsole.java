package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.controller.HomePageController;
import br.ufg.inf.quintacalendario.view.HomeView;
import br.ufg.inf.quintacalendario.view.console.util.ConsoleWrapper;

import java.io.PrintStream;

public class HomeViewConsole extends AbstractHeaderView implements HomeView {

    public HomeViewConsole(PrintStream output) {
        super(output);
    }

    public static void finishProgramExecution() {
        System.out.println(" - Finalizando programa - ");
        System.exit(0);
    }

    @Override
    public void displayOptions() {
    	int opcao = new ConsoleWrapper().askForInteger(displayOption());
    	new HomePageController().redirectScreenBy(opcao);
    }

    @Override
    public int askQuestion() {
        return 0;
    }

    public String displayOption() {
        return "Bem vindo \n" +
                "Selecione uma opção  \n" +
                "1 - Menu Eventos 	  \n" +
                "2 - Menu Regional 	  \n" +
                "3 - Menu Categoria   \n" +
                "4 - Menu Instituto   \n" +
                "5 - Logar 			  \n" +
                "6 - Sair 			  \n";
    }
}
