/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.DbTables;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.grids.AttachmentGrid;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.interfaces.SeriousGameDataInteractionForm;
import com.customlib.seriousgame.client.models.*;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public class GeneralInfoForm extends FormPanel implements SeriousGameDataInteractionForm{

    private MainEntryPointInterface entrypoint;

    private TextField<String> title;
    private ComboBox<GenericModel> publisher;
    private TextField<String> webSite;
    private ComboBox<GenericModel> status;
    private SimpleComboBox<Integer> yearOfFirstRelease ;
    private SimpleComboBox<Integer> yearOfLastRelease;
    private ComboBox<GenericModel> availability;
    private TextArea description;
//    private DualListField<Age> ageLists;
    private TextArea keywords;
    private AttachmentGrid attacments;

    private boolean changes = false;

    public GeneralInfoForm(final MainEntryPointInterface entrypoint) {
        this.entrypoint = entrypoint;

        setEncoding(FormPanel.Encoding.MULTIPART);
        setMethod(FormPanel.Method.POST);
        setAction("ServletUpload");
        setHeaderVisible(false);
        setWidth(Utilities.formWidth - 20);
        FormLayout layout = new FormLayout(LabelAlign.LEFT);
        layout.setLabelWidth(Utilities.formLabelWidth);
        setLayout(layout);
        setBodyBorder(false);
        setScrollMode(Style.Scroll.AUTO);
//        setHeight(450);

        FormData formData = new FormData("-30");

        title = new TextField<String>();
        title.setFieldLabel(Constants.Strings.sg_title());
        title.setAllowBlank(false);
        title.getFocusSupport().setPreviousId(getButtonBar().getId());
        title.setMaxLength(Utilities.varCharLimitNormal);
        add(title, formData);

        ListStore<GenericModel> publisherStore = new ListStore<GenericModel>();
        publisherStore.add(entrypoint.getDatabase().getPublisherList());
        publisherStore.add(new GenericModel(-10, Constants.Strings.addNew(), ""));

        publisher = new ComboBox<GenericModel>();
        publisher.setForceSelection(true);
        publisher.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
        publisher.setFieldLabel(Constants.Strings.sg_publisher());
        publisher.setDisplayField(ModelTag.name.toString());
        publisher.setTriggerAction(ComboBox.TriggerAction.ALL);
        publisher.setStore(publisherStore);
        publisher.addSelectionChangedListener(new SelectionChangedListener<GenericModel>() {
            @Override
            public void selectionChanged(SelectionChangedEvent<GenericModel> se) {
                if (se.getSelectedItem().getId() == -10) {
                    PublisherForm form = new PublisherForm(entrypoint);
                    form.show();
                    form.addListener(Events.Hide, new Listener<WindowEvent>() {

                        @Override
                        public void handleEvent(WindowEvent be) {
                            PublisherForm form = (PublisherForm) be.getSource();
                            if (form.isOkClose()) {
                                entrypoint.getPublishers(asyncGetPublishers);
                            }
                        }
                    });
                    publisher.setValue(null);
                }
            }
        });
        add(publisher, formData);

        webSite = new TextField<String>();
        webSite.setFieldLabel(Constants.Strings.sg_webSite());
        webSite.setRegex(Utilities.urlRegExp);
        webSite.getMessages().setRegexText(Constants.Strings.urlNotValid());
        webSite.setMaxLength(Utilities.varCharLimitLarge);
        add(webSite, formData);

        ListStore<GenericModel> statusStore = new ListStore<GenericModel>();
        statusStore.add(entrypoint.getDatabase().getStatusList());

        status = new ComboBox<GenericModel>();
        status.setForceSelection(true);
        status.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
        status.setFieldLabel(Constants.Strings.sg_status());
        status.setDisplayField(ModelTag.name.toString());
        status.setTriggerAction(ComboBox.TriggerAction.ALL);
        status.setStore(statusStore);
        status.addSelectionChangedListener(new SelectionChangedListener<GenericModel>() {
            @Override
            public void selectionChanged(SelectionChangedEvent<GenericModel> se) {
                Utilities.otherSelectedOnComboBox(entrypoint, se);
            }
        });
        add(status, formData);

        yearOfFirstRelease = new SimpleComboBox<Integer>();
        yearOfFirstRelease.setTriggerAction(ComboBox.TriggerAction.ALL);
        yearOfFirstRelease.add(entrypoint.getDatabase().getYears());
        yearOfFirstRelease.setFieldLabel(Constants.Strings.sg_yearFirstRelease());
        yearOfFirstRelease.getMessages().setInvalidText(Constants.Strings.sg_yearErrorMessage());
        yearOfFirstRelease.setValidator(new Validator() {
            @Override
            public String validate(Field<?> field, String value) {
                int currentYear = (new Date()).getYear() + 1900;
                try {
                    int year = Integer.parseInt(value);
                    if (year < 1900 || year > currentYear) {
                        return Constants.Strings.sg_yearErrorMessage().replaceAll("currentYear", String.valueOf(currentYear));
                    }
                } catch (NumberFormatException e) {
                    return Constants.Strings.sg_yearErrorMessage().replaceAll("currentYear", String.valueOf(currentYear));
                } catch (Exception e) {
                    return Constants.Strings.sg_yearErrorMessage().replaceAll("currentYear", String.valueOf(currentYear));
                }
                return null;
            }
        });
        add(yearOfFirstRelease, formData);

        yearOfLastRelease = new SimpleComboBox<Integer>();
        yearOfLastRelease.setTriggerAction(ComboBox.TriggerAction.ALL);
        yearOfLastRelease.add(entrypoint.getDatabase().getYears());
        yearOfLastRelease.setFieldLabel(Constants.Strings.sg_yearLastRelease());
        yearOfLastRelease.getMessages().setInvalidText(Constants.Strings.sg_yearErrorMessage());
        yearOfLastRelease.setValidator(new Validator() {

            @Override
            public String validate(Field<?> field, String value) {
                int currentYear = (new Date()).getYear() + 1900;
                try {
                    int year = Integer.parseInt(value);
                    if (year < 1900 || year > currentYear) {
                        return Constants.Strings.sg_yearErrorMessage().replaceAll("currentYear", String.valueOf(currentYear));
                    }
                } catch (NumberFormatException e) {
                    return Constants.Strings.sg_yearErrorMessage().replaceAll("currentYear", String.valueOf(currentYear));
                } catch (Exception e) {
                    return Constants.Strings.sg_yearErrorMessage().replaceAll("currentYear", String.valueOf(currentYear));
                }
                return null;
            }
        });
        add(yearOfLastRelease, formData);

        ListStore<GenericModel> availabilityStore = new ListStore<GenericModel>();
        availabilityStore.add(entrypoint.getDatabase().getAvailabilityList());
        availabilityStore.add(new GenericModel(-10, Constants.Strings.addNew(), null));

        availability = new ComboBox<GenericModel>();
        availability.setForceSelection(true);
        availability.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
        availability.setFieldLabel(Constants.Strings.sg_availability());
        availability.setDisplayField(ModelTag.name.toString());
        availability.setTriggerAction(ComboBox.TriggerAction.ALL);
        availability.setStore(availabilityStore);
        availability.addSelectionChangedListener(new SelectionChangedListener<GenericModel>() {
            @Override
            public void selectionChanged(SelectionChangedEvent<GenericModel> se) {
                if (se.getSelectedItem().getId() == -10) {
                    GenericForm form = new GenericForm(entrypoint, Constants.Strings.sg_availability(), DbTables.availability.toString());
                    form.show();
                    form.addListener(Events.Hide, new Listener<WindowEvent>() {

                        @Override
                        public void handleEvent(WindowEvent be) {
                            GenericForm form = (GenericForm) be.getSource();
                            if (form.isOkClose()) {
                                entrypoint.getGeneric(DbTables.availability.toString(), asyncGetAvailability);
                            }
                        }
                    });
                    availability.setValue(null);
                }
            }
        });
        add(availability, formData);

        description = new TextArea();
        description.setPreventScrollbars(true);
        description.setAllowBlank(false);
        description.setFieldLabel(Constants.Strings.sg_freeDescription());
        description.setMaxLength(Utilities.textLimitLarge);
        add(description, formData);

        keywords = new TextArea();
        keywords.setPreventScrollbars(true);
        keywords.setAllowBlank(false);
        keywords.setFieldLabel(Constants.Strings.sg_keywords());
        keywords.setMaxLength(Utilities.textLimitShort);
        add(keywords, formData);

        attacments = new AttachmentGrid(entrypoint);
        attacments.init();
        add(attacments, formData);

    }

    final AsyncCallback<List<GenericModel>> asyncGetPublishers = new AsyncCallback<List<GenericModel>>() {

        @Override
        public void onSuccess(List<GenericModel> result) {
            if(result != null)
            {
                entrypoint.getDatabase().setPublisherList(result);
                reloadPublisher();
            }
        }
        @Override
        public void onFailure(Throwable caught) {
            MessageBox.info("Exceprion", "Failed to get publishers.", null);
        }
    };
    final AsyncCallback<ArrayList<GenericModel>> asyncGetStatus = new AsyncCallback<ArrayList<GenericModel>>() {

        @Override
        public void onSuccess(ArrayList<GenericModel> result) {
            if(result != null)
            {
                entrypoint.getDatabase().setStatusList(result);
                reloadStatus();
            }
        }
        @Override
        public void onFailure(Throwable caught) {
            MessageBox.info("Exceprion", "Failed to get status.", null);
        }
    };
    final AsyncCallback<List<GenericModel>> asyncGetAvailability = new AsyncCallback<List<GenericModel>>() {

        @Override
        public void onSuccess(List<GenericModel> result) {
            if(result != null)
            {
                entrypoint.getDatabase().setAvailabilityList(result);
                reloadAvailability();
            }
        }
        @Override
        public void onFailure(Throwable caught) {
            MessageBox.info("Exceprion", "Failed to get availability.", null);
        }
    };
    private void reloadPublisher()
    {
        ListStore<GenericModel> store = new ListStore<GenericModel>();
        store.add(entrypoint.getDatabase().getPublisherList());
        store.add(new GenericModel(-10, Constants.Strings.addNew(), ""));

        publisher.setStore(store);
    }
    private void reloadStatus()
    {
        status.getStore().removeAll();
        status.getStore().add(entrypoint.getDatabase().getStatusList());
        status.getStore().add(new GenericModel(-10, Constants.Strings.addNew(), null));
    }
    private void reloadAvailability()
    {
        availability.getStore().removeAll();
        availability.getStore().add(entrypoint.getDatabase().getAvailabilityList());
        availability.getStore().add(new GenericModel(-10, Constants.Strings.addNew(), null));
    }

    /**
     * @return the record
     */
    @Override
    public void updateRecord(SeriousGame record) {

        record.set(SeriousGame.TagTitle, title.getValue());
        if (publisher.getValue() != null) {
            record.set(SeriousGame.TagPublisherID, publisher.getValue().getId());
        } else {
            record.set(SeriousGame.TagPublisherID, null);
        }
        record.set(SeriousGame.TagWebSite, webSite.getValue());
        if (status.getValue() != null) {
            record.set(SeriousGame.TagStatusID, status.getValue().getId());
            Utilities.updateOtherList(record, DbTables.status.toString(), status);
        } else {
            record.set(SeriousGame.TagStatusID, null);
        }
        if (yearOfFirstRelease.getValue() != null) {
            record.set(SeriousGame.TagYearFirstRelease, yearOfFirstRelease.getSimpleValue());
        } else {
            record.set(SeriousGame.TagYearFirstRelease, null);
        }
        if (yearOfLastRelease.getValue() != null) {
            record.set(SeriousGame.TagYearLastRelease, yearOfLastRelease.getSimpleValue());
        } else {
            record.set(SeriousGame.TagYearLastRelease, null);
        }
        if (availability.getValue() != null) {
            record.set(SeriousGame.TagAvailabilityID, availability.getValue().getId());
        } else {
            record.set(SeriousGame.TagAvailabilityID, null);
        }

        record.set(SeriousGame.TagAttachments.toString(), attacments.getData());
        record.set(SeriousGame.TagFreeDescription, description.getValue());
        record.set(SeriousGame.TagKeywords, keywords.getValue());

        record.set(SeriousGame.TagOwnerId, entrypoint.getDatabase().getCurrentUser().getId());
        record.set(SeriousGame.TagOwner, entrypoint.getDatabase().getCurrentUser().get(User.TagEmail));
        record.set(SeriousGame.TagGeneralIfoChanges, hasChanges());
    }

    /**
     * @param record the record to set
     */
    @Override
    public void setRecord(SeriousGame record) {
        attacments.setSeriousgameId(record.getId());

        title.setValue((String)record.get(SeriousGame.TagTitle));

        Integer publisherId = (Integer)record.get(SeriousGame.TagPublisherID);
        if(publisherId != null && publisherId.intValue() > 0)
            Utilities.setComboBoxSelection(new GenericModel(publisherId, "", ""), ModelTag.id.toString(), publisher);

        webSite.setValue((String)record.get(SeriousGame.TagWebSite));

        Integer statusId = (Integer)record.get(SeriousGame.TagStatusID);
        if(statusId != null && statusId.intValue() > 0)
        {
            Utilities.setComboBoxSelectionWhithOtherList(new GenericModel(statusId, "", ""), ModelTag.id.toString(), status, (List<GenericModel>)record.get(SeriousGame.TagOthers), DbTables.status.toString());
        }

        Integer yearOfFirstRelease = (Integer)record.get(SeriousGame.TagYearFirstRelease);
        if(yearOfFirstRelease != null && yearOfFirstRelease.intValue() > 0)
            this.yearOfFirstRelease.setSimpleValue(yearOfFirstRelease);

        Integer yearOfLastRelease = (Integer)record.get(SeriousGame.TagYearLastRelease);
        if(yearOfLastRelease != null && yearOfLastRelease.intValue() > 0)
            this.yearOfLastRelease.setSimpleValue(yearOfLastRelease);

        Integer availabilityId = (Integer)record.get(SeriousGame.TagAvailabilityID);
        if(availabilityId != null && availabilityId.intValue() > 0)
            Utilities.setComboBoxSelection(new GenericModel(availabilityId, "", ""), ModelTag.id.toString(), availability);

        description.setValue((String)record.get(SeriousGame.TagFreeDescription));

        keywords.setValue((String)record.get(SeriousGame.TagKeywords));
        attacments.setData((List<Attachment>)record.get(SeriousGame.TagAttachments));
    }

    @Override
    public boolean hasChanges() {
        return this.changes;
    }

    @Override
    public void setChanges(boolean changes) {
        this.changes = changes;
    }
}
