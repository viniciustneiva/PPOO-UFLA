public class Item {
    private String nome;
    private int peso;
    private String descricao;

    public Item(String nome, int peso, String descricao) {
        this.nome = nome;
        this.peso = peso;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public int getPeso() {
        return peso;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDescricaoCompleta() {
        String descricao = "Item: " + getNome() + "\n";
        descricao += "Peso do Item: " + getPeso() + "\n";
        descricao += "Descricao do Item: " + getDescricao() + "\n";
        return descricao;
    }

}
