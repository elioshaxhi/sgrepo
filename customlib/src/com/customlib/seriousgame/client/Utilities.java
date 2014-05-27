/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client;

import com.customlib.seriousgame.client.forms.GenericForm;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.GenericModelWithRelation;
import com.customlib.seriousgame.client.models.ModelTag;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.data.Model;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.regexp.shared.RegExp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
//import org.sg.client.forms.GenericForm;
//import org.sg.client.models.*;

/**
 *
 * @author NERTIL
 */
public class Utilities {

	public static int varCharLimitShort = 30;
	public static int varCharLimitNormal = 100;
	public static int varCharLimitLarge = 200;
	public static int textLimitShort = 500;
	public static int textLimitNormal = 5000;
	public static int textLimitLarge = 10000;
	public static double pageFactor = 0.9;
	public static double formFactor = 0.75;
	public static int formWidth = 650;
	public static int smallformWidth = 650;
	public static int formLabelWidth = 150;
	public static int formMinWidth = 200;
	public static String urlRegExp = "^((ftp|http|https)://[\\w@.\\-\\_]+\\.[a-zA-Z]{2,}(:\\d{1,5})?(/[\\w#!:.?+=&%@!\\_\\-/]+)*){1}$";
	public static final String fileNameseparator = "_$_";
	public static final String uploadedFolder = "../sguploadedfiles";
	public static final String otherString = "Other";
	private static RegExp urlValidator;
	private static RegExp urlPlusTldValidator;

	public static boolean isValidUrl(String url, boolean topLevelDomainRequired) {
		if (urlValidator == null || urlPlusTldValidator == null) {
			urlValidator = RegExp.compile("^((ftp|http|https)://[\\w@.\\-\\_]+(:\\d{1,5})?(/[\\w#!:.?+=&%@!\\_\\-/]+)*){1}$");
			urlPlusTldValidator = RegExp.compile(urlRegExp);
		}
		return (topLevelDomainRequired ? urlPlusTldValidator : urlValidator).exec(url) != null;
	}

	public static String replaceSpecialCharacter(String value) {
		return value.replaceAll("'", "''");
	}

	public static String getDateString(Date date) {
		if (date == null) {
//            MessageBox.info("Exceprion", "Date null.", null);
			return "";
		}
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		int day = date.getDate();
		int houre = date.getHours();
		int min = date.getMinutes();
		int sec = date.getSeconds();

		return year + "-" + month + "-" + day + " " + houre + ":" + min + ":" + sec;
	}

