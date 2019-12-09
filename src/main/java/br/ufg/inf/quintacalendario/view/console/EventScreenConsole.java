package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.controller.EventController;
import br.ufg.inf.quintacalendario.model.Category;
import br.ufg.inf.quintacalendario.model.Event;
import br.ufg.inf.quintacalendario.model.Institute;
import br.ufg.inf.quintacalendario.model.Regional;
import br.ufg.inf.quintacalendario.view.HomeView;
import br.ufg.inf.quintacalendario.view.console.util.ConsoleWrapper;

import java.io.PrintStream;
import java.util.List;

public class EventScreenConsole extends AbstractHeaderView implements HomeView {

    private ConsoleWrapper consoleWrapper;
    private EventController eventController;

    public EventScreenConsole(PrintStream out, EventController eventController) {
        super(out);
        setConsoleWrapper(new ConsoleWrapper());
        this.eventController = eventController;
    }

    @Override
    public void displayOptions() {
        displayHeader();
        displayInitialOption();
        Integer opcao = getConsoleWrapper().askForInteger(displayInitialOption().toString());
        redirect(opcao);
    }

    @Override
    public int askQuestion() {
        return 0;
    }

    private void redirect(Integer opcao) {
        switch (opcao) {
            case 1:
                createEvent();
                displayOptions();
                break;
            case 2:
                displayOptions();
                break;
            case 3:
                displayOptions();
                break;
            case 4:
                queryEvents();
                displayOptions();
                break;
            case 5:
                listarPorDescricao();
                displayOptions();
                break;
            case 6:
                queryByDateRange();
                displayOptions();
                break;
            case 7:
            case 8:
            case 9:
                System.out.println("Opção em desenvolvimento");
                displayOptions();
                break;
            case 10:
                new HomeViewConsole(System.out).displayOptions();
                break;
            case 11:
                break;
            default:
                System.out.println("Opção invalida");
                displayOptions();
                break;
        }
    }

    public String displayInitialOption() {
        return "1 - Cadastrar				  \n" +
                "2 - Editar					  \n" +
                "3 - Remover				  \n" +
                "4 - Pesquisar todos		  \n" +
                "5 - Pesquisar por descrição  \n" +
                "6 - Pesquisar por periodo    \n" +
                "7 - Pesquisar por instituto - ** Em desenvolvimento ** \n" +
                "8 - Pesquisar por regional  - ** Em desenvolvimento ** \n" +
                "9 - Pesquisar por categoria - ** Em desenvolvimento ** \n" +
                "10 - Voltar ao menu principal \n" +
                "11 - Sair 					  \n";
    }

    private void listarPorDescricao() {
        String descricaoEvento = getConsoleWrapper().askForString("Digite a descricão do evento", true);
        List<Event> events = eventController.listRecordsByDescription(descricaoEvento);
        if (events.isEmpty()) {
            System.out.println("Não existem eventos cadastrados com essa descrição");
        } else {
            events.forEach(x -> System.out.println(x.getId() + " - " + x.getTitle()));
        }
    }

    public void queryByDateRange() {
        String dataInicial = getConsoleWrapper().askForString("Digite a data inicial, no formato dd/MM/YYYY", true);
        String dataFinal = getConsoleWrapper().askForString("Digite a data final, no formato dd/MM/YYYY", true);

        List<Event> events = eventController.listByPeriod(dataInicial, dataFinal);
        if (events.isEmpty()) {
            System.out.println("Não existe eventos cadastrados no periodo informado");
        } else {
            events.forEach(x -> System.out.println(x.getId() + " - " + x.getDescription()));
        }
    }

    private void queryEvents() {
        EventController controller = eventController;
        List<Event> events = controller.listRecords();
        events.forEach(x -> System.out.println(x.getId() + " - " + x.getTitle()));
    }

    private void createEvent() {
        if (validadeEventData()) {

            String title = getConsoleWrapper().askForString("Digite o titulo do evento", true);
            String description = getConsoleWrapper().askForString("Digite a descricão do evento", true);
            String initialDate = getConsoleWrapper().askForString("Digite a data inicial do evento, no formato dd/MM/YYYY", true);
            String finalDate = getConsoleWrapper().askForString("Digite a data final do evento, no formato dd/MM/YYYY", true);

            int categoryCode = selectCategoryCode();
            int regionalCode = selectRegionalCode();
            int instituteCode = displayInstituteCode();

            boolean result = eventController.register(description, title, initialDate, finalDate, categoryCode, regionalCode, instituteCode);
            if (result) {
                System.out.println("Evento cadastrado com sucesso");
            }
        }
    }

    private int selectRegionalCode() {
        List<Regional> regionais = eventController.listRegionals();
        boolean result;
        int codigoRegional = 0;
        do {
            Integer codigo;
            regionais.forEach(x -> System.out.println(x.getId() + " - " + x.getName()));
            codigo = getConsoleWrapper().askForInteger("Digite o codigo da regional do evento");

            result = (regionais.stream().anyMatch(x -> x.getId() == codigo));

            if (!result) {
                System.out.println("Codigo invalido");
            } else {
                codigoRegional = codigo;
            }

        } while (!result);

        return codigoRegional;
    }

    private int displayInstituteCode() {
        List<Institute> institutes = eventController.listInstitutes();
        boolean result;
        int codigoInstituto = 0;

        do {
            Integer codigo;
            institutes.forEach(x -> System.out.println(x.getId() + " - " + x.getName()));
            codigo = getConsoleWrapper().askForInteger("Digite o codigo do instituto do evento");

            result = (institutes.stream().anyMatch(x -> x.getId() == codigo));

            if (!result) {
                System.out.println("Codigo invalido");
            } else {
                codigoInstituto = codigo;
            }

        } while (!result);

        return codigoInstituto;
    }

    private int selectCategoryCode() {
        List<Category> categories = eventController.listCategories();
        boolean result;
        int codigoCategoria = 0;

        do {
            Integer codigo;
            categories.forEach(x -> System.out.println(x.getId() + " - " + x.getName()));
            codigo = getConsoleWrapper().askForInteger("Digite o codigo da categoria do evento");

            result = (categories.stream().anyMatch(x -> x.getId() == codigo));

            if (!result) {
                System.out.println("Codigo invalido");
            } else {
                codigoCategoria = codigo;
            }

        } while (!result);

        return codigoCategoria;
    }

    private boolean validadeEventData() {
        boolean result = true;

        List<Regional> regionals = eventController.listRegionals();
        List<Institute> institutes = eventController.listInstitutes();
        List<Category> categories = eventController.listCategories();

        if (regionals == null || regionals.isEmpty()) {
            System.out.println("Não existem regionais cadastradas, efetue o seu cadastro antes de prosseguir");
            result = false;
        }

        if (institutes == null || institutes.isEmpty()) {
            System.out.println("Não existem institutos cadastrados, efetue o seu cadastro antes de prosseguir");
            result = false;
        }

        if (categories == null || categories.isEmpty()) {
            System.out.println("Não existem categorias cadastradas, efetue o seu cadastro antes de prosseguir");
            result = false;
        }

        return result;
    }

    public ConsoleWrapper getConsoleWrapper() {
        return consoleWrapper;
    }

    public void setConsoleWrapper(ConsoleWrapper consoleWrapper) {
        this.consoleWrapper = consoleWrapper;
    }
}
