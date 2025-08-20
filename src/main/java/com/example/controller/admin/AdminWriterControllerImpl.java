package com.example.controller.admin;

import java.util.List;
import com.example.controller.ControllerClientStatus;
import com.example.controller.WriterController;
import com.example.model.Writer;
import com.example.service.WriterService;
import com.example.view.GenericView;

public class AdminWriterControllerImpl extends WriterController {

    public AdminWriterControllerImpl(GenericView<Writer> view, WriterService service) {
        super(view, service);
        this.clientStatus = ControllerClientStatus.ADMIN;
    }

    @Override
    public void create() {
        try {
            String firstName = view.getInputString("Enter first name of the writer:");
            String lastName = view.getInputString("Enter last name of the writer:");
            Writer writer = new Writer();
            writer.setFirstName(firstName);
            writer.setLastName(lastName);
            Writer newWriter = service.save(writer);
            view.showMessage("Writer created.");
            view.showOne(newWriter);
        } catch (Exception e) {
            view.showMessage("Error occured during CREATE.");
        }
    }

    @Override
    public void update() {
        try {
            Long id = view.getInputID("Enter ID of the writer:");
            String firstName = view.getInputString("Enter first name of the writer:");
            String lastName = view.getInputString("Enter last name of the writer:");
            Writer writer = new Writer();
            writer.setId(id);
            writer.setFirstName(firstName);
            writer.setLastName(lastName);
            Writer newWriter = service.save(writer);
            view.showMessage("Writer updated.");
            view.showOne(newWriter);
        } catch (Exception e) {
            view.showMessage("Error occured during UPDATE.");
        }
    }

    @Override
    public void delete() {
        try {
            Long id = view.getInputID("Enter ID of the writer:");
            service.delete(id);
            view.showMessage("Writer " + id + " has been deleted successfully.");
        } catch (Exception e) {
            view.showMessage("Error occured during DELETE.");
        }
    }

    @Override
    public void read() {
        try {
            String input = view.getInputString("Enter writer ID or \"all\" to select all writers.");
            if (input.equalsIgnoreCase("all")) {
                List<Writer> writers = service.getAll();
                view.showMany(writers);
            } else {
                Long id = Long.parseLong(input);
                Writer writer = service.getById(id);
                view.showOne(writer);
            }
        } catch (NumberFormatException e) {
            view.showMessage("Error: wrong input.");
        } catch (Exception e) {
            view.showMessage("Error occured on READ.");
        }
    }
}