	public static GridCellRenderer<ModelData> getRenderCellWithTooltip() {
		return new GridCellRenderer<ModelData>() {
			@Override
			public String render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex,
				ListStore<ModelData> store, Grid<ModelData> grid) {
				String val = model.get(property);
				if (val == null) {
					return "<span qtip=''></span>";
				} else {
					return "<span qtip='" + val + "'>" + val + "</span>";
				}
			}
		};
	}

	public static List<Integer> getIDList(List<GenericModel> models) {
		List<Integer> retval = new ArrayList<Integer>();
		for (GenericModel model : models) {
			retval.add(model.getId());
		}
		return retval;
	}

	public static void otherSelectedOnComboBox(MainEntryPointInterface entrypoint, SelectionChangedEvent<GenericModel> se) {
		final ComboBox<GenericModel> source = (ComboBox<GenericModel>) se.getSource();
		final GenericModel model = se.getSelectedItem();
		if ((model.getName()).equals(Utilities.otherString)) {
			GenericForm gf = new GenericForm(entrypoint, Constants.Strings.other(), null);
			gf.addListener(Events.Hide, new Listener<WindowEvent>() {
				@Override
				public void handleEvent(WindowEvent be) {
					GenericForm form = (GenericForm) be.getSource();
					if (form.isOkClose()) {
						GenericModel other = new GenericModel(-1, form.getName(), form.getDescription());
						model.set(ModelTag.name.toString(), model.getName() + " (" + form.getName() + ")");
						model.set(ModelTag.other.toString(), other);
						source.getStore().update(model);
					}
				}
			});
			gf.show();
		}
	}

	public static void otherSelectedOnComboBoxII(MainEntryPointInterface entrypoint, SelectionChangedEvent<GenericModelWithRelation> se) {
		final ComboBox<GenericModelWithRelation> source = (ComboBox<GenericModelWithRelation>) se.getSource();
		final GenericModelWithRelation model = se.getSelectedItem();
		if ((model.getName()).equals(Utilities.otherString)) {
			GenericForm gf = new GenericForm(entrypoint, Constants.Strings.other(), null);
			gf.addListener(Events.Hide, new Listener<WindowEvent>() {
				@Override
				public void handleEvent(WindowEvent be) {
					GenericForm form = (GenericForm) be.getSource();
					if (form.isOkClose()) {
						GenericModel other = new GenericModel(-1, form.getName(), form.getDescription());
						model.set(ModelTag.name.toString(), model.getName() + " (" + form.getName() + ")");
						model.set(ModelTag.other.toString(), other);
						source.getStore().update(model);
					}
				}
			});
			gf.show();
		}
	}

	public static void setComboBoxSelection(Model model, String tag, ComboBox<GenericModel> combobox) {
		int id = ((Integer) model.get(tag)).intValue();
		for (GenericModel currentModel : combobox.getStore().getModels()) {
			if (id < 0) {
				break;
			}
			if (currentModel.getId().intValue() == id) {
				combobox.setValue(currentModel);
				break;
			}
		}
	}

	public static GenericModel setStringModel(Model model, String tag, List<GenericModel> source) {
		int id = ((Integer) model.get(tag)).intValue();
		GenericModel ret = null;
		for (GenericModel currentModel : source) {
			if (id < 0) {

				break;
			}
			if (currentModel.getId().intValue() == id) {
				ret = currentModel;
				break;
			}
		}
		return ret;
	}

	public static GenericModelWithRelation setStringModel2(Model model, String tag, List<GenericModelWithRelation> source) {
		int id = ((Integer) model.get(tag)).intValue();
		GenericModelWithRelation ret = null;
		for (GenericModelWithRelation currentModel : source) {
			if (id < 0) {

				break;
			}
			if (currentModel.getId().intValue() == id) {
				ret = currentModel;
				break;
			}
		}
		return ret;
	}

	public static void setComboBoxSelectionII(Model model, String tag, ComboBox<GenericModelWithRelation> combobox) {
		int id = ((Integer) model.get(tag)).intValue();
		for (GenericModelWithRelation currentModel : combobox.getStore().getModels()) {
			if (id < 0) {
				break;
			}
			if (currentModel.getId().intValue() == id) {
				combobox.setValue(currentModel);
				break;
			}
		}
	}

	public static void setComboBoxSelectionWhithOtherList(Model model, String tag, ComboBox<GenericModel> combobox, List<GenericModel> otherList, String tableName) {
		int id = ((Integer) model.get(tag)).intValue();
		for (GenericModel currentModel : combobox.getStore().getModels()) {
			if (id < 0) {
				break;
			}
			if (currentModel.getId().intValue() == id) {
				if (currentModel.getName().equals(Utilities.otherString)) {
					if (otherList != null) {
						for (GenericModel genericModel : otherList) {
							String relationTable = genericModel.get(DbTablesSpecialColums.relationTable.toString());
							if (relationTable.equals(tableName)) {
								currentModel.set(ModelTag.name.toString(), currentModel.getName() + " (" + genericModel.getName() + ")");
								currentModel.set(ModelTag.other.toString(), genericModel);
								break;
							}
						}
					}
				}
				combobox.setValue(currentModel);
				break;
			}
		}
	}

	public static String replaceStringTagInQuery(String query, String value, String tag) {
		if (value == null || value.length() == 0) {
			query = query.replaceAll(tag, "NULL");
		} else {
			query = query.replaceAll(tag, " '" + replaceSpecialCharacter(value) + "' ");
		}
		return query;
	}

	public static String replaceIntegerTagInQuery(String query, Integer value, String tag) {
		if (value == null) {
			query = query.replaceAll(tag, "NULL");
		} else {
			query = query.replaceAll(tag, String.valueOf(value));
		}
		return query;
	}

	public static String replaceIDTagInQuery(String query, Integer value, String tag) {
		if (value == null || value <= 0) {
			query = query.replaceAll(tag, "NULL");
		} else {
			query = query.replaceAll(tag, String.valueOf(value));
		}
		return query;
	}

	public static void updateOtherList(SeriousGame record, String table, ComboBox<GenericModel> combobox) {
		if (combobox.getValue().getName().startsWith(Utilities.otherString)) {
			GenericModel otherRecord = combobox.getValue().get(ModelTag.other.toString());
			List<GenericModel> otherList = record.get(SeriousGame.TagOthers);
			boolean hasThisOther = false;
			if (otherList != null) {
				for (GenericModel genericModel : otherList) {
					String tableName = genericModel.get(DbTablesSpecialColums.relationTable.toString());
					if (tableName.equals(table)) {
						genericModel.set(ModelTag.name.toString(), otherRecord.getName());
						genericModel.set(ModelTag.description.toString(), otherRecord.getDescription());
						hasThisOther = true;
						break;
					}
				}
			}

			if (!hasThisOther) {
				otherRecord.set(DbTablesSpecialColums.relationTable.toString(), table);
				otherList.add(otherRecord);
			}
		} else {
			List<GenericModel> otherList = record.get(SeriousGame.TagOthers);
			if (otherList != null) {
				for (GenericModel genericModel : otherList) {
					String tableName = genericModel.get(DbTablesSpecialColums.relationTable.toString());
					if (tableName.equals(table)) {
						otherList.remove(genericModel);
					}
				}
			}
		}
	}

	public static void updateOtherList(SeriousGame record, String table, List<GenericModel> list) {
		if (list != null) {
			for (GenericModel genericModel : list) {
				if (genericModel.getName().startsWith(Utilities.otherString)) {
					GenericModel otherRecord = genericModel.get(ModelTag.other.toString());
					List<GenericModel> otherList = record.get(SeriousGame.TagOthers);
					boolean hasThisOther = false;
					if (otherList != null) {
						for (GenericModel otherListModel : otherList) {
							String tableName = otherListModel.get(DbTablesSpecialColums.relationTable.toString());
							if (tableName.equals(table)) {
								otherListModel.set(ModelTag.name.toString(), otherRecord.getName());
								otherListModel.set(ModelTag.description.toString(), otherRecord.getDescription());
								hasThisOther = true;
								break;
							}
						}
					}

					if (!hasThisOther) {
						otherRecord.set(DbTablesSpecialColums.relationTable.toString(), table);
						otherList.add(otherRecord);
					}
				} else {
					List<GenericModel> otherList = record.get(SeriousGame.TagOthers);
					if (otherList != null) {
						for (GenericModel otherListModel : otherList) {
							String tableName = otherListModel.get(DbTablesSpecialColums.relationTable.toString());
							if (tableName.equals(table)) {
								otherList.remove(otherListModel);
							}
						}
					}
				}
			}
		}
	}

	public static void checkOtherList(List<GenericModel> selection, List<GenericModel> source, List<GenericModel> otherList, String tableName) {
		GenericModel otherModel = null;
		for (GenericModel currentModel : selection) {
			if (currentModel.getName().equals(Utilities.otherString)) {
				if (otherList != null) {
					for (GenericModel genericModel : otherList) {
						String relationTable = genericModel.get(DbTablesSpecialColums.relationTable.toString());
						if (relationTable.equals(tableName)) {
							currentModel.set(ModelTag.name.toString(), currentModel.getName() + " (" + genericModel.getName() + ")");
							currentModel.set(ModelTag.other.toString(), genericModel);
							otherModel = genericModel;
							break;
						}
					}
					if (otherModel != null) {
						break;
					}
				}
			}
		}
		if (otherModel != null) {
			for (int i = source.size() - 1; i >= 0; i--) {
				GenericModel currentModel = source.get(i);
				if (currentModel.getName().equals(Utilities.otherString)) {
					currentModel.set(ModelTag.name.toString(), currentModel.getName() + " (" + otherModel.getName() + ")");
					currentModel.set(ModelTag.other.toString(), otherModel);
					break;
				}
			}
		}
	}

	public static String getStringFromList(List<Integer> list) {
		String retval = "";
		if (list == null) {
			return retval;
		}
		for (Iterator<Integer> it = list.iterator(); it.hasNext();) {
			retval += it.next();
			if (it.hasNext()) {
				retval += ":";
			}
		}
		return retval;
	}

	public static List<Integer> getListFromString(String string) {
		List<Integer> retval = new ArrayList<Integer>();
		if (string == null || string.length() == 0) {
			return retval;
		}
		String[] words = string.split(":");
		for (String w : words) {
			retval.add(Integer.parseInt(w));
		}
		return retval;
	}

	public static String getModelValue(List<GenericModel> data, int modelId) {
		String retval = null;
		for (GenericModel model : data) {
			if (model.getId().intValue() == modelId) {
				retval = model.getName();
				break;
			}
		}
		return retval;
	}

	public static String getListValue(List<GenericModel> data) {
		String retval = "";
		for (GenericModel model : data) {
			if (retval.length() > 0) {
				retval += ", ";
			}
			retval += model.getName();
		}
		return retval;
	}

	public static boolean isImage(String filename) {
		boolean retval = false;
		if (filename.toLowerCase().endsWith("png")
			|| filename.toLowerCase().endsWith("jpg")
			|| filename.toLowerCase().endsWith("gif")) {
			retval = true;
		}
		return retval;
	}
}
