
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws IOException {

        // Menu Inicial do Programa

        Scanner scPrincipal = new Scanner(System.in);
        int tipo;

        do {

            System.out.println("------------------------------------------");
            System.out.println("|            DWDM AirViseu               |");
            System.out.println("|                                        |");
            System.out.println("|      (1) - Passageiro                  |");
            System.out.println("|      (2) - Assistente                  |");
            System.out.println("|      (0) - Sair                        |");
            System.out.println("------------------------------------------");

            tipo = scPrincipal.nextInt();

            switch (tipo) {

                case 1:
                    System.out.println(MenuPrincipal());
                    break;
                case 2:
                    System.out.println(menuAssis());
                    break;

                case 0:
                    System.out.println("Obrigado por voar com DWDM AirViseu");
                    break;

            }

        } while (tipo != 0);

        scPrincipal.close();

    }

    public static int MenuPrincipal() throws IOException {

        // Menu Principal

        GestorAirViseu G = new GestorAirViseu();

        Passageiros passageiro = null;
        Scanner scMenuPrincipal = new Scanner(System.in);
        int opMenuPrincipal;

        do {

            System.out.println("------------------------------------------");
            System.out.println("|                                        |");
            System.out.println("|             Passageiro                 |");
            System.out.println("|                                        |");
            System.out.println("|      (1) - Passageiro Existente        |");
            System.out.println("|      (2) - Passageiro S/Registo        |");
            System.out.println("|      (0) - Sair                        |");
            System.out.println("|                                        |");
            System.out.println("------------------------------------------");

            opMenuPrincipal = scMenuPrincipal.nextInt();

            switch (opMenuPrincipal) {

                case 1:

                    System.out.println("Insira o seu NIF:");
                    opMenuPrincipal = scMenuPrincipal.nextInt();

                    // Verifica se existe passageiros no ficheiro TxT

                    ArrayList<Passageiros> pass = GestorAirViseu.lerPassageiros("passageiros.txt", 0);
                    passageiro = Passageiros.getPassageiroByNif(pass, opMenuPrincipal);

                    try {

                        // Se não existir ele dá um erro

                        if (passageiro == null) {
                            System.out.println("NIF não encontrado");
                            System.out.println("Caso não esteja registado por favor registe-se no menu");
                            System.out.println(MenuPrincipal());
                            break;

                        } else {

                            // Caso esteja no ficheiro TxT ele avança para o Menu com o NIF do passageiro
                            System.out.println(menuPass(passageiro));
                            break;
                        }

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                case 2:

                    // Caso o passageiro não se encontre no ficheiro txt ele pede para registar o
                    // Passageiro
                    // Depois do passageiro se registar ele volta para o Menu principal para puder
                    // colocar o seu NIF

                    try {

                        System.out.println("-----------------------------------------");
                        System.out.println("|         REGISTAR - Passageiro         |");
                        System.out.println("-----------------------------------------");

                        G.addPassageiro();

                        System.out.println("Registado com sucesso por favor volte a colocar o seu Nif no menu");
                        System.out.println(MenuPrincipal());

                        break;

                    } catch (Exception e) {

                        System.out.println("Erro ao Registar");

                    }

                case 0:
                    System.exit(0);

            }

        } while (opMenuPrincipal != 0);

        scMenuPrincipal.close();
        return opMenuPrincipal;

    }

    public static int menuPass(Passageiros passageiro) throws IOException {

        GestorAirViseu G = new GestorAirViseu();
        Scanner scmenuPass = new Scanner(System.in);
        int opMenuPass;

        do {

            System.out.println("--------------------------------------------------");
            System.out.println("|                      MENU                      |");
            System.out.println("|  (1) - Comprar bilhete efetivo                 |");
            System.out.println("|  (2) - Cancelar Bilhete                        |");
            System.out.println("|  (3) - Cancelar Reserva                        |");
            System.out.println("|  (4) - Listar Rotas                            |");
            System.out.println("|  (5) - Listar Voos de uma rota                 |");
            System.out.println("|  (6) - Listar Historial                        |");
            System.out.println("|  (7) - Listar Bilhetes Adquiridos              |");
            System.out.println("|  (8) - Listar Reservas Efetuadas               |");
            System.out.println("|  (0) - Logout                                  |");
            System.out.println("--------------------------------------------------");

            opMenuPass = scmenuPass.nextInt();

            switch (opMenuPass) {

                case 1:

                    // Comprar o bilhete, na variável passageiro está presente o NIF inserido acima
                    // pelo passageiro
                    // evitando assim que o passageiro volte a colocar o nif
                    G.comprarBilhete(passageiro);
                    break;

                case 2:
                    // TODO Cancelar o bilhete
                    
                    G.ListarBilhetesBYNif(passageiro);
                    int bilheteID = 0;
                    Scanner s = new Scanner(System.in);

                    System.out.println("Insira o ID do bilhete que pretende cancelar");

                    bilheteID = s.nextInt();
                    G.cancelarBilhete(bilheteID);
                    break;

                case 3:

                    // TODO Cancelar a reserva do bilhete
                    break;

                case 4:
                    // Lista todas as rotas presentes no ficheiro txt
                    System.out.println("Listar Rotas");
                    G.lerRotas("rotas.txt");
                    break;

                case 5:

                    // Lista os voos de uma determinada rota
                    System.out.println("Listar Voos de uma rota");
                    G.listarVoosRotas();
                    break;

                case 6:

                    // TODO Listar o historico do passgeiro
                    G.listarVoosAntigos(passageiro);
                    break;

                case 7:

                    // TODO Listar os bilhetes do passageiro
                    G.ListarBilhetesBYNif(passageiro);
                    break;

                case 8:

                    // TODO Listar as reservas do passageiro
                    break;

                case 0:
                    System.exit(0);

            }

        } while (opMenuPass != 0);

        scmenuPass.close();
        return opMenuPass;
    }

    public static int menuAssis() throws IOException {

        GestorAirViseu S = new GestorAirViseu();
        Scanner Assis = new Scanner(System.in);
        int op;

        do {
            System.out.println("--------------------------------------------------");
            System.out.println("|                      MENU                      |");
            System.out.println("|  (1) - Listar Rotas                            |");
            System.out.println("|  (2) - Listar Voos de uma rota                 |");
            System.out.println("|  (3) - Listar todos os Passageiros             |");
            System.out.println("|  (4) - Listar Passageiros de um Voo            |");
            System.out.println("|  (5) - Listar Historial do Passageiro          |");
            System.out.println("|  (6) - Listar Bilhetes Efetivo do Passageiro   |");
            System.out.println("|  (7) - Listar Bilhetes Suplentes do Passageiro |");
            System.out.println("|  (8) - Listar Bilhetes Adquiridos              |");
            System.out.println("|  (9) - Listar Reservas Efetuadas               |");
            System.out.println("|  (0) - Logout                                  |");
            System.out.println("--------------------------------------------------");

            op = Assis.nextInt();

            switch (op) {

                case 1:

                    // Lista todas as rotas do ficheiro txt
                    System.out.println("Listar Rotas");
                    S.lerRotas("rotas.txt");
                    break;

                case 2:

                    // Lista os voos de uma rota
                    System.out.println("Listar Voos de uma rota");
                    S.listarVoosRotas();
                    break;

                case 3:

                    // Lista todos os passageiros
                    System.out.println("Listar Passageiros");
                    GestorAirViseu.lerPassageiros("passageiros.txt", 1);
                    break;

                case 4:

                    // Todo Compor o listar passageiros de um voo
                    System.out.println("Listar passageiros de um voo");
                    S.listarPassageirosVoos();
                    break;

                case 5:

                    // Todo Listar Historico do passageiro
                    S.listarhistoricopassageiro();
                    break;

                case 6:

                    // Todo listar os bilhetes do passageiro
                    System.out.println("Lista de Bilhetes do Passageiro");
                    S.listarPassageirosBilhete();
                    break;

                case 7:

                    // Todo Listar os blhetes suplentes do passageiro
                    break;

                case 8:

                    // Lista os bilhetes do ficheiro txt
                    System.out.println("Lista de Bilhetes");
                    GestorAirViseu.lerBilhetes("bilhetes.txt", 1);
                    break;

                case 9:

                    // Todo Listar reservas efetuadas
                    break;

                case 0:
                    System.exit(0);

            }

        } while (op != 0);
        Assis.close();
        return op;

    }
}
