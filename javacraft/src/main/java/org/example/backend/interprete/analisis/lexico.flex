package org.example.backend.interprete.analisis;

import java_cup.runtime.*;
import org.example.backend.interprete.error.*;
import java.util.LinkedList;


%%
%public
%class Scan
%cup
%line
%column
%ignorecase

%init{
    yyline = 1;
    yycolumn = 1;
%init}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]
ESCAPE         = [\n] | [\t] | [\r] | [\"]
CHAR           = "." | {ESCAPE}

IDENTIFIER     = [A-Za-z_][A-Za-z0-9_]*
DIGIT          = [0-9]+ | [0-9]+\.[0-9]+
COMENTARIO     = [//][^\n]* | "/*"([^*]|" *"[^/])*"*/"
CADENA         = "\"" {InputCharacter}* "\""
CARACTER       = "\'" {CHAR} "\'"

%{
    public LinkedList<ErrorM> errores = new LinkedList<>();

    StringBuffer str = new StringBuffer();

    private Symbol symbol(int tipo){
        return new Symbol(tipo, yyline, yycolumn);
    }

    private Symbol symbol(int tipo, Object value){
        return new Symbol(tipo, yyline, yycolumn, value);
    }
%}


%eofval{
    return symbol(ParserSym.EOF);
%eofval}

%%
    <YYINITIAL>{
            /*  Tipo de datos   */
            "int"      { return symbol(ParserSym.INT, yytext()); }
            "double"   { return symbol(ParserSym.DOUBLE, yytext()); }
            "bool"     { return symbol(ParserSym.BOOL, yytext()); }
            "char"     { return symbol(ParserSym.CHAR, yytext()); }
            "String"   { return symbol(ParserSym.STRING, yytext()); }

            /*  Tipo de variable    */
            "var"     { return symbol(ParserSym.VAR, yytext()); }
            "const"   { return symbol(ParserSym.CONST, yytext()); }

            /*  Operadores aritmeticos  */
            "+"    { return symbol(ParserSym.PLUS, yytext()); }
            "-"    { return symbol(ParserSym.MINUS, yytext()); }
            "*"    { return symbol(ParserSym.TIMES, yytext()); }
            "**"   { return symbol(ParserSym.POWER, yytext()); }
            "/"    { return symbol(ParserSym.DIV, yytext()); }
            "%"    { return symbol(ParserSym.MOD, yytext()); }

            /*  Operadores logicos   */
            "||"   { return symbol(ParserSym.OR, yytext()); }
            "&&"   { return symbol(ParserSym.AND, yytext()); }
            "^"    { return symbol(ParserSym.XOR, yytext()); }
            "!"    { return symbol(ParserSym.NOT, yytext()); }

            /*  Operaciones relacionales    */
            "=="   { return symbol(ParserSym.EQ, yytext()); }
            "!="   { return symbol(ParserSym.NE, yytext()); }
            "<"    { return symbol(ParserSym.MENOR, yytext()); }
            "<="   { return symbol(ParserSym.MENOR_IGUAL, yytext()); }
            ">"    { return symbol(ParserSym.MAYOR, yytext()); }
            ">="   { return symbol(ParserSym.MAYOR_IGUAL, yytext()); }

            /*  Simbolos de agrupacion   */
            "("    { return symbol(ParserSym.LPAREN, yytext()); }
            ")"    { return symbol(ParserSym.RPAREN, yytext()); }

            /*  Simbolos de asignacion   */
            "="    { return symbol(ParserSym.ASSIGN, yytext()); }

            /*  Simbolos de finalizacion y sentencias de agrupacion */
            ";"    { return symbol(ParserSym.FIN_INSTRUCCION, yytext()); }
            "{"    { return symbol(ParserSym.LBRACE, yytext()); }
            "}"    { return symbol(ParserSym.RBRACE, yytext()); }

            /*  Sentencias de control   */
            "if"         { return symbol(ParserSym.IF, yytext()); }
            "else"       { return symbol(ParserSym.ELSE, yytext()); }
            "match"      { return symbol(ParserSym.MATCH, yytext()); }
            "default"    { return symbol(ParserSym.DEFAULT, yytext()); }

            /*  Sentencias ciclicas */
            "while"    { return symbol(ParserSym.WHILE, yytext()); }
            "for"      { return symbol(ParserSym.FOR, yytext()); }
            "do"       { return symbol(ParserSym.DO, yytext()); }

            /*  Sentencias de tranferencia  */
            "break"      { return symbol(ParserSym.BREAK, yytext()); }
            "continue"   { return symbol(ParserSym.CONTINUE, yytext()); }
            "return"     { return symbol(ParserSym.RETURN, yytext()); }

            /*  Funciones   */
            "println"    { return symbol(ParserSym.PRINTLN, yytext()); }

            /*  Variables   */
            "true"       { return symbol(ParserSym.TRUE, yytext()); }
            "false"      { return symbol(ParserSym.FALSE, yytext()); }
            {CADENA}     { return symbol(ParserSym.CADENA, yytext()); }
            {CARACTER}   { return symbol(ParserSym.CARACTER, yytext()); }

            /*  otros   */
            ":"         { return symbol(ParserSym.DOS_PUNTOS, yytext()); }
            ","         { return symbol(ParserSym.COMA, yytext()); }
    }


    <YYINITIAL>{
            /* Identificadores */
            {IDENTIFIER}   { return symbol(ParserSym.ID, yytext()); }
            /* Numeros/digitos */
            {DIGIT}        { return symbol(ParserSym.NUMBER, yytext()); }
            /* Comentarios */
            {COMENTARIO}   { /* no hacer nada */ }
            /* espacios en blanco */
            {WhiteSpace}+  { /* no haceer nada */}

    }
    
    <YYINITIAL> . {
              errores.add(new ErrorM(TipoError.LEXICO, "El caracter no es valido: "+yytext(), yyline, yycolumn));
    }