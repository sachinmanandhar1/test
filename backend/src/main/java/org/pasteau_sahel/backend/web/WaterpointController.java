package org.pasteau_sahel.backend.web;

import org.pasteau_sahel.backend.entities.Waterpoint;
import org.pasteau_sahel.backend.entities.WaterpointType;
import org.pasteau_sahel.backend.services.WaterpointService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class WaterpointController implements Serializable {

    @Inject
    private WaterpointService waterpointService;

    private List<Waterpoint> waterpoints;
    private Waterpoint selectedWaterpoint;

    @PostConstruct
    public void init() {
        waterpoints = waterpointService.findAll();
        selectedWaterpoint = new Waterpoint();
    }

    public void save() {
        if (selectedWaterpoint.getId() == null) {
            waterpointService.save(selectedWaterpoint);
        } else {
            waterpointService.update(selectedWaterpoint);
        }
        waterpoints = waterpointService.findAll();
        selectedWaterpoint = new Waterpoint();
    }

    public void delete(Long id) {
        waterpointService.delete(id);
        waterpoints = waterpointService.findAll();
    }

    public void selectForEdit(Waterpoint waterpoint) {
        this.selectedWaterpoint = waterpoint;
    }

    public void prepareNew() {
        this.selectedWaterpoint = new Waterpoint();
    }

    public WaterpointType[] getWaterpointTypes() {
        return WaterpointType.values();
    }

    // Getters and Setters
    public List<Waterpoint> getWaterpoints() {
        return waterpoints;
    }

    public void setWaterpoints(List<Waterpoint> waterpoints) {
        this.waterpoints = waterpoints;
    }

    public Waterpoint getSelectedWaterpoint() {
        return selectedWaterpoint;
    }

    public void setSelectedWaterpoint(Waterpoint selectedWaterpoint) {
        this.selectedWaterpoint = selectedWaterpoint;
    }
}