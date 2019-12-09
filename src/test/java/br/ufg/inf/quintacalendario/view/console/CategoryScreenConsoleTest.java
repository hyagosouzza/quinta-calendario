package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.controller.CategoryController;
import br.ufg.inf.quintacalendario.view.console.util.ConsoleWrapper;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class CategoryScreenConsoleTest {

    @Test
    public void testDisplayOptions() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new ByteArrayInputStream("7\n".getBytes());
        CategoryScreenConsole categoryScreenConsole = new CategoryScreenConsole(new ConsoleWrapper(inputStream, outputStream), new CategoryController());

        categoryScreenConsole.displayOptions();

        assertEquals("===============================================================================\n" +
                "# CALENDÁRIO UFG - QUINTA na QUINTA                                           #\n" +
                "===============================================================================\n" +
                "1 - Cadastrar\t\t\t\t  \n" +
                "2 - Editar\t\t\t\t\t  \n" +
                "3 - Remover\t\t\t\t  \n" +
                "4 - Pesquisar todos\t\t  \n" +
                "5 - Pesquisar por descrição  \n" +
                "6 - Voltar ao menu principal \n" +
                "7 - Sair \t\t\t\t\t  \n" +
                "\n", new String(outputStream.toByteArray()));
    }
}