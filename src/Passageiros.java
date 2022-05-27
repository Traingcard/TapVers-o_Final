import java.util.ArrayList;

public class Passageiros {

    private int Nif;
    private String Nome;
    private String Profissao;
    private String Morada;
    private String DataNascimento;

    public Passageiros(Integer Nifo, String Name, String Prof, String Mor, String DataNasc) {

        Nif = Nifo;
        Nome = Name;
        Profissao = Prof;
        Morada = Mor;
        DataNascimento = DataNasc;

    }

    public int getNIF() {
        return Nif;
    }

    public int setNIF(Integer Nifo) {
        return Nif = Nifo;
    }

    public String getNome() {
        return Nome;
    }

    public String setNome(String Name) {
        return Nome = Name;
    }

    public String getProfissao() {
        return Profissao;
    }

    public String setProfissao(String Prof) {
        return Profissao = Prof;
    }

    public String getMorada() {
        return Morada;
    }

    public String setMorada(String Mor) {
        return Morada = Mor;
    }

    public String getDataNascimento() {
        return DataNascimento;
    }

    public String setDataNascimento(String DataNasc) {
        return DataNascimento = DataNasc;
    }

    public static Passageiros getPassageiroByNif (ArrayList<Passageiros> lista, int Nifo) {

        for (Passageiros p : lista) {

            if (p.getNIF() == Nifo) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getNIF() + " " + getNome() + " " + getProfissao() + " " + getMorada() + " " + getDataNascimento();
    }
}
