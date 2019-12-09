package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.controller.RegionalController;
import br.ufg.inf.quintacalendario.model.Regional;
import br.ufg.inf.quintacalendario.view.HomeView;
import br.ufg.inf.quintacalendario.view.console.util.ConsoleInput;

import java.io.PrintStream;
import java.util.List;

public class RegionalScreenConsole extends AbstractHeaderView implements HomeView {

    private ConsoleInput consoleInput;

    public RegionalScreenConsole(PrintStream output) {
        super(output);
        setConsoleInput(new ConsoleInput());
    }

    @Override
    public void displayOptions() {
        displayHeader();
        displayInitialOptions();
        Integer opcao = getConsoleInput().askForInteger(displayInitialOptions());
        redirect(opcao);
    }

    private void redirect(Integer opcao) {
        switch (opcao) {
            case 1:
                createRegional();
                displayOptions();
                break;
            case 2:
                editRegional();
                displayOptions();
                break;
            case 3:
                remove();
                displayOptions();
                break;
            case 4:
                List<Regional> regionais = queryRegionals();
                if (regionais.isEmpty()) {
                    System.out.println("Não existem regionais cadastradas");
                } else {
                    displayRegionals(regionais);
                }
                displayOptions();
                break;
            case 5:
                queryByDescription();
                displayOptions();
                break;
            case 6:
                new HomeViewConsole(System.out).displayOptions();
                break;
            case 7:
                break;
            default:
                System.out.println("Opção invalida");
                displayOptions();
                break;
        }
    }

    public void remove() {
        List<Regional> regionals = queryRegionals();
        if (!regionals.isEmpty()) {
            displayRegionals(regionals);
            Integer codigo = getConsoleInput().askForInteger("Digite o codigo da regional que deseja remover");
            new RegionalController().remove(codigo);
            System.out.println("Regional removida com sucesso");
        }
    }

    private void queryByDescription() {
        String descricao = getConsoleInput().askForString("Digite a descrição desejada", true);
        List<Regional> regionais = new RegionalController().listRecordsByDescription(descricao);
        displayRegionals(regionais);
    }

    private List<Regional> queryRegionals() {
        return new RegionalController().listRecords();
    }

    private void editRegional() {
        List<Regional> regionais = queryRegionals();
        if (regionais.isEmpty()) {
            System.out.println("Não existem regionais cadastradas para se realizar a alteração.");
        } else {
            displayRegionals(regionais);
            Integer codigo = getConsoleInput().askForInteger("Digite o codigo da regional que deseja editar");

            Regional regional = new RegionalController().getById(codigo);

            if (regional.getName().isEmpty()) {
                System.out.println("Regional não encontrada");
                editRegional();
            } else {
                System.out.println(regional.getId() + " - " + regional.getName());

                String nome = getConsoleInput().askForString("Digite o novo nome da Regional", true);
                new RegionalController().edit(codigo, nome);

                System.out.println("Regional Alterada Com Sucesso");
            }
        }
    }

    private void createRegional() {
        boolean result = false;
        while (!result) {
            String nome = getConsoleInput().askForString("Digite o nome da regional");
            result = new RegionalController().register(nome);
        }

        System.out.println("Regional Cadastrada Com Sucesso");
    }

    private void displayRegionals(List<Regional> regionais) {
        regionais.forEach(x -> System.out.println(x.getId() + " - " + x.getName()));
    }

    @Override
    public int askQuestion() {
        return 0;
    }

    public String displayInitialOptions() {
        return "1 - Cadastrar				  \n" +
                "2 - Editar					  \n" +
                "3 - Remover				  \n" +
                "4 - Pesquisar todos		  \n" +
                "5 - Pesquisar por descrição  \n" +
                "6 - Voltar ao menu principal \n" +
                "7 - Sair 					  \n";
    }

    public ConsoleInput getConsoleInput() {
        return consoleInput;
    }

    public void setConsoleInput(ConsoleInput consoleInput) {
        this.consoleInput = consoleInput;
    }
}
