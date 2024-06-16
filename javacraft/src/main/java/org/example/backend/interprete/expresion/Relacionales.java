package org.example.backend.interprete.expresion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.ErrorM;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

public class Relacionales extends Instruccion {
    private Instruccion condicion1;
    private Instruccion condicion2;
    private OpRelacional relacion;

    public Relacionales(Instruccion condicion1, Instruccion condicion2, OpRelacional relacion, int linea, int columna) {
        super(new Tipo(TipoDeDato.BOOLEAN), linea, columna);
        this.condicion1 = condicion1;
        this.condicion2 = condicion2;
        this.relacion = relacion;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var condIzq = this.condicion1.interpretar(arbol, tabla);
        if (condIzq instanceof ErrorM) {
            return condIzq;
        }

        var condDer = this.condicion2.interpretar(arbol, tabla);
        if (condDer instanceof ErrorM) {
            return condDer;
        }

        return switch (relacion) {
            case MAYOR -> this.opMayor(condIzq, condDer);
            case MENOR -> this.opMenor(condIzq, condDer);
            case MAYOR_IGUAL -> this.opMayorEq(condIzq, condDer);
            case MENOR_IGUAL -> this.opMenorEq(condIzq, condDer);
            case IGUAL -> this.opIgual(condIzq, condDer);
            case DIFERENCIACION -> this.opDiferenciacion(condIzq, condDer);
            //case NEGACION -> this.negacion(Unico);
            default -> new ErrorM(TipoError.SEMANTICO, "Operador invalido", this.linea, this.columna);
        };
    }


