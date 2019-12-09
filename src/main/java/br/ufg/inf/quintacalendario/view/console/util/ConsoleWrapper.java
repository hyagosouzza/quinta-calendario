package br.ufg.inf.quintacalendario.view.console.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Classe auxiliar para encapsular a leitura de entrada do usuário no console.
 */
public class ConsoleWrapper extends PrintStream {
    private Scanner scanner;

    public ConsoleWrapper() {
        this(System.in, System.out);
    }

    public ConsoleWrapper(InputStream in, OutputStream out) {
        super(out);
        scanner = new Scanner(in);
    }

    /**
     * Imprime uma question para o usuário e lê a entrada, esperando que seja um Inteiro
     *
     * @param question O texto da pergutna que deve ser feita
     * @return O número já convertido para Inteiro
     * @see Integer#parseInt(String)
     */
    public Integer askForInteger(String question) {
        String stringInput = askForString(question);

        boolean validInput = false;
        int integerInput = 0;

        while (!validInput) {
            try {
                integerInput = Integer.parseInt(stringInput);
                validInput = true;
            } catch (NumberFormatException ignored) {
                println("Entrada inválida. Tente novamente");
                validInput = false;
                stringInput = askForString(question);
            }
        }

        return integerInput;
    }


    /**
     * Imprime uma question para o usuário que lê o texto de entrada
     *
     * @param question O texto da pergutna que deve ser feita
     * @return O texto que o usuário inseriu
     */
    public String askForString(String question) throws NumberFormatException {
        println(question);
        return scanner.nextLine();
    }

    /**
     * Imprime uma question para o usuário e lê o texto de entrada, podendo
     *
     * @param question O texto da pergutna que deve ser feita
     * @param required Flag para indicar se deve bloquear texto em branco
     * @return O texto que o usuário inseriu
     */
    public String askForString(String question, boolean required) throws NumberFormatException {
        String input = askForString(question);

        if (required) {
            while (input.trim().equals("")) {
                input = askForString(question);
            }
        }

        return input;
    }
}
