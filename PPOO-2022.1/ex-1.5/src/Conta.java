public class Conta {
    private double saldo;
    private double limite;
    private int numeroConta;
    private static int totalContas = 1000;
    protected Cliente cliente;

    public int getNumeroConta() {
        return numeroConta;
    }
    
    public Conta(Cliente cliente, double saldoInicial, double limiteInicial) {
        this.cliente = cliente;
        saldo = saldoInicial;
        limite = limiteInicial;
        totalContas += 1;
        numeroConta = totalContas;
    }

    public Conta(Cliente cliente, double limiteInicial) {
        this.cliente = cliente;
        saldo = 0;
        limite = limiteInicial;
        totalContas += 1;
        numeroConta = totalContas;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    public boolean sacar(double valor) {
        if((saldo - valor) < (limite*-1)){
            return false;
        }else {
            saldo -= valor;
            return true;
        }
    }

    public boolean transferir(Conta favorecido, double valor) {
        if(sacar(valor)){
            favorecido.depositar(valor);
            return true;
        }

        return false;
    }

}