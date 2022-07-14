import java.util.HashMap;

/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 *
 * Esta classe é parte da aplicação "World of Zuul".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.  
 *
 * Um "Ambiente" representa uma localização no cenário do jogo. Ele é conectado aos 
 * outros ambientes através de saídas. As saídas são nomeadas como norte, sul, leste 
 * e oeste. Para cada direção, o ambiente guarda uma referência para o ambiente vizinho, 
 * ou null se não há saída naquela direção.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */
public class Ambiente  {
    // descrição do ambiente
    public String descricao;
    public Item item;
    // ambientes visinhos de acordo com a direção
    private HashMap<String, Ambiente> saidas;
    // public Ambiente saidaNorte;
    // public Ambiente saidaSul;
    // public Ambiente saidaLeste;
    // public Ambiente saidaOeste;

    /**
     * Cria um ambiente com a "descricao" passada. Inicialmente, ele não tem saidas. 
     * "descricao" eh algo como "uma cozinha" ou "um jardim aberto".
     * @param descricao A descrição do ambiente.
     */
    public Ambiente(String descricao)  {
        this.descricao = descricao;
        item = null;
        saidas = new HashMap<String, Ambiente>();
    }
    public Ambiente(String descricao, String nome, int peso, String descricaoItem)  {
        this.descricao = descricao;
        this.item = new Item(nome, peso, descricaoItem);
        saidas = new HashMap<String, Ambiente>();
    }

    /**
     * Define as saídas do ambiente. Cada direção ou leva a um outro ambiente ou é null 
     * (indicando que não tem nenhuma saída para lá).
     * @param norte A saída norte.
     * @param leste A saída leste.
     * @param sul A saída sul.
     * @param oeste A saída oeste.
     */
    public void ajustarSaidas(String direcao, Ambiente ambiente)  {
        saidas.put(direcao, ambiente);
    }

    /**
     * @return A descrição do ambiente.
     */
    public String getDescricao() {
        return descricao;
    }

    public Ambiente getAmbiente(String direcao) {
        return saidas.get(direcao);
    }

    public String getSaidas() {
        String elementos = "";
        for (String saida : saidas.keySet()) 
            elementos += saida + " ";

        return elementos;
    }

    public String getDescricaoLonga() {

        String descricaoLonga = getDescricao();

        descricaoLonga += "\n Saidas: \n" + getSaidas() + "\n";

       

        return descricaoLonga;
    }

    public boolean observar() {
        if(item != null) {
            return true;
        }

        return false;
    }

    public Item getItem() {
        return item;
    }

    public Item coletarItem() {
        if(item != null){
            Item itemAux = item;
            this.item = null;
            return itemAux;
        }

        return null;
        
    }

}
