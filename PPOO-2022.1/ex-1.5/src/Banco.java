import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Banco {

    private static ArrayList<Conta> contas = new ArrayList<>();
    public Banco() {};

    public static void consultarSaldo(int numeroConta){

        for(Conta conta :  getContas()) {
            if(numeroConta == conta.getNumeroConta()) {
                System.out.println("Olá " + conta.cliente.getNome() );
                System.out.println("O seu saldo é: R$ " + String.format("%.2f", conta.getSaldo()));
            }
        }
       
    }

    public static List<Conta> getContas() {
        return Collections.unmodifiableList(contas);
    }

    public static Conta criarConta(Cliente cliente, double saldo, double limite) {
        if(saldo > 0 && limite > 0) {
            Conta conta = new Conta(cliente, saldo, limite);
            contas.add(conta);
            return conta;
        }else{
            return null;
        } 
    }
    
    public static Conta criarConta(Cliente cliente, double limite) {
        if(limite > 0) {
            Conta contaDois = new Conta(cliente,  limite);
            contas.add(contaDois);
            return contaDois;
        }else{
            return null;
        } 
    }

    public static boolean depositar(int numeroConta,double valor) {

        for(Conta conta :  getContas()) {
            if(numeroConta == conta.getNumeroConta()) {
                conta.depositar(valor);
                return true;
            }
        }
        return false;
        
    }

    public static boolean sacar(int numeroConta, double valor) {
        boolean result = false;
        for(Conta conta :  getContas()) {
            if(numeroConta == conta.getNumeroConta()) {
                result = conta.sacar(valor);
                if(result){
                    return true;
                }
            }
        }

        return result;

    }

    public static boolean transferir(int idContaPagador, int idContaFavorecido, double valor) {
        Conta pagador = null;
        Conta favorecido = null;
        for(Conta conta :  getContas()) {
            if(conta.getNumeroConta() == idContaPagador) {
                pagador = conta;
            }
            if(conta.getNumeroConta() == idContaFavorecido) {
                favorecido = conta;
            }
           
        }    
        if(pagador != null && favorecido != null){
            if(pagador.getNumeroConta() != favorecido.getNumeroConta()){
                pagador.transferir(favorecido, valor);
                return true;
            } 
        }

        return false;
    }

    public static void sair() {
        System.exit(0);
    }

    public static boolean removerConta(int idConta) {
        for(Conta conta : contas) {
            if(conta.getNumeroConta() == idConta){
                if(conta.getSaldo() > 0) {
                    System.out.println("A conta não pode ser encerrada pois ainda tem saldo disponivel de: R$ " + String.format("%.2f", conta.getSaldo()));
                }else if(conta.getSaldo() < 0) {
                    System.out.println("Você ainda tem pendências com este banco : R$ " + String.format("%.2f", conta.getSaldo()));
                }else if(conta.getSaldo() == 0) {
                    contas.remove(conta);
                    System.out.println("Conta removida com sucesso!");
                    return true;
                }
            }
        }
        return false;
    }

    public static void menu() {
        Scanner entrada = new Scanner(System.in);
        String opcao;
        do {
            System.out.println("MENU");
            System.out.println("(1) Criar Conta");
            System.out.println("(2) Consultar saldo");
            System.out.println("(3) Depositar");
            System.out.println("(4) Sacar");
            System.out.println("(5) Transferir");
            System.out.println("(6) Listar Contas");
            System.out.println("(7) Apagar Conta");
            System.out.println("(8) Sair");
            opcao = entrada.nextLine();
            switch (opcao) {
                case "1":
                        System.out.println("Digite o seu nome completo:");
                        String entradaNome = entrada.nextLine();
                        System.out.println("Digite o seu CPF:");
                        String entradaCpf = entrada.nextLine();
                        Cliente novoCliente = new Cliente(entradaNome, entradaCpf);
                        System.out.println("Com quantos reais você vai abrir sua conta? Digite 0 para abrir uma conta sem saldo");
                        double entradaSaldo = Double.parseDouble(entrada.nextLine());
                        System.out.println("Qual será o seu limite?");
                        double entradaLimite = Double.parseDouble(entrada.nextLine());
                        if(entradaSaldo == 0){
                            Conta nova = Banco.criarConta(novoCliente, entradaLimite);
                            System.out.println("Conta criada, o seu ID é: " + nova.getNumeroConta());
                        }else{
                            Conta nova = Banco.criarConta(novoCliente, entradaSaldo, entradaLimite);
                            System.out.println("Conta criada, o seu ID é: " + nova.getNumeroConta());
                        }
                    break;
                case "2":
                    System.out.println("Digite o numero da sua conta: ");
                    int entradaConsultaSaldo = Integer.parseInt(entrada.nextLine());
                    Banco.consultarSaldo(entradaConsultaSaldo);
                    break;
                case "3":
                    System.out.println("Digite o numero da sua conta: ");
                    int entradaConsultaDeposito = Integer.parseInt(entrada.nextLine());
                    System.out.println("Qual valor deseja depositar?");
                    double entradaValor = Double.parseDouble(entrada.nextLine());
                    if(Banco.depositar(entradaConsultaDeposito, entradaValor)){
                        System.out.println("Operação realizada com sucesso! \n");
                    }else{
                        System.out.println("Usuário não encontrado! \n");
                    }
                    break;
                case "4":
                    System.out.println("Qual valor deseja sacar?");
                    double entradaSaque = Double.parseDouble(entrada.nextLine());
                    System.out.println("Digite o numero da sua conta: ");
                    int entradaConsultaSaque = Integer.parseInt(entrada.nextLine());
                    boolean resultado = Banco.sacar(entradaConsultaSaque, entradaSaque);
                    if(resultado) {
                        System.out.println("Operação realizada com sucesso! \n");
                    }else{
                        System.out.println("Saldo insuficiente! \n");
                    }
                    break;
                case "5": 
                    System.out.println("Digite o numero da sua conta (pagador): ");
                    int entradaPagador = Integer.parseInt(entrada.nextLine());
                    System.out.println("Digite o numero da conta do favorecido: ");
                    int entradaFavorecido = Integer.parseInt(entrada.nextLine());
                    System.out.println("Digite o valor da transferencia: ");
                    double entradaValorTransferencia = Double.parseDouble(entrada.nextLine());

                    if(Banco.transferir(entradaPagador, entradaFavorecido, entradaValorTransferencia)) {
                        System.out.println("Operação realizada com sucesso! \n");
                    }else {
                        System.out.println("Houve um problema na transferência! \n");
                    }
                    break;
                case "6":
                    for(Conta conta : getContas()){
                        System.out.println(conta.getNumeroConta() + " - " + conta.cliente.getNome());
                    }
                    break;
                case "7":
                    System.out.println("Digite o numero da conta que deseja deletar: ");
                    int entradaRemocao = Integer.parseInt(entrada.nextLine());
                    Banco.removerConta(entradaRemocao);
                    break;
                case "8":
                    entrada.close();
                    Banco.sair();
                    break;
                default:
                    System.out.println("Digite uma opção válida!! \n");
                    break;
            }     
        } while(opcao != "8");
    }

}
