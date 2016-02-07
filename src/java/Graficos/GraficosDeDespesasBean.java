package Graficos;

import Controller.UsuarioMB;
import Dao.TitulosDaoImp;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import Model.Titulo;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
@SessionScoped
public class GraficosDeDespesasBean {

    @ManagedProperty(value = "#{usuarioMB}")
    private UsuarioMB usuarioMB;

    private LineChartModel dateModel;
    private TitulosDaoImp daoImp;

    public GraficosDeDespesasBean() throws Exception {
        createDateModel();
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    private void createDateModel() throws Exception {
        dateModel = new LineChartModel();
        daoImp = new TitulosDaoImp();

        LineChartSeries entrada = new LineChartSeries();
        entrada.setLabel("Entrada");

        List<Titulo> list = daoImp.valores();
        int i = 0;

        for (Object o : daoImp.mesesGraficos()) {
            Object v = list.get(i);
            entrada.set(o.toString(), Double.parseDouble(v.toString()));
            i += 1;
        }

        //--------------------------------------------------
        LineChartSeries said = new LineChartSeries();
        said.setLabel("Saída");

        List<Titulo> saida = daoImp.valores_Saida();
        int i1 = 0;

        for (Object o : daoImp.mesesGraficos_Saida()) {
            Object v = saida.get(i1);
            said.set(o.toString(), Double.parseDouble(v.toString()));
            i1 += 1;
        }

        dateModel.addSeries(entrada);
        dateModel.addSeries(said);

        dateModel.setAnimate(true);
        dateModel.setTitle("Gráfico de Despesas");
        //dateModel.setZoom(true); Descomentar para habilitar o zoom
        dateModel.getAxis(AxisType.Y).setLabel("Valores R$");
        DateAxis axis = new DateAxis("Azul: Receita | Laranja: Despesa");
        axis.setTickAngle(-30); // Angulo nome dos meses
        axis.setMax("2016-12-01");
        axis.setMin("2016-01-01");
        axis.setTickFormat("%#d %b, %y");
        //axis.setTickFormat("%#d %#m %Y");        
        dateModel.getAxes().put(AxisType.X, axis);
    }

    public UsuarioMB getUsuarioMB() {
        return usuarioMB;
    }

    public void setUsuarioMB(UsuarioMB usuarioMB) {
        this.usuarioMB = usuarioMB;
    }
}
