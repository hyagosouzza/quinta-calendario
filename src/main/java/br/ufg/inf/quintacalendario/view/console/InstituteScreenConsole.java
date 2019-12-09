package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.controller.InstituteController;
import br.ufg.inf.quintacalendario.model.Institute;
import br.ufg.inf.quintacalendario.view.HomeView;
import br.ufg.inf.quintacalendario.view.console.util.ConsoleInput;

import java.io.PrintStream;
import java.util.List;

public class InstituteScreenConsole extends AbstractHeaderView implements HomeView {

    private ConsoleInput consoleInput;

    public InstituteScreenConsole(PrintStream output) {
        super(output);
        setConsoleInput(new ConsoleInput());
    }

    @Override
    public void displayOptions() {
        displayHeader();
        displayInitialOptions();
        Integer option = getConsoleInput().askForInteger(displayInitialOptions());
        redirect(option);
    }

    private void redirect(Integer opcao) {
        switch (opcao) {
            case 1:
                createInstitute();
                displayOptions();
                break;
            case 2:
                editInstitute();
                displayOptions();
                break;
            case 3:
                remove();
                displayOptions();
                break;
            case 4:
                List<Institute> institutes = queryInstitutes();
                if (institutes.isEmpty()) {
                    System.out.println("Não existem institutos cadastradas");
                } else {
                    displayInstitutes(institutes);
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
        List<Institute> institutes = queryInstitutes();
        if (!institutes.isEmpty()) {
            displayInstitutes(institutes);
            Integer codigo = getConsoleInput().askForInteger("Digite o codigo da Instituto que deseja remover");
            new InstituteController().remove(codigo);
            System.out.println("Instituto removida com sucesso");
        }
    }

    private void queryByDescription() {
        String descricao = getConsoleInput().askForString("Digite a descrição desejada", true);
        List<Institute> institutes = new InstituteController().listRecordsByDescription(descricao);
        displayInstitutes(institutes);
    }

    private List<Institute> queryInstitutes() {
        return new InstituteController().listRecords();
    }

    private void editInstitute() {
        List<Institute> institutes = queryInstitutes();
        if (institutes.isEmpty()) {
            System.out.println("Não existem institutos cadastrados para se realizar a alteração.");
        } else {
            displayInstitutes(institutes);
            Integer codigo = getConsoleInput().askForInteger("Digite o codigo da Instituto que deseja editar");

            Institute institute = new InstituteController().getById(codigo);

            if (institute == null) {
                System.out.println("Instituto não encontrado");
                editInstitute();
            } else {
                System.out.println(institute.getId() + " - " + institute.getName());

                String nome = getConsoleInput().askForString("Digite o novo nome do Instituto", true);
                new InstituteController().edit(codigo, nome);

                System.out.println("Instituto Alterado Com Sucesso");
            }
        }
    }

    private void createInstitute() {
        boolean result = false;
        while (!result) {
            String nome = getConsoleInput().askForString("Digite o nome do Instituto");
            result = new InstituteController().register(nome);
        }

        System.out.println("Instituto Cadastrado Com Sucesso");
    }

    private void displayInstitutes(List<Institute> institutes) {
        institutes.forEach(x -> System.out.println(x.getId() + " - " + x.getName()));
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
