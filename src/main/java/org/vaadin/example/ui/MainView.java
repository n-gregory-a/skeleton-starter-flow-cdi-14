package org.vaadin.example.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.example.backend.entity.ServiceEntity;
import org.vaadin.example.backend.entity.ServiceStatus;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.vaadin.example.backend.entity.ServiceStatus.*;

@Route("")
public class MainView extends VerticalLayout {

    private final Grid<ServiceEntity> grid = new Grid<>(ServiceEntity.class);
    private final List<ServiceEntity> serviceEntityList = initializeServiceList();

    @PostConstruct
    public void init() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(createButtonsLayout(), grid);
        updateGridList();

    }

    private Component createButtonsLayout() {
        Button start = new Button(VaadinIcon.PLAY.create());
        Button pause = new Button(VaadinIcon.PAUSE.create());
        Button stop = new Button(VaadinIcon.STOP.create());
        start.addClickListener(click -> performAction(Started));
        pause.addClickListener(click -> performAction(Paused));
        stop.addClickListener(click -> performAction(Stopped));
        return new HorizontalLayout(start, pause, stop);
    }

    private void performAction(ServiceStatus status) {
        Optional<ServiceEntity> optionalServiceEntity = grid.getSelectionModel().getFirstSelectedItem();
        if (optionalServiceEntity.isPresent()) {
            ServiceEntity serviceEntity = optionalServiceEntity.get();
            serviceEntity.setStatus(status);
            serviceEntityList.remove(serviceEntity.getId() - 1);
            serviceEntityList.add(serviceEntity.getId() - 1, serviceEntity);
            updateGridList();
        }
    }

    private void configureGrid() {
        grid.addClassName("service-list-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("status");
        grid.setColumns("id", "name", "address", "version");
        grid.addComponentColumn(serviceEntity -> {
            ServiceStatus status = serviceEntity.getStatus();
            return getIcon(status);
        }).setHeader("Status");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

    private List<ServiceEntity> initializeServiceList() {
        List<ServiceEntity> list = new ArrayList<>();
        list.add(new ServiceEntity(1, "Service_1", "http://service_1", "1.0.1", Stopped));
        list.add(new ServiceEntity(2, "Service_2", "http://service_2", "1.0.1", Stopped));
        list.add(new ServiceEntity(3, "Service_3", "http://service_3", "1.0.1", Stopped));
        list.add(new ServiceEntity(4, "Service_4", "http://service_4", "1.0.1", Stopped));
        list.add(new ServiceEntity(5, "Service_5", "http://service_5", "1.0.1", Stopped));
        list.add(new ServiceEntity(6, "Service_6", "http://service_6", "1.0.1", Stopped));
        list.add(new ServiceEntity(7, "Service_7", "http://service_7", "1.0.1", Stopped));
        list.add(new ServiceEntity(8, "Service_8", "http://service_8", "1.0.1", Stopped));
        list.add(new ServiceEntity(9, "Service_9", "http://service_9", "1.0.1", Stopped));
        list.add(new ServiceEntity(10, "Service_10", "http://service_10", "1.0.1", Stopped));
        list.add(new ServiceEntity(11, "Service_11", "http://service_11", "1.0.1", Stopped));
        list.add(new ServiceEntity(12, "Service_12", "http://service_12", "1.0.1", Stopped));
        return list;
    }

    private void updateGridList() {
        grid.setItems(serviceEntityList);
    }

    private Icon getIcon(ServiceStatus status) {
        Icon icon;
        switch (status) {
            case Started:
                icon = VaadinIcon.ARROW_CIRCLE_UP.create();
                icon.setColor("green");
            break;
            case Paused:
                icon = new Icon(VaadinIcon.CIRCLE);
                icon.setColor("blue");
            break;
            default:
                icon = new Icon(VaadinIcon.ARROW_CIRCLE_DOWN);
                icon.setColor("red");
        }
        return icon;
    }

}
