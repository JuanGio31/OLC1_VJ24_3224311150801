package org.example.backend.interprete.expresion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.ErrorM;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

public class Aritmeticas extends Instruccion {
    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresAritmeticos operacion;
    private Instruccion operandoUnico;

    //negacion
    public Aritmeticas(Instruccion operandoUnico, OperadoresAritmeticos operacion, int linea, int col) {
        super(new Tipo(TipoDeDato.INT), linea, col);
        this.operacion = operacion;
        this.operandoUnico = operandoUnico;
    }

    //cualquier operacion menos negacion
    public Aritmeticas(Instruccion operando1, Instruccion operando2, OperadoresAritmeticos operacion, int linea, int col) {
        super(new Tipo(TipoDeDato.INT), linea, col);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        Object opIzq = null, opDer = null, Unico = null;
        if (this.operandoUnico != null) {
            Unico = this.operandoUnico.interpretar(arbol, tabla);
            if (Unico instanceof ErrorM) {
                return Unico;
            }
        } else {
            opIzq = this.operando1.interpretar(arbol, tabla);
            if (opIzq instanceof ErrorM) {
                return opIzq;
            }
            opDer = this.operando2.interpretar(arbol, tabla);
            if (opDer instanceof ErrorM) {
                return opDer;
            }
        }

        return switch (operacion) {
            case SUMA -> this.suma(opIzq, opDer);
            case RESTA -> this.resta(opIzq, opDer);
            case MULTIPLICACION -> this.multiplicacion(opIzq, opDer);
            case DIVISION -> this.division(opIzq, opDer);
            case NEGACION -> this.negacion(Unico);
            default -> new ErrorM(TipoError.SEMANTICO, "Operador invalido", this.linea, this.columna);
        };
    }

