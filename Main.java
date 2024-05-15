import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;


public class Main {
    public static boolean isOperando(String simb){
        return simb != null && simb.matches("[0-9.]+");
    }
    public static boolean isOperador(String simb){
        if(simb.equals("+") || simb.equals("-") || simb.equals("*") || simb.equals("/")){
            return true;
        }else{
            return false;
        }
    }
    public  static int getPrioridade(String valor){
        if(valor.equals("(") || valor.equals(")")){
            return 0;
        } else if (valor.equals("+") || valor.equals("-")) {
            return 1;
        }else {
            return 2;
        }
    }
    public static Stack<String> calcularPilha(LinkedList<String> filaPosFixa){
        Stack<String> pilhaCalc = new Stack();
        String simb;
        Integer resultado = 0;
        while (!filaPosFixa.isEmpty()){
            simb = filaPosFixa.pop();
            if(isOperando(simb)){
                pilhaCalc.push(simb);
            } else if (isOperador(simb)) {
                Integer operadorA = Integer.valueOf(pilhaCalc.pop());
                Integer operadorB = Integer.valueOf(pilhaCalc.pop());
                if(simb.equals("+")){
                    resultado = operadorB + operadorA;
                } else if (simb.equals("-")) {
                    resultado = operadorB - operadorA;
                } else if (simb.equals("*")) {
                    resultado = operadorB * operadorA;
                } else if (simb.equals("/")) {
                    resultado = operadorB / operadorA;
                }
                pilhaCalc.push(resultado.toString());
            }
        }

        return pilhaCalc;
    }
    public static LinkedList<String> PosFixa(LinkedList<String> filaInfixa){
        LinkedList<String> filaPosFixa = new LinkedList<>();
        Stack<String> pilhaConv = new Stack();
        String simbPilha;
        while(!filaInfixa.isEmpty()){
            String simb = filaInfixa.poll();
            if (isOperando(simb)){
                filaPosFixa.offer(simb);
            } else if (simb.equals("(")) {
                pilhaConv.push(simb);
            }else if(isOperador(simb)){
                while(! pilhaConv.isEmpty() && getPrioridade(pilhaConv.peek()) >= getPrioridade(simb)){
                    simbPilha = pilhaConv.pop();
                    filaPosFixa.offer(simbPilha);
                }
                pilhaConv.push(simb);
            } else if (simb.equals(")")) {
                while (!pilhaConv.peek().equals("(")){
                    simbPilha = pilhaConv.pop();
                    filaPosFixa.offer(simbPilha);
                }
                pilhaConv.pop();
            }
        }
        while (!pilhaConv.isEmpty()){
            simbPilha = pilhaConv.pop();
            filaPosFixa.offer(simbPilha);
        }
        return filaPosFixa;
    }





    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String exp = scan.nextLine();
        String[] simbolos = exp.split(" ");

        LinkedList<String> filaSimb = new LinkedList<>();
        LinkedList<String> filaPosFixa = new LinkedList<>();
        Stack<String> pilhaCalc = new Stack();

        for(String simb : simbolos) {
            filaSimb.offer(simb);
        }

        filaPosFixa = PosFixa(filaSimb);
        System.out.println(filaPosFixa);

        pilhaCalc = calcularPilha(filaPosFixa);
        System.out.println(pilhaCalc.peek());
    }

}
