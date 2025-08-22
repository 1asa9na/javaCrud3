package com.example.controller.user;

import com.example.controller.ControllerClientStatus;
import com.example.controller.WriterController;
import com.example.model.Writer;
import com.example.service.ServiceException;
import com.example.service.WriterService;
import com.example.view.GenericView;
import com.example.view.ViewException;
import java.util.List;

/**
 * Implementation of WriterController with user permission.
 */

public class UserWriterControllerImpl extends WriterController {

    public UserWriterControllerImpl(GenericView<Writer> view, WriterService service) {
        super(view, service);
        setClientStatus(ControllerClientStatus.USER);
    }

    @Override
    public void create() {
        try {
            String firstName = getView().getInputString("Enter first name of the writer:");
            String lastName = getView().getInputString("Enter last name of the writer:");
            Writer writer = new Writer();
            writer.setFirstName(firstName);
            writer.setLastName(lastName);
            Writer newWriter = getService().save(writer);
            getView().showMessage("Writer created.");
            getView().showOne(newWriter);
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            Long id = getView().getInputID("Enter ID of the writer:");
            String firstName = getView().getInputString("Enter first name of the writer:");
            String lastName = getView().getInputString("Enter last name of the writer:");
            Writer writer = new Writer();
            writer.setId(id);
            writer.setFirstName(firstName);
            writer.setLastName(lastName);
            Writer newWriter = getService().save(writer);
            getView().showMessage("Writer updated.");
            getView().showOne(newWriter);
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

    @Override
    public void delete() {
        try {
            Long id = getView().getInputID("Enter ID of the writer:");
            getService().delete(id);
            getView().showMessage("Writer " + id + " has been deleted successfully.");
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

    @Override
    public void read() {
        try {
            String input = getView().getInputString("Enter writer ID or \"all\" to select all writers.");
            if ("all".equalsIgnoreCase(input)) {
                List<Writer> writers = getService().getAll();
                getView().showMany(writers);
            } else {
                Long id = Long.parseLong(input);
                Writer writer = getService().getById(id);
                getView().showOne(writer);
            }
        } catch (NumberFormatException e) {
            getView().showMessage("Error: wrong input.");
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }
}
