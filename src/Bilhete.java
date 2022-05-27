import java.util.ArrayList;

public class Bilhete {

    private int IDBilhete;
    private int NIFPassageiro;
    private int IDRota;
    private int IDVoo;
    private int AnoViagem;
    private int MesViagem;
    private int DiaViagem;
    private int HoraViagem;
    private int MinutosViagem;
    private int SegundosViagem;
    private int AnoAquisicao;
    private int MesAquisicao;
    private int DiaAquisicao;
    private int HoraAquisicao;
    private int MinutosAquisicao;
    private int SegundosAquisicao;
    private double Preco;

    public Bilhete(Integer IDB, Integer NIF, Integer IDR, Integer IDV, int AnoV, int MesV, int DiaV, int HoraV,
            int MinutosV, int SegundosV, int AnoA, int MesA, int DiaA, int HoraA, int MinutosA, int SegundosA,
            double Custo) {

        IDBilhete = IDB;
        NIFPassageiro = NIF;
        IDRota = IDR;
        IDVoo = IDV;
        AnoViagem = AnoV;
        MesViagem = MesV;
        DiaViagem = DiaV;
        HoraViagem = HoraV;
        MinutosViagem = MinutosV;
        SegundosViagem = SegundosV;
        AnoAquisicao = AnoA;
        MesAquisicao = MesA;
        DiaAquisicao = DiaA;
        HoraAquisicao = HoraA;
        MinutosAquisicao = MinutosA;
        SegundosAquisicao = SegundosA;
        Preco = Custo;
    }

    public int getIDBilhete() {
        return IDBilhete;
    }

    public int setIDBilhete(Integer IDB) {
        return IDBilhete = IDB;
    }

    public int getNIFPassageiro() {
        return NIFPassageiro;
    }

    public int setNIFPassageiro(Integer NIF) {
        return NIFPassageiro = NIF;
    }

    public int getIDRota() {
        return IDRota;
    }

    public int setIDRota(int IDR) {
        return IDRota = IDR;
    }

    public int getIDVoo() {
        return IDVoo;
    }

    public int setIDVoo(int IDV) {
        return IDVoo = IDV;
    }

    public int getAnoViagem() {
        return AnoViagem;
    }

    public int setAnoViagem(int AnoV) {
        return AnoViagem = AnoV;
    }

    public int getMesViagem() {
        return MesViagem;
    }

    public int setMesViagem(int MesV) {
        return MesViagem = MesV;
    }

    public int getDiaViagem() {
        return DiaViagem;
    }

    public int setDiaViagem(int DiaV) {
        return DiaViagem = DiaV;
    }

    public int getHoraViagem() {
        return HoraViagem;
    }

    public int setHoraViagem(int HoraV) {
        return HoraViagem = HoraV;
    }

    public int getMinutosViagem() {
        return MinutosViagem;
    }

    public int setMinutosViagem(int MinutosV) {
        return MinutosViagem = MinutosV;
    }

    public int getSegundosViagem() {
        return SegundosViagem;
    }

    public int setSegundosViagem(int SegundosV) {
        return SegundosViagem = SegundosV;
    }

    public int getAnoAquisicao() {
        return AnoAquisicao;
    }

    public int setAnoAquisicao(int AnoA) {
        return AnoAquisicao = AnoA;
    }

    public int getMesAquisicao() {
        return MesAquisicao;
    }

    public int setMesAquisicao(int MesA) {
        return MesAquisicao = MesA;
    }

    public int getDiaAquisicao() {
        return DiaAquisicao;
    }

    public int setDiaAquisicao(int DiaA) {
        return DiaAquisicao = DiaA;
    }

    public int getHoraAquisicao() {
        return HoraAquisicao;
    }

    public int setHoraAquisicao(int HoraA) {
        return HoraAquisicao = HoraA;
    }

    public int getMinutosAquisicao() {
        return MinutosAquisicao;
    }

    public int setMinutosAquisicao(int MinutosA) {
        return MinutosAquisicao = MinutosA;
    }

    public int getSegundosAquisicao() {
        return SegundosAquisicao;
    }

    public int setSegundosAquisicao(int SegundosA) {
        return SegundosAquisicao = SegundosA;
    }

    public double getPreco() {
        return Preco;
    }

    public double setPreco(double Custo) {
        return Preco = Custo;
    }

    public static Bilhete getBilheteByID(ArrayList<Bilhete> lista, int id) {

        for (Bilhete bilhete : lista) {
            if (bilhete.getIDBilhete() == id) {
                return bilhete;
            }
        }
        return null;
    }

    public static Bilhete getBilheteByNIF(ArrayList<Bilhete> lista, int NIF) {

        for (Bilhete bilhete : lista) {
            if (bilhete.getNIFPassageiro() == NIF) {
                return bilhete;
            }
        }
        return null;
    }

    public static Bilhete getBilheteByVoo(ArrayList<Bilhete> lista, int id_voo) {

        for (Bilhete bilhete : lista) {
            if (bilhete.getIDVoo() == id_voo) {
                return bilhete;
            }
        }
        return null;
    }

    public static void tostringHeader() {
        System.out.println("+----+-----+------+-----+--------------------+-------------------+--------+");
        System.out.println("| ID | NIF | Rota | Voo |     Data Viagem     |  Data Aquisição   | Preço  |");
        System.out.println("+----+-----+------+-----+--------------------+-------------------+--------+");
    }

    @Override
    public String toString() {

        return "| " + this.getIDBilhete() + " | " + this.getNIFPassageiro() + " |    " + this.getIDRota() + " |   "
                + this.getIDVoo()
                + " | " + this.getAnoViagem() + "/"
                + this.getMesViagem() + "/" + this.getDiaViagem() + " " + this.getHoraViagem() + ":"
                + this.getMinutosViagem() + ":"
                + this.getSegundosViagem() + " | " + this.getAnoAquisicao() + "/" + this.getMesAquisicao() + "/"
                + this.getDiaAquisicao() + " " + this.getHoraAquisicao() + ":" + this.getMinutosAquisicao() + ":"
                + this.getSegundosAquisicao() + " | " + this.getPreco();
    }

}