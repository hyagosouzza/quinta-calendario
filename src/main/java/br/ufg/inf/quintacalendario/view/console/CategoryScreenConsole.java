package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.controller.CategoryController;
import br.ufg.inf.quintacalendario.model.Category;
import br.ufg.inf.quintacalendario.view.HomeView;
import br.ufg.inf.quintacalendario.view.console.util.ConsoleInput;

import java.io.PrintStream;
import java.util.List;

public class CategoryScreenConsole extends AbstractHeaderView implements HomeView {

    private ConsoleInput consoleInput;
    private CategoryController categoryController;

    public CategoryScreenConsole(PrintStream output, CategoryController categoryController) {
        super(output);
        setConsoleInput(new ConsoleInput());
        this.categoryController = categoryController;
    }

    @Override
    public void displayOptions() {
        displayHeader();
        displayHomeOptions();
        Integer opcao = getConsoleInput().askForInteger(displayHomeOptions());
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
                    System.out.println("Não existem categorias cadastradas");
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
        List<Category> categories = queryCategories();
        if (!categories.isEmpty()) {
            displayCategories(categories);
            Integer codigo = getConsoleInput().askForInteger("Digite o codigo da Categoria que deseja remover");
            categoryController.remove(codigo);
            System.out.println("Categoria removida com sucesso");
        }
    }

    private void queryByDescription() {
        String descricao = getConsoleInput().askForString("Digite a descrição desejada", true);
        List<Category> categories = categoryController.listRecordsByDescription(descricao);
        displayCategories(categories);
    }

    private List<Category> queryCategories() {
        return categoryController.listRecords();
    }

    private void editCategory() {
        List<Category> categories = queryCategories();
        if (categories.isEmpty()) {
            System.out.println("Não existem categorias cadastradas para se realizar a alteração.");
        } else {
            displayCategories(categories);
            Integer codigo = getConsoleInput().askForInteger("Digite o codigo da Categoria que deseja editar");

            Category Category = categoryController.getById(codigo);

            if (Category.getName().isEmpty()) {
                System.out.println("Categoria não encontrada");
                editCategory();
            } else {
                System.out.println(Category.getId() + " - " + Category.getName());

                String name = getConsoleInput().askForString("Digite o novo nome da Categoria", true);
                categoryController.edit(codigo, name);

                System.out.println("Categoria Alterada Com Sucesso");
            }
        }
    }

    private void createCategory() {
        boolean result = false;
        while (!result) {
            String nome = getConsoleInput().askForString("Digite o nome da Categoria");
            result = categoryController.register(nome);
        }

        System.out.println("Categoria Cadastrada Com Sucesso");
    }

    private void displayCategories(List<Category> categories) {
        categories.stream().forEach(x -> System.out.println(x.getId() + " - " + x.getName()));
    }

    @Override
    public int askQuestion() {
        return 0;
    }

    public String displayHomeOptions() {
        StringBuilder tela = new StringBuilder();
        tela.append("1 - Cadastrar				  \n")
                .append("2 - Editar					  \n")
                .append("3 - Remover				  \n")
                .append("4 - Pesquisar todos		  \n")
                .append("5 - Pesquisar por descrição  \n")
                .append("6 - Voltar ao menu principal \n")
                .append("7 - Sair 					  \n");
        return tela.toString();
    }

    public ConsoleInput getConsoleInput() {
        return consoleInput;
    }

    public void setConsoleInput(ConsoleInput consoleInput) {
        this.consoleInput = consoleInput;
    }
}
