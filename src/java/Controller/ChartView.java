package Controller;
/*
import Dao.TitulosDaoImp;
import Model.Titulo;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.primefaces.event.ItemSelectEvent;

import org.primefaces.model.chart.PieChartModel;

@ManagedBean
public class ChartView {

    private PieChartModel pieModel1;
    private TitulosDaoImp dao;

    @ManagedProperty(value = "#{usuarioMB}")
    private UsuarioMB usuarioMB;

    public ChartView() {
    }

    @PostConstruct
    public void init() {
        createPieModels();
    }

    private void createPieModels() {
        createPieModel1();
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();
        dao = new TitulosDaoImp();

        dao.usuarioLogado(usuarioMB.usuarioDaSessao(), usuarioMB.senhaUsuarioDaSessao());

        for (Object ti : dao.valoresGraficoDeDespessas()) {
            for (Titulo t : dao.graficoDeDespessas()) {
                pieModel1.set(t.getEntidadeId().getNome(), (Number) ti);
            }
        }

        pieModel1.setTitle("Gráfico de Despesa");
        pieModel1.setLegendPosition("w");
    }

    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selecionado",
                "Item: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public UsuarioMB getUsuarioMB() {
        return usuarioMB;
    }

    public void setUsuarioMB(UsuarioMB usuarioMB) {
        this.usuarioMB = usuarioMB;
    }
}
*/