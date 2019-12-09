package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.controller.InstituteController;
import br.ufg.inf.quintacalendario.model.Institute;
import br.ufg.inf.quintacalendario.view.HomeView;
import br.ufg.inf.quintacalendario.view.console.util.ConsoleWrapper;

import java.io.PrintStream;
import java.util.List;

public class InstituteScreenConsole extends AbstractHeaderView implements HomeView {

    private ConsoleWrapper consoleWrapper;
    private InstituteController instituteController;

    public InstituteScreenConsole(PrintStream output, InstituteController instituteController) {
        super(output);
        setConsoleWrapper(new ConsoleWrapper());
        this.instituteController = instituteController;
    }

    @Override
    public void displayOptions() {
        displayHeader();
        displayInitialOptions();
        Integer option = getConsoleWrapper().askForInteger(displayInitialOptions());
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
            Integer codigo = getConsoleWrapper().askForInteger("Digite o codigo da Instituto que deseja remover");
            instituteController.remove(codigo);
            System.out.println("Instituto removida com sucesso");
        }
    }

    private void queryByDescription() {
        String descricao = getConsoleWrapper().askForString("Digite a descrição desejada", true);
        List<Institute> institutes = instituteController.listRecordsByDescription(descricao);
        displayInstitutes(institutes);
    }

    private List<Institute> queryInstitutes() {
        return instituteController.listRecords();
    }

    private void editInstitute() {
        List<Institute> institutes = queryInstitutes();
        if (institutes.isEmpty()) {
            System.out.println("Não existem institutos cadastrados para se realizar a alteração.");
        } else {
            displayInstitutes(institutes);
            Integer codigo = getConsoleWrapper().askForInteger("Digite o codigo da Instituto que deseja editar");

            Institute institute = instituteController.getById(codigo);

            if (institute == null) {
                System.out.println("Instituto não encontrado");
                editInstitute();
            } else {
                System.out.println(institute.getId() + " - " + institute.getName());

                String nome = getConsoleWrapper().askForString("Digite o novo nome do Instituto", true);
                instituteController.edit(codigo, nome);

                System.out.println("Instituto Alterado Com Sucesso");
            }
        }
    }

    private void createInstitute() {
        boolean result = false;
        while (!result) {
            String nome = getConsoleWrapper().askForString("Digite o nome do Instituto");
            result = instituteController.register(nome);
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

    public ConsoleWrapper getConsoleWrapper() {
        return consoleWrapper;
    }

    public void setConsoleWrapper(ConsoleWrapper consoleWrapper) {
        this.consoleWrapper = consoleWrapper;
    }

}
