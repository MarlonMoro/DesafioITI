import model.Extrato;
import model.Transacao;
import service.GeradorExtrato;

public class Application {

    public static void main(String[] args) {
        Extrato extrato = new Extrato();

        GeradorExtrato geradorExtrato = new GeradorExtrato();
        geradorExtrato.leitorArquivo(extrato);
        geradorExtrato.leitorApi(extrato);

    }
}
