/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.grids.GenericGrid;
import com.customlib.seriousgame.client.interfaces.ClosureFormInterface;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.Context;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.GenericModelWithRelation;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public class ContextForm extends Window implements ClosureFormInterface {

	private MainEntryPointInterface entrypoint;
	private FormPanel form;
	private boolean okClose = false;
	private TextField<String> name;
	private MyExtendedClasses.CostomTextArea description;
	private ComboBox<GenericModel> typeOfContext;
	private ComboBox<GenericModel> environment;
	private ComboBox<GenericModel> industrySchoolType;
	private ComboBox<GenericModelWithRelation> industrySchoolSubType;
	private MyExtendedClasses.CostomTextArea industrySchoolDescription;
	private ComboBox<GenericModel> learningRoleType;
	private MyExtendedClasses.CostomTextArea learningRoleDescription;
	private ComboBox<GenericModel> instructorRoleType;
	private MyExtendedClasses.CostomTextArea instructorRoleDescription;
	private GenericGrid learningTopic;
	private GenericGrid pedagogicalParadigm;
	private GenericGrid learningGoals;
	private int contextId = -1;
	private List<GenericModel> currentContextList;
	private String contextName = "";

	public ContextForm(final MainEntryPointInterface entrypoint, List<GenericModel> currentContexts) {
		this.entrypoint = entrypoint;
		this.currentContextList = currentContexts;

		learningTopic = new GenericGrid(entrypoint, Constants.Strings.context_learningTopics(), null);
		pedagogicalParadigm = new GenericGrid(entrypoint, Constants.Strings.context_pedagogicalApproaches(), null);
		learningGoals = new GenericGrid(entrypoint, Constants.Strings.context_learningGoals(), null);

		setWidth((int) ((double) com.google.gwt.user.client.Window.getClientWidth() * Utilities.formFactor));
		setHeight((int) ((double) com.google.gwt.user.client.Window.getClientHeight() * Utilities.formFactor));
		setMinWidth(Utilities.formMinWidth);
		setLayout(new FillLayout(Style.Orientation.HORIZONTAL));
		setStyleName("form-font");
		setModal(true);
		setButtonAlign(Style.HorizontalAlignment.CENTER);
		setClosable(false);



		Button ok_button = new Button(Constants.Strings.b_ok(), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (!form.isValid()) {
					return;
				}
				okClose = true;
				hide();
			}
		});
		Button cancel_button = new Button(Constants.Strings.b_cancel(), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				hide();
			}
		});
		addButton(ok_button);
		addButton(cancel_button);

		form = new FormPanel();
		form.setEncoding(FormPanel.Encoding.MULTIPART);
		form.setMethod(FormPanel.Method.POST);
		form.setAction("ServletUpload");
		form.setHeaderVisible(false);
		FormLayout formLayout = new FormLayout(FormPanel.LabelAlign.LEFT);
		formLayout.setLabelWidth(Utilities.formLabelWidth);
		form.setLayout(formLayout);
		form.setWidth(Utilities.smallformWidth);
		form.setBodyBorder(false);
		form.setScrollMode(Style.Scroll.AUTO);

		FormData formData = new FormData("-30");
		AnchorData anchoreData = new AnchorData("100%", new Margins(0, 15, 10, 0));

		ListStore<GenericModel> store = new ListStore<GenericModel>();
		store.add(entrypoint.getDatabase().getContextTypesList());

		name = new TextField<String>();
		name.setFieldLabel(Constants.Strings.name());
		name.setAllowBlank(false);
		name.getFocusSupport().setPreviousId(getButtonBar().getId());
		name.setMaxLength(Utilities.varCharLimitNormal);
		name.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {
				for (GenericModel model : currentContextList) {
					if (model.getName().equals(value) && !model.getName().equals(contextName)) {
						return Constants.Strings.context_alertName();
					}
				}
				return null;
			}
		});
		form.add(name, formData);

		description = new MyExtendedClasses.CostomTextArea(Constants.Strings.description(), Constants.Strings.tt_context_description());
		form.add(description, formData);

		typeOfContext = new ComboBox<GenericModel>();
		typeOfContext.setForceSelection(true);
		typeOfContext.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
		typeOfContext.setFieldLabel(Constants.Strings.context_type());
		typeOfContext.setDisplayField(ModelTag.name.toString());
		typeOfContext.setTriggerAction(ComboBox.TriggerAction.ALL);
		typeOfContext.setStore(store);
		typeOfContext.addSelectionChangedListener(new SelectionChangedListener<GenericModel>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<GenericModel> se) {
				Utilities.otherSelectedOnComboBox(entrypoint, se);
			}
		});
		form.add(typeOfContext, formData);

		store = new ListStore<GenericModel>();
		store.add(entrypoint.getDatabase().getContextEnvironmentsList());

		environment = new ComboBox<GenericModel>();
		environment.setForceSelection(true);
		environment.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
		environment.setFieldLabel(Constants.Strings.context_environment());
		environment.setDisplayField(ModelTag.name.toString());
		environment.setTriggerAction(ComboBox.TriggerAction.ALL);
		environment.setStore(store);
		form.add(environment, formData);

		FieldSet fs1 = new FieldSet();
		fs1.setHeading(Constants.Strings.context_industrySchool());
		fs1.setCollapsible(true);
		FormLayout l1 = new FormLayout();
		l1.setLabelWidth(90);
		fs1.setLayout(l1);

		store = new ListStore<GenericModel>();
		store.add(entrypoint.getDatabase().getContextIndustrySchoolTypesList());

		industrySchoolType = new ComboBox<GenericModel>();
		industrySchoolType.setForceSelection(true);
		industrySchoolType.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
		industrySchoolType.setFieldLabel(Constants.Strings.type());
		industrySchoolType.setDisplayField(ModelTag.name.toString());
		industrySchoolType.setTriggerAction(ComboBox.TriggerAction.ALL);
		industrySchoolType.setStore(store);
		industrySchoolType.addSelectionChangedListener(new SelectionChangedListener<GenericModel>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<GenericModel> se) {
				industrySchoolTypeSelectionChange();
			}
		});
		fs1.add(industrySchoolType, new FormData("-5"));

		ListStore<GenericModelWithRelation> storeWithRelation = new ListStore<GenericModelWithRelation>();
		store.add(new ArrayList<GenericModelWithRelation>());

		industrySchoolSubType = new ComboBox<GenericModelWithRelation>();
		industrySchoolSubType.setForceSelection(true);
		industrySchoolSubType.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
		industrySchoolSubType.setFieldLabel(Constants.Strings.subtype());
		industrySchoolSubType.setDisplayField(ModelTag.name.toString());
		industrySchoolSubType.setTriggerAction(ComboBox.TriggerAction.ALL);
		industrySchoolSubType.setStore(storeWithRelation);
		industrySchoolSubType.addSelectionChangedListener(new SelectionChangedListener<GenericModelWithRelation>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<GenericModelWithRelation> se) {
				Utilities.otherSelectedOnComboBoxII(entrypoint, se);
			}
		});
		fs1.add(industrySchoolSubType, new FormData("-5"));

		industrySchoolDescription = new MyExtendedClasses.CostomTextArea(Constants.Strings.description(), null);
		fs1.add(industrySchoolDescription, new FormData("-5"));
		form.add(fs1, anchoreData);

		FieldSet fs2 = new FieldSet();
		fs2.setHeading(Constants.Strings.context_learnerRole());
		fs2.setToolTip(Constants.Strings.tt_context_learnerRole());
		fs2.setCollapsible(true);
		FormLayout l2 = new FormLayout();
		l2.setLabelWidth(90);
		fs2.setLayout(l2);

		store = new ListStore<GenericModel>();
		store.add(entrypoint.getDatabase().getContextLearnerRolesList());

		learningRoleType = new ComboBox<GenericModel>();
		learningRoleType.setForceSelection(true);
		learningRoleType.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
		learningRoleType.setFieldLabel(Constants.Strings.type());
		learningRoleType.setDisplayField(ModelTag.name.toString());
		learningRoleType.setTriggerAction(ComboBox.TriggerAction.ALL);
		learningRoleType.setStore(store);
		learningRoleType.addSelectionChangedListener(new SelectionChangedListener<GenericModel>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<GenericModel> se) {
				Utilities.otherSelectedOnComboBox(entrypoint, se);
			}
		});
		fs2.add(learningRoleType, new FormData("-5"));

		learningRoleDescription = new MyExtendedClasses.CostomTextArea(Constants.Strings.description(), null);
		fs2.add(learningRoleDescription, new FormData("-5"));
		form.add(fs2, anchoreData);

		FieldSet fs3 = new FieldSet();
		fs3.setHeading(Constants.Strings.context_teacherRole());
		fs3.setToolTip(Constants.Strings.tt_context_teacherRole());
		fs3.setCollapsible(true);
		FormLayout l3 = new FormLayout();
		l3.setLabelWidth(90);
		fs3.setLayout(l3);

		store = new ListStore<GenericModel>();
		store.add(entrypoint.getDatabase().getContextInstructorRolesList());

		instructorRoleType = new ComboBox<GenericModel>();
		instructorRoleType.setForceSelection(true);
		instructorRoleType.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
		instructorRoleType.setFieldLabel(Constants.Strings.type());
		instructorRoleType.setDisplayField(ModelTag.name.toString());
		instructorRoleType.setTriggerAction(ComboBox.TriggerAction.ALL);
		instructorRoleType.setStore(store);
		instructorRoleType.addSelectionChangedListener(new SelectionChangedListener<GenericModel>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<GenericModel> se) {
				Utilities.otherSelectedOnComboBox(entrypoint, se);
			}
		});
		fs3.add(instructorRoleType, new FormData("-5"));

		instructorRoleDescription = new MyExtendedClasses.CostomTextArea(Constants.Strings.description(), null);
		fs3.add(instructorRoleDescription, new FormData("-5"));
		form.add(fs3, anchoreData);

		learningTopic.SetGridHeaders(Constants.Strings.type(), ModelTag.name.toString(), Constants.Strings.subtype(), ModelTag.subType.toString());
		learningTopic.SetAddButtonSelectionListner(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final GenericGrid source = (GenericGrid) ((Button) ce.getSource()).getParent().getParent();
				GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getContextLearningTopicsList());
				form.setSubType(entrypoint.getDatabase().getContextLearningSubTopicsList());
				form.addListener(Events.Hide, new Listener<WindowEvent>() {
					@Override
					public void handleEvent(WindowEvent be) {
						GenericModelSelectionForm gsf = (GenericModelSelectionForm) be.getSource();
						if (gsf.isOkClose()) {
							source.addModel(gsf.getGenericModel());
						}
					}
				});
				form.show();
				form.setSubType(entrypoint.getDatabase().getContextLearningSubTopicsList());
			}
		});
		learningTopic.addToolBarButtonButton(new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final GenericGrid source = (GenericGrid) ((Button) ce.getSource()).getParent().getParent();
				GenericModel model = source.getSelection();
				if (model == null) {
					return;
				}
				GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getContextLearningTopicsList());
				form.setSubType(entrypoint.getDatabase().getContextLearningSubTopicsList());
				form.setModel(model);
				form.addListener(Events.Hide, new Listener<WindowEvent>() {
					@Override
					public void handleEvent(WindowEvent be) {
						GenericModelSelectionForm gsf = (GenericModelSelectionForm) be.getSource();
						if (gsf.isOkClose()) {
							source.removeSelectedElements();
							source.addModel(gsf.getGenericModel());
						}
					}
				});
				form.show();
				form.setSubType(entrypoint.getDatabase().getContextLearningSubTopicsList());
			}
		}));
		form.add(learningTopic, anchoreData);

		pedagogicalParadigm.SetGridHeaders(Constants.Strings.type(), ModelTag.name.toString(), Constants.Strings.notes(), ModelTag.description.toString());
		pedagogicalParadigm.SetAddButtonSelectionListner(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final GenericGrid source = (GenericGrid) ((Button) ce.getSource()).getParent().getParent();
				GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getContextPedagogicalParadigmList());
				form.addListener(Events.Hide, new Listener<WindowEvent>() {
					@Override
					public void handleEvent(WindowEvent be) {
						GenericModelSelectionForm gsf = (GenericModelSelectionForm) be.getSource();
						if (gsf.isOkClose()) {
							source.addModel(gsf.getGenericModel());
						}
					}
				});
				form.show();
			}
		});
		pedagogicalParadigm.addToolBarButtonButton(new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final GenericGrid source = (GenericGrid) ((Button) ce.getSource()).getParent().getParent();
				GenericModel model = source.getSelection();
				if (model == null) {
					return;
				}
				GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getContextPedagogicalParadigmList());
				form.setModel(model);
				form.addListener(Events.Hide, new Listener<WindowEvent>() {
					@Override
					public void handleEvent(WindowEvent be) {
						GenericModelSelectionForm gsf = (GenericModelSelectionForm) be.getSource();
						if (gsf.isOkClose()) {
							source.removeSelectedElements();
							source.addModel(gsf.getGenericModel());
						}
					}
				});
				form.show();
			}
		}));
		form.add(pedagogicalParadigm, anchoreData);

		learningGoals.SetGridHeaders(Constants.Strings.type(), ModelTag.name.toString(), Constants.Strings.subtype(), ModelTag.subType.toString());
		learningGoals.setHeaderToolTip(Constants.Strings.tt_context_learningGoals());
		learningGoals.SetAddButtonSelectionListner(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final GenericGrid source = (GenericGrid) ((Button) ce.getSource()).getParent().getParent();
				GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getContextLearningGoalsTypesList());
				form.setSubSubType(entrypoint.getDatabase().getContextSoftSkillList());
				form.setSubType(entrypoint.getDatabase().getContextLearningGoalsList());
				form.addListener(Events.Hide, new Listener<WindowEvent>() {
					@Override
					public void handleEvent(WindowEvent be) {
						GenericModelSelectionForm gsf = (GenericModelSelectionForm) be.getSource();
						if (gsf.isOkClose()) {
							source.addModel(gsf.getGenericModel());
						}
					}
				});
				form.show();
				form.setSubSubType(entrypoint.getDatabase().getContextSoftSkillList());
				form.setSubType(entrypoint.getDatabase().getContextLearningGoalsList());
			}
		});
		learningGoals.addToolBarButtonButton(new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final GenericGrid source = (GenericGrid) ((Button) ce.getSource()).getParent().getParent();
				GenericModel model = source.getSelection();
				if (model == null) {
					return;
				}
				GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getContextLearningGoalsTypesList());
				form.setSubSubType(entrypoint.getDatabase().getContextSoftSkillList());
				form.setSubType(entrypoint.getDatabase().getContextLearningGoalsList());
				form.addListener(Events.Hide, new Listener<WindowEvent>() {
					@Override
					public void handleEvent(WindowEvent be) {
						GenericModelSelectionForm gsf = (GenericModelSelectionForm) be.getSource();
						if (gsf.isOkClose()) {
							source.removeSelectedElements();
							source.addModel(gsf.getGenericModel());
						}
					}
				});
				form.show();
				form.setSubSubType(entrypoint.getDatabase().getContextSoftSkillList());
				form.setSubType(entrypoint.getDatabase().getContextLearningGoalsList());
				form.setModel(model);
			}
		}));
		form.add(learningGoals, anchoreData);

		add(form);
	}

	private void industrySchoolTypeSelectionChange() {
		if (industrySchoolSubType == null) {
			return;
		}
		List<GenericModelWithRelation> store = entrypoint.getDatabase().getRelationSubList(entrypoint.getDatabase().getContextIndustriesSchoolsList(), industrySchoolType.getValue().getId());
		industrySchoolSubType.getStore().removeAll();
		industrySchoolSubType.getStore().add(store);
	}

	/**
	 * @return the okClose
	 */
	@Override
	public boolean isOkClose() {
		return okClose;
	}

	public GenericModel getContextModel() {
		int typeID = -1;
		String type = "";
		int environmentID = -1;
		String environmentValue = "";
		int industrySchoolID = -1;
		int industrySchoolSubTypeID = -1;
		String industrySchoolDescriptionValue = "";
		int learnerRoleID = -1;
		String learnerRoleDescription = "";
		int instructorRoleID = -1;
		String instructorRoleDescriptionValue = "";

		if (typeOfContext.getValue() != null) {
			typeID = typeOfContext.getValue().getId();
			type = typeOfContext.getValue().getName();
		}
		if (environment.getValue() != null) {
			environmentID = environment.getValue().getId();
			environmentValue = environment.getValue().getName();
		}
		if (industrySchoolType.getValue() != null) {
			industrySchoolID = industrySchoolType.getValue().getId();
		}
		if (industrySchoolSubType.getValue() != null) {
			industrySchoolSubTypeID = industrySchoolSubType.getValue().getId();
		}
		if (industrySchoolDescription.getValue() != null) {
			industrySchoolDescriptionValue = industrySchoolDescription.getValue();
		}
		if (learningRoleType.getValue() != null) {
			learnerRoleID = learningRoleType.getValue().getId();
		}
		if (learningRoleDescription.getValue() != null) {
			learnerRoleDescription = learningRoleDescription.getValue();
		}
		if (instructorRoleType.getValue() != null) {
			instructorRoleID = instructorRoleType.getValue().getId();
		}
		if (instructorRoleDescription.getValue() != null) {
			instructorRoleDescriptionValue = instructorRoleDescription.getValue();
		}

		Context context = new Context(contextId, name.getValue(), description.getValue(), typeID, type, environmentID,
			environmentValue, industrySchoolID, industrySchoolSubTypeID,
			industrySchoolDescriptionValue, learnerRoleID, learnerRoleDescription, instructorRoleID,
			instructorRoleDescriptionValue, learningTopic.getData(), pedagogicalParadigm.getData(), learningGoals.getData());
		GenericModel retval = new GenericModel(contextId, name.getValue(), description.getValue());
		retval.set(ModelTag.context.toString(), context);
		retval.set(Context.tagType, type);
		return retval;
	}

	public void setContextModel(GenericModel model) {
		Context context = (Context) model.get("Context");
		contextId = context.getId();
		contextName = context.getName();
		name.setValue(context.getName());
		description.setValue(context.getDescription());
		Utilities.setComboBoxSelection(context, Context.tagTypeID, typeOfContext);
		Utilities.setComboBoxSelection(context, Context.tagEnvironmentID, environment);
		Utilities.setComboBoxSelection(context, Context.tagIndustrySchoolID, industrySchoolType);
		Utilities.setComboBoxSelectionII(context, Context.tagIndustrySchoolSubTypeID, industrySchoolSubType);
		industrySchoolDescription.setValue((String) context.get(Context.tagIndustrySchoolDescription));
		Utilities.setComboBoxSelection(context, Context.tagLearnerRoleID, learningRoleType);
		learningRoleDescription.setValue((String) context.get(Context.tagLearnerRoleDescription));
		Utilities.setComboBoxSelection(context, Context.tagInstructorRoleID, instructorRoleType);
		instructorRoleDescription.setValue((String) context.get(Context.tagInstructorRoleDescription));

		learningTopic.setData((List<GenericModel>) context.get(Context.tagLearningTopics));
		pedagogicalParadigm.setData((List<GenericModel>) context.get(Context.tagPedagogicalParadigm));
		learningGoals.setData((List<GenericModel>) context.get(Context.tagLearningGoals));

	}
}
