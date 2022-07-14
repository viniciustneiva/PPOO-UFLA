import java.util.ArrayList;

public class Pessoa {
    private String nome;
    private ArrayList<Item> mochila;

    public Pessoa(String nome) {
        this.nome = nome;
        mochila = new ArrayList<Item>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarItem(Item item) {
        mochila.add(item);
    }

    public Item removerItem(String nomeItem) {
        for(Item itemAux : mochila){
            if(itemAux.getNome().equals(nomeItem)){
                Item itemRetornado = itemAux;
                mochila.remove(itemAux);
                return itemRetornado;
            }
                
        }
            
        return null;

    }

    public String inspecionaMochila() {
        String descricaoMochila = "";
        for(Item item : mochila) {
            descricaoMochila += item.getDescricaoCompleta() + " \n \n";
        }

        return descricaoMochila;
    }

    public int getPesoMochila() {
        int pesoTotal = 0;
        for(Item item : mochila) {
            pesoTotal += item.getPeso();
        }
        return pesoTotal;
    }

}