    public Object suma(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case INT -> {
                switch (tipo2) {
                    case INT -> {
                        this.tipo.setTipo(TipoDeDato.INT);
                        return (int) op1 + (int) op2;
                    }
                    case DOUBLE -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (int) op1 + (double) op2;
                    }
                    case CHAR -> {
                        this.tipo.setTipo(TipoDeDato.CHAR);
                        int aux = Character.getNumericValue(op2.toString().charAt(0));
                        System.out.println(aux);
                        return (int) op1 + aux;
                    }
                    case STRING -> {
                        this.tipo.setTipo(TipoDeDato.STRING);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new ErrorM(TipoError.SEMANTICO, "Suma erronea", this.linea, this.columna);
                    }
                }
            }
            case DOUBLE -> {
                switch (tipo2) {
                    case INT -> {
                        this.tipo.setTipo(TipoDeDato.INT);
                        return (double) op1 + (int) op2;
                    }
                    case DOUBLE -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (double) op1 + (double) op2;
                    }
                    case CHAR -> {
                        this.tipo.setTipo(TipoDeDato.CHAR);
                        return (double) op1 + (int) op2;
                    }
                    case STRING -> {
                        this.tipo.setTipo(TipoDeDato.STRING);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new ErrorM(TipoError.SEMANTICO, "Suma erronea", this.linea, this.columna);
                    }
                }
            }
            case CHAR -> {
                switch (tipo2) {
                    case INT -> {
                        this.tipo.setTipo(TipoDeDato.INT);
                        return (int) op1 + (int) op2;
                    }
                    case DOUBLE -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (int) op1 + (double) op2;
                    }
                    case CHAR -> {
                        this.tipo.setTipo(TipoDeDato.CHAR);
                        return op1.toString() + op2.toString();
                    }
                    case STRING -> {
                        this.tipo.setTipo(TipoDeDato.STRING);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new ErrorM(TipoError.SEMANTICO, "Suma erronea", this.linea, this.columna);
                    }
                }
            }
            case STRING -> {
                this.tipo.setTipo(TipoDeDato.STRING);
                return op1.toString() + op2.toString();
            }
            default -> {
                return new ErrorM(TipoError.SEMANTICO, "Suma erronea", this.linea, this.columna);

            }
        }
    }

    public Object resta(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case INT -> {
                switch (tipo2) {
                    case INT -> {
                        this.tipo.setTipo(TipoDeDato.INT);
                        return (int) op1 - (int) op2;
                    }
                    case DOUBLE -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (double) op1 - (double) op2;
                    }
                    case CHAR -> {
                        this.tipo.setTipo(TipoDeDato.CHAR);
                        return (int) op1 - (int) op2;
                    }
                    default -> {
                        return new ErrorM(TipoError.SEMANTICO, "Resta erronea", this.linea, this.columna);
                    }
                }
            }
            case DOUBLE -> {
                switch (tipo2) {
                    case INT -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (double) op1 - (int) op2;
                    }
                    case DOUBLE -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (double) op1 - (double) op2;
                    }
                    case CHAR -> {
                        this.tipo.setTipo(TipoDeDato.CHAR);
                        return (double) op1 + (int) op2;
                    }
                    default -> {
                        return new ErrorM(TipoError.SEMANTICO, "Resta erronea", this.linea, this.columna);
                    }
                }
            }
            case CHAR -> {
                switch (tipo2) {
                    case INT -> {
                        this.tipo.setTipo(TipoDeDato.INT);
                        return (int) op1 - (int) op2;
                    }
                    case DOUBLE -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (int) op1 - (double) op2;
                    }
                    default -> {
                        return new ErrorM(TipoError.SEMANTICO, "Resta erronea", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new ErrorM(TipoError.SEMANTICO, "Resta erronea", this.linea, this.columna);

            }
        }
    }

    public Object multiplicacion(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case INT -> {
                switch (tipo2) {
                    case INT -> {
                        this.tipo.setTipo(TipoDeDato.INT);
                        return (int) op1 * (int) op2;
                    }
                    case DOUBLE -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (double) op1 * (double) op2;
                    }
                    case CHAR -> {
                        this.tipo.setTipo(TipoDeDato.CHAR);
                        return (int) op1 * (int) op2;
                    }
                    default -> {
                        return new ErrorM(TipoError.SEMANTICO, "Multiplicacion erronea", this.linea, this.columna);
                    }
                }
            }
            case DOUBLE -> {
                switch (tipo2) {
                    case INT -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (double) op1 * (int) op2;
                    }
                    case DOUBLE -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (double) op1 * (double) op2;
                    }
                    case CHAR -> {
                        this.tipo.setTipo(TipoDeDato.CHAR);
                        return (double) op1 * (int) op2;
                    }
                    default -> {
                        return new ErrorM(TipoError.SEMANTICO, "Multiplicacion erronea", this.linea, this.columna);
                    }
                }
            }
            case CHAR -> {
                switch (tipo2) {
                    case INT -> {
                        this.tipo.setTipo(TipoDeDato.INT);
                        return (int) op1 * (int) op2;
                    }
                    case DOUBLE -> {
                        this.tipo.setTipo(TipoDeDato.DOUBLE);
                        return (int) op1 * (double) op2;
                    }
                    default -> {
                        return new ErrorM(TipoError.SEMANTICO, "Multiplicacion erronea", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new ErrorM(TipoError.SEMANTICO, "Multiplicacion erronea", this.linea, this.columna);

            }
        }
    }

    public Object division(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        double auxiliar = -1;
        if (tipo2 == TipoDeDato.CHAR) {
            auxiliar = Character.getNumericValue(op2.toString().charAt(1));
        } else if (tipo2 == TipoDeDato.DOUBLE || tipo2 == TipoDeDato.INT) {
            auxiliar = Double.parseDouble(String.valueOf(op2));
        }

        if (auxiliar == 0.0) {
            System.out.println("Division por cero");
            return new ErrorM(TipoError.SEMANTICO, "Division por cero", this.linea, this.columna);
        }

        if (tipo1 != TipoDeDato.CHAR && tipo2 != TipoDeDato.CHAR) {
            this.tipo.setTipo(TipoDeDato.DOUBLE);
            return Double.parseDouble(op1.toString()) / Double.parseDouble(op2.toString());
        }

        if (tipo1 == TipoDeDato.CHAR && tipo2 == TipoDeDato.CHAR) {
            int auxiliar2 = Character.getNumericValue(op1.toString().charAt(1));
            this.tipo.setTipo(TipoDeDato.DOUBLE);
            return (double) auxiliar2 / (double) auxiliar;
        }

        return new ErrorM(TipoError.SEMANTICO, "Division erronea", this.linea, this.columna);
    }

    public Object negacion(Object op1) {
        var opU = this.operandoUnico.tipo.getTipo();
        switch (opU) {
            case INT -> {
                this.tipo.setTipo(TipoDeDato.INT);
                return (int) op1 * -1;
            }
            case DOUBLE -> {
                this.tipo.setTipo(TipoDeDato.DOUBLE);
                return (double) op1 * -1;
            }
            default -> {
                return new ErrorM(TipoError.SEMANTICO, "Negacion erronea", this.linea, this.columna);
            }
        }
    }
}