    private Object opIgual(Object condIzq, Object condDer) {
        var comparando1 = this.condicion1.tipo.getTipo();
        var comparando2 = this.condicion2.tipo.getTipo();

        return switch (comparando1) {
            case INT -> switch (comparando2) {
                case INT -> Integer.parseInt(condIzq.toString()) == Integer.parseInt(condDer.toString());
                case DOUBLE -> Integer.parseInt(condIzq.toString()) == Double.parseDouble(condDer.toString());
                case CHAR ->
                        Integer.parseInt(condIzq.toString()) == Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case DOUBLE -> switch (comparando2) {
                case INT -> Double.parseDouble(condIzq.toString()) == Integer.parseInt(condDer.toString());
                case DOUBLE -> Double.parseDouble(condIzq.toString()) == Double.parseDouble(condDer.toString());
                case CHAR ->
                        Double.parseDouble(condIzq.toString()) == Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case CHAR -> switch (comparando2) {
                case INT ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) == Integer.parseInt(condDer.toString());
                case DOUBLE ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) == Double.parseDouble(condDer.toString());
                case CHAR -> condIzq.toString().charAt(1) == condDer.toString().charAt(1);
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case BOOLEAN -> {
                if (comparando2 == TipoDeDato.BOOLEAN) {
                    yield Boolean.parseBoolean(condIzq.toString()) == Boolean.parseBoolean(condDer.toString());
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
            case STRING -> {
                if (comparando2 == TipoDeDato.STRING) {
                    yield condIzq.toString().equalsIgnoreCase(condDer.toString());
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
            default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
        };
    }

    private Object opMenorEq(Object condIzq, Object condDer) {
        var comparando1 = this.condicion1.tipo.getTipo();
        var comparando2 = this.condicion2.tipo.getTipo();

        return switch (comparando1) {
            case INT -> switch (comparando2) {
                case INT -> Integer.parseInt(condIzq.toString()) <= Integer.parseInt(condDer.toString());
                case DOUBLE -> Integer.parseInt(condIzq.toString()) <= Double.parseDouble(condDer.toString());
                case CHAR ->
                        Integer.parseInt(condIzq.toString()) <= Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case DOUBLE -> switch (comparando2) {
                case INT -> Double.parseDouble(condIzq.toString()) <= Integer.parseInt(condDer.toString());
                case DOUBLE -> Double.parseDouble(condIzq.toString()) <= Double.parseDouble(condDer.toString());
                case CHAR ->
                        Double.parseDouble(condIzq.toString()) <= Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case CHAR -> switch (comparando2) {
                case INT ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) <= Integer.parseInt(condDer.toString());
                case DOUBLE ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) <= Double.parseDouble(condDer.toString());
                case CHAR -> condIzq.toString().charAt(1) <= condDer.toString().charAt(1);
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case BOOLEAN -> {
                if (comparando2 == TipoDeDato.BOOLEAN) {
                    int aux1 = Boolean.parseBoolean(condIzq.toString()) ? 1 : 0;
                    int aux2 = Boolean.parseBoolean(condDer.toString()) ? 1 : 0;
                    yield aux1 <= aux2;
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
/*
            case STRING -> {
                if (comparando2 == TipoDeDato.STRING) {
                    yield condIzq.toString().length() <= condDer.toString().length();
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
*/
            default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
        };
    }

    private Object opMayorEq(Object condIzq, Object condDer) {
        var comparando1 = this.condicion1.tipo.getTipo();
        var comparando2 = this.condicion2.tipo.getTipo();

        return switch (comparando1) {
            case INT -> switch (comparando2) {
                case INT -> Integer.parseInt(condIzq.toString()) >= Integer.parseInt(condDer.toString());
                case DOUBLE -> Integer.parseInt(condIzq.toString()) >= Double.parseDouble(condDer.toString());
                case CHAR ->
                        Integer.parseInt(condIzq.toString()) >= Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case DOUBLE -> switch (comparando2) {
                case INT -> Double.parseDouble(condIzq.toString()) >= Integer.parseInt(condDer.toString());
                case DOUBLE -> Double.parseDouble(condIzq.toString()) >= Double.parseDouble(condDer.toString());
                case CHAR ->
                        Double.parseDouble(condIzq.toString()) >= Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case CHAR -> switch (comparando2) {
                case INT ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) >= Integer.parseInt(condDer.toString());
                case DOUBLE ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) >= Double.parseDouble(condDer.toString());
                case CHAR -> condIzq.toString().charAt(1) >= condDer.toString().charAt(1);
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case BOOLEAN -> {
                if (comparando2 == TipoDeDato.BOOLEAN) {
                    int aux1 = Boolean.parseBoolean(condIzq.toString()) ? 1 : 0;
                    int aux2 = Boolean.parseBoolean(condDer.toString()) ? 1 : 0;
                    yield aux1 >= aux2;
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
/*
            case STRING -> {
                if (comparando2 == TipoDeDato.STRING) {
                    yield condIzq.toString().length() >= condDer.toString().length();
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
*/
            default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
        };
    }

    private Object opMenor(Object condIzq, Object condDer) {
        var comparando1 = this.condicion1.tipo.getTipo();
        var comparando2 = this.condicion2.tipo.getTipo();

        return switch (comparando1) {
            case INT -> switch (comparando2) {
                case INT -> Integer.parseInt(condIzq.toString()) < Integer.parseInt(condDer.toString());
                case DOUBLE -> Integer.parseInt(condIzq.toString()) < Double.parseDouble(condDer.toString());
                case CHAR ->
                        Integer.parseInt(condIzq.toString()) < Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case DOUBLE -> switch (comparando2) {
                case INT -> Double.parseDouble(condIzq.toString()) < Integer.parseInt(condDer.toString());
                case DOUBLE -> Double.parseDouble(condIzq.toString()) < Double.parseDouble(condDer.toString());
                case CHAR ->
                        Double.parseDouble(condIzq.toString()) < Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case CHAR -> switch (comparando2) {
                case INT ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) < Integer.parseInt(condDer.toString());
                case DOUBLE ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) < Double.parseDouble(condDer.toString());
                case CHAR -> condIzq.toString().charAt(1) < condDer.toString().charAt(1);
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case BOOLEAN -> {
                if (comparando2 == TipoDeDato.BOOLEAN) {
                    int aux1 = Boolean.parseBoolean(condIzq.toString()) ? 1 : 0;
                    int aux2 = Boolean.parseBoolean(condDer.toString()) ? 1 : 0;
                    yield aux1 < aux2;
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
/*
            case STRING -> {
                if (comparando2 == TipoDeDato.STRING) {
                    yield condIzq.toString().length() < condDer.toString().length();
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
*/
            default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
        };
    }

    private Object opMayor(Object condIzq, Object condDer) {
        var comparando1 = this.condicion1.tipo.getTipo();
        var comparando2 = this.condicion2.tipo.getTipo();

        return switch (comparando1) {
            case INT -> switch (comparando2) {
                case INT -> Integer.parseInt(condIzq.toString()) > Integer.parseInt(condDer.toString());
                case DOUBLE -> Integer.parseInt(condIzq.toString()) > Double.parseDouble(condDer.toString());
                case CHAR ->
                        Integer.parseInt(condIzq.toString()) > Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case DOUBLE -> switch (comparando2) {
                case INT -> Double.parseDouble(condIzq.toString()) > Integer.parseInt(condDer.toString());
                case DOUBLE -> Double.parseDouble(condIzq.toString()) < Double.parseDouble(condDer.toString());
                case CHAR ->
                        Double.parseDouble(condIzq.toString()) > Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case CHAR -> switch (comparando2) {
                case INT ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) > Integer.parseInt(condDer.toString());
                case DOUBLE ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) > Double.parseDouble(condDer.toString());
                case CHAR -> condIzq.toString().charAt(1) > condDer.toString().charAt(1);
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case BOOLEAN -> {
                if (comparando2 == TipoDeDato.BOOLEAN) {
                    int aux1 = Boolean.parseBoolean(condIzq.toString()) ? 1 : 0;
                    int aux2 = Boolean.parseBoolean(condDer.toString()) ? 1 : 0;
                    yield aux1 > aux2;
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
/*
            case STRING -> {
                if (comparando2 == TipoDeDato.STRING) {
                    yield condIzq.toString().length() > condDer.toString().length();
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
*/
            default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
        };
    }

    private Object opDiferenciacion(Object condIzq, Object condDer) {
        var comparando1 = this.condicion1.tipo.getTipo();
        var comparando2 = this.condicion2.tipo.getTipo();

        return switch (comparando1) {
            case INT -> switch (comparando2) {
                case INT -> Integer.parseInt(condIzq.toString()) != Integer.parseInt(condDer.toString());
                case DOUBLE -> Integer.parseInt(condIzq.toString()) != Double.parseDouble(condDer.toString());
                case CHAR ->
                        Integer.parseInt(condIzq.toString()) != Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case DOUBLE -> switch (comparando2) {
                case INT -> Double.parseDouble(condIzq.toString()) != Integer.parseInt(condDer.toString());
                case DOUBLE -> Double.parseDouble(condIzq.toString()) != Double.parseDouble(condDer.toString());
                case CHAR ->
                        Double.parseDouble(condIzq.toString()) != Character.getNumericValue(condDer.toString().charAt(1));
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case CHAR -> switch (comparando2) {
                case INT ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) != Integer.parseInt(condDer.toString());
                case DOUBLE ->
                        Character.getNumericValue(condIzq.toString().charAt(1)) != Double.parseDouble(condDer.toString());
                case CHAR -> condIzq.toString().charAt(1) != condDer.toString().charAt(1);
                default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            };
            case BOOLEAN -> {
                if (comparando2 == TipoDeDato.BOOLEAN) {
                    yield Boolean.parseBoolean(condIzq.toString()) != Boolean.parseBoolean(condDer.toString());
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
            case STRING -> {
                if (comparando2 == TipoDeDato.STRING) {
                    yield !condIzq.toString().equalsIgnoreCase(condDer.toString());
                }
                yield new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
            }
            default -> new ErrorM(TipoError.SEMANTICO, "Relacional Invalido", this.linea, this.columna);
        };
    }
}
