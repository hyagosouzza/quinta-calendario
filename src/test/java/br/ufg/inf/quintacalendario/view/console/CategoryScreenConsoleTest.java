package br.ufg.inf.quintacalendario.view.console;

import br.ufg.inf.quintacalendario.controller.CategoryController;
import br.ufg.inf.quintacalendario.view.console.util.ConsoleWrapper;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class CategoryScreenConsoleTest {

    @Test
    public void testDisplayOptions() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new ByteArrayInputStream("7\n".getBytes());
        CategoryScreenConsole categoryScreenConsole = new CategoryScreenConsole(new ConsoleWrapper(inputStream, outputStream), new CategoryController());

        categoryScreenConsole.displayOptions();

        String generatedString = new String(outputStream.toByteArray());

        assertThat(generatedString, containsString("CALENDÁRIO UFG - QUINTA na QUINTA"));
        assertThat(generatedString, containsString("1 - Cadastrar"));
        assertThat(generatedString, containsString("2 - Editar"));
        assertThat(generatedString, containsString("3 - Remover"));
        assertThat(generatedString, containsString("4 - Pesquisar todos"));
        assertThat(generatedString, containsString("5 - Pesquisar por descrição"));
        assertThat(generatedString, containsString("6 - Voltar ao menu principal"));
        assertThat(generatedString, containsString("7 - Sair"));
    }
}
