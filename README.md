# README — Trabalho M2 Linguagens Formais e Autômatos

## Objetivo do Trabalho

Implementar uma linguagem utilizando o GALS, contendo:

- análise léxica
- análise sintática
- análise semântica
- interpretação de expressões binárias

A linguagem trabalha apenas com:

- números binários inteiros sem sinal
- variáveis
- operações matemáticas
- exibição de resultados

---

# Exemplo da Linguagem

```txt
A = 10;
B = 11;
B = 111 + A * B;
Show( B );
```

Saída esperada:

```txt
B = 1101
```

---

# Como a Linguagem Funciona

## Variáveis

As variáveis armazenam valores binários.

Exemplo:

```txt
A=10;
```

O valor `10` em binário corresponde ao decimal `2`.

---

# Operações Suportadas

| Operação | Símbolo |
|---|---|
| Soma | + |
| Subtração | - |
| Multiplicação | * |
| Divisão | / |
| Potência | ^ |
| Logaritmo base 2 | log |

---

# Estrutura Geral do Compilador

O projeto foi dividido em 3 partes principais:

| Parte | Responsabilidade |
|---|---|
| Léxico | reconhecer tokens |
| Sintático | validar a gramática |
| Semântico | executar os cálculos |

---

# Configuração do GALS

## Definições Regulares

```txt
ESPACO: [ \n\r\t]+
```

---

# Tokens

```txt
SHOW: "Show"
LOG: "log"
ID: [a-zA-Z][a-zA-Z0-9]*
NUM_BIN: [01]+
ATRIB: "="
MAIS: "+"
MENOS: "-"
VEZES: "*"
DIV: "/"
POT: "^"
ABRE: "("
FECHA: ")"
PVIRG: ";"
: {ESPACO}*
```

---

# Observação Importante sobre Espaços

A versão utilizada do GALS não ignorava espaços automaticamente.

Para resolver isso foi utilizado:

```txt
: {ESPACO}*
```

Isso faz com que:

- espaços
- tabs
- quebras de linha

sejam ignorados pelo analisador léxico.

Sem isso, ocorria:

```txt
Erro Léxico: Caractere não esperado
```

---

# Não-Terminais

```txt
<programa>
<lista_cmd>
<comando>
<expr>
<termo>
<fator>
<base>
```

---

# Gramática

```txt
<programa> ::= <lista_cmd> ;

<lista_cmd> ::= <comando> <lista_cmd>
              | <comando> ;

<comando> ::= ID #1 ATRIB <expr> PVIRG #2
            | SHOW ABRE ID #3 FECHA PVIRG #4 ;

<expr> ::= <termo>
         | <termo> MAIS <expr> #5
         | <termo> MENOS <expr> #6 ;

<termo> ::= <fator>
          | <fator> VEZES <termo> #7
          | <fator> DIV <termo> #8 ;

<fator> ::= <base>
          | <base> POT <fator> #9 ;

<base> ::= LOG ABRE <expr> FECHA #10
         | ABRE <expr> FECHA
         | NUM_BIN #11
         | ID #12 ;
```

---

# Ações Semânticas

## #1

Guarda o nome da variável.

Exemplo:

```txt
A = ...
```

---

## #2

Salva o resultado da expressão na variável.

---

## #3

Guarda o identificador usado no `Show()`.

---

## #4

Exibe o valor da variável em binário.

---

## #5

Realiza soma.

---

## #6

Realiza subtração.

Valida se o resultado não fica negativo.

---

## #7

Realiza multiplicação.

---

## #8

Realiza divisão.

Valida divisão por zero.

---

## #9

Realiza exponenciação.

---

## #10

Calcula logaritmo base 2.

---

## #11

Converte número binário para decimal.

Exemplo:

```txt
101 -> 5
```

---

## #12

Busca o valor de uma variável.

---

# Funcionamento do Semântico

O interpretador utiliza:

## HashMap

Para armazenar variáveis:

```java
HashMap<String, Integer>
```

Exemplo:

```txt
A -> 2
B -> 13
```

---

## Stack

Utilizada para calcular expressões.

Exemplo:

```txt
111 + A * B
```

Fluxo:

```txt
111 -> empilha 7
A -> empilha 2
B -> empilha 3
* -> desempilha 2 e 3
resultado -> empilha 6
+ -> desempilha 7 e 6
resultado -> empilha 13
```

---

# Conversões

## Binário → Decimal

```java
Integer.parseInt(valor, 2)
```

---

## Decimal → Binário

```java
Integer.toBinaryString(valor)
```

---

# Principal.java

O `Principal.java` foi criado para:

- ler o arquivo `.txt`
- criar os analisadores
- executar o parser
- mostrar erros léxicos, sintáticos e semânticos

---

# Como Executar

## Compilar

```bash
javac *.java
```

---

## Executar

```bash
java Principal programa.txt
```

