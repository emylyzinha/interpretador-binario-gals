import java.util.HashMap;
import java.util.Stack;

public class Semantico implements Constants
{
    // Tabela de variáveis
    private HashMap<String, Integer> variaveis = new HashMap<>();

    // Pilha para cálculo das expressões
    private Stack<Integer> pilha = new Stack<>();

    // Variável atual
    private String varAtual = "";

    public void executeAction(int action, Token token) throws SemanticError
    {
        switch (action)
        {
            case 1:
                varAtual = token.getLexeme();
                break;

            case 2:
                if (pilha.isEmpty())
                    throw new SemanticError(
                        "Erro: expressão vazia.",
                        token.getPosition()
                    );

                variaveis.put(varAtual, pilha.pop());
                break;

            case 3:
                varAtual = token.getLexeme();
                break;

            case 4:
                if (!variaveis.containsKey(varAtual))
                    throw new SemanticError(
                        "Variável '" + varAtual + "' não definida.",
                        token.getPosition()
                    );

                System.out.println(
                    varAtual + " = " +
                    decimalParaBinario(variaveis.get(varAtual))
                );
                break;

            case 5:
            {
                int b = pilha.pop();
                int a = pilha.pop();
                pilha.push(a + b);
                break;
            }

            case 6:
            {
                int b = pilha.pop();
                int a = pilha.pop();

                int resultado = a - b;

                if (resultado < 0)
                    throw new SemanticError(
                        "Resultado negativo não permitido.",
                        token.getPosition()
                    );

                pilha.push(resultado);
                break;
            }

            case 7:
            {
                int b = pilha.pop();
                int a = pilha.pop();
                pilha.push(a * b);
                break;
            }

            case 8:
            {
                int b = pilha.pop();
                int a = pilha.pop();

                if (b == 0)
                    throw new SemanticError(
                        "Divisão por zero.",
                        token.getPosition()
                    );

                pilha.push(a / b);
                break;
            }

            case 9:
            {
                int exp = pilha.pop();
                int base = pilha.pop();

                pilha.push((int)Math.pow(base, exp));
                break;
            }

            case 10:
            {
                int val = pilha.pop();

                if (val <= 0)
                    throw new SemanticError(
                        "Log inválido.",
                        token.getPosition()
                    );

                pilha.push((int)(Math.log(val) / Math.log(2)));
                break;
            }

            case 11:
            {
                pilha.push(
                    Integer.parseInt(token.getLexeme(), 2)
                );
                break;
            }

            case 12:
            {
                String nomeVar = token.getLexeme();

                if (!variaveis.containsKey(nomeVar))
                    throw new SemanticError(
                        "Variável '" + nomeVar + "' não definida.",
                        token.getPosition()
                    );

                pilha.push(variaveis.get(nomeVar));
                break;
            }
        }
    }

    private String decimalParaBinario(int valor)
    {
        return Integer.toBinaryString(valor);
    }
}