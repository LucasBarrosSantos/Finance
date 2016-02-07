package Controller;

import Dao.EventosDaoImp;
import Model.Evento;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author lucas
 */
@ManagedBean
@ViewScoped
public class EventoMB {

    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    private Evento evento;

    private EventosDaoImp dao;

    @ManagedProperty(value = "#{usuarioMB}")
    private UsuarioMB usuarioMB;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        dao = new EventosDaoImp();
        dao.usuarioLogado(usuarioMB.usuarioDaSessao(), usuarioMB.senhaUsuarioDaSessao());

        for (Evento e : dao.listarTodos()) {
            eventModel.addEvent(new DefaultScheduleEvent(e.getTitulo(), e.getDatainicial(), e.getDatafinal()));
        }

    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);

            dao = new EventosDaoImp();
            evento = new Evento();

            evento.setTitulo(event.getTitle());
            evento.setDatainicial(event.getStartDate());
            evento.setDatafinal(event.getEndDate());
            evento.setCriadoEm(new Date());
            evento.setDescricao(event.getTitle());
            evento.setCriadoPor(usuarioMB.usuarioDaSessao() + usuarioMB.senhaUsuarioDaSessao());

            dao.adicionar(evento);
        } else {
            eventModel.updateEvent(event);

            dao = new EventosDaoImp();
            evento = new Evento();

            List<Evento> eventos = dao.pesquisar(event.getTitle(), event.getStartDate(),
                    event.getEndDate(), event.getTitle(),
                    usuarioMB.usuarioDaSessao() + usuarioMB.senhaUsuarioDaSessao());

            for (Evento e : eventos) {
                evento.setId(e.getId());
            }

            evento.setTitulo(event.getTitle());
            evento.setDatainicial(event.getStartDate());
            evento.setDatafinal(event.getEndDate());
            evento.setCriadoEm(new Date());
            evento.setDescricao(event.getTitle());
            evento.setCriadoPor(usuarioMB.usuarioDaSessao() + usuarioMB.senhaUsuarioDaSessao());

            dao.atualizar(evento);
        }

        event = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public UsuarioMB getUsuarioMB() {
        return usuarioMB;
    }

    public void setUsuarioMB(UsuarioMB usuarioMB) {
        this.usuarioMB = usuarioMB;
    }

}
