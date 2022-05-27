import java.util.ArrayList;

public class Voo {
    private int ID;
    private int Rota;
    private String Dia;
    private int Hora;
    private int Minutos;
    private int Segundos;
    private String Marca;
    private int Lugares;

    public Voo(Integer IDVoo, Integer IDRota, String Day, int Hour, int Minutes, int Seconds, String MarcaAviao) {
        this.ID = IDVoo;
        this.Rota = IDRota;
        this.Dia = Day;
        this.Hora = Hour;
        this.Minutos = Minutes;
        this.Segundos = Seconds;
        this.Marca = MarcaAviao;
        this.setLugaresByMarca(MarcaAviao);
    }

    public int getID() {
        return ID;
    }

    public int setID(Integer IDVoo) {
        return ID = IDVoo;
    }

    public int getRota() {
        return Rota;
    }

    public int setRota(int IDRota) {
        return Rota = IDRota;
    }

    public String getDia() {
        return Dia;
    }

    public String setDia(String Day) {
        return Dia = Day;
    }

    public int getHora() {
        return Hora;
    }

    public int setHora(int Hour) {
        return Hora = Hour;
    }

    public int getMinutos() {
        return Minutos;
    }

    public int setMinutos(int Minutes) {
        return Minutos = Minutes;
    }

    public int getSegundos() {
        return Segundos;
    }

    public int setSegundos(int Seconds) {
        return Segundos = Seconds;
    }

    public String getMarca() {
        return Marca;
    }

    public String setMarca(String MarcaAviao) {
        return Marca = MarcaAviao;
    }

    public int getLugares() {
        return this.Lugares;
    }

    public void setLugares(int lugares) {
        this.Lugares = lugares;
    }

    public void setLugaresByMarca(String MarcaAviao) {
        int lugares = 0;

        if (MarcaAviao.trim().length() < 1) {
            this.Lugares = lugares;
            return;
        }

        switch (MarcaAviao) {
            case "Bombardier Challenger 350":
                lugares = 10;
                break;
            case "Embraer Phenom 300":
                lugares = 9;
                break;
            case "Cessna Citation Latitude":
                lugares = 12;
                break;
            case "Bombardier Global 6000":
                lugares = 17;
                break;
        }

        this.Lugares = lugares;
    }

    public static Voo getVooByID(ArrayList<Voo> lista, int id) {
        for (Voo v : lista) {
            if (v.getID() == id) {
                return v;
            }
        }
        return null;
    }

    public double getCustoVoo(double distancia) {
        if (distancia * 1 > 1000) {
            return distancia * 0.50;
        } else if (distancia * 1 > 300) {
            return distancia * 0.75;
        } else {
            return distancia;
        }
    }

    @Override
    public String toString() {
        return this.getRota() + " " + this.getID() + " " + this.getDia() + " " + this.getHora() + " "
                + this.getMinutos() + " "
                + this.getSegundos() + " " + this.getMarca();
    }

}
