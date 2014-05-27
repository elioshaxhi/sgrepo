/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client;

import com.customlib.seriousgame.client.interfaces.GridBasicFuncionalities;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author NERTIL
 */
public class MyExtendedClasses {

	public static class CostomTextArea extends TextArea {

		public CostomTextArea(String fieldName, String tooltip) {
			super();
			setPreventScrollbars(false);
			setFieldLabel(fieldName);
			setMaxLength(Utilities.textLimitNormal);
			if (tooltip != null) {
				setToolTip(tooltip);
			}
		}
	}

	public static class NotesTextArea extends TextArea {

		public NotesTextArea() {
			super();
			setPreventScrollbars(false);
			setFieldLabel(Constants.Strings.notes());
			setMaxLength(Utilities.textLimitShort);

		}
	}

	public static class SeriouGameFormTabItem extends TabItem {

		public SeriouGameFormTabItem(String title, Widget widget) {
			super(title);
			setId(title);
			setLayout(new FillLayout());
			getHeader().setStyleName("alineaincentro");
			getHeader().setToolTip(title);
			setScrollMode(Style.Scroll.AUTO);
			add(widget);
		}
	}

	public static class CostomGrid<M extends ModelData> extends Grid {

		public CostomGrid(ListStore<M> store, ColumnModel cm, final CheckBoxSelectionModel<M> selectionModel, String autoExpandColumn, final GridBasicFuncionalities parent) {
			super(store, cm);
			if (selectionModel != null) {
				setSelectionModel(selectionModel);
				addPlugin(selectionModel);
			}
			setAutoExpandColumn(autoExpandColumn);
			setStyleAttribute("border", "none");
			getView().setAdjustForHScroll(false);
			getView().setAutoFill(true);
			setBorders(false);
			setStripeRows(true);
			addListener(Events.CellDoubleClick, new Listener<GridEvent<ModelData>>() {
				@Override
				public void handleEvent(GridEvent<ModelData> be) {
					parent.editElement();
				}
			});
			addListener(Events.OnKeyDown, new Listener<ComponentEvent>() {
				@Override
				public void handleEvent(ComponentEvent be) {
					if (be.getKeyCode() == KeyCodes.KEY_DELETE || be.getKeyCode() == KeyCodes.KEY_BACKSPACE) {
						parent.deleteElements();
					}
					if (be.getKeyCode() == 107)// key "+"
					{
						parent.newElement();
					}
					if (be.getKeyCode() == KeyCodes.KEY_ENTER && selectionModel != null) {
						if (selectionModel.getSelectedItems().size() > 0) {
							parent.editElement();
						} else {
							parent.newElement();
						}
					}
				}
			});
		}
	}

	public static class SeriouGameLabelField extends LabelField {

		public SeriouGameLabelField(String label) {
			super();
			setFieldLabel(label);
//    era attivo disativato per test			
			setStyleName("sg-form-field");

		}
	}
}
