package jp.sourceforge.pdt_tools.indentguide.preferences;

import jp.sourceforge.pdt_tools.indentguide.Activator;

import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.ENABLED, true);
		store.setDefault(PreferenceConstants.LINE_ALPHA, 50);
		store.setDefault(PreferenceConstants.LINE_STYLE, SWT.LINE_SOLID);
		store.setDefault(PreferenceConstants.LINE_WIDTH, 1);
		store.setDefault(PreferenceConstants.LINE_SHIFT, 3);
		store.setDefault(PreferenceConstants.LINE_COLOR, "0,0,0"); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.DRAW_LEFT_END, true); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.DRAW_BLANK_LINE, false); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.SKIP_COMMENT_BLOCK, false); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.CONTENT_TYPES,
				IContentTypeManager.CT_TEXT);
	}

}
