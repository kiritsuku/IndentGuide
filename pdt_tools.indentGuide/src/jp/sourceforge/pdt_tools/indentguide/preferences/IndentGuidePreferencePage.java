package jp.sourceforge.pdt_tools.indentguide.preferences;

import jp.sourceforge.pdt_tools.indentguide.Activator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

public class IndentGuidePreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private Button enabled;
	private Composite attributes;
	private Spinner lineAlpha;
	private Combo lineStyle;
	private static final String[] styles = {
			Messages.IndentGuidePreferencePage_style_solid,
			Messages.IndentGuidePreferencePage_style_dash,
			Messages.IndentGuidePreferencePage_style_dot,
			Messages.IndentGuidePreferencePage_style_dash_dot,
			Messages.IndentGuidePreferencePage_style_dash_dot_dot };
	private Spinner lineWidth;
	private Spinner lineShift;
	private ColorFieldEditor colorFieldEditor;
	private Composite drawing;
	private Button drawLeftEnd;
	private Button drawBlankLine;
	private Button skipCommentBlock;
	private Composite target;
	private Tree contentTypesTree;
	IContentType textType;

	public IndentGuidePreferencePage() {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.IndentGuidePreferencePage_description);
		textType = Platform.getContentTypeManager().getContentType(
				IContentTypeManager.CT_TEXT);
	}

	public void init(final IWorkbench workbench) {
	}

	@Override
	protected Control createContents(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));

		enabled = new Button(composite, SWT.CHECK);
		enabled.setText(Messages.IndentGuidePreferencePage_enabled_label);
		enabled.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				super.widgetSelected(e);
				enableControls(enabled.getSelection());
			}
		});

		final Group group = new Group(composite, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		group.setLayout(new GridLayout(1, true));
		group.setText(Messages.IndentGuidePreferencePage_group_label);
		attributes = new Composite(group, SWT.NONE);
		final GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.horizontalIndent = 5;
		attributes.setLayoutData(gridData);
		attributes.setLayout(new GridLayout(2, false));

		new Label(attributes, SWT.NONE)
				.setText(Messages.IndentGuidePreferencePage_alpha_label);
		lineAlpha = new Spinner(attributes, SWT.BORDER);
		lineAlpha.setMinimum(0);
		lineAlpha.setMaximum(255);
		new Label(attributes, SWT.NONE)
				.setText(Messages.IndentGuidePreferencePage_style_label);
		lineStyle = new Combo(attributes, SWT.READ_ONLY);
		lineStyle.setItems(styles);
		new Label(attributes, SWT.NONE)
				.setText(Messages.IndentGuidePreferencePage_width_label);
		lineWidth = new Spinner(attributes, SWT.BORDER);
		lineWidth.setMinimum(1);
		lineWidth.setMaximum(8);
		new Label(attributes, SWT.NONE)
				.setText(Messages.IndentGuidePreferencePage_shift_label);
		lineShift = new Spinner(attributes, SWT.BORDER);
		lineShift.setMinimum(0);
		lineShift.setMaximum(8);
		colorFieldEditor = new ColorFieldEditor(PreferenceConstants.LINE_COLOR,
				Messages.IndentGuidePreferencePage_color_label, attributes);
		colorFieldEditor.setPreferenceStore(getPreferenceStore());

		final Group group2 = new Group(composite, SWT.NONE);
		group2.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		group2.setLayout(new GridLayout(1, true));
		group2.setText(Messages.IndentGuidePreferencePage_group2_label);
		drawing = new Composite(group2, SWT.NONE);
		drawing.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		drawing.setLayout(new GridLayout(1, false));

		drawLeftEnd = new Button(drawing, SWT.CHECK);
		drawLeftEnd
				.setText(Messages.IndentGuidePreferencePage_draw_left_end_label);
		drawBlankLine = new Button(drawing, SWT.CHECK);
		drawBlankLine
				.setText(Messages.IndentGuidePreferencePage_draw_blank_line_label);
		skipCommentBlock = new Button(drawing, SWT.CHECK);
		skipCommentBlock
				.setText(Messages.IndentGuidePreferencePage_skip_comment_block_label);

		final Group group3 = new Group(composite, SWT.NONE);
		group3.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		group3.setLayout(new GridLayout(1, true));
		group3.setText(Messages.IndentGuidePreferencePage_group3_label);
		target = new Composite(group3, SWT.NONE);
		target.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		target.setLayout(new GridLayout(1, false));

		contentTypesTree = new Tree(target, SWT.CHECK | SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL);
		final GridData gridData2 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData2.heightHint = 88;
		contentTypesTree.setLayoutData(gridData2);
		setupContentTypes((TreeItem) null, (IContentType) null);

		loadPreferences();

		return composite;
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		final IPreferenceStore store = getPreferenceStore();
		enabled.setSelection(store
				.getDefaultBoolean(PreferenceConstants.ENABLED));
		lineAlpha.setSelection(store
				.getDefaultInt(PreferenceConstants.LINE_ALPHA));
		int index = store.getDefaultInt(PreferenceConstants.LINE_STYLE) - 1;
		if (index < 0 || index >= styles.length) {
			index = 0;
		}
		lineStyle.setText(styles[index]);
		lineWidth.setSelection(store
				.getDefaultInt(PreferenceConstants.LINE_WIDTH));
		lineShift.setSelection(store
				.getDefaultInt(PreferenceConstants.LINE_SHIFT));
		colorFieldEditor.loadDefault();
		drawLeftEnd.setSelection(store
				.getDefaultBoolean(PreferenceConstants.DRAW_LEFT_END));
		drawBlankLine.setSelection(store
				.getDefaultBoolean(PreferenceConstants.DRAW_BLANK_LINE));
		skipCommentBlock.setSelection(store
				.getDefaultBoolean(PreferenceConstants.SKIP_COMMENT_BLOCK));
		enableControls(enabled.getSelection());
		for (final TreeItem item : contentTypesTree.getItems()) {
			checkItems(item, false);
		}
		final String type = store.getDefaultString(PreferenceConstants.CONTENT_TYPES);
		final String types[] = type.split("\\|");
		for (final TreeItem child : contentTypesTree.getItems()) {
			checkContentType(child, types);
		}
	}

	@Override
	public boolean performOk() {
		final IPreferenceStore store = getPreferenceStore();
		store.setValue(PreferenceConstants.ENABLED, enabled.getSelection());
		store.setValue(PreferenceConstants.LINE_ALPHA, lineAlpha.getSelection());
		store.setValue(PreferenceConstants.LINE_STYLE,
				lineStyle.getSelectionIndex() + 1);
		store.setValue(PreferenceConstants.LINE_WIDTH, lineWidth.getSelection());
		store.setValue(PreferenceConstants.LINE_SHIFT, lineShift.getSelection());
		colorFieldEditor.store();
		final RGB rgb = colorFieldEditor.getColorSelector().getColorValue();
		final Color color = new Color(PlatformUI.getWorkbench().getDisplay(), rgb);
		Activator.getDefault().setColor(color);
		store.setValue(PreferenceConstants.DRAW_LEFT_END,
				drawLeftEnd.getSelection());
		store.setValue(PreferenceConstants.DRAW_BLANK_LINE,
				drawBlankLine.getSelection());
		store.setValue(PreferenceConstants.SKIP_COMMENT_BLOCK,
				skipCommentBlock.getSelection());
		String types = "";
		for (final TreeItem item : contentTypesTree.getItems()) {
			types = getContentTypes(item, types);
		}
		store.setValue(PreferenceConstants.CONTENT_TYPES, types);
		return super.performOk();
	}

	private void loadPreferences() {
		final IPreferenceStore store = getPreferenceStore();
		enabled.setSelection(store.getBoolean(PreferenceConstants.ENABLED));
		lineAlpha.setSelection(store.getInt(PreferenceConstants.LINE_ALPHA));
		int index = store.getInt(PreferenceConstants.LINE_STYLE) - 1;
		if (index < 0 || index >= styles.length) {
			index = 0;
		}
		lineStyle.setText(styles[index]);
		lineWidth.setSelection(store.getInt(PreferenceConstants.LINE_WIDTH));
		lineShift.setSelection(store.getInt(PreferenceConstants.LINE_SHIFT));
		colorFieldEditor.load();
		drawLeftEnd.setSelection(store
				.getBoolean(PreferenceConstants.DRAW_LEFT_END));
		drawBlankLine.setSelection(store
				.getBoolean(PreferenceConstants.DRAW_BLANK_LINE));
		skipCommentBlock.setSelection(store
				.getBoolean(PreferenceConstants.SKIP_COMMENT_BLOCK));
		enableControls(enabled.getSelection());
		final String type = store.getString(PreferenceConstants.CONTENT_TYPES);
		final String types[] = type.split("\\|");
		for (final TreeItem child : contentTypesTree.getItems()) {
			checkContentType(child, types);
		}
	}

	private void enableControls(final boolean enabled) {
		for (final Control control : attributes.getChildren()) {
			control.setEnabled(enabled);
		}
		for (final Control control : drawing.getChildren()) {
			control.setEnabled(enabled);
		}
		for (final Control control : target.getChildren()) {
			control.setEnabled(enabled);
		}
	}

	private String getContentTypes(final TreeItem item, final String types) {
		String result = types;
		if (item.getChecked() && !item.getGrayed()) {
			if (!"".equals(types)) {
				result += "|";
			}
			result += item.getData();
		}
		for (final TreeItem child : item.getItems()) {
			result = getContentTypes(child, result);
		}
		return result;
	}

	private void checkContentType(final TreeItem item, final String[] types) {
		for (final TreeItem child : item.getItems()) {
			checkContentType(child, types);
		}
		final String id = (String) item.getData();
		for (final String type : types) {
			if (id.equals(type)) {
				item.setChecked(true);
				break;
			}
		}
	}

	private void checkItems(final TreeItem item, final boolean checked) {
		item.setChecked(checked);
		final TreeItem[] items = item.getItems();
		for (final TreeItem item2 : items) {
			checkItems(item2, checked);
		}
	}

	private void setupContentTypes(final TreeItem parent, final IContentType baseType) {
		final IContentTypeManager manager = Platform.getContentTypeManager();
		final IContentType[] contentTypes = manager.getAllContentTypes();
		for (final IContentType type : contentTypes) {
			if (equals(type.getBaseType(), baseType) && type.isKindOf(textType)) {
				TreeItem item = null;
				if (parent == null) {
					item = new TreeItem(contentTypesTree, SWT.NONE);
				} else {
					item = new TreeItem(parent, SWT.NONE);
				}
				item.setText(type.getName());
				item.setData(type.getId());
				setupContentTypes(item, type);
			}
		}
	}

	private boolean equals(final Object left, final Object right) {
		return left == null ? right == null : right != null && left
				.equals(right);
	}
}
