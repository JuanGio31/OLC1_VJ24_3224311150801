package org.example.backend.interprete.analisis;

import java_cup.runtime.*;
//import org.apache.commons.text.StringEscapeUtils;
import org.example.backend.interprete.error.*;
import java.util.LinkedList;


%%
%cup
%public
%class Scan
%line
%char
%column
%full
//%debug
%ignorecase

%init{
    yyline = 1;
    yycolumn = 1;
%init}

LineTerminator       = \r|\n|\r\n
InputCharacter       = [^\r\n]
CommentContent       = ( [^*] | \*+ [^/*] )*
ComentarioMultiLine  = "/*" {CommentContent} "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
WhiteSpace     = {LineTerminator} | [ \t\f]

ESCAPE         = [\n] | [\t] | [\r] | [\"]
CHAR           = "." | {ESCAPE}

IDENTIFIER     = [A-Za-z_][A-Za-z0-9_]*
ENTERO         = [0-9]+
DECIMAL        = [0-9]+"."[0-9]+
COMENTARIO     =  {EndOfLineComment} | {ComentarioMultiLine}
CADENA         = [\"] {InputCharacter}* [\"]
CARACTER       = "\'" {CHAR} "\'"
CHARACTER      = "\'" [^\'] "\'"

%{
    public LinkedList<Errores> listaErrores = new LinkedList<>();

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
            "int"      {     return symbol(ParserSym.INT, yytext());      }
            "double"   {     return symbol(ParserSym.DOUBLE, yytext());   }
            "bool"     {     return symbol(ParserSym.BOOL, yytext());     }
            "char"     {     return symbol(ParserSym.CHAR, yytext());     }
            "String"   {     return symbol(ParserSym.STRING, yytext());   }

            /*  Tipo de variable    */
            "var"     {     return symbol(ParserSym.VAR, yytext());     }
            "const"   {     return symbol(ParserSym.CONST, yytext());   }

            /*  Operadores aritmeticos  */
            "+"    {     return symbol(ParserSym.MAS, yytext());     }
            "-"    {     return symbol(ParserSym.MENOS, yytext());   }
            "*"    {     return symbol(ParserSym.TIMES, yytext());   }
           // "**"   { return symbol(ParserSym.POW, yytext()); }
            "/"    {     return symbol(ParserSym.DIVIDE, yytext());  }
            "%"    {     return symbol(ParserSym.MOD, yytext());     }

            /*  Operadores logicos   */
            "||"   {     return symbol(ParserSym.OR, yytext());      }
            "&&"   {     return symbol(ParserSym.AND, yytext());     }
            "^"    {     return symbol(ParserSym.XOR, yytext());     }
            "!"    {     return symbol(ParserSym.NOT, yytext());     }

            /*  Operaciones relacionales    */
            "=="   { return symbol(ParserSym.EQ, yytext()); }
            "!="   {     return symbol(ParserSym.NE, yytext());          }
            "<"    {     return symbol(ParserSym.MENOR, yytext());       }
            "<="   {     return symbol(ParserSym.MENOR_IGUAL, yytext()); }
            ">"    {     return symbol(ParserSym.MAYOR, yytext());       }
            ">="   {     return symbol(ParserSym.MAYOR_IGUAL, yytext()); }

            /*  Simbolos de agrupacion   */
            "("    {     return symbol(ParserSym.LPAREN, yytext());      }
            ")"    {     return symbol(ParserSym.RPAREN, yytext());      }

            /*  Simbolos de asignacion   */
            "="    {     return symbol(ParserSym.IGUAL, yytext());     }

            /*  Simbolos de finalizacion y sentencias de agrupacion */
            ";"    {     return symbol(ParserSym.FIN_INSTRUCCION, yytext());     }
            "{"    {     return symbol(ParserSym.LBRACE, yytext());              }
            "}"    {     return symbol(ParserSym.RBRACE, yytext());              }

            /*  Sentencias de control   */
            "if"         {     return symbol(ParserSym.IF, yytext());      }
            "else"       {     return symbol(ParserSym.ELSE, yytext());    }
            "match"      {     return symbol(ParserSym.MATCH, yytext());   }
            "default"    {     return symbol(ParserSym.DEFAULT, yytext()); }

            /*  Sentencias ciclicas */
            "while"    {     return symbol(ParserSym.WHILE, yytext());    }
            "for"      {     return symbol(ParserSym.FOR, yytext());      }
            "do"       {     return symbol(ParserSym.DO, yytext());  }

            /*  Sentencias de tranferencia  */
            "break"      { return symbol(ParserSym.BREAK, yytext()); }
            "continue"   { return symbol(ParserSym.CONTINUE, yytext()); }
            "return"     { return symbol(ParserSym.RETURN, yytext()); }

            /*  Funciones   */
            "println"    {     return symbol(ParserSym.PRINTLN, yytext());     }

            /*  Variables   */
            "true"       {     return symbol(ParserSym.BOOLEAN, yytext());     }
            "false"      {     return symbol(ParserSym.BOOLEAN, yytext());     }
            //{CADENA}     { return symbol(ParserSym.CADENA, yytext());   }
            {CHARACTER}  {     return symbol(ParserSym.CARACTER, yytext().charAt(1));    }

            /*  otros   */
            ":"         {     return symbol(ParserSym.DOS_PUNTOS, yytext());    }
            ","         {     return symbol(ParserSym.COMA, yytext());          }
    }

    <YYINITIAL> {CADENA} {
            String cadena = yytext();
            cadena = cadena.substring(1, cadena.length()-1);
            return symbol(ParserSym.CADENA, cadena);
    }


    <YYINITIAL>{
            /* Identificadores */
            {IDENTIFIER}    {     return symbol(ParserSym.ID, yytext());           }
            /* Numeros/digitos */
            {ENTERO}        {     return symbol(ParserSym.ENTERO, yytext());       }
            {DECIMAL}       {     return symbol(ParserSym.DECIMAL, yytext());      }
            /* Comentarios */
            {COMENTARIO}    {     /* no haceer nada */       }
            /* espacios en blanco */
            {WhiteSpace}+   {     /* no haceer nada */        }
    }

[^] {     listaErrores.add(new Errores(TipoError.LEXICO, "El caracter no es valido: "+yytext(), yyline, yycolumn));    }