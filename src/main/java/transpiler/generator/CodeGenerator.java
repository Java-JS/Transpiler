package transpiler.generator;

import transpiler.domain.Lexeme;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CodeGenerator {

    public List<Lexeme> ls;

    public CodeGenerator(List<Lexeme> ls) {
        this.ls = ls;
    }

    public void generate() throws IOException {
        FileWriter arq = new FileWriter("src/main/resources/resultado.js");
        PrintWriter gravarArq = new PrintWriter(arq);
        for (int i = 0; i < ls.size(); i++) {
            System.out.println("linha: " + i + " length: " + ls.get(i).toString().length() + " conteudo: " + ls.get(i));
            if (ls.get(i).toString().length() > 19) {
                if (ls.get(i).toString().substring(0, 18).equals("Lexeme[parenthesis")) {
                    System.out.println(String.valueOf(" " + ls.get(i).toString().charAt(ls.get(i).toString().length() - 2)));
                    gravarArq.printf(String.valueOf(" " + ls.get(i).toString().charAt(ls.get(i).toString().length() - 2)));
                }
            }
            if (ls.get(i).toString().substring(0, ls.get(i).toString().length() - 4).equals("Lexeme[curly_brackets")) {
                //    System.out.println("achou um cavao: "+ls.get(i).toString().charAt(ls.get(i).toString().length()-2));
                gravarArq.printf(" " + String.valueOf(ls.get(i).toString().charAt(ls.get(i).toString().length() - 2)) + "\n");
            }
            if (ls.get(i).toString().substring(0, ls.get(i).toString().length() - 8).equals("Lexeme[class")) {
                //   System.out.println("achou uma classica: "+ls.get(i).toString().substring(ls.get(i).toString().length()-7,ls.get(i).toString().length()-1));
                gravarArq.printf(" " + ls.get(i).toString().substring(ls.get(i).toString().length() - 7, ls.get(i).toString().length() - 1));
            }
            if (ls.get(i).toString().substring(0, 13).equals("Lexeme[string")) {

                //     System.out.println("achou uma Str: "+ls.get(i).toString().substring(20,ls.get(i).toString().length()-1));
                gravarArq.printf(" " + ls.get(i).toString().substring(20, ls.get(i).toString().length() - 1));
            }
            if (ls.get(i).toString().substring(0, 17).equals("Lexeme[unexpected")) {
                //   System.out.println("achou alguma coisa: "+ls.get(i).toString().substring(19,ls.get(i).toString().length()-1));
                if (ls.get(i).toString().substring(19, ls.get(i).toString().length() - 1).equals("args)")) {
                    // esse "if" e uma gambiarra para resolver um erro q tava dando quando usava o "args" no main
                    // NAO MEXA
                    //ta funcionando
                    //tava cheio de sono quando fiz
                    gravarArq.printf(" " + ls.get(i).toString().substring(19, ls.get(i).toString().length() - 2));
                    //  System.out.println("ddddd"+ls.get(i).toString().substring(19,ls.get(i).toString().length()-2));

                } else {
                    //   System.out.println(" "+ls.get(i).toString().substring(19,ls.get(i).toString().length()-1));
                    gravarArq.printf(" " + ls.get(i).toString().substring(19, ls.get(i).toString().length() - 1));
                }
            }
            if (ls.get(i).toString().substring(7, 13).equals("print")) {
                //    System.out.println("printao");
                gravarArq.printf(" " + "console.log");

            }
            if (ls.get(i).toString().substring(7, 11).equals("main")) {
                //      System.out.println("mainzao");
                gravarArq.printf(" " + "main");

            }
            if (ls.get(i).toString().substring(0, 11).equals("Lexeme[type")) {
                //     System.out.println("achei um tipao: ");
                //      System.out.println(ls.get(i).toString().substring(13,ls.get(i).toString().length()-1));
                int aux = i - 1;
                if (ls.get(aux).toString().substring(0, ls.get(aux).toString().length() - 4).equals("Lexeme[parenthesis")) {
                    System.out.println("me recuso a fazer algo ");
                } else if (ls.get(i).toString().substring(13, ls.get(i).toString().length() - 1).equals("static")) {
                    //       System.out.println("tipo estatico");
                } else if (ls.get(i).toString().substring(13, ls.get(i).toString().length() - 1).equals("void")) {
                    //       System.out.println("tipo vazio");
                } else if (ls.get(i).toString().substring(13, ls.get(i).toString().length() - 1).equals("final")) {
                    //       System.out.println("tipo vazio");
                    gravarArq.printf(" " + "const ");
                } else {
                    //        System.out.println("olha eu akiiiiiiiiiii");
                    gravarArq.printf(" " + "var ");
                }
            }
            if (ls.get(i).toString().substring(0, 16).equals("Lexeme[operation")) {
                //   System.out.println("operador mas nao de caixa"+ls.get(i).toString().charAt(18));
                gravarArq.printf(String.valueOf(" " + ls.get(i).toString().charAt(ls.get(i).toString().length() - 2)));
            }
            if (ls.get(i).toString().substring(0, 13).equals("Lexeme[number")) {
                //      System.out.println("numero "+ls.get(i).toString().charAt(15));
                gravarArq.printf(String.valueOf(ls.get(i).toString().charAt(15)));
            }
            if (ls.get(i).toString().substring(0, 16).equals("Lexeme[semicolon")) {
                //       System.out.println("ponto e virgula "+ls.get(i).toString().charAt(18));
                gravarArq.printf(String.valueOf(ls.get(i).toString().charAt(18)) + "\n");
            }
            if (ls.get(i).toString().substring(0, 14).equals("Lexeme[command")) {
                //      System.out.println("comando "+ls.get(i).toString().substring(16,21));
                gravarArq.printf(ls.get(i).toString().substring(16, ls.get(i).toString().length() - 1));
            }
        }

        arq.close();
    }


}
