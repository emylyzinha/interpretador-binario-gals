import java.io.*;
import java.nio.file.*;

public class Principal
{
    public static void main(String[] args)
    {
        // Verifica se foi passado o nome do arquivo como argumento
        if (args.length < 1)
        {
            System.out.println("Uso: java Principal <arquivo.txt>");
            System.out.println("Exemplo: java Principal programa.txt");
            return;
        }

        try
        {
            // Lê o conteúdo do arquivo
            String codigo = new String(Files.readAllBytes(Paths.get(args[0])));

            System.out.println("=== Executando: " + args[0] + " ===");
            System.out.println();

            // Cria os analisadores
            Lexico lexico       = new Lexico(codigo);
            Sintatico sintatico = new Sintatico();
            Semantico semantico = new Semantico();

		Token t;

		System.out.println("TOKENS ENCONTRADOS:");

		while ((t = lexico.nextToken()) != null)
		{
		    System.out.println(
		        "Classe: " + t.getId() +
		        " | Lexema: " + t.getLexeme()
		    );
		}

		lexico = new Lexico(codigo);

            // Liga o léxico ao sintático
            sintatico.parse(lexico, semantico);

            System.out.println();
            System.out.println("=== Programa executado com sucesso! ===");
        }
        catch (LexicalError e)
        {
            System.err.println("Erro Léxico: " + e.getMessage());
        }
        catch (SyntaticError e)
        {
            System.err.println("Erro Sintático: " + e.getMessage());
        }
        catch (SemanticError e)
        {
            System.err.println("Erro Semântico: " + e.getMessage());
        }
        catch (AnalysisError e)
        {
            System.err.println("Erro de Análise: " + e.getMessage());
        }
        catch (IOException e)
        {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}