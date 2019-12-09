package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.controller.CategoryController;
import br.ufg.inf.quintacalendario.model.Category;
import br.ufg.inf.quintacalendario.view.HomeView;
import br.ufg.inf.quintacalendario.view.console.util.ConsoleWrapper;

import java.util.List;

/**
 * Console implementation for the Categories management
 */
public class CategoryScreenConsole extends AbstractHeaderView implements HomeView {

    private ConsoleWrapper console;
    private CategoryController categoryController;

    /**
     * @param console            Output for printing
     * @param categoryController controller for the category logic
     */
    public CategoryScreenConsole(
            ConsoleWrapper console,
            CategoryController categoryController
    ) {
        super(console);
        setConsole(console);
        this.categoryController = categoryController;
    }

    /**
     * Displays category options
     */
    @Override
    public void displayOptions() {
        displayHeader();
        displayHomeOptions();
        Integer opcao = console.askForInteger(displayHomeOptions());
        redirect(opcao);
    }

    private void redirect(Integer opcao) {
        switch (opcao) {
            case 1:
                createCategory();
                displayOptions();
                break;
            case 2:
                editCategory();
                displayOptions();
                break;
            case 3:
                remove();
                displayOptions();
                break;
            case 4:
                List<Category> categories = queryCategories();
                if (categories.isEmpty()) {
                    console.println("Não existem categorias cadastradas");
                } else {
                    displayCategories(categories);
                }
                displayOptions();
                break;
            case 5:
                queryByDescription();
                displayOptions();
                break;
            case 6:
                new HomeViewConsole(output).displayOptions();
                break;
            case 7:
                break;
            default:
                console.println("Opção invalida");
                displayOptions();
                break;
        }
    }

    /**
     * Displays logic for category removal
     */
    public void remove() {
        List<Category> categories = queryCategories();
        if (!categories.isEmpty()) {
            displayCategories(categories);
            Integer codigo = console.askForInteger("Digite o codigo da Categoria que deseja remover");
            categoryController.remove(codigo);
            console.println("Categoria removida com sucesso");
        }
    }

    private void queryByDescription() {
        String descricao = console.askForString("Digite a descrição desejada", true);
        List<Category> categories = categoryController.listRecordsByDescription(descricao);
        displayCategories(categories);
    }

    private List<Category> queryCategories() {
        return categoryController.listRecords();
    }

    private void editCategory() {
        List<Category> categories = queryCategories();
        if (categories.isEmpty()) {
            console.println("Não existem categorias cadastradas para se realizar a alteração.");
        } else {
            displayCategories(categories);
            Integer codigo = console.askForInteger("Digite o codigo da Categoria que deseja editar");

            Category Category = categoryController.getById(codigo);

            if (Category.getName().isEmpty()) {
                console.println("Categoria não encontrada");
                editCategory();
            } else {
                console.println(Category.getId() + " - " + Category.getName());

                String name = console.askForString("Digite o novo nome da Categoria", true);
                categoryController.edit(codigo, name);

                console.println("Categoria Alterada Com Sucesso");
            }
        }
    }

    private void createCategory() {
        boolean result = false;
        while (!result) {
            String nome = console.askForString("Digite o nome da Categoria");
            result = categoryController.register(nome);
        }

        console.println("Categoria Cadastrada Com Sucesso");
    }

    private void displayCategories(List<Category> categories) {
        categories.forEach(x -> console.println(x.getId() + " - " + x.getName()));
    }

    @Override
    public int askQuestion() {
        return 0;
    }

    public String displayHomeOptions() {
        return "1 - Cadastrar				  \n" +
                "2 - Editar					  \n" +
                "3 - Remover				  \n" +
                "4 - Pesquisar todos		  \n" +
                "5 - Pesquisar por descrição  \n" +
                "6 - Voltar ao menu principal \n" +
                "7 - Sair 					  \n";
    }

    public ConsoleWrapper getConsole() {
        return console;
    }

    public void setConsole(ConsoleWrapper console) {
        this.console = console;
    }
}
