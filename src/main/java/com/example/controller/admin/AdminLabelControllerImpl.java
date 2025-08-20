package com.example.controller.admin;

import java.util.List;
import com.example.controller.LabelController;
import com.example.model.Label;
import com.example.service.LabelService;
import com.example.view.GenericView;

public class AdminLabelControllerImpl extends LabelController {

    public AdminLabelControllerImpl(GenericView<Label> view, LabelService service) {
        super(view, service);
    }

    @Override
    public void create() {
        try {
            String name = view.getInputString("Enter name of the label:");
            Label label = new Label();
            label.setName(name);
            Label newLabel = service.save(label);
            view.showMessage("Label created.");
            view.showOne(newLabel);
        } catch (Exception e) {
            view.showMessage("Error occured during CREATE.");
        }
    }

    @Override
    public void update() {
        try {
            Long id = view.getInputID("Enter ID of the label:");
            String name = view.getInputString("Enter name of the label:");
            Label label = new Label(id, name);
            Label newLabel = service.update(label);
            view.showMessage("Label updated.");
            view.showOne(newLabel);
        } catch (Exception e) {
            view.showMessage("Error occured during UPDATE.");
        }
    }

    @Override
    public void delete() {
        try {
            Long id = view.getInputID("Enter ID of the label:");
            service.delete(id);
            view.showMessage("Label " + id + " has been deleted successfully.");
        } catch (Exception e) {
            view.showMessage("Error occured during DELETE.");
        }
    }

    @Override
    public void read() {
        try {
            String input = view.getInputString("Enter label ID or \"all\" to select all labels.");
            if (input.equalsIgnoreCase("all")) {
                List<Label> labels = service.getAll();
                view.showMany(labels);
            } else {
                Long id = Long.parseLong(input);
                Label label = service.getById(id);
                view.showOne(label);
            }
        } catch (NumberFormatException e) {
            view.showMessage("Error: wrong input.");
        } catch (Exception e) {
            view.showMessage("Error occured on READ.");
        }
    }

}
