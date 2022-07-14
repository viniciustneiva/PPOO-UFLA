/**
 * Essa é a classe principal da aplicacao "World of Zull".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.
 * 
 * Usuários podem caminhar em um cenário. E é tudo! Ele realmente precisa ser 
 * estendido para fazer algo interessante!
 * 
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o método "jogar".
 * 
 * Essa classe principal cria e inicializa todas as outras: ela cria os ambientes, 
 * cria o analisador e começa o jogo. Ela também avalia e  executa os comandos que 
 * o analisador retorna.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */

public class Jogo {
    // analisador de comandos do jogo
    private Analisador analisador;
    // ambiente onde se encontra o jogador
    private Ambiente ambienteAtual;
    
    private Pessoa jogador;

    private boolean achouItem;
    
    private long tempoInicial;
    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo()  {
        criarAmbientes();
        analisador = new Analisador();
        achouItem = false;
        jogador = new Pessoa("jogador");
        tempoInicial = System.currentTimeMillis();
    }

    public long getTempoAtual(long tempoAtual) {
        return (tempoAtual - tempoInicial)/1000;
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private void criarAmbientes() {
        Ambiente entrada, sala, cozinha, quarto, banheiro, pontoOnibus, dentroOnibus;
      
        // cria os ambientes
        entrada = new Ambiente("na entrada da casa");
        sala = new Ambiente("na sala de estar");
        quarto = new Ambiente("no seu quarto");
        banheiro = new Ambiente("no banheiro");
        banheiro = new Ambiente("no banheiro", "VT", 1, "Seu vale transporte, você pode pegar o ônibus se quiser!!");
        pontoOnibus = new Ambiente("no ponto de ônibus");
        cozinha = new Ambiente("na cozinha", "carteira", 10, "Sua carteira com os documentos e um pouco de dinheiro.");
        dentroOnibus  = new Ambiente("no ônibus");


        // inicializa as saidas dos ambientes
        
        entrada.ajustarSaidas("norte", pontoOnibus);
        entrada.ajustarSaidas("leste", sala);
        entrada.ajustarSaidas("sul", quarto);
        entrada.ajustarSaidas("oeste", cozinha);  
        sala.ajustarSaidas("oeste", entrada);
        cozinha.ajustarSaidas("leste", entrada);
        quarto.ajustarSaidas("norte",entrada);
        quarto.ajustarSaidas("leste", banheiro);
        banheiro.ajustarSaidas("oeste", quarto);
        pontoOnibus.ajustarSaidas("sul", entrada);
        pontoOnibus.ajustarSaidas("embarcar", dentroOnibus);
        dentroOnibus.ajustarSaidas("desembarcar", pontoOnibus);


        ambienteAtual = entrada;  // o jogo comeca em frente à reitoria
    }

    /**
     *  Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar()  {
        imprimirBoasVindas();

        // Entra no loop de comando principal. Aqui nós repetidamente lemos comandos e 
        // os executamos até o jogo terminar.
                
        

        boolean terminado = false;
        while (! terminado) {
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
            long tempoAtual = System.currentTimeMillis();
            if(getTempoAtual(tempoAtual) > 60) {
                System.out.println("Você acaba de perder o ônibus...");
                System.out.println("Seu tempo: " + getTempoAtual(tempoAtual) + " segundos");
                terminado = true;
            }
        }
        System.out.println("Obrigado por jogar. Até mais!" );
       
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        System.out.println();
        System.out.println("Bem-vindo ao Perdi minha carteira!");
        System.out.println("Você está muito atrasado para sair de casa, mas não consegue encontrar sua carteira!");
        System.out.println("Digite 'ajuda' se voce precisar de ajuda.");
        System.out.println();
        
        imprimirLocalizacaoAtual();
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    private boolean processarComando(Comando comando)  {
        boolean querSair = false;

        if(comando.ehDesconhecido()) {
            System.out.println("Eu nao entendi o que voce disse...");
            return false;
        }

        String palavraDeComando = comando.getPalavraDeComando();
        if (palavraDeComando.equals("ajuda")) {
            imprimirAjuda();
        }
        else if (palavraDeComando.equals("ir")) {
            irParaAmbiente(comando);
        }
        else if (palavraDeComando.equals("sair")) {
            querSair = sair(comando);
        }
        else if (palavraDeComando.equals("pegar")) {
            pegar(comando);
        }
        else if (palavraDeComando.equals("observar")) {
            observar(comando);
            achouItem = ambienteAtual.observar();
            if(achouItem) {
                System.out.println(ambienteAtual.getItem().getDescricaoCompleta());
            }else{
                System.out.println("Não tem nada aqui... ");
            }
        }

        return querSair;
    }

    /**
     * Exibe informações de ajuda.
     * Aqui nós imprimimos algo bobo e enigmático e a lista de  palavras de comando
     */
    private void imprimirAjuda()  {
        System.out.println("Você vai se atrasar, onde tá essa carteira??");
        System.out.println("Tenta pensar onde você viu ela por último ????");
        System.out.println("Voce tambem pode procurar pelo seu VT (Vale transporte também!)");
        System.out.println("Você tem dois minutos até o ônibus sumir!!!");
        System.out.println();
        System.out.println("Suas palavras de comando sao:");
        System.out.println(analisador.getComandos());
    }

    /** 
     * Tenta ir em uma direcao. Se existe uma saída para lá entra no novo ambiente, 
     * caso contrário imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando)  {
        // se não há segunda palavra, não sabemos pra onde ir...
        if(!comando.temSegundaPalavra()) {            
            System.out.println("Ir pra onde?");
            return;
        }

        String direcao = comando.getSegundaPalavra();

        // Tenta sair do ambiente atual
        Ambiente proximoAmbiente = null;
        if(direcao.equals("norte") ||
         direcao.equals("leste") ||
         direcao.equals("sul") ||
         direcao.equals("oeste") ||
         direcao.equals("embarcar") || 
         direcao.equals("desembarcar")) {
            proximoAmbiente = ambienteAtual.getAmbiente(direcao);
        }
       
        if (proximoAmbiente == null) {
            System.out.println("Nao ha passagem!");
        } else {
            ambienteAtual = proximoAmbiente;
            
            imprimirLocalizacaoAtual();
            if(ambienteAtual.getDescricao().equals("no ônibus") && jogador.getPesoMochila() > 0) {
                System.out.println("Você entrou no ônibus a tempo e pagou sua passagem!");
                System.out.println("Seu tempo foi de: " + getTempoAtual(System.currentTimeMillis()) + " segundos");
                System.exit(0);
            }
        }
    }

    private void observar(Comando comando) {
        imprimirLocalizacaoAtual();
        jogador.inspecionaMochila();
    }
       


    /**
     * Imprime a mensagem sobre o ambiente atual do jogador.
     */

    public void imprimirLocalizacaoAtual() {
        System.out.println("Você está " + ambienteAtual.getDescricaoLonga());
    }

    /** 
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nós queremos 
     * realmente sair do jogo.
     * @return true, se este comando sai do jogo, false, caso contrário.
     */
    private boolean sair(Comando comando)  {
        if(comando.temSegundaPalavra()) {
            System.out.println("Sair o que?");
            return false;
        }
        else {
            return true;  // sinaliza que nós realmente queremos sair
        }
    }

    private void pegar(Comando comando) {
        if(!comando.temSegundaPalavra()) {            
            System.out.println("Pegar o que?");
            return;
        }

        String nomeItem = comando.getSegundaPalavra();

        if(nomeItem.equals(ambienteAtual.getItem().getNome())) {
            Item itemRecebido = ambienteAtual.coletarItem();
            jogador.adicionarItem(itemRecebido);
            System.out.println(itemRecebido.getDescricaoCompleta());
        }else{
            System.out.println("Não foi encontrado nenhum(a) " + nomeItem);
        }
    }




}
