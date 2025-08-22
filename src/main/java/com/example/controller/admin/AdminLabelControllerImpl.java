package com.example.controller.admin;

import com.example.controller.LabelController;
import com.example.model.Label;
import com.example.service.LabelService;
import com.example.service.ServiceException;
import com.example.view.GenericView;
import com.example.view.ViewException;
import java.util.List;

/**
 * Implementation of LabelController with administrator permission.
 */

public class AdminLabelControllerImpl extends LabelController {

    public AdminLabelControllerImpl(GenericView<Label> view, LabelService service) {
        super(view, service);
    }

    @Override
    public void create() {
        try {
            String name = getView().getInputString("Enter name of the label:");
            Label label = new Label();
            label.setName(name);
            Label newLabel = getService().save(label);
            getView().showMessage("Label created.");
            getView().showOne(newLabel);
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            Long id = getView().getInputID("Enter ID of the label:");
            String name = getView().getInputString("Enter name of the label:");
            Label label = new Label(id, name);
            Label newLabel = getService().update(label);
            getView().showMessage("Label updated.");
            getView().showOne(newLabel);
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

    @Override
    public void delete() {
        try {
            Long id = getView().getInputID("Enter ID of the label:");
            getService().delete(id);
            getView().showMessage("Label " + id + " has been deleted successfully.");
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

    @Override
    public void read() {
        try {
            String input = getView().getInputString("Enter label ID or \"all\" to select all labels.");
            if ("all".equalsIgnoreCase(input)) {
                List<Label> labels = getService().getAll();
                getView().showMany(labels);
            } else {
                Long id = Long.parseLong(input);
                Label label = getService().getById(id);
                getView().showOne(label);
            }
        } catch (NumberFormatException e) {
            getView().showMessage("Error: wrong input.");
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

}
