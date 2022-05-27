import java.util.ArrayList;

public class Auxiliar {
    private int RotaID;
    private int VooID;
    private int Lugares;
    private int Vendidos;
    private int Reservados;

    public Auxiliar(int RotaID, int VooID, int Lugares, int Vendidos, int Reservados) {
        this.RotaID = RotaID;
        this.VooID = VooID;
        this.Lugares = Lugares;
        this.Vendidos = Vendidos;
        this.Reservados = Reservados;
    }

    public int getRotaID() {
        return RotaID;
    }

    public Rota getRota(ArrayList<Rota> lista) {
        return Rota.getRotaByID(lista, this.RotaID);
    }

    public Voo getVoo(ArrayList<Voo> lista) {
        return Voo.getVooByID(lista, this.VooID);
    }

    public static Auxiliar getAuxiliarByRotaIDAndVooID(ArrayList<Auxiliar> lista, int rotaID, int vooID) {

        if ((lista == null ) || (rotaID == 0 || vooID == 0)) {
            return null;
        }

        for (Auxiliar aux : lista) {
            if (aux.getRotaID() == rotaID && aux.getVooID() == vooID) {
                return aux;
            }
        }

        return null;
    }

    public void setRotaID(int RotaID) {
        this.RotaID = RotaID;
    }

    public int getVooID() {
        return VooID;
    }

    public void setVooID(int VooID) {
        this.VooID = VooID;
    }

    public int getLugares() {
        return Lugares;
    }

    public void setLugares(int Lugares) {
        this.Lugares = Lugares;
    }

    public int getVendidos() {
        return Vendidos;
    }

    public void setVendidos(int Vendidos) {
        this.Vendidos = Vendidos;
    }

    public int getReservados() {
        return Reservados;
    }

    public void setReservados(int Reservados) {
        this.Reservados = Reservados;
    }

    @Override
    public String toString() {
        return "Auxiliar{" + "RotaID=" + RotaID + ", VooID=" + VooID + ", Lugares=" + Lugares + ", Vendidos=" + Vendidos + ", Reservados=" + Reservados + '}';
    }
}
