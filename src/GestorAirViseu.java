
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class GestorAirViseu {

    private ArrayList<Passageiros> listagem;
    private ArrayList<Rota> lista_Rota;
    private ArrayList<Voo> lista_Voo;
    private ArrayList<Bilhete> lista_Bilhete;
    private ArrayList<Auxiliar> lista_Auxiliar;

    public GestorAirViseu() {

        listagem = new ArrayList<Passageiros>();
        this.lista_Rota = new ArrayList<Rota>();
        this.lista_Voo = new ArrayList<Voo>();
        lista_Bilhete = new ArrayList<Bilhete>();
        this.lista_Auxiliar = new ArrayList<Auxiliar>();

    }

    public void addPassageiro() throws IOException {

        String Name, Profissao, Morada, AnoNascimento;

        int Nifo;
        Scanner scAddPassageiro = new Scanner(System.in);

        System.out.println("Introduza o seu NIF");
        Nifo = scAddPassageiro.nextInt();

        System.out.println("Qual o nome do passageiro?");
        Name = scAddPassageiro.next();

        System.out.println("Qual é profissão da passageiro?");
        Profissao = scAddPassageiro.next();

        System.out.println("Qual a morada da passageiro?");
        Morada = scAddPassageiro.next();

        System.out.println("Qual o ano de nascimento da passageiro?");
        AnoNascimento = scAddPassageiro.next();

        Passageiros p = new Passageiros(Nifo, Name, Profissao, Morada, AnoNascimento);
        scAddPassageiro.nextLine();

        try {

            listagem = lerPassageiros("passageiros.txt", 0);
            listagem.add(p);

            GuardarPassageiroTxT("passageiros.txt");

        } catch (Exception e) {

            System.err.println("Erro ao inserir no TxT");
        }

    }

    public void GuardarPassageiroTxT(String nf) throws IOException {

        try {

            FileWriter fich = new FileWriter(new File(nf));
            BufferedWriter f = new BufferedWriter(fich);

            for (Passageiros P : this.listagem) {
                f.write(P.getNIF() + "," + P.getNome() + "," + P.getProfissao() + "," + P.getMorada() + ","
                        + P.getDataNascimento() + "\n");
            }

            f.close();

        } catch (Exception e) {

            System.out.println("Erro ao inserir no TxT");
        }
    }

    public void comprarBilhete(Passageiros passageiro) throws IOException {

        HashMap<Rota, ArrayList<Voo>> data = listarVoosRotas();

        Boolean isNew = false;
        String action = "";
        int vooID;
        Scanner scComprarBilhete = new Scanner(System.in);

        System.out.println("Insira o ID do voo:");
        vooID = scComprarBilhete.nextInt();

        Rota rota = ((Rota) (data.keySet().toArray()[0]));
        Voo voo = Voo.getVooByID(data.get(rota), vooID);

        if (voo == null) {
            System.out.println("Voo não encontrado");
            scComprarBilhete.close();
            return;
        }

        lerAuxiliar("auxiliar.txt", 0);

        Auxiliar aux = Auxiliar.getAuxiliarByRotaIDAndVooID(lista_Auxiliar, rota.getID(), voo.getID());

        if (aux == null) {
            aux = new Auxiliar(rota.getID(), voo.getID(), voo.getLugares(), 0, 0);
            lista_Auxiliar.add(aux);
            isNew = true;
        }

        System.out.println("Voo selecionado:");
        System.out.println(voo.toString());

        System.out.println("Insira o ano de voo:");
        int AnoV = scComprarBilhete.nextInt();

        System.out.println("Insira o mes de voo:");
        int MesV = scComprarBilhete.nextInt();

        System.out.println("Insira o dia de voo:");
        int DiaV = scComprarBilhete.nextInt();

        try {
            Bilhete bilhete;

            // Atualiza Auxiliar \\
            int indexAux = lista_Auxiliar.indexOf(aux); // Obtem o id do auxiliar

            if ((voo.getLugares() - aux.getVendidos()) > -4) {
                aux.setVendidos(aux.getVendidos() + 1); // Incrementa o numero de vendidos

                this.lista_Bilhete = lerBilhetes("bilhetes.txt", 0);
                int newID = this.lista_Bilhete.get(this.lista_Bilhete.size() - 1).getIDBilhete() + 1;

                // Insere Bilhete novo
                bilhete = new Bilhete(
                        newID,
                        passageiro.getNIF(),
                        voo.getRota(),
                        voo.getID(),
                        Calendar.getInstance().get(Calendar.YEAR),
                        (Calendar.getInstance().get(Calendar.MONTH) + 1),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                        Calendar.getInstance().get(Calendar.HOUR),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        Calendar.getInstance().get(Calendar.SECOND),
                        AnoV,
                        MesV,
                        DiaV,
                        voo.getHora(),
                        voo.getMinutos(),
                        voo.getSegundos(),
                        voo.getCustoVoo(rota.getDistancia()));

                System.out.println(bilhete.toString());

                this.lista_Bilhete.add(bilhete);
                GuardarBilhetesTxT("bilhetes.txt", false);
                action = "Comprado";
            } else if ((voo.getLugares() - aux.getReservados()) > 0) {
                aux.setReservados(aux.getReservados() + 1); // Incrementa o numero de reservas
                action = "Reservado";
            } else {
                System.out.println("Não existem lugares disponiveis");
                if (isNew) {
                    lista_Auxiliar.remove(indexAux);
                }
                return;
            }

            // Atualiza o auxiliar
            lista_Auxiliar.set(indexAux, aux);
            GuardarAuxiliarTxT("auxiliar.txt"); // Atualiza o ficheiro auxiliar

            System.out.println("Bilhete " + action + " com sucesso");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            main.menuPass(passageiro);
        }

        scComprarBilhete.close();
    }

    public void cancelarBilhete(int bilheteID) {
        if (bilheteID <= 0) {
            System.out.println("ID inválido");
            return;
        }
        try {
            Bilhete bilhete = Bilhete.getBilheteByID(this.lista_Bilhete, bilheteID);

            if (bilhete == null) {
                System.out.println("Bilhete não encontrado");
                return;
            }

            if ((Calendar.getInstance().get(Calendar.YEAR) <= bilhete.getAnoViagem()
                    && bilhete.getMesViagem() <= (Calendar.getInstance().get(Calendar.MONTH) + 1)
                    && bilhete.getDiaViagem() <= Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                    ||
                    (bilhete.getHoraViagem() <= Calendar.getInstance().get(Calendar.HOUR)
                            && bilhete.getMinutosViagem() <= Calendar.getInstance().get(Calendar.MINUTE)
                            && bilhete.getSegundosViagem() <= Calendar.getInstance().get(Calendar.SECOND))) {
                System.out.println("Bilhete não pode ser cancelado pois já passou");
                return;
            }
            Scanner sc = new Scanner(System.in);
            int opcao = 0;
            do {
                System.out.println("Tem a certeza que deseja cancelar o bilhete?");
                System.out.println("1 - Sim");
                System.out.println("2 - Não");
                opcao = sc.nextInt();
            } while (opcao != 1 && opcao != 2);

            if (opcao == 2) {
                System.out.println("Operação cancelada com sucesso");
                return;
            }

            int index = lista_Bilhete.indexOf(bilhete);

            lerAuxiliar("auxiliar.txt", 0);
            Auxiliar aux = Auxiliar.getAuxiliarByRotaIDAndVooID(this.lista_Auxiliar, bilhete.getIDRota(),
                    bilhete.getIDVoo());
            aux.setVendidos(aux.getVendidos() - 1);

            lista_Bilhete.remove(index);

            GuardarBilhetesTxT("bilhetes.txt", false);
            GuardarAuxiliarTxT("auxiliar.txt");
            System.out.println("Bilhete cancelado com sucesso");

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void GuardarAuxiliarTxT(String nf) {

        try {

            FileWriter fich = new FileWriter(new File(nf), false);
            BufferedWriter f = new BufferedWriter(fich);
            for (Auxiliar A : this.lista_Auxiliar) {
                f.write(A.getRotaID() + "," + A.getVooID() + "," + A.getLugares() + "," + A.getVendidos() + ","
                        + A.getReservados() + "\n");
            }
            f.close();

        } catch (Exception e) {
            System.out.println("Erro ao inserir no TxT");
        }
    }

    public void GuardarBilhetesTxT(String nf, boolean append) throws IOException {

        try {

            FileWriter fich = new FileWriter(new File(nf), append);
            BufferedWriter f = new BufferedWriter(fich);
            for (Bilhete B : this.lista_Bilhete) {
                f.write(B.getIDBilhete() + "," + B.getNIFPassageiro() + "," + B.getIDRota() + "," + B.getIDVoo() + ","
                        + B.getAnoViagem() + "," + B.getMesViagem() + ","
                        + B.getDiaViagem() + "," + B.getHoraViagem() + "," + B.getMinutosViagem() + ","
                        + B.getSegundosViagem() + "," + B.getAnoAquisicao() + ","
                        + B.getMesAquisicao() + "," + B.getDiaAquisicao() + "," + B.getHoraAquisicao() + ","
                        + B.getMinutosAquisicao() + "," + B.getSegundosAquisicao() + ","
                        + B.getPreco() + "\n");
            }
            f.close();

        } catch (Exception e) {
            System.out.println("Erro ao inserir no TxT");
        }
    }

    public void ListarBilhetesBYNif(Passageiros Passageiro) throws IOException {
        this.lista_Bilhete = lerBilhetes("bilhetes.txt", 0);

        try {
            int nif = Passageiro.getNIF();

            if (nif <= 0) {
                throw new Exception("Nif inválido");
            }
            Bilhete.tostringHeader();
            for (Bilhete B : this.lista_Bilhete) {
                if (B.getNIFPassageiro() == nif) {
                    System.out.println(B.toString());
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR-> Não foi possivel listar os bilhetes");
        }
    }

    public HashMap<Rota, ArrayList<Voo>> listarVoosRotas() throws IOException {

        lerRotas("rotas.txt");

        if (this.lista_Rota.size() <= 0) {
            System.out.println("Sem rotas.");
            return null;
        }

        HashMap<Rota, ArrayList<Voo>> data = new HashMap<>();

        Scanner scListarVoosRotas = new Scanner(System.in);

        System.out.println("Insira o ID rota.");

        int idRota = scListarVoosRotas.nextInt();

        Rota rota = Rota.getRotaByID(lista_Rota, idRota);

        if (idRota <= 0 || rota == null) {
            System.out.println("Rota não encontrada.");
            return null;
        }

        data.put(rota, new ArrayList<Voo>());

        lerVoos("voos.txt", 0);

        if (this.lista_Voo.size() <= 0) {
            System.out.println("Sem voos.");
            return null;
        }

        for (Voo v : this.lista_Voo) {
            if (v.getRota() == idRota) {
                System.out.println(v.toString());
                data.get(rota).add(v);
            }
        }

        if (data.get(rota).size() == 0) {
            System.out.println("Não existem voos para esta rota.");
            return null;
        }

        return data;
    }

    public HashMap<Passageiros, ArrayList<Bilhete>> listarPassageirosBilhete() throws IOException {

        this.listagem = lerPassageiros("passageiros.txt", 1);

        if (this.listagem.size() <= 0) {
            System.out.println("Sem Passageiros.");
            return null;
        }

        HashMap<Passageiros, ArrayList<Bilhete>> data = new HashMap<>();

        Scanner scListarPassageirosBilhete = new Scanner(System.in);

        System.out.println("Insira o ID do Passageiro.");
        int NIF = scListarPassageirosBilhete.nextInt();

        Passageiros p = Passageiros.getPassageiroByNif(this.listagem, NIF);

        if (NIF <= 0 || p == null) {
            System.out.println("Passageiro não encontrado.");
            return null;
        }

        data.put(p, new ArrayList<Bilhete>());

        this.lista_Bilhete = lerBilhetes("bilhetes.txt", 1);

        if (this.lista_Bilhete.size() <= 0) {
            System.out.println("Sem Bilhetes.");
            return null;
        }

        for (Bilhete b : this.lista_Bilhete) {
            if (b.getNIFPassageiro() == NIF) {
                System.out.println(b.toString());
                data.get(p).add(b);
            }
        }

        if (data.get(p).size() == 0) {
            System.out.println("Não existem passageiros com este bilhete.");
            return null;
        }

        return data;
    }

    public void listarVoosAntigos(Passageiros passageiro) throws IOException {

        try {
            if (passageiro == null) {
                throw new Exception("Utilizador inválido.");
            }
            this.lista_Bilhete = lerBilhetes("bilhetes.txt", 0);

            int nif = passageiro.getNIF();

            Bilhete bilhete = Bilhete.getBilheteByNIF(this.lista_Bilhete, nif);

            if ((bilhete.getAnoViagem() < Calendar.getInstance().get(Calendar.YEAR)
                    && bilhete.getMesViagem() < (Calendar.getInstance().get(Calendar.MONTH) + 1)
                    && bilhete.getDiaViagem() < Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                    ||
                    (bilhete.getHoraViagem() < Calendar.getInstance().get(Calendar.HOUR)
                            && bilhete.getMinutosViagem() < Calendar.getInstance().get(Calendar.MINUTE)
                            && bilhete.getSegundosViagem() < Calendar.getInstance().get(Calendar.SECOND))) {
                System.out.println(bilhete);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void listarhistoricopassageiro() throws IOException {

        this.listagem = lerPassageiros("passageiros.txt", 1);
        Scanner scbuscarhistoricopassageiro = new Scanner(System.in);

        System.out.println("Insira o NIF do user que deseja:");
        int nif = scbuscarhistoricopassageiro.nextInt();

        try {

            this.lista_Bilhete = lerBilhetes("bilhetes.txt", 0);

            Bilhete bilhete = Bilhete.getBilheteByNIF(this.lista_Bilhete, nif);

            if (bilhete == null) {
                throw new Exception("Utilizador sem bilhetes.");
            }

            if ((bilhete.getAnoViagem() < Calendar.getInstance().get(Calendar.YEAR)
                    && bilhete.getMesViagem() < (Calendar.getInstance().get(Calendar.MONTH) + 1)
                    && bilhete.getDiaViagem() < Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                    ||
                    (bilhete.getHoraViagem() < Calendar.getInstance().get(Calendar.HOUR)
                            && bilhete.getMinutosViagem() < Calendar.getInstance().get(Calendar.MINUTE)
                            && bilhete.getSegundosViagem() < Calendar.getInstance().get(Calendar.SECOND))) {
                System.out.println(bilhete);
            }

            else {
                throw new Exception("Utilizador inválido.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //* DONE Compor listar passageiros por voo
    public void listarPassageirosVoos() throws IOException {

        lerVoos("voos.txt", 0);

        if (this.lista_Voo.size() <= 0) {
            System.out.println("Sem voos.");
            return;
        }

        HashMap<Passageiros, ArrayList<Voo>> data = new HashMap<>();

        Scanner scListarPassageirosVoos = new Scanner(System.in);

        System.out.println("Insira o id do voo.");

        int id_voo = scListarPassageirosVoos.nextInt();

        Voo voo = Voo.getVooByID(this.lista_Voo, id_voo);

        if (voo == null) {
            System.out.println("Esse voo não existe!");
            return;
        }

        this.lista_Bilhete = lerBilhetes("bilhetes.txt", 0);

        ArrayList<Bilhete> bilhetes = new ArrayList();

        for (Bilhete b : this.lista_Bilhete) {
            if (b.getIDVoo() == voo.getID()) {
                bilhetes.add(b);
            }
        }

        if (bilhetes.size() <= 0) {
            System.out.println("Não existem bilhetes para esse voo.");
            return;
        }

        this.listagem = lerPassageiros("passageiros.txt", 0);

        System.out.println("+----------+--------------------------------+");
        System.out.println("| ID       | Nome                           |");
        System.out.println("+----------+--------------------------------+");

        for (Passageiros passageiro : listagem) {
            for (Bilhete bilhete : bilhetes) {
                if (bilhete.getNIFPassageiro() == passageiro.getNIF()) {
                    System.out.println("| "+passageiro.getNIF() + "       | "+passageiro.getNome() + "|");
                }
            }
        }

        System.out.println("+----------+--------------+");
    }

    public void lerRotas(String nf) throws IOException {
        this.lista_Rota.clear();

        int ID, Quant;
        double Distancia;
        String Destino;

        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
        String linha = f.readLine();

        while (linha != null) {
            String[] camps = linha.split(",");
            ID = Integer.parseInt(camps[0]);
            Quant = Integer.parseInt(camps[1]);
            Destino = camps[2];
            Distancia = Double.parseDouble(camps[3]);
            Rota R = new Rota(ID, Quant, Destino, Distancia);

            this.lista_Rota.add(R);

            System.out.println(R.toString());

            linha = f.readLine();
        }

        f.close();
    }

    public void lerAuxiliar(String nf, int type) throws IOException {
        int RotaID, VooID, Lugares, Vendidos, Reservados;
        this.lista_Auxiliar.clear();
        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
        String linha = f.readLine();
        while (linha != null) {
            String[] camps = linha.split(",");
            RotaID = Integer.parseInt(camps[0]);
            VooID = Integer.parseInt(camps[1]);
            Lugares = Integer.parseInt(camps[2]);
            Vendidos = Integer.parseInt(camps[3]);
            Reservados = Integer.parseInt(camps[4]);

            Auxiliar A = new Auxiliar(RotaID, VooID, Lugares, Vendidos, Reservados);

            this.lista_Auxiliar.add(A);
            if (type == 1) {
                System.out.println(A.toString());
            }

            linha = f.readLine();
        }
        f.close();
    }

    public void lerVoos(String nf, int type) throws IOException {
        this.lista_Voo.clear();
        int IDRota, IDVoo, Hora, Minutos, Segundos;
        String Dia, Marca;
        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
        String linha = f.readLine();
        while (linha != null) {
            String[] camps = linha.split(",");
            IDVoo = Integer.parseInt(camps[0]);
            IDRota = Integer.parseInt(camps[1]);
            Dia = camps[2];
            Hora = Integer.parseInt(camps[3]);
            Minutos = Integer.parseInt(camps[4]);
            Segundos = Integer.parseInt(camps[5]);
            Marca = camps[6];
            Voo V = new Voo(IDVoo, IDRota, Dia, Hora, Minutos, Segundos, Marca);

            this.lista_Voo.add(V);
            if (type == 1) {
                System.out.println(V.toString());
            }

            linha = f.readLine();
        }
        f.close();
    }

    public static ArrayList<Passageiros> lerPassageiros(String nf, int type) throws IOException {

        ArrayList<Passageiros> passageiros = new ArrayList<>();
        int Nif;
        String Nome, Profissao, Morada, DataNascimento;

        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
        String linha = f.readLine();

        while (linha != null) {
            String[] camps = linha.split(",");
            Nif = Integer.parseInt(camps[0]);
            Nome = camps[1];
            Profissao = camps[2];
            Morada = camps[3];
            DataNascimento = camps[4];

            Passageiros p = new Passageiros(Nif, Nome, Profissao, Morada, DataNascimento);

            if (type == 1) { // se for tipo 1 lista os passageiros, se for tipo 0 ele não lista
                System.out.println(p.toString());
            }

            passageiros.add(p);

            linha = f.readLine();
        }

        f.close();

        return passageiros;
    }

    public static ArrayList<Bilhete> lerBilhetes(String nf, int type) throws IOException {

        ArrayList<Bilhete> bilhetes = new ArrayList<>();
        int NIF, IDRota, IDVoo, idBilhete, AnoA, MesA, DiaA, HoraA, MinutosA, SegundosA, AnoV, MesV, DiaV, HoraV,
                MinutosV,
                SegundosV;

        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
        String linha = f.readLine();
        while (linha != null) {
            String[] camps = linha.split(",");
            NIF = Integer.parseInt(camps[0]);
            IDRota = Integer.parseInt(camps[1]);
            IDVoo = Integer.parseInt(camps[2]);
            idBilhete = Integer.parseInt(camps[3]);
            AnoA = Integer.parseInt(camps[4]);
            MesA = Integer.parseInt(camps[5]);
            DiaA = Integer.parseInt(camps[6]);
            HoraA = Integer.parseInt(camps[7]);
            MinutosA = Integer.parseInt(camps[8]);
            SegundosA = Integer.parseInt(camps[9]);
            AnoV = Integer.parseInt(camps[10]);
            MesV = Integer.parseInt(camps[11]);
            DiaV = Integer.parseInt(camps[12]);
            HoraV = Integer.parseInt(camps[13]);
            MinutosV = Integer.parseInt(camps[14]);
            SegundosV = Integer.parseInt(camps[15]);
            preco = Double.parseDouble(camps[16]);

            Bilhete B = new Bilhete(NIF, IDRota, IDVoo, idBilhete, AnoV, MesV, DiaV, HoraV, MinutosV, SegundosV, AnoA,
                    MesA, DiaA,
                    HoraA, MinutosA, SegundosA, preco);

            if (type == 1) {
                System.out.println(B.toString());
            }

            bilhetes.add(B);

            linha = f.readLine();
        }
        f.close();

        return bilhetes;
    }

}