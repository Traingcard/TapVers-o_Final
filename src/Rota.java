import java.util.ArrayList;

public class Rota {

    private int ID;
    private int Quantidade;
    private String Destino;
    private double Distancia;
    
    public Rota(Integer IDRota, Integer Quant, String Dest, double Dista) {

        ID = IDRota;
        Quantidade = Quant;
        Destino = Dest;
        Distancia = Dista;

    }

    public int getID() {
        return ID;
    }

    public int setID(Integer IDRota) {
        return ID = IDRota;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public int setQuantidade(int Quant) {
        return Quantidade = Quant;
    }

    public String getDestino() {
        return Destino;
    }

    public String setDestino(String Dest) {
        return Destino = Dest;
    }

    public double getDistancia() {
        return Distancia;
    }

    public double setDistancia(double Dista) {
        return Distancia = Dista;
    }

    public static Rota getRotaByID(ArrayList<Rota> lista, int id){
        for (Rota r : lista) {
            if (r.getID() == id) {
                return r;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getID() + " " + this.getQuantidade() + " " + this.getDestino() + " " + this.getDistancia();
    }
}
